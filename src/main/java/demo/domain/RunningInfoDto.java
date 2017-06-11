package demo.domain;

import lombok.Data;

@Data
public class RunningInfoDto {
    private String runningId;
    private double totalRunningTime;
    private int heartRate;
    private String userName;
    private String userAddress;
    private RunningInfo.HealthWarningLevel healthWarningLevel;

    public RunningInfoDto() {
    }

    public RunningInfoDto(RunningInfo runningInfo){
        this.runningId = runningInfo.getRunningId();
        this.totalRunningTime = runningInfo.getTotalRunningTime();
        this.heartRate = runningInfo.getHeartRate();
        this.userName = runningInfo.getUserName();
        this.userAddress = runningInfo.getUserAddress();
        this.healthWarningLevel = runningInfo.getHealthWarningLevel();
    }

}
