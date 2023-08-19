package school.redrover.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class BaseService {

    protected final JenkinsSpecBuilder specBuilder;

    public BaseService(JenkinsSpecBuilder specBuilder) {
        this.specBuilder = specBuilder;
    }

    protected Response getCrumbRequest() {
        return specBuilder.buildBasicSpec()
                .basePath("crumbIssuer/%s".formatted(specBuilder.suffix))
                .get();
    }

    @Step("Set description {0}")
    public Response submitDescription(String description) {
        var crumb = getCrumbRequest();
        return specBuilder.buildBasicSpec()
                .basePath("submitDescription")
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .formParam("description", description)
                .post();
    }
}
