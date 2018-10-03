package demos.expmind.network_client;


import demos.expmind.network_client.interceptors.ApiKeyInterceptor;
import demos.expmind.network_client.models.ApiKey;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ServiceGenerator {

    public static String apiBaseUrl = "";

    private static Retrofit retrofit;

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClientBuilder =
            new OkHttpClient.Builder();


    private ServiceGenerator() {
        // No need to instantiate this class.
    }


    public static void setupBaseUrl(String newApiBaseUrl) {
        apiBaseUrl = newApiBaseUrl;
        if (!httpClientBuilder.interceptors().contains(logging)) {
            httpClientBuilder.addInterceptor(logging);
        }
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder.build())
                .baseUrl(apiBaseUrl)
                .build();
    }

    public static <S> S createService(Class<S> serviceClass) {
        return create(serviceClass);
    }

    public static <S> S createService(Class<S> serviceClass, ApiKey key) {
        if (key != null) {
            ApiKeyInterceptor interceptor = new ApiKeyInterceptor(key);
            if (!httpClientBuilder.interceptors().contains(interceptor)) {
                httpClientBuilder.addInterceptor(interceptor);
                Retrofit.Builder builder = new Retrofit.Builder()
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClientBuilder.build())
                        .baseUrl(apiBaseUrl);
                retrofit = builder.build();
            }
        }
        return create(serviceClass);
    }


    private static <S> S create(Class<S> serviceClass) {
        if (retrofit == null) {
            throw new IllegalStateException("API base URL has not been configured yet. Call " +
                    "setupBaseUrl() method before creating a Service.");
        }

        return retrofit.create(serviceClass);
    }

}
