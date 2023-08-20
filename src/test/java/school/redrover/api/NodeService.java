package school.redrover.api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import school.redrover.JenkinsInstance;

import java.util.List;

public class NodeService extends BaseService {

    public static final List<String> DEFAULT_NODES = List.of("Built-In Node");

    public NodeService(JenkinsInstance instance) {
        super(instance);
    }

    @Step("Get Nodes")
    public List<String> getNodes() {
        return buildBasicSpec()
                .basePath("manage/computer/%s".formatted(suffix))
                .get()
                .jsonPath()
                .getList("computer.displayName");
    }

    @Step("Delete Node {0}")
    public Response deleteNode(String nodeName) {
        var crumb = getCrumbRequest();
        return buildBasicSpec()
                .basePath("computer/%s/doDelete".formatted(nodeName))
                .header("Jenkins-Crumb", crumb.jsonPath().getString("crumb"))
                .cookies(crumb.getCookies())
                .post();
    }
}
