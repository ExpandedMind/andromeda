package demos.expmind.andromeda.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/* Data Transfer Objects*/

data class VideoListDTO(val nextPageToken: String, val items: List<VideoItem>)

data class VideoItem(val id: String, val snippet: VideoSnippet, val contentDetails: ContentDetails)

data class VideoSnippet(val title: String, val thumbnails: VideoIcons)

data class VideoIcons(val default: Thumb, val medium: Thumb?, val high: Thumb?, val standard: Thumb?, val maxRes: Thumb?)

data class Thumb(val url: String, val width: Int, val height: Int)

data class ContentDetails(val duration: String, val caption: String)

/* Domain Layer Models*/
@Parcelize
data class Caption(val text: String) : Parcelable

@Parcelize
data class Video(val ytID: String, val title: String, val thumbnail: String, val duration: String,
                 val category: VideoCategory, val subtitles: List<Caption> = emptyList()) : Parcelable

/**
 * Hardcoded categories that belong official Youtube API
 */
@Parcelize
enum class VideoCategory(val categoryName: String, val ytIndex: String) : Parcelable {
    FILM("Film & Animation", "1"),
    VEHICLES("Autos & Vehicles", "2"),
    MUSIC("Music", "10"),
    PETS("Pets & Animals", "15"),
    SPORTS("Sports", "17"),
    COMEDY("Comedy", "23"),
    HOWTO("Howto's", "26"),
    GAMING("Gaming", "20"),
    SCIENCE("Science", "28"),
}
