package com.bootrestemailauth.userapi.helper;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {

    //static path
    //private String uploadDir = "C:\\Users\\shiva\\SpringBoot-VSCode\\bootrestbook\\src\\main\\resources\\static\\image";
    
    


    //Dynamic file path
    //private String uploadDir = new ClassPathResource("/static/image/").getFile().getAbsolutePath();
    //If dynamic file path throws any exception constructor will return it.
    public FileUploadHelper() throws IOException{

    }

    public boolean isFileUploaded(MultipartFile multipartFile, String email, String role){
        boolean isUploaded=false;
        //String uploadDir = "E:\\SIH\\SIHBackend\\src\\main\\resources\\static\\image" + File.separator + role;
        String uploadDir = "C:\\Users\\shiva\\SpringBoot-VSCode\\SIHBackend\\src\\main\\resources\\static\\image" + File.separator + role;
        try{
            String ext = multipartFile.getOriginalFilename();
                int i = 0;
                for(; i < ext.length(); i++){
                    if(ext.charAt(i) == '.'){
                        break;
                    }
                }
                // System.out.println(i);
                ext = ext.substring(i+1);
                // System.out.println(ext);
            Files.copy(multipartFile.getInputStream(),Paths.get(uploadDir+File.separator+email+"."+ext),StandardCopyOption.REPLACE_EXISTING);
            isUploaded = true;
        }catch(Exception e){
            e.printStackTrace();
        }
        return isUploaded;
    }

    public boolean isMonumentFileUploaded(MultipartFile multipartFile,String monument_name,String file_name) throws IOException{
        boolean isUploaded=false;
        // String uploadDir = "C:\\Users\\shiva\\SpringBoot-VSCode\\SIHBackend\\src\\main\\resources\\static\\image\\monument";
        String uploadDir = new ClassPathResource("/static/image/monument/").getFile().getAbsolutePath();
        try{
            String ext = multipartFile.getOriginalFilename();
                int i = 0;
                for(; i < ext.length(); i++){
                    if(ext.charAt(i) == '.'){
                        break;
                    }
                }
                ext = ext.substring(i+1);
            Files.copy(multipartFile.getInputStream(),Paths.get(uploadDir+File.separator+monument_name+"_"+file_name+"."+ext),StandardCopyOption.REPLACE_EXISTING);
            isUploaded = true;
        }catch(Exception e){
            e.printStackTrace();
            isUploaded = false;
        }
        return isUploaded;
    }
    
}