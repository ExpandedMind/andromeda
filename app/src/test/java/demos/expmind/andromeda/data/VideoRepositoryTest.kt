package demos.expmind.andromeda.data

import demos.expmind.andromeda.RestTestHelper
import demos.expmind.andromeda.TestExecutors
import demos.expmind.andromeda.data.remote.RemoteVideoDataSource
import demos.expmind.andromeda.data.remote.VideoCategory
import demos.expmind.andromeda.network.YoutubeService
import demos.expmind.network.ServiceGenerator
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.MockitoAnnotations

/**
 * Created by RAJ1GA on 14/11/2018.
 */
class VideoRepositoryTest {

    val server: MockWebServer = MockWebServer()
    lateinit var repository: VideoRepository
    @Captor
    lateinit var getAllCallbackCaptor: ArgumentCaptor<VideoDataSource.GetAllCallback>
    @Mock
    lateinit var getAllCallback: VideoDataSource.GetAllCallback

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        server.start()
        ServiceGenerator.setBaseUrl(server.url("/").toString())
        val remoteSource = RemoteVideoDataSource.getInstance(TestExecutors(),
                ServiceGenerator.createService(YoutubeService::class.java))
        repository = VideoRepository.getInstance(remoteSource)
    }

    @After
    fun tearDown() {
        server.shutdown()
        VideoRepository.destroyInstance()
    }

    @Test
    fun getAll_givenCategoryFirstCall_requestsVideosFromRemoteSource() {
        server.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(RestTestHelper.VIDEO_RESPONSE_200))

        repository.getAll(VideoCategory.MUSIC, getAllCallback)

        assertThat(repository.cacheVideos.size).isEqualTo(3)
    }

    @Test
    fun getAll_givenCategorySecondCall_loadVideosFromCache() {

    }

    @Test
    fun getAll_serviceUnavailable_notifiesOnError() {

    }

}