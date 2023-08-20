package school.redrover.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import school.redrover.JenkinsInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyViewService extends BaseService {

    public MyViewService(JenkinsInstance instance) {
        super(instance);
    }

    @Step("Get My Views")
    public List<String> getMyViews() {
        var html = buildBasicSpec()
                .basePath("me/my-views")
                .get()
                .body()
                .asString();

        Pattern pattern = Pattern.compile("a href=\"/user/admin/my-views/view/.{2,20}/\"");
        Matcher matcher = pattern.matcher(html);
        var matches = new ArrayList<String>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        return matches.stream()
                .map(e -> e.substring(0, e.lastIndexOf("/")))
                .map(e -> e.substring(e.lastIndexOf("/") + 1))
                .toList();
    }

    @Step("Delete My View {0}")
    public Response deleteMyView(String viewName) {
        var crumb = getCrumbRequest();
        return buildBasicSpec()
                .basePath("me/my-views/view/%s/doDelete".formatted(viewName))
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .post();
    }
}
