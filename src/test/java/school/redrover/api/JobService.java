package school.redrover.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class JobService extends BaseService {

    public JobService(JenkinsSpecBuilder baseService) {
        super(baseService);
    }

    @Step("Get Jobs")
    public List<String> getJobs() {
        return specBuilder.buildBasicSpec()
                .basePath(specBuilder.suffix)
                .get()
                .jsonPath()
                .getList("jobs.name");
    }

    @Step("Delete Job {0}")
    public Response deleteJob(String jobName) {
        var crumb = getCrumbRequest();
        return specBuilder.buildBasicSpec()
                .basePath("job/%s/doDelete".formatted(jobName))
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .post();
    }
}
