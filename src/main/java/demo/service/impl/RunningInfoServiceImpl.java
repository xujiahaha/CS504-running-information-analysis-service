package demo.service.impl;


import demo.domain.RunningInfo;
import demo.domain.RunningInfoRepository;
import demo.service.RunningInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    public void deleteByRunningId(String runningId) {
        this.runningInfoRepository.deleteByRunningId(runningId);
    }

    @Override
    public void deleteAll() {
        this.runningInfoRepository.deleteAll();
    }

    @Override
    public Page<RunningInfo> findAllRunningInfoOrderBySingleProperty(int page, int size, String sortDir, String sortBy) {
        Pageable pageable = new PageRequest(page, size, Sort.Direction.fromString(sortDir.toLowerCase()), sortBy);
        return this.runningInfoRepository.findAll(pageable);
    }

}
