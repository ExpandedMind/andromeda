package demos.expmind.andromeda.network

/**
 * Created by RAJ1GA on 02/10/2018.
 */

data class LyricsResponse(val message: MessageDTO)

data class MessageDTO(val header: HeaderDTO, val body: BodyDTO)

data class HeaderDTO(val status_code: Int, val execute_time: Double)

data class BodyDTO(val lyrics: LyricsDTO)

data class LyricsDTO(val lyrics_id: Int, val lyrics_body: String, val lyrics_language: String)