package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableOAuth2Client
@RestController
@EnableAuthorizationServer
public class ConcurrentApplication extends WebSecurityConfigurerAdapter {

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConcurrentApplication.class, args);
    }

    /**
     * Spring Boot WebSecurityConfigurer对携带@EnableOAuth2Sso注释的类附加了特殊含义：
     * 它使用它来配置携带OAuth2身份验证处理器的安全筛选器链。
     * 所以我们所需要做的就是显式authorizeRequests()地将主页和它所包含的静态资源（我们还包括对处理认证的登录端点的访问）进行显式的显示。
     * 所有其他请求（例如到/user端点）都需要认证。
     * <p>
     * 随着这种变化，应用程序已经完成，如果你运行它并访问主页，
     * 你应该看到一个很好的HTML链接，用来“登录Facebook”。该链接不会将您直接转到Facebook，
     * 而是转到处理认证的本地路径（并将重定向发送到Facebook）。通过身份验证后，您将重定向回本地应用程序，
     * 并在该应用程序中显示您的姓名（假设您已在Facebook中设置了允许访问该数据的权限）。
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //所有请求都被默认保护
                .antMatcher("/**")
                .authorizeRequests()
                //主页和登录端点被明确排除
                .antMatchers("/", "/login**", "/webjars/**").permitAll()
                //所有其他端点都需要经过身份验证的用户
                .anyRequest().authenticated()
                .and().exceptionHandling()
                //未经身份验证的用户将被重定向到主页
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/"))
                //添加注销端点
                .and().logout().logoutSuccessUrl("/").permitAll()
                /*
                许多JavaScript框架都支持CSRF（例如，在Angular中称它为XSRF），
                但它通常以与Spring Security的即装即用行为稍有不同的方式实现。
                例如，在Angular中，前端希望服务器向它发送一个名为“XSRF-TOKEN”的cookie，
                如果它看到了，它会将该值作为名为“X-XSRF-TOKEN”的标题发回。
                我们可以使用我们简单的jQuery客户端实现相同的行为，然后服务器端更改将与其他前端实现协同工作，
                而无需或仅有很少的更改。为了向Spring Security讲授这一点，我们需要添加一个创建cookie的过滤器，
                同时我们还需要告诉现有的CRSF过滤器关于标题名称
                 */
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //注入OAuth2ClientContext并使用它来构建我们添加到安全配置中的认证过滤器
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();
        //facebook认证
//        filters.add(ssoFilter(facebook(), "/login/facebook"));
        //github认证
        filters.add(ssoFilter(github(), "/login/github"));
        filter.setFilters(filters);
        return filter;
    }

    private Filter ssoFilter(ClientResources client, String path) {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(path);
        OAuth2RestTemplate template = new OAuth2RestTemplate(client.getClient(), oauth2ClientContext);
        filter.setRestTemplate(template);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
                client.getResource().getUserInfoUri(), client.getClient().getClientId());
        tokenServices.setRestTemplate(template);
        filter.setTokenServices(tokenServices);
        return filter;
    }

    /**
     * 处理重定向
     * 我们需要做的最后一项改变是明确支持从我们的应用程序到Facebook的重定向。
     * 这是在Spring OAuth2中用servlet处理的，Filter因为我们使用了，
     * 所以过滤器在应用程序上下文中已经可用@EnableOAuth2Client。所有需要的是连接过滤器，
     * 以便在Spring Boot应用程序中按照正确的顺序调用过滤器。要做到这一点，我们需要一个FilterRegistrationBean：
     *
     * @param filter
     * @return
     */
    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties("github")
    public ClientResources github() {
        return new ClientResources();
    }

    @Bean
    @ConfigurationProperties("github.resource")
    public ResourceServerProperties githubResource() {
        return new ResourceServerProperties();
    }

    class ClientResources {

        @NestedConfigurationProperty
        private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();

        @NestedConfigurationProperty
        private ResourceServerProperties resource = new ResourceServerProperties();

        AuthorizationCodeResourceDetails getClient() {
            return client;
        }

        ResourceServerProperties getResource() {
            return resource;
        }
    }

//    @Bean
//    @ConfigurationProperties("facebook")
//    public ClientResources facebook() {
//        return new ClientResources();
//    }
//
//    @Bean
//    @ConfigurationProperties("facebook.resource")
//    public ResourceServerProperties facebookResource() {
//        return new ResourceServerProperties();
//    }
}
