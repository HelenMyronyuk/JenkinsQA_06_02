package school.redrover;

import java.util.List;

public class JenkinsConstants {

    public static final JenkinsInstance jenkins1 = new JenkinsInstance("localhost", 8080, false);
    public static final JenkinsInstance jenkins2 = new JenkinsInstance("localhost", 8081, false);

    public static final List<JenkinsInstance> instances = List.of(jenkins1, jenkins2);

}
