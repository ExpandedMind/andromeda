package demos.expmind.andromeda.db;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.room.Room;
import demos.expmind.andromeda.data.Caption;
import demos.expmind.andromeda.data.Video;
import demos.expmind.andromeda.data.YoutubeChannels;
import demos.expmind.andromeda.data.local.AndromedaDatabase;
import demos.expmind.andromeda.data.local.VideoEntity;
import demos.expmind.andromeda.data.local.VideosDao;

import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class VideosDAOTest {

    private VideosDao dao;
    private AndromedaDatabase db;
    private static List<Caption> sampleCaptions = new ArrayList<>();

    static {
        Collections.addAll(sampleCaptions, new Caption("I have found, you have found"),
                new Caption("Before you, living grown."),
                new Caption("World demise. [x2]"));
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

        assertThat(newEntity, equalTo(dao.loadByYoutubeID("ajth198")));
    }


}
