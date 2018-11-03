package demos.expmind.network_client;


import java.util.HashMap;
import java.util.Map;

import demos.expmind.network_client.interceptors.ApiKeyInterceptor;
import demos.expmind.network_client.models.ApiKey;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

    private static Retrofit currentRetrofit;
    /**
     * Caches retrofit instances under base url key to avoid creating multiple objects with the same
     * url.
     */
    private static Map<String, Retrofit> urlRetrofitInstanceMap = new HashMap<>();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    /**
     * We share same OkHttpClient among retrofit instances to optimise overall app performance
     */
    private static OkHttpClient commonHttpClient =
            new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build();


    private ServiceGenerator() {
        // No need to instantiate this class.
    }

    /**
     * Registers a new supported base url to the retrofit instance collection.
     * Make sure to call this method at least once before creating any service.
     *
     * @param newApiBaseUrl url to support. If url is new , then it is added to the collection of supported
     *                      urls, if already exists, then ServiceGenerator switches to this
     */
    public static void setBaseUrl(String newApiBaseUrl) {
        setBaseUrl(newApiBaseUrl, null);
    }

    public static void setBaseUrl(String newApiBaseUrl, ApiKey key) {
        if (urlRetrofitInstanceMap.containsKey(newApiBaseUrl)) {
            currentRetrofit = urlRetrofitInstanceMap.get(newApiBaseUrl);
        } else {
            Retrofit.Builder builder = new Retrofit.Builder();
            if (key != null) {
                ApiKeyInterceptor interceptor = new ApiKeyInterceptor(key);
                // Sharing core OkHttpClient (request pooling, disk cache, …)
                OkHttpClient customHttpClient =
                        commonHttpClient.newBuilder().addInterceptor(interceptor).build();
                builder.client(customHttpClient);
            } else {
                builder.client(commonHttpClient);
            }
            builder.addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(newApiBaseUrl);
            urlRetrofitInstanceMap.put(newApiBaseUrl, builder.build());
            currentRetrofit = urlRetrofitInstanceMap.get(newApiBaseUrl);
        }
    }

    /**
     * Generates specific service class with the given setup
     *
     * @param serviceClass Class that contains action methods to call apis
     * @param <S>          Class type for service to create
     * @return Service
     */
    public static <S> S createService(Class<S> serviceClass) {
        return create(serviceClass);
    }

    private static <S> S create(Class<S> serviceClass) {
        if (currentRetrofit == null) {
            throw new IllegalStateException("API base URL has not been configured yet. Call " +
                    "setBaseUrl() method before creating a Service.");
        }

        return currentRetrofit.create(serviceClass);
    }

}
