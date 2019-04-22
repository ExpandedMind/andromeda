package demos.expmind.andromeda.data


/**
 * Created by RAJ1GA on 07/11/2018.
 */
class VideoDataMapper {

    fun fromDTOtoDomain(response: VideoListDTO, category: YoutubeChannels): List<Video> =
            response.items.map {
                Video(it.id.videoId, it.snippet.title,
                        it.snippet.thumbnails.medium?.url ?: it.snippet.thumbnails.default.url,
                        "", category)
            }


    fun formatDuration(durationString: String): String {
        val formattedDuration = StringBuilder()
        val hourExp = Regex("\\d+H")
        val minExp = Regex("\\d+M")
        val secExp = Regex("\\d+S")

        if (hourExp.containsMatchIn(durationString)) {
            val matchResult = hourExp.find(durationString)
            formattedDuration.append(matchResult!!.value.replace("H","h "))
        }

        if (minExp.containsMatchIn(durationString)) {
            val matchResult = minExp.find(durationString)
            formattedDuration.append(matchResult!!.value.replace("M", "m "))
        }

        if (secExp.containsMatchIn(durationString)) {
            val matchResult = secExp.find(durationString)
            formattedDuration.append(matchResult!!.value.replace("S", "s"))
        }

        return formattedDuration.toString()
    }

}