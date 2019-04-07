package demos.expmind.andromeda.data

import demos.expmind.andromeda.RestTestHelper
import demos.expmind.andromeda.TestExecutors
import demos.expmind.andromeda.data.remote.RemoteVideoDataSource
import demos.expmind.andromeda.network.YoutubeService
import demos.expmind.network.ServiceGenerator
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

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
    @Mock
    lateinit var mockRemoteSource: VideoDataSource
    var newsVideo: Video = Video("id1", "white house is broken", "thumb_url", "2:35",
            VideoCategory.SCIENCE)
    var petsVideo1: Video = Video("idpet1", "how to call your dog", "pet_thumb1", "1:08", VideoCategory.PETS)
    var petsVideo2: Video = Video("idpet2", "naughty cats", "pet_thumb2", "4:16", VideoCategory.PETS)
    var sportsVideo: Video = Video("idsports1", "Chicago Cubs won the series", "thumb_url", "1:22",
            VideoCategory.SPORTS)

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

    /**
     * For this test we use mock server to verify that our components properly parse server
     * response.
     */
    @Test
    fun getAll_givenCategoryCall_requestsVideosToServer() {
        server.enqueue(MockResponse()
                .setResponseCode(200)
                .setBody(RestTestHelper.VIDEO_RESPONSE_200))

        repository.getAll(VideoCategory.MUSIC, getAllCallback)

        assertThat(repository.cacheVideos.size).isEqualTo(3)
    }

    /**
     * For this test, we use mock data source dependencies to check logic.
     */
    @Test
    fun getAll_givenCategorySecondCall_loadVideosFromCache() {
        //setup repository with mock source
        VideoRepository.destroyInstance()
        repository = VideoRepository.getInstance(mockRemoteSource)
        doAnswer(object : Answer<InvocationOnMock> {
            override fun answer(invocation: InvocationOnMock): InvocationOnMock {
                val args = invocation.arguments
                val callback = args[1] as VideoDataSource.GetAllCallback
                callback.onSuccess(listOf(petsVideo1, petsVideo2))
                return invocation
            }

        }).`when`(mockRemoteSource).getAll(any(), any())
        //setup source

        //Make call twice
        repository.getAll(VideoCategory.PETS, getAllCallback)
        repository.getAll(VideoCategory.PETS, getAllCallback)

        //Verify that remote source is called just once
        verify(mockRemoteSource, times(1)).getAll(any(), any())
        //Verify that expected videos are stored inside cache
        assertThat(repository.cacheVideos.values).containsAll(listOf(petsVideo1, petsVideo2))
    }

    @Test
    fun getAll_serviceUnavailable_notifiesOnError() {
        server.enqueue(MockResponse().setResponseCode(500).setBody("Service Unavailable"))

        repository.getAll(VideoCategory.SCIENCE, getAllCallback);

        verify(getAllCallback).onError()
    }

    @Test
    fun getAll_withPreviousCachedVideos_returnsVideosFromCorrespondingCategoryOnly() {
        val sampleVideos: Map<String, Video> = mapOf(Pair(newsVideo.ytID, newsVideo),
                Pair(petsVideo1.ytID, petsVideo1),
                Pair(petsVideo2.ytID, petsVideo2),
                Pair(sportsVideo.ytID, sportsVideo))
        repository.cacheVideos.putAll(sampleVideos)

        repository.getAll(VideoCategory.SCIENCE, getAllCallback)

        //Verify that callback was called with expected video list
        verify(getAllCallback).onSuccess(listOf(newsVideo))
    }

    @Test
    fun getAll_emptyCacheForGivenCategory_thenRemoteIsTriggered() {
        VideoRepository.destroyInstance()
        repository = VideoRepository.getInstance(mockRemoteSource)
        val sampleVideos: Map<String, Video> = mapOf(Pair(newsVideo.ytID, newsVideo),
                Pair(petsVideo1.ytID, petsVideo1),
                Pair(petsVideo2.ytID, petsVideo2),
                Pair(sportsVideo.ytID, sportsVideo))
        repository.cacheVideos.putAll(sampleVideos)

        repository.getAll(VideoCategory.MUSIC, getAllCallback)

        verify(mockRemoteSource).getAll(any(), any())
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T
}