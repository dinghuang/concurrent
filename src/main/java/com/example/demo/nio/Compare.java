package com.example.demo.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO的强大功能部分来自于Channel的非阻塞特性，套接字的某些操作可能会无限期地阻塞
 *
 * @author dinghuang123@gmail.com
 * @since 2018/9/9
 */
public class Compare {

    /**
     * 传统io
     */
    public static void oldIO() {
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream("src/main/resources/static/app.js"));
            byte[] buf = new byte[1024];
            int reader = inputStream.read(buf);
            while (reader != -1) {
                for (int i = 0; i < reader; i++) {
                    System.out.print((char) buf[i]);
                    reader = inputStream.read(buf);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对应的NIO（这里通过RandomAccessFile进行操作，当然也可以通过FileInputStream.getChannel()进行操作）
     */
    public static void newIO() {
        RandomAccessFile accessFile = null;
        try {
            accessFile = new RandomAccessFile("src/main/resources/static/app.js", "rw");
            FileChannel fileChannel = accessFile.getChannel();
            //分配空间（ByteBuffer buf = ByteBuffer.allocate(1024); 还有一种allocateDirector后面再陈述）
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            //写入数据到Buffer
            int byteRead = fileChannel.read(byteBuffer);
            System.out.println(byteRead);
            while (byteRead != -1) {
                //调用filp()方法
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    //从Buffer中读取数据
                    System.out.print((char) byteBuffer.get());
                }
                //调用clear()方法或者compact()方法,compact()方法将所有未读的数据拷贝到Buffer起始处
                byteBuffer.compact();
                byteRead = fileChannel.read(byteBuffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (accessFile != null) {
                    accessFile.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 这里通过采用ByteBuffer和MappedByteBuffer分别读取大小约为5M的文件”src/1.ppt”来比较两者之间的区别，
     * method3()是采用MappedByteBuffer读取的，method4()对应的是ByteBuffer。
     */
    public static void method4() {
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try {
            aFile = new RandomAccessFile("src/main/resources/static/app.js", "rw");
            fc = aFile.getChannel();

            long timeBegin = System.currentTimeMillis();
            ByteBuffer buff = ByteBuffer.allocate((int) aFile.length());
            buff.clear();
            fc.read(buff);
            //System.out.println((char)buff.get((int)(aFile.length()/2-1)));
            //System.out.println((char)buff.get((int)(aFile.length()/2)));
            //System.out.println((char)buff.get((int)(aFile.length()/2)+1));
            long timeEnd = System.currentTimeMillis();
            System.out.println("Read time: " + (timeEnd - timeBegin) + "ms");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void method3() {
        RandomAccessFile aFile = null;
        FileChannel fc = null;
        try {
            aFile = new RandomAccessFile("src/main/resources/static/app.js", "rw");
            fc = aFile.getChannel();
            long timeBegin = System.currentTimeMillis();
            //JAVA处理大文件，一般用BufferedReader,BufferedInputStream这类带缓冲的IO类，不过如果文件超大的话，更快的方式是采用MappedByteBuffer。
            MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, aFile.length());
            System.out.println((char) mbb.get((int) (aFile.length() / 2 - 1)));
            System.out.println((char) mbb.get((int) (aFile.length() / 2)));
            System.out.println((char) mbb.get((int) (aFile.length() / 2) + 1));
            long timeEnd = System.currentTimeMillis();
            System.out.println("Read time: " + (timeEnd - timeBegin) + "ms");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (aFile != null) {
                    aFile.close();
                }
                if (fc != null) {
                    fc.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
