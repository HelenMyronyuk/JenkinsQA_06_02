package school.redrover.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

public class NodeService extends BaseService {

    public static final List<String> DEFAULT_NODES = List.of("Built-In Node");

    public NodeService(JenkinsSpecBuilder baseService) {
        super(baseService);
    }

    @Step("Get Nodes")
    public List<String> getNodes() {
        return specBuilder.buildBasicSpec()
                .basePath("manage/computer/%s".formatted(specBuilder.suffix))
                .get()
                .jsonPath()
                .getList("computer.displayName");
    }

    @Step("Delete Node {0}")
    public Response deleteNode(String nodeName) {
        var crumb = getCrumbRequest();
        return specBuilder.buildBasicSpec()
                .basePath("computer/%s/doDelete".formatted(nodeName))
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .post();
    }
}
