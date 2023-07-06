package kopo.poly.Persistance.Mapper;

import kopo.poly.DTO.NoticeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper // sql쿼리를 실행시켜주는 매개체
public interface INoticeMapper {

    // 게시판 목록을 가져와야 하므로 List<NoticeDTO>를 반환타입으로 가짐
    List<NoticeDTO> getNoticeList() throws Exception; // 게시판 리스트
    void insertNoticeInfo(NoticeDTO pDTO) throws Exception; // 게시판 글 등록

    NoticeDTO getNoticeInfo(NoticeDTO pDTO) throws Exception; // 게시판 상세보기

    void updateNoticeReadCnt(NoticeDTO pDTO) throws Exception; // 게시판 조회수 업데이트

    void updateNoticeInfo(NoticeDTO pDTO) throws Exception; // 게시판 글 수정

    void deleteNoticeInfo(NoticeDTO pDTO) throws Exception; // 게시판 글 삭제
}
