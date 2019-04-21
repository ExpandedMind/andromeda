package demos.expmind.andromeda.di;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import demos.expmind.andromeda.AndromedaApp;
import demos.expmind.andromeda.welcome.VideoListFragment;

@Singleton
@Component(modules = {AppModule.class,
        AndroidSupportInjectionModule.class,
        NetworkModule.class,
        BuildersModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder bindsApplication(AndromedaApp app);
        AppComponent build();
    }

    void inject(AndromedaApp app);

    void inject(VideoListFragment videoListFragment);
}
