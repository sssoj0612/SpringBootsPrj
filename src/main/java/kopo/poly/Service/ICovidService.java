package kopo.poly.Service;

import kopo.poly.DTO.CovidDTO;

import java.util.List;

public interface ICovidService {
    // 코로나 확진자 정보 가져오기
    List<CovidDTO> getCovidRes() throws Exception;
}
