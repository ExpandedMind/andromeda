package demos.expmind.andromeda.di;

import android.content.Context;

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
    AppExecutors providesAppExecutors() {
        return new AppExecutors();
    }

}
