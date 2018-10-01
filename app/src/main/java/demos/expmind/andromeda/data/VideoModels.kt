package demos.expmind.andromeda.data


/* Data Transfer Objects*/

/* Domain Layer Models*/
data class Caption(val text: String)

data class Video(val ytID: String, val subtitles: List<Caption>)