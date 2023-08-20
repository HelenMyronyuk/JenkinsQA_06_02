package school.redrover.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import school.redrover.JenkinsInstance;

import java.util.List;

public class UserService extends BaseService {

    public static final List<String> DEFAULT_USERS = List.of("User", "admin");

    public UserService(JenkinsInstance instance) {
        super(instance);
    }

    @Step("Get Users")
    public List<String> getUsers() {
        return buildBasicSpec()
                .basePath("/manage/asynchPeople/%s".formatted(suffix))
                .get()
                .jsonPath()
                .getList("users.user.fullName");
    }

    @Step("Delete User {0}")
    public Response deleteUser(String userName) {
        var crumb = getCrumbRequest();
        return buildBasicSpec()
                .basePath("/user/%s/doDelete".formatted(userName))
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .post();
    }
}
