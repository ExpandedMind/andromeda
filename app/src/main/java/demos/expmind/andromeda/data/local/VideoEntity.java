package demos.expmind.andromeda.data.local;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import demos.expmind.andromeda.data.Caption;
import demos.expmind.andromeda.data.Video;

@SuppressWarnings("unused")
@Entity
@TypeConverters({CaptionsConverter.class})
public class VideoEntity {

    @PrimaryKey
    @NonNull
    public String id;

    public String title;

    @ColumnInfo(name = "thumb_url")
    public String thumbUrl;

    public String channel;

    public List<Caption> captions;

    public VideoEntity(@NonNull String id, String title, String thumbUrl, String channel, List<Caption> captions) {
        this.id = id;
        this.title = title;
        this.thumbUrl = thumbUrl;
        this.channel = channel;
        this.captions = captions;
    }

    public VideoEntity(Video domainModel) {
        this.id = domainModel.getYtID();
        this.title = domainModel.getTitle();
        this.thumbUrl = domainModel.getThumbnail();
        this.channel = domainModel.getCategory().name();
        this.captions = domainModel.getSubtitles();
    }
}
