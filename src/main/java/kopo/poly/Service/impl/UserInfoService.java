package kopo.poly.Service.impl;

import kopo.poly.DTO.MailDTO;
import kopo.poly.DTO.UserInfoDTO;
import kopo.poly.Service.IMailService;
import kopo.poly.Service.IUserInfoService;
import kopo.poly.Util.CmmUtil;
import kopo.poly.Util.DateUtil;
import kopo.poly.Util.EncryptUtil;
import kopo.poly.repository.UserInfoRepository;
import kopo.poly.repository.entity.UserInfoEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RequiredArgsConstructor
@Service("UserInfoService")
public class UserInfoService implements IUserInfoService {

//    private final IUserInfoMapper userInfoMapper; // 회원관련 SQL 사용하기 위한 Mapper 가져오기
//    private final IMailService mailService; // 메일 발송을 위한 mailService 자바 객체 가져오기

    private final UserInfoRepository userInfoRepository;

    /* 아이디 중복체크 */
    @Override
    public UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getUserIdExists Start!");

//        UserInfoDTO rDTO = userInfoMapper.getUserIdExists(pDTO);

        UserInfoDTO rDTO = new UserInfoDTO();

        String userId = CmmUtil.nvl(pDTO.getUserId());

        log.info("userId : " + userId);

        /* 회원가입 중복 방지 위해 DB에서 데이터 조회 */
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        /* 값이 존재하면 */
        if (rEntity.isPresent()) {
            rDTO.setExistsYn("Y"); /* 아이디 중복 */
        } else { /* 값이 존재 안하면 */
            rDTO.setExistsYn("N"); /* 아이디 중복 안됨 */
        }

        log.info(this.getClass().getName() + ".getUserIdExists End!");

        return rDTO;
    }


//    @Override
//    public UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception {
//
//        log.info(this.getClass().getName() + ".emailAuth Start!");
//        log.info("pdto email : " + pDTO.getEmail());
//
//        // DB 이메일이 존재하는지 SQL 쿼리 실행
//        // SQL 쿼리에 COUNT()를 사용하기때문에 반드시 조회 결과는 존재함
//        UserInfoDTO rDTO = userInfoMapper.getEmailExists(pDTO);
//
//        if (rDTO == null){
//            rDTO = new UserInfoDTO();
//        }
//
//        String exists_yn = CmmUtil.nvl(rDTO.getExists_yn());
//
//        log.info("exists_yn : " + exists_yn);
//
//        // 가입 된 이메일이 없다면(N) 6자리 랜덤 숫자 생성하기
//        if (exists_yn.equals("N")){
//            int authNumber = ThreadLocalRandom.current().nextInt(100000, 1000000);
//            log.info("authNumber : " + authNumber);
//
//            // 인증번호 발송 로직
//            MailDTO dto = new MailDTO();
//
//            dto.setTitle("이메일 중복 확인 인증번호 발송 메일");
//            dto.setContents("인증번호는 " + authNumber + " 입니다.");
//            dto.setToMail(EncryptUtil.decAES128CBC(CmmUtil.nvl(pDTO.getEmail())));
//
//            mailService.doSendMail(dto); // 이메일 발송
//
//            dto = null;
//
//            rDTO.setAuthNumber(authNumber); // 인증번호를 결과값에 넣어주기
//        }
//
//        log.info(this.getClass().getName() + ".emailAuth End!");
//        return rDTO;
//    }

    /* 회원가입 */
    @Override
    public int insertUserInfo(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".insertUserInfo Start!");

        // 회원가입 성공 : 1    기타 에러 발생 : 0
        int res = 0;

//        // 회원가입
//        res = userInfoMapper.insertUserInfo(pDTO);

        String userId = CmmUtil.nvl(pDTO.getUserId());
        String userName = CmmUtil.nvl(pDTO.getUserName());
        String password = CmmUtil.nvl(pDTO.getPassword());
        String email = CmmUtil.nvl(pDTO.getEmail());
        String tel = CmmUtil.nvl(pDTO.getTel());
        String addr1 = CmmUtil.nvl(pDTO.getAddr1());
        String addr2 = CmmUtil.nvl(pDTO.getAddr2());

        log.info(this.getClass().getName() + ".insertUserInfo Start!");

        /* 회원가입 중복방지 위해 DB에서 데이터 조회 */
        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserId(userId);

        /* 값이 존재하면 */
        if (rEntity.isPresent()) {
            res = 2;
        } else { /* 값이 존재 안하면 회원가입을 위한 Entity 생성 */
            UserInfoEntity pEntity = UserInfoEntity.builder()
                    .userId(userId).userName(userName).password(password)
                    .email(email).tel(tel).addr1(addr1).addr2(addr2)
                    .regId(userId).regDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                    .chgId(userId).chgDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                    .build();

            userInfoRepository.save(pEntity); /* 회원정보 DB에 저장 */

            /* JPA의 save 함수는 데이터 값에 따라 등록, 수정을 수행함
            * 물론 잘 저장되겠지만 내가 실행한 save 함수가 DB에 잘 등록됐는지 100% 확신은 불가능
            * 회원가입 후 혹시나 저장 안될 수 있기에 조회 수행
            * 회원가입 중복 방지 위해 DB에서 데이터 조회 */
            rEntity = userInfoRepository.findByUserId(userId);

            if (rEntity.isPresent()) {
                res = 1;
            } else {
                res = 0;
            }
        }

        log.info(this.getClass().getName() + ".insertUserInfo End!");
        return res;
    }


    /* 로그인하기 (아디,비번 조회) */
    @Override
    public int getUserLogin(UserInfoDTO pDTO) throws Exception {

        log.info(this.getClass().getName() + ".getUserLogin Start!");

        int res = 0;

        String userId = CmmUtil.nvl(pDTO.getUserId());
        String password = CmmUtil.nvl(pDTO.getPassword());

        log.info("userId : " + userId + "/password : " + password);

        Optional<UserInfoEntity> rEntity = userInfoRepository.findByUserIdAndPassword(userId, password);

        if (rEntity.isPresent()) {
            res = 1;
        }

//        // 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기 위한 mapper 호출
//        UserInfoDTO rDTO = Optional.ofNullable // rDTO에 null값이 들어오면
//                (userInfoMapper.getLogin(pDTO)).orElseGet(UserInfoDTO::new);
//                // orElseGet 함수 실행
//                // -> null 대신 UserInfoDTO 객체를 강제로 메모리에 값을 올리기


        /* DTO의 변수에 값이 있는지 확인하기 처리속도 측면에서 가장 좋은 방법 = 변수의 길이 가져오기
           따라서 .length() 함수를 통해 회원아이디의 글자수를 가져와 0보다 크면
           글자가 존재하는 것이기 때문에 값이 존재한다는걸 알 수 있음.*/
//        if (CmmUtil.nvl( rDTO.getUser_id() ) .length() > 0) {
//            log.info("로그인 성공");
//        }

        log.info(this.getClass().getName() + ".getLogin End!");

        return res;
    }
}