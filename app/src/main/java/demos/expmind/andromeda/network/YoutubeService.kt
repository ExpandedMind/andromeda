package demos.expmind.andromeda.network

import demos.expmind.andromeda.data.VideoListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Definition of endpoints for Youtube API
 */
interface YoutubeService {

    @GET("search")
    fun listVideos(@Query("channelId") channelID: String,
                   @Query("part") part: String = "snippet",
                   @Query("maxResults") maxResults: Int = 30,
                   @Query("order") order: String = "date", // can be date, rating, title, videoCount, viewCount
                   @Query("type") type: String = "video",
                   @Query("videoCaption") captions: String = "closedCaption",
                   @Query("videoDuration") videoDuration: String = "any" // can be medium, long, any
    ) : Call<VideoListDTO>

    @GET("search")
    fun searchVideos(@Query("q") query: String,
                     @Query("part") part: String = "snippet",
                     @Query("maxResults") maxResults: Int = 50,
                     @Query("order") order: String = "relevance", // can be date, rating, title, videoCount, viewCount
                     @Query("relevanceLanguage") relevanceLanguage: String = "en",
                     @Query("type") type: String = "video",
                     @Query("videoCaption") captions: String = "closedCaption",
                     @Query("videoDuration") videoDuration: String = "any" // can be medium, long, any
    ) : Call<VideoListDTO>

}