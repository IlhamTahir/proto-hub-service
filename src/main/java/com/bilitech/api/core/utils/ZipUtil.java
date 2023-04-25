package com.bilitech.api.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ZipUtil {

    /**
     * 将zip文件解压缩到指定目录
     * @param zipFilePath zip文件路径
     * @param destPath 解压缩目标路径
     * @throws IOException
     */
    public static void unzip(String zipFilePath, String destPath) throws IOException {
        Path destDir = Paths.get(destPath);
        // 如果目标目录不存在，则创建它
        if (!Files.exists(destDir)) {
            Files.createDirectories(destDir);
        }
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (!entry.isDirectory()) {
                    Path path = destDir.resolve(entryName);
                    // 如果父目录不存在，则创建它
                    if (!Files.exists(path.getParent())) {
                        Files.createDirectories(path.getParent());
                    }
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        Files.copy(inputStream, path);
                    }
                }
            }
        }
    }

    // 测试
    public static void main(String[] args) throws IOException {
        String zipFilePath = "/var/workspace/data/管理系统.zip";
        String destPath = "/var/workspace/data/demo";
        unzip(zipFilePath, destPath);
    }
}
