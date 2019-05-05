package demos.expmind.andromeda.network

import demos.expmind.andromeda.data.TranscriptDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CaptionsService {

    @GET("timedtext")
    fun getTranscript(@Query("v") yt_id: String,
                      @Query("type") type: String = "track",
                      @Query("lang") lang: String = "en"): Call<TranscriptDTO>

}