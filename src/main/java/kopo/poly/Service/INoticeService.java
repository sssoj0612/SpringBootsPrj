package kopo.poly.Service;

import kopo.poly.DTO.NoticeDTO;

import java.util.List;

public interface INoticeService {

    // 공지사항 리스트
    // @return 조회 경과
    List<NoticeDTO> getNoticeList() throws Exception;

    // 공지사항 상세보기
    /* @param pDTO 상세 내용 조회할 notice_seq 값
       @param type 조회수 증가여부(수정보기는 조회수 증가하지 않음)
       @return 조회 경과
     */
    NoticeDTO getNoticeInfo(NoticeDTO pDTO, boolean type) throws Exception;

    // 공지사항 등록
    // @param pDTO 화면에서 입력된 공지사항 입력된 값들
    void insertNoticeInfo(NoticeDTO pDTO) throws Exception;

    // 공지사항 수정
    // @param pDTO = 화면에서 입력 된 수정되기 위한 공지사항 입력된 값들
    void updateNoticeInfo(NoticeDTO pDTO) throws Exception;

    // 공지사항 삭제
    // @param pDTO = 삭제할 notice_seq 값
    void deleteNoticeInfo(NoticeDTO pDTO) throws Exception;

}
