package com.example.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @author dinghuang123@gmail.com
 * @since 2018/9/9
 */
public class ServerConnect {

    private static final int BUF_SIZE = 1024;
    private static final int PORT = 8080;
    private static final int TIMEOUT = 3000;

    public static void main(String[] args) {
        selector();
    }

    private static void handleAccept(SelectionKey key) throws IOException {
        //channel：返回为其创建此键的通道。 即使在取消密钥后, 此方法仍将继续返回通道。
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        //可选择的通道, 用于面向流的连接插槽。
        SocketChannel socketChannel = serverSocketChannel.accept();
        //设定为非阻塞
        socketChannel.configureBlocking(false);
        //接受客户端，并将它注册到选择器，并添加附件
        socketChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
        System.out.println("Accepted connection from " + socketChannel);
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        long bytesRead = socketChannel.read(byteBuffer);
        while (bytesRead > 0) {
            //设置缓冲区
            byteBuffer.flip();
            while (byteBuffer.hasRemaining()) {
                System.out.print((char) byteBuffer.get());
            }
            System.out.println();
            byteBuffer.clear();
            bytesRead = socketChannel.read(byteBuffer);
        }
        if (bytesRead == -1) {
            socketChannel.close();
        }
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        //attachment : 检索当前附件
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        SocketChannel socketChannel = (SocketChannel) key.channel();
        byteBuffer.flip();
        while (byteBuffer.hasRemaining()) {
            socketChannel.write(byteBuffer);
        }
        //压缩缓冲区
        byteBuffer.compact();
    }

    /**
     * Selector类可以用于避免使用阻塞式客户端中很浪费资源的“忙等”方法。例如，考虑一个IM服务器。像QQ或者旺旺这样的，
     * 可能有几万甚至几千万个客户端同时连接到了服务器，但在任何时刻都只是非常少量的消息。
     * 直到有一个或更多的信道准备好了I/O操作或等待超时。select()方法将返回可进行I/O操作的信道数量。现在，在一个单独的线程中，
     * 通过调用select()方法就能检查多个信道是否准备好进行I/O操作。如果经过一段时间后仍然没有信道准备好，select()方法就会返回0，并允许程序继续执行其他任务。
     */
    public static void selector() {
        Selector selector = null;
        ServerSocketChannel serverSocketChannel = null;
        try {
            selector = Selector.open();
            //需要创建一个Selector实例（使用静态工厂方法open()）
            serverSocketChannel = ServerSocketChannel.open();
            //使用选择器（Selector），并将其注册（register）到想要监控的信道上
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            //注意register()方法的第二个参数。这是一个“interest集合”，意思是在通过Selector监听Channel时对什么事件感兴趣。可以监听四种不同类型的事件：
            //1. Connect
            //2. Accept
            //3. Read
            //4. Write
            //通道触发了一个事件意思是该事件已经就绪。所以，某个channel成功连接到另一个服务器称为“连接就绪”。一个server socket channel准备好接收新进入的连接称为“接收就绪”。一个有数据可读的通道可以说是“读就绪”。等待写数据的通道可以说是“写就绪”。
            //这四种事件用SelectionKey的四个常量来表示：
            //1. SelectionKey.OP_CONNECT
            //2. SelectionKey.OP_ACCEPT
            //3. SelectionKey.OP_READ
            //4. SelectionKey.OP_WRITE
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                //调用选择器的select()方法。该方法会阻塞等待，阻塞将一直持续到下一个传入事件
//                selector.select();
                if (selector.select(TIMEOUT) == 0) {
                    System.out.println("无限期阻塞");
                    continue;
                } else if (selector.select(TIMEOUT) > 0) {
                    System.out.println("阻塞" + selector.select(TIMEOUT) + "毫秒");
                    continue;
                }
                //获取所有接收事件的SelectionKey实例
                //这个对象包含了一些你感兴趣的属性：
                //interest集合
                //ready集合
                //Channel
                //Selector
                //附加的对象（可选）
                Iterator<SelectionKey> selectionKeyIterator = selector.selectedKeys().iterator();
                while (selectionKeyIterator.hasNext()) {
                    SelectionKey key = selectionKeyIterator.next();
                    selectionKeyIterator.remove();
                    //测试此键的通道是否准备好接受新的Socket*连接。
                    if (key.isAcceptable()) {
                        System.out.println("通道准备好接受新的Socket*连接");
                        handleAccept(key);
                    }
                    //检查套接字是否已经准备好读数据
                    if (key.isReadable()) {
                        System.out.println("通道准备好读数据");
                        handleRead(key);
                    }
                    //检查套接字是否已经准备好写数据
                    if (key.isWritable() && key.isValid()) {
                        System.out.println("通道准备好写数据");
                        handleWrite(key);
                    }
                    if (key.isConnectable()) {
                        System.out.println("isConnectable = true");
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                if (serverSocketChannel != null) {
                    serverSocketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
