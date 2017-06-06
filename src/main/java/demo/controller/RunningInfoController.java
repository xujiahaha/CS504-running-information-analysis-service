package demo.controller;

import demo.domain.RunningInfo;
import demo.service.RunningInfoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class RunningInfoController {

    // Default value for pageable
    private static final String DEFAULT_PAGE_NUMBER = "0";
    private static final String DEFAULT_PAGE_SIZE = "2";
    private static final String DEFAULT_SORT_DIR = "desc";
    private static final String DEFAULT_SORT_BY = "heartRate";

    private RunningInfoService runningInfoService;

    @Autowired
    public RunningInfoController(RunningInfoService runningInfoService) {
        this.runningInfoService = runningInfoService;
    }

    @RequestMapping(value = "/runningInfo", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public void upload(@RequestBody List<RunningInfo> runningInfoList) {
        this.runningInfoService.saveRunningInfoList(runningInfoList);
    }

    @RequestMapping(value = "runningInfo/purge", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void purge() {
        this.runningInfoService.deleteAll();
    }

    @RequestMapping(value = "runningInfo/{runningId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteByRunningId(@PathVariable("runningId") String runningId) {
        this.runningInfoService.deleteByRunningId(runningId);
    }

    @RequestMapping(value = "/runningInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String findAllRunningInfoOrderBySingleProperty(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "orderDir", required = false, defaultValue = DEFAULT_SORT_DIR) String sortDir,
            @RequestParam(value = "orderBy", required = false, defaultValue = DEFAULT_SORT_BY) String sortBy) {

        Page<RunningInfo> rawRunningInfo = this.runningInfoService.findAllRunningInfoOrderBySingleProperty(page, size, sortDir, sortBy);
        List<RunningInfo> runningInfoContents = rawRunningInfo.getContent();
        List<JSONObject> runningInfoDto = new ArrayList<>(page);
        for(RunningInfo item : runningInfoContents) {
            JSONObject info = new JSONObject();
            info.put("runningId", item.getRunningId());
            info.put("totalRunningTime", item.getTotalRunningTime());
            info.put("heartRate", item.getHeartRate());
            info.put("userName", item.getUserName());
            info.put("userAddress", item.getUserAddress());
            info.put("healthWarningLevel", item.getHealthWarningLevel());
            runningInfoDto.add(info);
        }
        return runningInfoDto.toString();
    }

}
