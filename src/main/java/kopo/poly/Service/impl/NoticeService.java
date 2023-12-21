package kopo.poly.Service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.DTO.NoticeDTO;
import kopo.poly.Service.INoticeService;
import kopo.poly.Util.CmmUtil;
import kopo.poly.Util.DateUtil;
import kopo.poly.repository.NoticeRepository;
import kopo.poly.repository.entity.NoticeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService implements INoticeService {

//    private final INoticeMapper noticeMapper;
    private final NoticeRepository noticeRepository;

    @Override
    public List<NoticeDTO> getNoticeList() {
        log.info(this.getClass().getName() + ".getNoticeList start!");

        /* 공지사항 전체 리스트 조회하기 */
        List<NoticeEntity> rList = noticeRepository.findAllByOrderByNoticeSeqDesc();

        /* 엔티티의 값들을 DTO에 맞게 넣어주기 */
        List<NoticeDTO> nList = new ObjectMapper().convertValue(rList,
                new TypeReference<List<NoticeDTO>>() {
                });

        return nList;
//        return noticeMapper.getNoticeList();
    }

    @Transactional /* 조회수 증가와 같이 테이블 값을 변경하는 쿼리 실행은 반드시 트랜잭션 설정 */
    @Override
    public NoticeDTO getNoticeInfo(NoticeDTO pDTO, boolean type) {

        log.info(this.getClass().getName() + ".getNoticeInfo Start!");

        // 상세보기할 때마다, 조회수 증가하기(수정보기는 제외)
        if (type) {
//            noticeMapper.updateNoticeReadCnt(pDTO);
            int res = noticeRepository.updateReadCnt(pDTO.getNoticeSeq());
            log.info("res : " + res);
        }

        /* 공지사항 상세내역 가져오기 */
        NoticeEntity rEntity = noticeRepository.findByNoticeSeq(pDTO.getNoticeSeq());

       /* 엔티티 값들을 DTO에 맞게 넣어주기 */
       NoticeDTO rDTO = new ObjectMapper().convertValue(rEntity,NoticeDTO.class);

       return rDTO;
//        return noticeMapper.getNoticeInfo(pDTO);
    }

    @Override
    public void insertNoticeInfo(NoticeDTO pDTO) {
        log.info(this.getClass().getName() + ".InsertNoticeInfo start!");

        String title = CmmUtil.nvl(pDTO.getTitle());
        String noticeYn = CmmUtil.nvl(pDTO.getNoticeYn());
        String contents = CmmUtil.nvl(pDTO.getContents());
        String userId = CmmUtil.nvl(pDTO.getUserId());

        log.info("title : " + title + "/ noticeYn : " + noticeYn + "/ contents : " + contents
                + "/ userId : " + userId);

        /* 공지사항 저장을 위해서는 PK 값은 빌더에 추가X, JPA에 자동증가 설정 */
        NoticeEntity pEntity = NoticeEntity.builder()
                .title(title).noticeYn(noticeYn).contents(contents).userId(userId).readCnt(0L)
                .regId(userId).regDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                .chgId(userId).chgDt(DateUtil.getDateTime("yyyy-MM-dd hh:mm:ss"))
                .build();

        /* 공지사항 저장하기 */
        noticeRepository.save(pEntity);

//        noticeMapper.insertNoticeInfo(pDTO);
    }

    @Transactional
    @Override
    public void updateNoticeInfo(NoticeDTO pDTO) {
        log.info(this.getClass().getName() + ".UpdateNoticeInfo start!");

        Long noticeSeq = pDTO.getNoticeSeq();

        String title = CmmUtil.nvl(pDTO.getTitle());
        String noticeYn = CmmUtil.nvl(pDTO.getNoticeYn());
        String contents = CmmUtil.nvl(pDTO.getContents());
        String userId = CmmUtil.nvl(pDTO.getUserId());

        log.info("noticeSeq : " + noticeSeq);
        log.info("title : " + title + "/ noticeYn : " + noticeYn + "/ contents : " + contents
        + "/ userId : " + userId);

        /* 현재 공지사항 조회수 가져오기 */
        NoticeEntity rEntity = noticeRepository.findByNoticeSeq(noticeSeq);

        /* 수정할 값들을 빌더를 통해 엔티티에 저장하기 */
        NoticeEntity pEntity = NoticeEntity.builder()
                .noticeSeq(noticeSeq).title(title).noticeYn(noticeYn).contents(contents).userId(userId).readCnt(rEntity.getReadCnt())
                .build();

        /* 데이터 수정하기 */
        /* JPA는 수정과 등록 함수를 구분하지 않고, save 함수 사용함
        * 캐시에 저장된 Entity 값과 비교한 뒤, 값이 다르면 Update, 없으면 Insert 쿼리 실행 */
        noticeRepository.save(pEntity);
//        noticeMapper.updateNoticeInfo(pDTO);
    }

    /* 데이터 삭제는 NoticeRepository에 함수를 만들지 않고 NoticeRepository가 상속한 JpaRepository에 삭제 함수 사용 */
    /* 삭제는 PK 컬럼을 기준으로 삭제함 */
    @Override
    public void deleteNoticeInfo(NoticeDTO pDTO) {
        log.info(this.getClass().getName() + ".deleteNoticeInfo start!");

        Long noticeSeq = pDTO.getNoticeSeq();

        log.info("noticeSeq : " + noticeSeq);

        /* 데이터 수정 */
        noticeRepository.deleteById(noticeSeq);
    }

}