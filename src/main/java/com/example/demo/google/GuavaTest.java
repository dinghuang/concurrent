package com.example.demo.google;


import com.google.common.base.Functions;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.primitives.Ints;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.google.common.base.Throwables.throwIfInstanceOf;
import static com.google.common.base.Throwables.throwIfUnchecked;

/**
 * google 的guava使用demo
 *
 * @author dinghuang123@gmail.com
 * @see <a href="https://google.github.io/guava/releases/18.0/api/docs/">https://google.github.io/guava/releases/18.0/api/docs/</a>
 * @since 2018/5/28
 */
public class GuavaTest {

    /**
     * Ordering是Guava类库提供的一个犀利强大的比较器工具，
     * Guava的Ordering和JDK Comparator相比功能更强。它非常容易扩展，
     * 可以轻松构造复杂的comparator，然后用在容器的比较、排序等操作中。
     */
    public void orderingTest() {
        List<String> list = Lists.newArrayList();
        list.add("peida");
        list.add("jerry");
        list.add("harry");
        list.add("eva");
        list.add("jhon");
        list.add("neron");
        //使用Comparable类型的自然顺序， 例如：整数从小到大，字符串是按字典顺序 java8  Comparator.naturalOrder()
        Ordering<String> naturalOrdering = Ordering.natural();
        Comparator<String> stringComparable = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return 0;
            }
        };
        //从现有比较器返回一个
        Ordering<String> fromOrdering = Ordering.from(stringComparable);
        //从当前2个值，第一个参数为最小值，返回他们之间的比较器
        Ordering<Object> explicit = Ordering.explicit(list.get(0), list.get(1));
        //不排序,不改变原值赋值拷贝
        Ordering.allEqual().nullsLast().sortedCopy(list);
        //使用toString()返回的字符串按字典顺序进行排序 java8 Comparator.comparing(Object::toString)
        Ordering<Object> usingToStringOrdering = Ordering.usingToString();
        //倒序 java8 Comparator.reversed()
        Ordering<Object> reverse = usingToStringOrdering.reverse();
        //返回将null视为小于所有其他值的排序，并使用它来比较非空值 java8  Comparator.nullsFirst(thisComparator)
        Ordering.allEqual().nullsFirst().sortedCopy(list);
        //返回将null视为大于所有其他值的顺序，并使用此顺序比较非空值 java8 Comparator.nullsLast(thisComparator)
        //Java 8 users: Use the lambda expression (a, b) -> 0 instead (in certain cases you may need to cast that to Comparator<YourType>)
        Ordering.allEqual().nullsLast().sortedCopy(list);
        //返回一个所有对象的任意顺序， 即compare(a, b) == 0 就是 a == b (identity equality)。
        // 本身的排序是没有任何含义， 但是在VM的生命周期是一个常量
        Ordering<Object> arbitraryOrdering = Ordering.arbitrary();
        //在F上返回一个新的排序顺序，首先对它们应用一个函数，然后用这个函数比较这些结果。
        //例如，要以字符串形式比较对象，以不区分大小写的方式使用 java8  Comparator.comparing(function, thisComparator)
        Ordering<Object> onResultOf = Ordering.from(String.CASE_INSENSITIVE_ORDER)
                .onResultOf(Functions.toStringFunction());
        //复合排序，第一个排序结束，可以用.compound()进行第二个排序,java8  Comparator.thenComparing(Comparator)
        Ordering<Object> compound = onResultOf.compound(arbitraryOrdering);
        //返回一个新的顺序，它通过两两比较相应的元素来排序迭代，直到找到一个非零的结果;强加“字典顺序”
        //如果达到一个迭代的结束，但不是另一个，则较短的迭代被认为小于较长的迭代。 java8  Comparators.lexicographical(Comparator)
        Ordering<Iterable<String>> o = Ordering.<String>natural().lexicographical();
        //根据此顺序返回指定值中的最小值。如果有多个最小值，则返回第一个值。 java8 Streams.stream(iterator).min(thisComparator).get()
        String min = naturalOrdering.min(list.iterator());
        String max = naturalOrdering.max(list.iterator());
        //按照此顺序返回给定迭代的k个最小元素，从最小到最大。如果存在少于k个元素，则将包括所有元素.
        // java8  Streams.stream(iterable).collect(Comparators.least(k, thisComparator)
        naturalOrdering.leastOf(list.iterator(), 5);
        //按照此顺序返回给定迭代的k个最大元素，从最小到最大。如果存在少于k个元素，则将包括所有元素.
        //java8  Streams.stream(iterable).collect(Comparators.greatest(k, thisComparator))
        naturalOrdering.greatestOf(list.iterator(), 5);
        //返回包含按此排序排序的元素的可变列表;只有当结果列表可能需要进一步修改时才使用它，
        // 或者可能包含null。输入未被修改。返回的列表是可序列化的并具有随机访问。
        //According to our benchmarking on Open JDK 7, immutableSortedCopy(java.lang.Iterable<E>) generally performs better
        // (in both time and space) than this method, and this method in turn generally performs better than copying
        // the list and calling Collections.sort(List).
        naturalOrdering.sortedCopy(list);
        //返回包含按此排序排序的元素的不可变列表。输入未被修改。
        //与Sets.newTreeSet（Iterable）不同，此方法不会根据比较器丢弃重复的元素。
        //执行的排序是稳定的，这意味着这些元素将按照它们出现在元素中的顺序出现在返回的列表中。
        naturalOrdering.immutableSortedCopy(list);
        //根据此顺序，如果每个元素在第一个元素之后的迭代中的每个元素大于或等于它之前的元素，则返回true。请注意，当迭代器少于两个元素时，总是如此。
        //java8  Comparators.isInOrder(Iterable, Comparator)
        Boolean a = naturalOrdering.isOrdered(list);
        //根据这个顺序，如果每个元素在第一个元素之后的迭代次数严格大于其之前的元素，则返回true。请注意，当迭代器少于两个元素时，总是如此。
        //java8  Comparators.isInStrictOrder(Iterable, Comparator)
        Boolean b = naturalOrdering.isStrictlyOrdered(list);
        //======================================================================
        System.out.println("naturalOrdering:" + naturalOrdering.sortedCopy(list));
        System.out.println("usingToStringOrdering:" + usingToStringOrdering.sortedCopy(list));
        System.out.println("arbitraryOrdering:" + arbitraryOrdering.sortedCopy(list));
        System.out.println("reverse:" + reverse.sortedCopy(list));
        System.out.println("onResultOf:" + onResultOf.sortedCopy(list));
        System.out.println("compound:" + compound.sortedCopy(list));

    }

    /**
     * 简化异常和错误的传播与检查
     * 有时候，你会想把捕获到的异常再次抛出。这种情况通常发生在Error或RuntimeException被捕获的时候，
     * 你没想捕获它们，但是声明捕获Throwable和Exception的时候，
     * 也包括了了Error或RuntimeException。Guava提供了若干方法，来判断异常类型并且重新传播异常
     */
    public void throwables() throws Exception {
        try {
        } catch (Throwable t) {
            Throwables.throwIfInstanceOf(t, IOException.class);
            Throwables.throwIfInstanceOf(t, SQLException.class);
            //返回throwable的最内层原因。链中的第一个throwable提供了最初检测到错误或异常时的上下文。用法示例：
            "Unable to assign a customer id".equals(Throwables.getRootCause(t).getMessage());
            //获取Throwable cause链作为列表。列表中的第一个条目将被抛出，然后是其原因层次结构。
            // 请注意，这是原因链的快照，并不会反映对原因链的任何后续更改。
            Iterables.filter(Throwables.getCausalChain(t), IOException.class);
            //getCauseAs 返回throwable的原因，转换为expectedCauseType。
            //返回一个包含toString（）结果的字符串，然后是throwable的完整递归堆栈跟踪
            // 。请注意，你可能不应该解析结果字符串;如果您需要编程访问堆栈帧，则可以调用Throwable.getStackTrace（）。
            t.getStackTrace();
        }


    }


    private class User implements Comparable<User> {

        private String name;
        private Integer age;

        private User() {
        }

        private User(String name, Integer age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        /**
         * guava比较器
         *
         * @param other other
         * @return int
         */
        @Override
        public int compareTo(User other) {
            return ComparisonChain.start()
                    .compare(name, other.name)
                    //natural()	对可排序类型做自然排序，如数字按大小，日期按先后排序
                    //usingToString()	按对象的字符串形式做字典排序[lexicographical ordering]
                    //from(Comparator)	把给定的Comparator转化为排序器
                    //实现自定义的排序器时，除了用上面的from方法，也可以跳过实现Comparator，而直接继承Ordering
                    //地址：https://google.github.io/guava/releases/snapshot-jre/api/docs/
                    .compare(age, other.age, Ordering.natural().nullsLast())
                    .result();
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .addValue(name)
                    .addValue(age)
                    .toString();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(name, age);
        }
    }
}
