package school.redrover.api;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import school.redrover.JenkinsInstance;

public class JenkinsSpecBuilder {

    private final JenkinsInstance instance;
    protected final String suffix = "api/json";

    public JenkinsSpecBuilder(JenkinsInstance instance) {
        this.instance = instance;
    }

    public RequestSpecification buildBasicSpec() {
        return RestAssured.given()
                .baseUri(instance.getUrl())
                .auth()
                .preemptive()
                .basic(instance.getUser(), instance.getPassword());
    }
}
