package Logic.APIController;

import org.apache.http.HttpResponse;

public interface IController {
    HttpResponse sendRequest(EndPointBuilder endPointBuilder);
}
