package demos.expmind.andromeda.data;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "transcript")
public class TranscriptDTO {

    @ElementList(inline = true, required = false)
    private List<CaptionDTO> captions;

    //    public TranscriptDTO(@ElementList(inline = true, required = false)List<CaptionDTO> parsedCaptions) {
//        this.captions = (parsedCaptions != null)? parsedCaptions : new ArrayList<CaptionDTO>();
//    }
    public List<CaptionDTO> getCaptions() {
        return captions;
    }
}


@Root(name = "text")
class CaptionDTO {

    @Attribute(name = "start")
    private final String start;

    @Attribute(name = "dur")
    private final String duration;

    @org.simpleframework.xml.Text
    private final String content;

    public CaptionDTO(@Attribute(name = "start") String start,
                      @Attribute(name = "dur") String duration,
                      @org.simpleframework.xml.Text String content) {
        this.start = start;
        this.duration = duration;
        this.content = content;
    }

    public String getStart() {
        return start;
    }

    public String getDuration() {
        return duration;
    }

    public String getText() {
        return content;
    }
}

