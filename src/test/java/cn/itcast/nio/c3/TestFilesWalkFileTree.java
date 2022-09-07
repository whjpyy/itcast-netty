package cn.itcast.nio.c3;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class TestFilesWalkFileTree {
    public static void main(String[] args) throws IOException {
        count();
        delete();
    }

    private static void delete() throws IOException {
        Files.walkFileTree(Paths.get("F:\\Work\\Apache\\apache-activemq-5.14.4bak"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
        System.out.println("删除F:\\Work\\Apache\\apache-activemq-5.14.4bak完成！");
    }

    private static void count() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        AtomicInteger txtCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("F:\\Work\\Java\\jdk1.8.0_77"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".txt")) {
                    System.out.println(file);
                    txtCount.incrementAndGet();
                }
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("dirCount:" + dirCount.get());
        System.out.println("fileCount:" + fileCount.get());
        System.out.println("txtCount:" + txtCount.get());
    }

}
