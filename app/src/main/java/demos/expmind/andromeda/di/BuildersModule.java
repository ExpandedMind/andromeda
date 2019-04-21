package demos.expmind.andromeda.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import demos.expmind.andromeda.player.PlayerActivity;
import demos.expmind.andromeda.player.PlayerModule;
import demos.expmind.andromeda.player.PlayerViewModule;

/**
 * Binds all subcomponents within the app
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = {PlayerModule.class, PlayerViewModule.class})
    abstract PlayerActivity bindPlayerActivity();
}
