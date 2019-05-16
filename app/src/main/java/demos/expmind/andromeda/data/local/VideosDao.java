package demos.expmind.andromeda.data.local;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;
import demos.expmind.andromeda.data.YoutubeChannels;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
@TypeConverters({CaptionsConverter.class, YoutubeChannelConverter.class})
public interface VideosDao {

    @Query("SELECT * FROM VideoEntity")
    List<VideoEntity> getAll();

    @Query("SELECT * FROM VideoEntity WHERE VideoEntity.channel = :channel")
    List<VideoEntity> filterByChannel(YoutubeChannels channel);

    @Query("SELECT * FROM VideoEntity WHERE VideoEntity.id = :ytID")
    VideoEntity loadByYoutubeID(String ytID);

    @Insert(onConflict = REPLACE)
    void insert(VideoEntity newVideo);

    @Insert(onConflict = REPLACE)
    void insertAll(VideoEntity... videos);

    @Update(onConflict = REPLACE)
    void updateVideoInfo(VideoEntity video);

    @Query("DELETE FROM VideoEntity")
    void deleteAll();

}
