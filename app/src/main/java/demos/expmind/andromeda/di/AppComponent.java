package demos.expmind.andromeda.di;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import demos.expmind.andromeda.AndromedaApp;
import demos.expmind.andromeda.data.DataLayerModule;
import demos.expmind.andromeda.welcome.VideoListFragment;

@Singleton
@Component(modules = {AppModule.class,
        AndroidSupportInjectionModule.class,
        NetworkModule.class,
        BuildersModule.class,
        DataLayerModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder bindsApplication(AndromedaApp app);
        AppComponent build();
    }

    void inject(AndromedaApp app);
    //TODO Change this injection , make it thorugh AndroidInjector.inject(fragment)
    void inject(VideoListFragment videoListFragment);
}
