package User;

import Commen.Action;
import tomTiger.Request;
import tomTiger.Response;

public class Login implements Action {
    @Override
    public void get(Request request, Response response) {
        System.out.println("好的，你已经接触到核心秘密了！！！");
    }

    @Override
    public void post(Request request, Response response) {

    }
}
