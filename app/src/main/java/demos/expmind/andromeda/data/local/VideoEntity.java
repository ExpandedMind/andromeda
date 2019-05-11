package demos.expmind.andromeda.data.local;

import java.util.List;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import demos.expmind.andromeda.data.Caption;
import demos.expmind.andromeda.data.Video;

@Entity
public class VideoEntity {

    @PrimaryKey
    public String id;

    public String title;

    @ColumnInfo(name = "thumb_url")
    public String thumbUrl;

    public String channel;

    public List<Caption> captions;

    public VideoEntity(Video domainModel) {
        this.id = domainModel.getYtID();
        this.title = domainModel.getTitle();
        this.thumbUrl = domainModel.getThumbnail();
        this.channel = domainModel.getCategory().name();
        this.captions = domainModel.getSubtitles();
    }
}
