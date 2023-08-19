package school.redrover;

public class JenkinsInstance {

    private final String host;
    private final int port;
    private boolean busy;
    private String user;
    private String password;

    public JenkinsInstance(String host, int port, boolean busy){
        this.host = host;
        this.port = port;
        this.busy = busy;
    }

    public boolean isBusy(){
        return busy;
    }

    public void setBusy(boolean busy){
        this.busy = busy;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUrl(){
        return "http://%s:%s/".formatted(host, port);
    }
}
