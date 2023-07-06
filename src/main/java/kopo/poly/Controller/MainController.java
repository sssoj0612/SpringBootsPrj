package kopo.poly.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {
    @GetMapping("/main")
    public String main() throws Exception {
        log.info(this.getClass().getName() + ".main 페이지 보여주는 함수 실행");
        return "/main";
    }

    @GetMapping("/siso/booking")
    public String booking() throws Exception {
        log.info(this.getClass().getName() + ".booking 페이지 보여주는 함수 실행");
        return "siso/booking";
    }

    @GetMapping("/siso/local")
    public String local() throws Exception {
        log.info(this.getClass().getName() + ".local 페이지 보여주는 함수 실행");
        return "siso/local";
    }

    @GetMapping("/siso/foreign")
    public String foreign() throws Exception {
        log.info(this.getClass().getName() + ".foreign 페이지 보여주는 함수 실행");
        return "siso/foreign";
    }

    @GetMapping("/siso/photo")
    public String photo() throws Exception {
        log.info(this.getClass().getName() + ".photo 페이지 보여주는 함수 실행");
        return "siso/photo";
    }

    // ##이것만 하면 오류남##
    /*@GetMapping("/notice/noticeList")
    public String noticeList() throws Exception {
        log.info(this.getClass().getName() + ".noticeList 페이지 보여주는 함수 실행");
        return "notice/noticeList";
    }*/
}