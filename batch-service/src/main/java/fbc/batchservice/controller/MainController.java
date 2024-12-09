package fbc.batchservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ===========================================================
 *
 * @author : jglee
 * @packageName : fbc.batchservice.controller
 * @fileName : MainController
 * @date : 24. 12. 4.
 * @description :
 * ===========================================================
 */
@Controller
@ResponseBody
@Slf4j
public class MainController {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    public MainController(JobLauncher jobLauncher, JobRegistry jobRegistry) {
        this.jobLauncher = jobLauncher;
        this.jobRegistry = jobRegistry;
    }

    /**
     * 테이블 to 테이블 배치 : BeforeEntity 테이블 -> AfterEntity 테이블 복사
     * @param value
     * @return
     * @throws Exception
     */
    @GetMapping("/first")
    public String firstApi(@RequestParam("value") String value) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", value)
                .toJobParameters();

        jobLauncher.wait(); //run(jobRegistry.getJob("firstJob"), jobParameters);

        return "ok";
    }

    /**
     * JPA 조건을 통해 배치 : win 값이 10이상인 항목의 reward 를 true로 설정 처리
     * @param value
     * @return
     * @throws Exception
     */
    @GetMapping("/second")
    public String secondApi(@RequestParam("value") String value) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", value)
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("secondJob"), jobParameters);
        return "ok";
    }

    /**
     * Excel -> 테이블 저장 배치
     * @param value
     * @return
     * @throws Exception
     */
    @GetMapping("/excel")
    public String excelToTableApi(@RequestParam("value") String value) throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", value)
                .toJobParameters();

        JobExecution jobExecution = jobLauncher.run(jobRegistry.getJob("excelToTableJob"), jobParameters);
        log.info("JobId : {}", jobExecution.getId());

        return "ok";
    }
}
