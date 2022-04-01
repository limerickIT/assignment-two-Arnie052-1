/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.StreamUtils;

import org.springframework.core.io.FileSystemResource;

/**
 *
 * @author arnie
 */
public class DownloadZipServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(DownloadZipServiceImpl.class);
    
    public static void compressFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] nextfile = fileToZip.listFiles();
            for (File newFile : nextfile) {
                compressFile(newFile, fileName + "/" + newFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fileInputStream = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fileInputStream.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fileInputStream.close();
    }
//    public static void downloadZipFile(HttpServletResponse response, List<String> listOfFileNames) {
//        response.setContentType("application/zip");
//        response.setHeader("Content-Disposition", "attachment; filename=beerImages.zip");
//        try (ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream())) {
//            for (String fileName : listOfFileNames) {
//                FileSystemResource fileSystemResource = new FileSystemResource(fileName);
//                ZipEntry zipEntry = new ZipEntry(fileSystemResource.getFilename());
//                zipEntry.setSize(fileSystemResource.contentLength());
//                zipEntry.setTime(System.currentTimeMillis());
//
//                zipOutputStream.putNextEntry(zipEntry);
//
//                StreamUtils.copy(fileSystemResource.getInputStream(), zipOutputStream);
//                zipOutputStream.closeEntry();
//            }
//            zipOutputStream.finish();
//        } catch (IOException e) {
//             logger.error(e.getMessage(), e);
//        }
//    }
}
