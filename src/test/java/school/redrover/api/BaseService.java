package school.redrover.api;

import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import school.redrover.JenkinsInstance;

public class BaseService {

    protected final JenkinsInstance instance;
    protected final String suffix = "api/json";

    public BaseService(JenkinsInstance instance) {
        this.instance = instance;
    }

    protected RequestSpecification buildBasicSpec() {
        return RestAssured.given()
                .baseUri(instance.getUrl())
                .auth()
                .preemptive()
                .basic(instance.getUser(), instance.getPassword());
    }

    @Step("Get Crumb")
    protected synchronized Response getCrumbRequest() {
        Allure.addAttachment("Base url:", instance.toString());
        return buildBasicSpec()
                .basePath("crumbIssuer/%s".formatted(suffix))
                .get();
    }

    @Step("Set description {0}")
    public Response submitDescription(String description) {
        var crumb = getCrumbRequest();
        return buildBasicSpec()
                .basePath("submitDescription")
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .formParam("description", description)
                .post();
    }
}
