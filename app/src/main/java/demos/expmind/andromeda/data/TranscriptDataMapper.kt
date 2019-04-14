package demos.expmind.andromeda.data

class TranscriptDataMapper {

    fun fromDTOToModel(transcriptDTO: TranscriptDTO): List<Caption> =
            transcriptDTO.captions.map {
                Caption(it.text)
            }

}