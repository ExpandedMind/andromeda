package demos.expmind.andromeda.player;

import dagger.Binds;
import dagger.Module;

/**
 * This module class keeps abstract because we can not directly create an Activity so
 * Dagger / Dagger Android frameworks
 */
@Module
public abstract class PlayerViewModule {

    @Binds
    abstract PlayerContract.View providesPlayerView(PlayerActivity activity);
}
