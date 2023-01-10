package APIController;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

public class APIController implements IController {
    private HttpGet httpGet;
    @Override
    public HttpResponse sendRequest(EndPointBuilder endPointBuilder){
        httpGet = new HttpGet(endPointBuilder.getEndPoint());
        HttpClient client = HttpClientBuilder.create().build();
        try{
            return client.execute(httpGet);
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
