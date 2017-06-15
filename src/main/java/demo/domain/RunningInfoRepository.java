package demo.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;



public interface RunningInfoRepository extends JpaRepository<RunningInfo, String> {

    void deleteByRunningId(@Param("RunningId") String runningId);

    RunningInfo findByRunningId(@Param("runningId") String runningId);

    Page<RunningInfo> findAll(Pageable pageable);

    Page<RunningInfo> findByUserInfoUsernameOrderByTimestampDesc(@Param("username") String username, Pageable pageable);
}
