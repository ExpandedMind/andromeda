package demos.expmind.network.models;

/**
 * Created by RAJ1GA on 02/10/2018.
 */

public class ApiKey {

    public final String param;
    public final String key;

    public ApiKey(String param, String key) {
        this.param = param != null? param: "apikey";
        this.key = key;
    }
}
