package demos.expmind.andromeda



import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest

/**
 * Created by RAJ1GA on 26/12/2018.
 */
class AndromedaDispatcher : Dispatcher() {

    override fun dispatch(request: RecordedRequest): MockResponse {
        if (isVideosByCategory(request)) {
            return okhttp3.mockwebserver.MockResponse()
                    .setResponseCode(200)
                    .setBody(VideoCategoryMockResponses.VIDEOS_LIST_OK)
        } else if (isGetSongLyrics(request)) {
            return MockResponse().setResponseCode(200)
                    .setBody(LyricsMockResponses.GET_SONG_LYRICS_OK)
        }
        return okhttp3.mockwebserver.MockResponse().setResponseCode(404)
    }

    // Endpoint matcher
    private fun isVideosByCategory(request: RecordedRequest): Boolean =
            request.method == HttpMethod.GET.name &&
                    request.requestUrl.pathSize() == 1 &&
                    request.requestUrl.pathSegments()[0] == "videos"

    private fun isGetSongLyrics(request: RecordedRequest): Boolean =
            request.method == HttpMethod.GET.name &&
                    request.requestUrl.pathSize() == 1 &&
                    request.requestUrl.pathSegments()[0] == "matcher.lyrics.get"


    enum class HttpMethod() {
        GET,
        POST(),
        PUT(),
        DELETE()
    }
}