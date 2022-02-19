package com.bootrestemailauth.userapi.helper;


import javax.mail.PasswordAuthentication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class VerifyMonumentHelper {
    

    private MimeBodyPart createTextPart(String text) throws MessagingException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(text);
        return messageBodyPart;
    }
 
    private MimeBodyPart createAttachmentPart(MultipartFile attachment,String name) throws MessagingException, IllegalStateException, IOException {
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        
        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+attachment.getOriginalFilename());
        attachment.transferTo(convFile);
        
        FileDataSource source = new FileDataSource(convFile);

        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(name);
        messageBodyPart.setDisposition(MimeBodyPart.ATTACHMENT);
        messageBodyPart.setHeader("Content-Type", attachment.getContentType());
        return messageBodyPart;
    }

    public boolean isEmailSent(String monument_name,String website, MultipartFile monumentImage,String monument_location, String admin_phone,MultipartFile monument_poa,String admin_aadhar){

        try {
            //gmail port setting
            String from = "shivam380.testing@gmail.com";
            String to = "shivam380.testing@gmail.com";
            String subject= "Document Verifiaction"+monument_name;
           

            String host = "smtp.gmail.com";

            Properties properties = System.getProperties();
        
            properties.put("mail.smtp.host", host);  //set up host as gmail.com
            properties.put("mail.smtp.port", "465"); // gmail port number
            properties.put("mail.smtp.ssl.enable", "true"); // security purposes
            properties.put("mail.smtp.auth", "true"); //for authentication

            // Step 1: Create session class object and override PasswordAuthentication method to set from

            Session session = Session.getInstance(properties, new Authenticator() {
            
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(from, "shivam380");
                }

            });

            session.setDebug(true);

            //Step 2 Use Mimemessage class to send mail
            MimeMessage m = new MimeMessage(session);
            m.setFrom(from);
            m.addRecipient(RecipientType.TO, new InternetAddress(to));
            m.setSubject(subject);

            MimeMultipart multipart = new MimeMultipart();
            m.setContent(multipart);

            String mssg = "Monument/Museum name : "+monument_name+"\n Website Link : "+website+"\nMonument/Museum Location : "+monument_location+"\nContact Details : "+admin_phone+"\nAdmin Aadhar : "+admin_aadhar;
            

            MimeBodyPart messageBodyPart1 = createTextPart(mssg);
            multipart.addBodyPart(messageBodyPart1);

            

            MimeBodyPart messaageBodyPart7 = createAttachmentPart(monumentImage,"Image");
            multipart.addBodyPart(messaageBodyPart7);

            MimeBodyPart messageBodyPart6 = createAttachmentPart(monument_poa,"POA");
            multipart.addBodyPart(messageBodyPart6);

            //Step 3: Transport mail
            Transport.send(m);  //message send karne ke liye
  
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
