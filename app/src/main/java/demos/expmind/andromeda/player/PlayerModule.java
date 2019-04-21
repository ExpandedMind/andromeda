package demos.expmind.andromeda.player;

import dagger.Module;
import dagger.Provides;
import demos.expmind.andromeda.network.CaptionsService;

/**
 * Defines PlayerActivity specific dependencies
 */
@Module
public class PlayerModule {

    @Provides
    PlayerContract.Presenter providesPlayerPresenter(PlayerContract.View view, CaptionsService service) {
        return new PlayerPresenter(view, service);
    }
}
