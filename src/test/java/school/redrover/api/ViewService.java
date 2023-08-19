package school.redrover.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class ViewService extends BaseService {

    public ViewService(JenkinsSpecBuilder baseService) {
        super(baseService);
    }

    @Step("Get Views")
    public List<String> getViews() {
        return specBuilder.buildBasicSpec()
                .basePath(specBuilder.suffix)
                .get()
                .jsonPath()
                .getList("views.name");
    }

    @Step("Delete View {0}")
    public Response deleteView(String viewName) {
        var crumb = getCrumbRequest();
        return specBuilder.buildBasicSpec()
                .basePath("view/%s/doDelete".formatted(viewName))
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .post();
    }
}
