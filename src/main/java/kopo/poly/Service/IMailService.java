package kopo.poly.Service;

import kopo.poly.DTO.MailDTO;

public interface IMailService {
    int doSendMail(MailDTO pDTO); // 메일 발송
}
