package demos.expmind.andromeda.db;

import android.content.Context;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import demos.expmind.andromeda.data.Caption;
import demos.expmind.andromeda.data.Video;
import demos.expmind.andromeda.data.YoutubeChannels;
import demos.expmind.andromeda.data.local.AndromedaDatabase;
import demos.expmind.andromeda.data.local.VideoEntity;
import demos.expmind.andromeda.data.local.VideosDao;



@RunWith(AndroidJUnit4.class)
public class VideosDAOTest {

    private VideosDao dao;
    private AndromedaDatabase db;
    private static List<Caption> sampleCaptions = new ArrayList<>();
    private static VideoEntity motivationalSpeech, todayNews, rollingInTheDeep, science1minute, howTos;

    static {
        Collections.addAll(sampleCaptions, new Caption("I have found, you have found"),
                new Caption("Before you, living grown."),
                new Caption("World demise. [x2]"));
    }

    @BeforeClass
    public static void generateTestData() {
        motivationalSpeech = new VideoEntity(new Video("zhtwex", "TED talk", "thumb", "2m12s", YoutubeChannels.TED, Collections.<Caption>emptyList()));
        todayNews = new VideoEntity(new Video("owi276cc", "Breaking News May 15", "", "14m56s", YoutubeChannels.BBC, Collections.<Caption>emptyList()));
        rollingInTheDeep = new VideoEntity(new Video("77s5sgeb", "Rolling In the Deep - Adele", "", "3m:35s", YoutubeChannels.BBC, Collections.<Caption>emptyList()));
        science1minute = new VideoEntity(new Video("ooqux2h5", "Bozons Higgs", "", "1m01s", YoutubeChannels.SCIENCE, Collections.<Caption>emptyList()));
        howTos = new VideoEntity(new Video("88sj2nx62g", "How to cook eggs", "", "2m10s", YoutubeChannels.ENGVID, Collections.<Caption>emptyList()));
    }

    @Before
    public void setup() {
        Context context = InstrumentationRegistry.getContext();
        db = Room.inMemoryDatabaseBuilder(context, AndromedaDatabase.class).build();
        dao = db.videosDao();
    }

    @After
    public void cleanup() {
        db.close();
    }

    @Test
    public void insert_insertsSingleVideo() {
        Video domainModel = new Video("ajth198",
                "World demise",
                "http://ezl.yn.r",
                "03m48s",
                YoutubeChannels.SCIENCE,
                sampleCaptions
        );
        VideoEntity newEntity = new VideoEntity(domainModel);

        dao.insert(newEntity);

        Assertions.assertThat(newEntity).isEqualTo(dao.loadByYoutubeID("ajth198"));
    }


    @Test
    public void insertAll_insertsAllVideos() {
        List<VideoEntity> expectedResult = new ArrayList<>();
        Collections.addAll(expectedResult, motivationalSpeech, rollingInTheDeep, science1minute, howTos, todayNews);

        dao.insertAll(howTos, motivationalSpeech, todayNews, rollingInTheDeep, science1minute);

        Assertions.assertThat(dao.getAll()).containsAll(expectedResult);
    }

    @Test
    public void filterByChannel_returnsExpectedVideos() {
        dao.insertAll(howTos, motivationalSpeech, todayNews, rollingInTheDeep, science1minute);

        Assertions.assertThat(dao.filterByChannel(YoutubeChannels.BBC))
                .containsOnly(rollingInTheDeep, todayNews);
        Assertions.assertThat(dao.filterByChannel(YoutubeChannels.SCIENCE))
                .containsOnly(science1minute);
        Assertions.assertThat(dao.filterByChannel(YoutubeChannels.TED)).containsOnly(motivationalSpeech);
        Assertions.assertThat(dao.filterByChannel(YoutubeChannels.VOA)).isEmpty();
    }

    @Test
    public void filterByChannel_givenNoResults_returnsEmptyList() {
        dao.insertAll(howTos, todayNews, science1minute);

        Assertions.assertThat(dao.filterByChannel(YoutubeChannels.SKILLS)).isEmpty();
    }

    @Test
    public void updateVideoInfo_updatesAllModifiedProperties() {
        dao.insert(science1minute);

        VideoEntity updatedVideo = new VideoEntity(science1minute);
        List<Caption> newCaptions = new ArrayList<>();
        Collections.addAll(newCaptions, new Caption("cc 1"), new Caption("cc 2"), new Caption("cc 3"));
        updatedVideo.captions = newCaptions;
        updatedVideo.title = "Alpha waves on the Sun";
        updatedVideo.channel = YoutubeChannels.ENGVID.name();


        dao.updateVideoInfo(updatedVideo);

        Assertions.assertThat(dao.loadByYoutubeID(science1minute.id)).isEqualTo(updatedVideo);

    }

    @Test
    public void deleteAll_deletesAllVideosFromDB() {
        dao.insertAll(howTos, rollingInTheDeep, motivationalSpeech);

        dao.deleteAll();

        Assertions.assertThat(dao.getAll()).isEmpty();
    }


}
