package kopo.poly.Service;

import kopo.poly.DTO.UserInfoDTO;

public interface IUserInfoService { // 인터페이스로 먼저 선언하고 만든 클래스로 구현

    /* 로그인을 위해 아이디와 비밀번호가 일치하는지 확인하기
    @param pDTO 로그인을 위한 회원정보
    @return 회원가입 결과 */
    int getUserLogin(UserInfoDTO pDTO) throws Exception;

    // 아이디 중복체크
    UserInfoDTO getUserIdExists(UserInfoDTO pDTO) throws Exception;

//    // 이메일 주소 중복 체크 및 인증 값
//    UserInfoDTO getEmailExists(UserInfoDTO pDTO) throws Exception;

    /* 회원 가입하기(회원정보 등록하기)
    @param pDTO 로그인을 위한 회원정보
    @return 회원가입 결과 */
    int insertUserInfo(UserInfoDTO pDTO) throws Exception;


    /*// 아이디, 비밀번호 찾기에 활용
    UserInfoDTO searchUserIdOrPasswordProc(UserInfoDTO pDTO) throws Exception;

    // 비밀번호 재설정
    int newPasswordProc(UserInfoDTO pDTO) throws Exception;*/
}