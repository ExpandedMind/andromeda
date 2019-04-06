package demos.expmind.andromeda.data

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class VideoDataMapperTest {

    lateinit var videoMapper: VideoDataMapper

    @Before
    fun setUp() {
        videoMapper = VideoDataMapper()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun formatDuration() {
        val durationSamples: List<Pair<String, String>> =
                listOf(Pair("PT8M", "8m "),
                        Pair("PT26M17S", "26m 17s"),
                        Pair("PT54S", "54s"),
                        Pair("PT1H49M57S", "1h 49m 57s"),
                        Pair("PT2H", "2h "),
                        Pair("",""),
                        Pair("!#$#cORRUPTED FormAT",""))

        for (sample in durationSamples) {
            assertEquals("${sample.first} does not match with expected format",
                    sample.second, videoMapper.formatDuration(sample.first))
        }

    }
}