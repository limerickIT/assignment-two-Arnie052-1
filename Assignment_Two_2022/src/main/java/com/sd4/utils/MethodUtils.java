/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sd4.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import javax.imageio.ImageIO;

/**
 *
 * @author arnie
 */
public class MethodUtils {

    private MethodUtils() {
    }
    public static byte[] generateImageQRCode(String text, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
            BufferedImage bi =MatrixToImageWriter.toBufferedImage(bitMatrix);      
            ByteArrayOutputStream bArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bi, "jpeg", bArrayOutputStream);            
            bArrayOutputStream.flush();
            byte [] QRInBytes = bArrayOutputStream.toByteArray();
            return QRInBytes;
            
        } catch (WriterException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
