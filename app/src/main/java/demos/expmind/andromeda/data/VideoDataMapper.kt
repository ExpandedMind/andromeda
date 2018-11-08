package demos.expmind.andromeda.data

/**
 * Created by RAJ1GA on 07/11/2018.
 */
class VideoDataMapper {

    fun fromDTOtoDomain(response: VideoListDTO): List<Video> =
            response.items.map {
                Video(it.id, it.snippet.title,
                        it.snippet.thumbnails.medium?.url ?: it.snippet.thumbnails.default.url,
                        it.contentDetails.duration)
            }

}