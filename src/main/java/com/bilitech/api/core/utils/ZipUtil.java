package com.bilitech.api.core.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ZipUtil {

    /**
     * 将zip文件解压缩到指定目录，并返回解压后的 index.html 文件路径
     * @param zipFilePath zip文件路径
     * @param destPath 解压缩目标路径
     * @return 解压后的 index.html 文件路径，如果不存在则返回 null
     * @throws IOException
     */
    public static String unzipAndGetIndexPath(String zipFilePath, String destPath) throws IOException {
        Path destDir = Paths.get(destPath);
        // 如果目标目录不存在，则创建它
        if (!Files.exists(destDir)) {
            Files.createDirectories(destDir);
        }

        String indexPath = null;

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
                    if (Files.exists(path)) {
                        continue;
                    }
                    try (InputStream inputStream = zipFile.getInputStream(entry)) {
                        Files.copy(inputStream, path);
                    }
                    // 如果 entryName 包含 index.html，则将它赋值给 indexPath
                    if (entryName.contains("index.html")) {
                        indexPath = path.toString();
                    }
                }
            }
        }

        return indexPath;
    }
}
