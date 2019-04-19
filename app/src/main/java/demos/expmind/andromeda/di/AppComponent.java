package demos.expmind.andromeda.di;

import javax.inject.Singleton;

import dagger.Component;
import demos.expmind.andromeda.welcome.VideoListFragment;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {

     void inject(VideoListFragment videoListFragment);
}
