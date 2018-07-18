package Commen;

import tomTiger.Request;
import tomTiger.Response;

public interface Action {
    void get(Request request, Response response);

    void post(Request request, Response response);

}
