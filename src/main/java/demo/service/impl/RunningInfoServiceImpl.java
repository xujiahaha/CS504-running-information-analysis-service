package demo.service.impl;


import demo.domain.RunningInfo;
import demo.domain.RunningInfoRepository;
import demo.service.RunningInfoDto;
import demo.service.RunningInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RunningInfoServiceImpl implements RunningInfoService{
    private RunningInfoRepository runningInfoRepository;

    @Autowired
    public RunningInfoServiceImpl(RunningInfoRepository runningInfoRepository) {
        this.runningInfoRepository = runningInfoRepository;
    }

    @Override
    public  RunningInfo saveRunningInfo(RunningInfo runningInfo) {
        return this.runningInfoRepository.save(runningInfo);
    }
    @Override
    public List<RunningInfo> saveRunningInfoList(List<RunningInfo> runningInfoList) {
        return this.runningInfoRepository.save(runningInfoList);
    }

    @Override
    @Transactional
    public void deleteByRunningId(String runningId) {
        this.runningInfoRepository.deleteByRunningId(runningId);
    }

    @Override
    public void deleteAll() {
        this.runningInfoRepository.deleteAll();
    }

    @Override
    public RunningInfoDto findRunningInfoByRunningId(String runningId) {
        RunningInfo runningInfo = this.runningInfoRepository.findByRunningId(runningId);
        if(runningInfo == null) return null;
        return new RunningInfoDto(runningInfo);
    }

    @Override
    public List<RunningInfoDto> findAllRunningInfoOrderBySingleProperty(int page, int size, String sortDir, String sortBy) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.fromString(sortDir.toLowerCase()), sortBy);
        return createDtoResult(this.runningInfoRepository.findAll(pageable));
    }

    @Override
    public List<RunningInfoDto> findRunningInfoByUsernameOrderByTimestampDesc(int page, int size, String username) {
        Pageable pageable = new PageRequest(page, size);
        return createDtoResult(this.runningInfoRepository.findByUserInfoUsernameOrderByTimestampDesc(username, pageable));
    }

    private List<RunningInfoDto> createDtoResult(Page<RunningInfo> runningInfo) {
        List<RunningInfo> runningInfoContents = runningInfo.getContent();
        List<RunningInfoDto> runningInfoDto = new ArrayList<>();
        for(RunningInfo item : runningInfoContents) {
            runningInfoDto.add(new RunningInfoDto(item));
        }
        return runningInfoDto;
    }
}
