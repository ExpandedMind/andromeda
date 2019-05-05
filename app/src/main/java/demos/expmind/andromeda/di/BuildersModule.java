package demos.expmind.andromeda.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import demos.expmind.andromeda.player.PlayerActivity;
import demos.expmind.andromeda.player.PlayerModule;
import demos.expmind.andromeda.player.PlayerViewModule;
import demos.expmind.andromeda.search.SearchActivity;
import demos.expmind.andromeda.search.SearchModule;

/**
 * Binds all subcomponents within the app
 */
@Module
public abstract class BuildersModule {

    @ContributesAndroidInjector(modules = {PlayerModule.class, PlayerViewModule.class})
    abstract PlayerActivity bindPlayerActivity();

    @ContributesAndroidInjector(modules = {SearchModule.class})
    abstract SearchActivity bindSearchActivity();
}
