package demos.expmind.network_client.interceptors;


import java.io.IOException;

import demos.expmind.network_client.models.ApiKey;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by RAJ1GA on 02/10/2018.
 */

public class ApiKeyInterceptor implements Interceptor {

    private ApiKey apiKey;

    public ApiKeyInterceptor(ApiKey key) {
        this.apiKey = key;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        HttpUrl originalHttpUrl = original.url();
        HttpUrl newHttpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter(apiKey.param, apiKey.key).build();
        Request.Builder builder = original.newBuilder()
                .url(newHttpUrl)
                .method(original.method(), original.body());
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
