package demo.service;

import demo.domain.RunningInfo;

import java.util.List;

public interface RunningInfoService {

    RunningInfo saveRunningInfo(RunningInfo runningInfo);

    List<RunningInfo> saveRunningInfoList(List<RunningInfo> runningInfoList);

    void deleteByRunningId(String runningId);

    void deleteAll();

    RunningInfoDto findRunningInfoByRunningId(String runningId);

    // get all running information and order them by one property
    List<RunningInfoDto> findAllRunningInfoOrderBySingleProperty(int page, int size, String sortDir, String sortBy);

    // get running information by username and order them by timestamp. most recent one is on top.
    List<RunningInfoDto> findRunningInfoByUsernameOrderByTimestampDesc(int page, int size, String username);
}
