package demos.expmind.andromeda.di;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import demos.expmind.andromeda.AndromedaApp;

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
