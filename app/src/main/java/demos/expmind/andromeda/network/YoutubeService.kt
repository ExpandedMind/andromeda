package demos.expmind.andromeda.network

import demos.expmind.andromeda.data.VideoListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Definition of endpoints for Youtube API
 */
interface   YoutubeService {

    @GET("search")
    fun listVideos(@Query("videoCategoryId") categoryId: String = "",
                   @Query("q") query: String = "",
                   @Query("part") part: String = "snippet",
                   @Query("eventType") eventType: String = "completed",
                   @Query("maxResults") maxResults: Int = 25,
                   @Query("order") order: String = "date", // can be date, rating, title, videoCount, viewCount
                   @Query("regionCode") regionCode: String = "US",
                   @Query("relevanceLanguage") relevanceLanguage: String = "en",
                   @Query("type") type: String = "video",
                   @Query("videoCaption") captions: String = "closedCaption",
                   @Query("videoDuration") videoDuration: String = "short" // can be medium, long, any
    ) : Call<VideoListDTO>

}