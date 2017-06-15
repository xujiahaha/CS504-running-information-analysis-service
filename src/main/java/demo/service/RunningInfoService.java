package demo.service;

import demo.domain.RunningInfo;
import demo.domain.RunningInfoDto;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RunningInfoService {

    RunningInfo saveRunningInfo(RunningInfo runningInfo);

    List<RunningInfo> saveRunningInfoList(List<RunningInfo> runningInfoList);

    @Transactional
    void deleteByRunningId(String runningId);

    void deleteAll();

    RunningInfoDto findRunningInfoByRunningId(String runningId);

    // get all running information and order them by one property
    List<RunningInfoDto> findAllRunningInfoOrderBySingleProperty(int page, int size, String sortDir, String sortBy);

}
