package school.redrover.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import school.redrover.JenkinsInstance;

import java.util.List;

public class ViewService extends BaseService {

    public ViewService(JenkinsInstance instance) {
        super(instance);
    }

    @Step("Get Views")
    public List<String> getViews() {
        return buildBasicSpec()
                .basePath(suffix)
                .get()
                .jsonPath()
                .getList("views.name");
    }

    @Step("Delete View {0}")
    public Response deleteView(String viewName) {
        var crumb = getCrumbRequest();
        return buildBasicSpec()
                .basePath("view/%s/doDelete".formatted(viewName))
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .post();
    }
}
