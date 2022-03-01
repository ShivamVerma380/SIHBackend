package com.bootrestemailauth.userapi.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class QRUploadHelper {

    public static void generateQRcode(String data, String path, String charset, Map map, int h, int w) throws WriterException, IOException  
    {  
        //the BitMatrix class represents the 2D matrix of bits  
        //MultiFormatWriter is a factory class that finds the appropriate Writer subclass for the BarcodeFormat requested and encodes the barcode with the supplied contents.  
        BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes(charset), charset), BarcodeFormat.QR_CODE, w, h);  
        MatrixToImageWriter.writeToFile(matrix, path.substring(path.lastIndexOf('.') + 1), new File(path));  
    }  
    public boolean isQRUploaded(String msg,int monument_id,int user_id,Date date_of_visit) throws IOException{
        
        String uploadDir = Paths.get("/home/ec2-user/SIHBackend/src/main/resources/static/Qr_code/default.jpg").toAbsolutePath().toString();
        try{
            // MultipartFile multipartFile = new MockMultipartFile("default.jpg", new FileInputStream(new File("E:/SIH/SIHBackend/src/main/resources/static/Qr_code/default.jpg")));
            // Path targetDir = Paths.get("E:/SIH/SIHBackend/src/main/resources/static/image/QRcode/"); 
            // MultipartFile multipartFile = new MockMultipartFile("default.jpg", new FileInputStream(new File("src/main/resources/static/Qr_code/default.jpg")));
            // Path targetDir = Paths.get("src/main/resources/static/image/QRcode/"); 
            // MultipartFile multipartFile = new MockMultipartFile("default.jpg", new FileInputStream(new File(Paths.get("/home/ec2-user/SIHBackend/src/main/resources/static/Qr_code/default.jpg").toAbsolutePath().toString())));
            // Path targetDir = Paths.get("src/main/resources/static/image/QRcode/"); 
            // Path target = targetDir.resolve(monument_id+""+user_id+""+date_of_visit+".jpg");// create new path ending with `name` content
            // System.out.println("copying into " + target);
            // Files.copy(multipartFile.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            // //uploadDir = "E:\\SIH\\SIHBackend\\src\\main\\resources\\static\\image\\QRcode\\"+monument_id+""+user_id+""+date_of_visit+".jpg";
            // uploadDir = Paths.get("/home/ec2-user/SIHBackend/src/main/resources/static/image/QRcode/"+monument_id+""+user_id+""+date_of_visit+".jpg").toAbsolutePath().toString();
            
            //Encoding charset to be used  
            String charset = "UTF-8";  
            Map<EncodeHintType, ErrorCorrectionLevel> hashMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();  
            //generates QR code with Low level(L) error correction capability  
            hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);  
            //invoking the user-defined method that creates the QR code  
            generateQRcode(msg, uploadDir, charset, hashMap, 200, 200);//increase or decrease height and width accodingly   
            //prints if the QR code is generated   
            // Path source = Paths.get("D:/SIH/SIHBackend/src/main/resources/static/Qr_code/default.jpg"); //original file
            System.out.println("QR Code created successfully.");  

            return true;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
