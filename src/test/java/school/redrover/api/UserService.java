package school.redrover.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class UserService extends BaseService {

    public static final List<String> DEFAULT_USERS = List.of("User", "admin");

    public UserService(JenkinsSpecBuilder baseService) {
        super(baseService);
    }

    @Step("Get Users")
    public List<String> getUsers() {
        return specBuilder.buildBasicSpec()
                .basePath("/manage/asynchPeople/%s".formatted(specBuilder.suffix))
                .get()
                .jsonPath()
                .getList("users.user.fullName");
    }

    @Step("Delete User {0}")
    public Response deleteUser(String userName) {
        var crumb = getCrumbRequest();
        return specBuilder.buildBasicSpec()
                .basePath("/user/%s/doDelete".formatted(userName))
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .post();
    }
}
