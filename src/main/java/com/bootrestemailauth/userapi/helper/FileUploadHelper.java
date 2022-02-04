package com.bootrestemailauth.userapi.helper;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {

    //static path
    //private String uploadDir = "C:\\Users\\shiva\\SpringBoot-VSCode\\bootrestbook\\src\\main\\resources\\static\\image";
    
    private String uploadDir = "C:\\Users\\shiva\\SpringBoot-VSCode\\bootrestemailauthentication\\userapi\\src\\main\\resources\\static\\image";


    //Dynamic file path
    //private String uploadDir = new ClassPathResource("/static/image/").getFile().getAbsolutePath();
    //If dynamic file path throws any exception constructor will return it.
    public FileUploadHelper() throws IOException{

    }

    public boolean isFileUploaded(MultipartFile multipartFile){
        boolean isUploaded=false;
        try{
            Files.copy(multipartFile.getInputStream(),Paths.get(uploadDir+File.separator+multipartFile.getOriginalFilename()),StandardCopyOption.REPLACE_EXISTING);
            isUploaded = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return isUploaded;
    }
    
}