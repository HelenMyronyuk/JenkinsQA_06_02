package school.redrover.api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import school.redrover.JenkinsInstance;

import java.util.List;
import java.util.stream.Collectors;

public class JobService extends BaseService {

    public JobService(JenkinsInstance instance) {
        super(instance);
    }

    @Step("Get Jobs")
    public List<String> getJobs() {
        Allure.addAttachment("Base url:", instance.toString());
        return buildBasicSpec()
                .basePath(suffix)
                .get()
                .jsonPath()
                .getList("jobs.name");
    }

    @Step("Get Job Urls")
    public List<String> getJobUrls() {
        Allure.addAttachment("Base url:", instance.toString());
        List<String> jobs =  buildBasicSpec()
                .basePath(suffix)
                .get()
                .jsonPath()
                .getList("jobs.url")
                .stream()
                .map(j -> j.toString().replaceAll("host:\\d{4}", "host:%s".formatted(instance.getPort())))
                .toList();
        Allure.addAttachment("Jobs:", String.join(",", jobs));
        return jobs;
    }

    @Step("Delete Job {0}")
    public Response deleteJob(String jobName) {
        var crumb = getCrumbRequest();
        return buildBasicSpec()
                .basePath("job/%s/doDelete".formatted(jobName))
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .post();
    }

    @Step("Delete Job {0}")
    public Response deleteJobByUrl(String jobUrl) {
        var crumb = getCrumbRequest();
        Allure.addAttachment("Base url:", instance.toString());
        return buildBasicSpec()
                .urlEncodingEnabled(false)
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .post(jobUrl + "doDelete");
    }
}
