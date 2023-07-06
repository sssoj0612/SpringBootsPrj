package kopo.poly.Service.impl;

import kopo.poly.DTO.MailDTO;
import kopo.poly.Service.IMailService;
import kopo.poly.Util.CmmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;


@Slf4j
@RequiredArgsConstructor
@Service
public class MailService implements IMailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Override
    public int doSendMail(MailDTO pDTO) {
        log.info(this.getClass().getName() + ".doSendMail start!");

        int res = 1; // 메일 발송 성공:1 / 실패:0

        if (pDTO == null){ // 전달 받은 DTO로부터 데이터 가져오기
            // (DTO객체가 메모리에 올라가지않아 Null 발생될수있으므로 에러방지차원 if문 사용
            pDTO = new MailDTO();
        }

        String toMail = CmmUtil.nvl(pDTO.getToMail()); // 받는이
        String title = CmmUtil.nvl(pDTO.getTitle()); // 메일제목
        String contents = CmmUtil.nvl(pDTO.getContents()); // 메일내용

        log.info("toMail : " + toMail);
        log.info("title : " + title);
        log.info("contents : " + contents);


        // 메일 발송 메시지 구조(파일 첨부 가능)
        MimeMessage message = mailSender.createMimeMessage();

        // 메일 발송 메시지 구조를 쉽게 생성하게 도와주는 객체
        MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");

        try{
            messageHelper.setTo(toMail); // 수신자
            messageHelper.setFrom(fromMail); // 발신자
            messageHelper.setSubject(title); // 메일 제목
            messageHelper.setText(contents); // 메일 내용

            mailSender.send(message);

        } catch (Exception e){ // 모든 에러 다 잡기
            res = 0; // 메일 발송이 실패하기 때문에 0으로 변경
            log.info("[ERROR] " + this.getClass().getName() + ".doSendMail : " + e);
        }

        log.info(this.getClass().getName() + ".doSendMail end!");
        return res;
    }
}