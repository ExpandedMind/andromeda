package demos.expmind.andromeda.data.local;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import demos.expmind.andromeda.data.Caption;

import static org.assertj.core.api.Assertions.*;

public class CaptionsConverterTest {

    private CaptionsConverter converter = new CaptionsConverter();

    @Test
    public void serializeToJSON() {
        List<Caption> captionList = new ArrayList<>();
        captionList.add(new Caption("Yeah, we're goin' to the roadhouse"));
        captionList.add(new Caption("We're gonna have a real"));
        captionList.add(new Caption("A-good time"));

        String jsonResult = converter.serializeToJSON(captionList);

        assertThat(jsonResult).isEqualToIgnoringWhitespace("[{\"text\":\"Yeah, we\\u0027re goin\\u0027 to the roadhouse\"},{\"text\":\"We\\u0027re gonna have a real\"},{\"text\":\"A-good time\"}]");
    }

    @Test
    public void deserializeFromJSON() {

        List<Caption> listResult = converter.deserializeFromJSON("[{\"text\":\"Yeah, we\\u0027re goin\\u0027 to the roadhouse\"},{\"text\":\"We\\u0027re gonna have a real\"},{\"text\":\"A-good time\"}]");

        assertThat(listResult).containsExactly(new Caption("Yeah, we're goin' to the roadhouse"),
                new Caption("We're gonna have a real"),
                new Caption("A-good time"));
    }
}