package kopo.poly.Service.impl;

import kopo.poly.DTO.NoticeDTO;
import kopo.poly.Persistance.Mapper.INoticeMapper;
import kopo.poly.Service.INoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class NoticeService implements INoticeService {

    private final INoticeMapper noticeMapper;

    @Override
    public List<NoticeDTO> getNoticeList() throws Exception {
        log.info(this.getClass().getName() + ".getNoticeList start!");
        return noticeMapper.getNoticeList();
    }

    @Transactional
    @Override
    public NoticeDTO getNoticeInfo(NoticeDTO pDTO, boolean type) throws Exception {

        log.info(this.getClass().getName() + ".getNoticeInfo Start!");

        // 상세보기할 때마다, 조회수 증가하기(수정보기는 제외)
        if (type) {
            log.info("Update ReadCNT");
            noticeMapper.updateNoticeReadCnt(pDTO);
        }

        return noticeMapper.getNoticeInfo(pDTO);
    }

    @Transactional
    @Override
    public void insertNoticeInfo(NoticeDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".InsertNoticeInfo start!");
        noticeMapper.insertNoticeInfo(pDTO);
    }

    @Transactional
    @Override
    public void updateNoticeInfo(NoticeDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".UpdateNoticeInfo start!");
        noticeMapper.updateNoticeInfo(pDTO);
    }

    @Transactional
    @Override
    public void deleteNoticeInfo(NoticeDTO pDTO) throws Exception {
        log.info(this.getClass().getName() + ".deleteNoticeInfo start!");
        noticeMapper.deleteNoticeInfo(pDTO);
    }
}