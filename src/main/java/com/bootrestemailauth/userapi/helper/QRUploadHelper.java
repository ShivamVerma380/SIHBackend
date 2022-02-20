package com.bootrestemailauth.userapi.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
 
import javax.imageio.ImageIO;
@Component
public class QRUploadHelper {

    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException  
    {  
        //the BitMatrix class represents the 2D matrix of bits  
        //MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.  
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);  
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));  
    }  
    public boolean isQRUploaded(String msg) throws IOException{
        String img = "E:\\SIH\\SIHBackend\\src\\main\\resources\\static\\Qr_code\\default.jpg";
        // String uploadDir = new ClassPathResource("/static/Qr_code/image.jpg").getFile().getAbsolutePath();

        
        try{
            // int width = 250;
            // int height = 250;
            // BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
 
            // // Create a graphics which can be used to draw into the buffered image
            // Graphics2D g2d = bufferedImage.createGraphics();
    
            // // fill all the image with white
            // g2d.setColor(Color.white);
            // g2d.fillRect(0, 0, width, height);
            // g2d.dispose();
     
            // // Save as JPEG
            // File file = new File("test.jpg");
            // ImageIO.write(bufferedImage, "jpg", file);
                // Path source = Paths.get("E:\\SIH\\SIHBackend\\src\\main\\resources\\static\\Qr_code\\default.jpg"); //original file
                // Path targetDir = Paths.get("E:\\SIH\\SIHBackend\\src\\main\\resources\\static\\QRcode"); 
                // Path target = targetDir.resolve("test.jpg");// create new path ending with `name` content
                // System.out.println("copying into " + target);
                // Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);

        }catch(Exception e){
            e.printStackTrace();
        }
        String uploadDir = "E:\\SIH\\SIHBackend\\src\\main\\resources\\static\\Qr_code\\";
        try{
            //Encoding charset to be used  
            String charset = "UTF-8";  
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();  
            //generates QR code with Low level(L) error correction capability  
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  
            //invoking the user-defined method that creates the QR code  
            generateQRcode(msg, uploadDir, charset, hashMap, 200, 200);//increase or decrease height and width accodingly   
            //prints if the QR code is generated   
            System.out.println("QR Code created successfully.");  

            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
