package demos.expmind.andromeda.network

import demos.expmind.andromeda.data.VideoListDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Definition of endpoints for Youtube API
 */
interface   YoutubeService {

    @GET("videos")
    fun listVideos(@Query("videoCategoryId") categoryId: String,
                   @Query("part") part: String = "snippet,contentDetails",
                   @Query("chart") chart: String = "mostPopular",
                   @Query("maxResults") maxResults: Int = 10,
                   @Query("regionCode") regionCode: String = "US",
                   @Query("prettyPrint") prettyPrint: Boolean = true
    ) : Call<VideoListDTO>

}