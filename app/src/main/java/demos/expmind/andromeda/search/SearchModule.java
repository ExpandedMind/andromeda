package demos.expmind.andromeda.search;

import dagger.Module;
import dagger.Provides;
import demos.expmind.andromeda.data.VideoRepository;

/**
 * Specific Search screen dependencies
 */
@Module
public class SearchModule {

    @Provides
    public static SearchViewModelFactory providesSearchViewModelFactory(VideoRepository repository) {
        return new SearchViewModelFactory(repository);
    }

}
