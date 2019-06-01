package demos.expmind.andromeda.di;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import demos.expmind.andromeda.AndromedaApp;
import demos.expmind.andromeda.data.DataLayerModule;
import demos.expmind.andromeda.player.PlayerActivity;
import demos.expmind.andromeda.search.SearchActivity;
import demos.expmind.andromeda.welcome.VideoListFragment;

@Singleton
@Component(modules = {AppModule.class,
        NetworkModule.class,
        DataLayerModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder bindsApplication(AndromedaApp app);

        AppComponent build();
    }

    void inject(VideoListFragment videoListFragment);

    void inject(SearchActivity searchActivity);

    void inject(PlayerActivity playerActivity);
}
