package school.redrover;

import java.util.List;

public class JenkinsConstants {

    public static final JenkinsInstance jenkins1 = new JenkinsInstance("localhost", 8080, false);
    public static final JenkinsInstance jenkins2 = new JenkinsInstance("localhost", 8081, false);
    public static final JenkinsInstance jenkins3 = new JenkinsInstance("localhost", 8082, false);
    public static final JenkinsInstance jenkins4 = new JenkinsInstance("localhost", 8083, false);

    public static final List<JenkinsInstance> instances = List.of(jenkins1, jenkins2, jenkins3, jenkins4);

}
