package demos.expmind.andromeda.data;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import demos.expmind.andromeda.common.AppExecutors;
import demos.expmind.andromeda.data.local.AndromedaDatabase;
import demos.expmind.andromeda.data.local.LocalVideoDataSource;
import demos.expmind.andromeda.data.remote.RemoteVideoDataSource;
import demos.expmind.andromeda.network.YoutubeService;

@Module
public class DataLayerModule {

    @Provides
    @Singleton
    public static AndromedaDatabase providesAppDatabase(Context applicationContext) {
        return Room.databaseBuilder(applicationContext, AndromedaDatabase.class, "andromeda.db")
                .build();

    }

    @Provides
    public static RemoteVideoDataSource providesRemoteVideoDataSource(AppExecutors appExecutors, YoutubeService service) {
        return RemoteVideoDataSource.getInstance(appExecutors, service);
    }

    @Provides
    public static LocalVideoDataSource providesLocalVideoDataSource(AppExecutors appExecutors, AndromedaDatabase db, VideoDataMapper mapper) {
        return LocalVideoDataSource.getInstance(appExecutors, db.videosDao(), mapper);
    }

    @Provides
    public static VideoRepository providesVideoRepository(RemoteVideoDataSource remoteVideoDataSource, LocalVideoDataSource localSource) {
        return new VideoRepository(remoteVideoDataSource, localSource);
    }
}
