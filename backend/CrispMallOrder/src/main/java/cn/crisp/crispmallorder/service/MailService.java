package cn.crisp.crispmallorder.service;


public interface MailService {
    //文本邮件
    void sendSimpleMail(String to, String subject, String content);
}
