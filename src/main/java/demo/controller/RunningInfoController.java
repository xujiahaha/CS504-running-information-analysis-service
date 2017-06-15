package demo.controller;

import demo.domain.RunningInfo;
import demo.service.RunningInfoDto;
import demo.service.RunningInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/runningInfo/purge", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void purge() {
        this.runningInfoService.deleteAll();
    }

    @RequestMapping(value = "/runningInfo/{runningId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteByRunningId(@PathVariable("runningId") String runningId) {
        this.runningInfoService.deleteByRunningId(runningId);
    }

    @RequestMapping(value = "/runningInfo/{runningId}", method = RequestMethod.GET)
    public ResponseEntity<?> findRunningInfoByRunningId(@PathVariable("runningId") String runningId) {
        RunningInfoDto runningInfoDto = this.runningInfoService.findRunningInfoByRunningId(runningId);
        if(runningInfoDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(runningInfoDto);
    }

    @RequestMapping(value = "/runningInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RunningInfoDto> findAllRunningInfoOrderBySingleProperty(
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size,
            @RequestParam(value = "sortDir", required = false, defaultValue = DEFAULT_SORT_DIR) String sortDir,
            @RequestParam(value = "sortBy", required = false, defaultValue = DEFAULT_SORT_BY) String sortBy) {
        return this.runningInfoService.findAllRunningInfoOrderBySingleProperty(page, size, sortDir, sortBy);
    }

    @RequestMapping(value = "/{username}/runningInfo", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RunningInfoDto> findRunningInfoByUsername(
            @PathVariable("username") String username,
            @RequestParam(value = "page", required = false, defaultValue = DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", required = false, defaultValue = DEFAULT_PAGE_SIZE) int size) {
        return this.runningInfoService.findRunningInfoByUsernameOrderByTimestampDesc(page, size, username);
    }

}
