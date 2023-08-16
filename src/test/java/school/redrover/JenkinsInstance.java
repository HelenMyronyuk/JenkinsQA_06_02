package school.redrover;

public class JenkinsInstance {

    private String host;
    private int port;
    private boolean busy;

    public JenkinsInstance(String host, int port, boolean busy){
        this.host = host;
        this.port = port;
        this.busy = busy;
    }

    public String getHost(){
        return host;
    }

    public void setHost(String host){
        this.host = host;
    }

    public int getPort(){
        return port;
    }

    public void setPort(int port){
        this.port = port;
    }

    public boolean isBusy(){
        return busy;
    }

    public void setBusy(boolean busy){
        this.busy = busy;
    }

    @Override
    public String toString(){
        return "http://%s:%s/".formatted(host, port);
    }
}
