package demos.expmind.andromeda.data

import demos.expmind.andromeda.data.remote.VideoCategory

/**
 * Created by RAJ1GA on 07/11/2018.
 */
class VideoDataMapper {

    fun fromDTOtoDomain(response: VideoListDTO, category: VideoCategory): List<Video> =
            response.items.map {
                Video(it.id, it.snippet.title,
                        it.snippet.thumbnails.medium?.url ?: it.snippet.thumbnails.default.url,
                        it.contentDetails.duration, category)
            }

}