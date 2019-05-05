package demos.expmind.andromeda.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/* Data Transfer Objects*/

data class VideoListDTO(val nextPageToken: String, val items: List<VideoItem>)

data class VideoItem(val id: VideoID, val snippet: VideoSnippet)

data class VideoID(val kind: String, val videoId: String)

data class VideoSnippet(val title: String, val thumbnails: VideoIcons, val channelTitle: String)

data class VideoIcons(val default: Thumb, val medium: Thumb?, val high: Thumb?)

data class Thumb(val url: String, val width: Int, val height: Int)


/* Domain Layer Models*/
@Parcelize
data class Caption(val text: String) : Parcelable

@Parcelize
data class Video(val ytID: String, val title: String, val thumbnail: String, val duration: String,
                 val category: YoutubeChannels, val subtitles: List<Caption> = emptyList()) : Parcelable

/**
 * Hardcoded categories that belong official Youtube API
 */
@Parcelize
enum class YoutubeChannels(val channelName: String, val ytIndex: String, val lang: String = "en") : Parcelable {
    TED("TED Talks", "UCAuUUnT6oDeKwE6v1NGQxug"),
    SCIENCE("Science", "UCUHW94eEFW7hkUMVaZz4eDg"),
    BBC("BBC Learning", "UCHaHD477h-FeBbVh9Sh7syA", "en-GB"),
    VOA("Voice of America", "UCKyTokYo0nK2OA-az-sDijA"),
    TOEFL("Toefl TV", "UCEI_IuJ2jdqjYl0T-k04RLw"),
    ENGVID("English with Emma", "UCVBErcpqaokOf4fI5j73K_w"),
    OXFORD("Oxford Online", "UCNbeSPp8RYKmHUliYBUDizg"),
    TV("TV Series", "UCKgpamMlm872zkGDcBJHYDg"),
    SKILLS("Daily Life", "UCC3UrdqNf6rpWZoTiYbYk4A", "en-GB"),
    IELTS("IELTS", "UCicjynhfFw2LiIQFnoS1JTw", "en-GB")
}
