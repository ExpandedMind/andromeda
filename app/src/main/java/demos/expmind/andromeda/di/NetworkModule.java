package demos.expmind.andromeda.di;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import demos.expmind.andromeda.BuildConfig;
import demos.expmind.andromeda.network.ApiKeyInterceptor;
import demos.expmind.andromeda.network.CaptionsService;
import demos.expmind.andromeda.network.YoutubeService;
import demos.expmind.network.models.ApiKey;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

@Module
public abstract class NetworkModule {

    @Provides
    @Singleton
    public static OkHttpClient providesCommonOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    @Provides
    @Reusable
    public static YoutubeService providesYoutubeService(OkHttpClient commonClient) {
        ApiKeyInterceptor keyInterceptor =
                new ApiKeyInterceptor(new ApiKey("key", BuildConfig.YOUTUBE_DEVELOPER_KEY));
        OkHttpClient customOkHttpClient =
                commonClient.newBuilder().addInterceptor(keyInterceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.YOUTUBE_URL)
                .client(customOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(YoutubeService.class);
    }

    @Provides
    @Reusable
    public static CaptionsService providesCaptionsService(OkHttpClient commonClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.VIDEO_CAPTIONS_URL)
                .client(commonClient)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        return retrofit.create(CaptionsService.class);
    }

}
