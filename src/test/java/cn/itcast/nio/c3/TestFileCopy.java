package cn.itcast.nio.c3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestFileCopy {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();

        String source = "F:\\Work\\Apache\\apache-activemq-5.14.4";
        String target = "F:\\Work\\Apache\\apache-activemq-5.14.4bak";

        Files.walk(Paths.get(source)).forEach(path -> {
            try {
                String targetName = path.toString().replace(source, target);
                // 是目录
                if (Files.isDirectory(path)) {
                    Files.createDirectory(Paths.get(targetName));
                }
                // 是文件
                else if (Files.isRegularFile(path)) {
                    Files.copy(path, Paths.get(targetName));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        long end = System.currentTimeMillis();
        System.out.println(end - start);
    }
}
