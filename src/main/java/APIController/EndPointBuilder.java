package APIController;

public abstract class EndPointBuilder {
    private String endPoint;
    protected abstract void buildEndPoint();
    
    public String getEndPoint(){
        return endPoint;
    }

    protected void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
