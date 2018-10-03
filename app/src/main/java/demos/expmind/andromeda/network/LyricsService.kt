package demos.expmind.andromeda.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by RAJ1GA on 02/10/2018.
 */
interface LyricsService {

    @GET("matcher.lyrics.get")
    fun search(@Query("q_track") songTitle: String, @Query("q_artist") artist: String): Call<LyricsResponse>
}