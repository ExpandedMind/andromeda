package demos.expmind.andromeda.data.local;

import androidx.room.TypeConverter;
import demos.expmind.andromeda.data.YoutubeChannels;

public class YoutubeChannelConverter {

    @TypeConverter
    public YoutubeChannels toEnum(String enumName) {
        return YoutubeChannels.valueOf(enumName);
    }

    @TypeConverter
    public String toPlainString(YoutubeChannels channel) {
        return channel.name();
    }

}
