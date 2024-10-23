package edu.icet.crm.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSending {
    private JavaMailSender javaMailSender;

    public void sendPreOrderEmail(String sender,String title){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(sender);
        simpleMailMessage.setSubject("Pre Order Notice");
        simpleMailMessage.setText("You have pre ordered "+title+". You have 48 hours to physically come to the library to borrow the book.");

        javaMailSender.send(simpleMailMessage);
    }

}
