package demos.expmind.andromeda.data

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import demos.expmind.andromeda.data.remote.AbstractRemoteVideoDataSource
import org.assertj.core.api.Java6Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.invocation.InvocationOnMock
import org.mockito.stubbing.Answer

/**
 * Created by RAJ1GA on 14/11/2018.
 */

class VideoRepositoryTest {

    lateinit var repository: VideoRepository
    @Mock
    lateinit var getAllCallback: VideoDataSource.GetAllCallback
    @Mock
    lateinit var searchCallback: Searchable.Callback
    @Mock
    lateinit var mockRemoteSource: AbstractRemoteVideoDataSource
    @Mock
    lateinit var mockLocalSource: VideoDataSource
    var newsVideo: Video = Video("id1", "white house is broken", "thumb_url", "2:35",
            YoutubeChannels.BBC)
    var petsVideo1: Video = Video("idpet1", "how to call your dog", "pet_thumb1", "1:08", YoutubeChannels.VOA)
    var petsVideo2: Video = Video("idpet2", "naughty cats", "pet_thumb2", "4:16", YoutubeChannels.VOA)
    var sportsVideo: Video = Video("idsports1", "Chicago Cubs won the series", "thumb_url", "1:22",
            YoutubeChannels.TOEFL)

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = VideoRepository(mockRemoteSource, mockLocalSource)
    }

    @Test
    fun getAll_givenEmptyDB_thenLoadVideosFromRemoteSource() {
        val videosFromCloud = listOf(petsVideo1, petsVideo2)
        Mockito.doAnswer(object : Answer<InvocationOnMock> {
            override fun answer(invocation: InvocationOnMock): InvocationOnMock {
                val args = invocation.arguments
                val callback = args[1] as VideoDataSource.GetAllCallback
                callback.onSuccess(videosFromCloud)
                return invocation
            }

        }).whenever(mockRemoteSource).getAll(any(), any())

        Mockito.doAnswer(object : Answer<InvocationOnMock> {
            override fun answer(invocation: InvocationOnMock): InvocationOnMock {
                val args = invocation.arguments
                val callback = args[1] as VideoDataSource.GetAllCallback
                callback.onSuccess(listOf())
                return invocation
            }

        }).whenever(mockLocalSource).getAll(any(), any())

        repository.getAll(YoutubeChannels.VOA, getAllCallback)

        verify(mockRemoteSource).getAll(eq(YoutubeChannels.VOA), any())
        //Downloaded videos should be replicated to local source and cache
        verify(mockLocalSource).store(eq(videosFromCloud))
        assertThat(repository.cachedVideos).isEqualTo(videosFromCloud)
    }

    @Test
    fun getAll_givenPopulatedDB_returnsResultsFromDB() {
        val storedVideosOnDB = listOf(petsVideo1, petsVideo2)
        Mockito.doAnswer(object : Answer<InvocationOnMock> {
            override fun answer(invocation: InvocationOnMock): InvocationOnMock {
                val args = invocation.arguments
                val callback = args[1] as VideoDataSource.GetAllCallback
                callback.onSuccess(storedVideosOnDB)
                return invocation
            }

        }).whenever(mockLocalSource).getAll(any(), any())

        repository.getAll(YoutubeChannels.VOA, getAllCallback)

        //Verify that local source is called just once
        verify(mockLocalSource, times(1)).getAll(eq(YoutubeChannels.VOA), any())
        // Remote source is not even touched
        verify(mockRemoteSource, never()).getAll(any(), any())
        //Verify that expected videos are stored inside cache
        assertThat(repository.cachedVideos).containsAll(storedVideosOnDB)
    }

    @Test
    fun getAll_givenCachedVideos_returnsDataFromCache() {
        val cachedList: List<Video> = listOf(newsVideo, petsVideo1, petsVideo2, sportsVideo)
        repository.cachedVideos.addAll(cachedList)

        repository.getAll(YoutubeChannels.BBC, getAllCallback)

        //Verify that callback was called with expected video list
        verify(getAllCallback).onSuccess(cachedList)
        //Other sources never accessed
        verify(mockLocalSource, never()).getAll(any(), any())
        verify(mockRemoteSource, never()).getAll(any(), any())

    }

    @Test
    fun getAll_givenCloudError_replicatesErrorThroughCallback() {
        val errorMessage = "Unavailable Service 500"
        Mockito.doAnswer(object : Answer<InvocationOnMock> {
            override fun answer(invocation: InvocationOnMock): InvocationOnMock {
                val callback = invocation.arguments[1] as VideoDataSource.GetAllCallback
                callback.onError(errorMessage)
                return invocation
            }

        }).whenever(mockRemoteSource).getAll(any(), any())
        // DB is empty
        Mockito.doAnswer(object : Answer<InvocationOnMock> {
            override fun answer(invocation: InvocationOnMock): InvocationOnMock {
                val callback = invocation.arguments[1] as VideoDataSource.GetAllCallback
                callback.onSuccess(listOf())
                return invocation
            }

        }).whenever(mockLocalSource).getAll(any(), any())

        repository.getAll(YoutubeChannels.SKILLS, getAllCallback)

        verify(getAllCallback).onError(eq(errorMessage))
    }

    @Test
    fun search_searchesVideosOnCloud() {
        val query = "Digicult .."

        repository.search(query, searchCallback)

        verify(mockRemoteSource).search(eq(query), any())
    }


}