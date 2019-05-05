package demos.expmind.andromeda.di;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;

import dagger.Module;
import dagger.Provides;
import demos.expmind.andromeda.AndromedaApp;
import demos.expmind.andromeda.common.AppExecutors;

/**
 * Application wide dependencies
 */
@Module
public class AppModule {

    @Provides
    Context providesContext(AndromedaApp app) {
        return app.getApplicationContext();
    }

    @Provides
    ConnectivityManager providesConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
    }

    @Provides
    AppExecutors providesAppExecutors() {
        return new AppExecutors();
    }

}
