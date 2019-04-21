package demos.expmind.andromeda.di;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable;
import demos.expmind.andromeda.AndromedaApp;
import demos.expmind.andromeda.BuildConfig;
import demos.expmind.andromeda.network.ApiKeyInterceptor;
import demos.expmind.andromeda.network.YoutubeService;
import demos.expmind.network.models.ApiKey;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Application wide dependencies
 */
@Module
public class AppModule {

    @Provides
    Context providesContext(AndromedaApp app) {
        return app.getApplicationContext();
    }


}
