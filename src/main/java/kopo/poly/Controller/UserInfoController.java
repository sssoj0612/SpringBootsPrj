package kopo.poly.Controller;

import kopo.poly.DTO.UserInfoDTO;
import kopo.poly.Service.IUserInfoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserInfoController {
    private final IUserInfoService userInfoService; // 서비스를 안에서 사용할수 있게 하는 선언문

    // 회원가입 화면으로 이동
    @GetMapping(value = "/user/userRegForm")
    public String userRegForm() {
        log.info(this.getClass().getName() + ".user/userRegForm");

        return "/user/userRegForm";
    }

    //회원가입 로직 처리
    @PostMapping(value = "/user/insertUserInfo")
    public String insertUserInfo(HttpServletRequest request, ModelMap modelMap) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo start!");
        int res;
        String msg = ""; // 회원가입 결과에 대한 메시지를 전달할 변수
        String url = ""; // 회원가입 결과에 대한 url을 전달할 변수

        UserInfoDTO pDTO = null; // 웹(외원정보 입력화면)에서 받는 정보를 저장할 변수

        try { /*
             웹(회원정보 입력화면)에서 받는 정보를 String 변수에 저장 시작!
             무조건 웹으로 받은 정보는 DTO에 저장하기 위해 임시로 String 변수에 저장.

             CmmUtil 함수 : 자바는 null값이 들어가면 무조건 에러남.
             해당 함수는 들어가있는 문자열이 null값이면 빈 문자열로 바꿔준다.

             getParameter 함수 : HTTP요청에 웹페이지 폼을 통해 전송된 데이터 값을 가져오는 함수.
             */
            String user_id = CmmUtil.nvl(request.getParameter("user_id")); //아이디
            String user_name = CmmUtil.nvl(request.getParameter("user_name")); //이름
            String password = CmmUtil.nvl(request.getParameter("password")); //비번
            String email = CmmUtil.nvl(request.getParameter("email")); //이메일
            String addr1 = CmmUtil.nvl(request.getParameter("addr1")); //주소
            String addr2 = CmmUtil.nvl(request.getParameter("addr2")); //상세줒소


            /* 값을 받았으면 반드시 로그를 찍어서 값이 제대로 들어오는지 파악해야함 */
            log.info("user_id : " + user_id);
            log.info("user_name : " + user_name);
            log.info("password : " + password);
            log.info("email : " + email);
            log.info("addr1 : " + addr1);
            log.info("addr2 : " + addr2);


            /* 웹(회원정보 입력화면)에서 받는 정보를 DTO에 저장하기 시작
               무조건 웹으로 받는 정보는 DTO에 저장해야한다고 이해할 것 */

            //웹(회원정보 입력화면)에서 받는 정보를 저장할 변수를 메모리에 올리기
            pDTO = new UserInfoDTO();

            pDTO.setUser_id(user_id);
            pDTO.setUser_name(user_name);

            // 비밀번호는 절대로 복호화되지 않도록 해시 알고리즘으로 암호화함
            pDTO.setPassword(EncryptUtil.encHashSHA256(password));

            // 민감 정보인 이메일은 AES128-CBC로 암호화함
            pDTO.setEmail(EncryptUtil.encAES128CBC(email));
            pDTO.setAddr1(addr1);
            pDTO.setAddr2(addr2);

            // 회원가입
            res = userInfoService.insertUserInfo(pDTO);

            log.info("회원가입 결과(res) : " + res);

            if (res == 1) {
                msg = "회원가입되었습니다.";

            // 추후 회원가입 입력화면에서 ajax를 활용해서 아이디 중복, 이메일 중복 체크 할 것.
            } else if (res == 2) {
                msg = "이미 가입된 아이디입니다.";
            } else {
                msg = "오류로 인해 회원가입이 실패하였습니다.";
            }

        } catch (Exception e) { // 저장이 실패되면 사용자에게 보여줄 메시지
            msg = "실패하였습니다. : " + e;
            log.info(e.toString());
            e.printStackTrace();
        } finally {
            modelMap.addAttribute("msg", msg);
            modelMap.addAttribute("url", url);

            log.info(this.getClass().getName() + ".insertUserInfo End!");
        }

        return "/redirect";
    }
}