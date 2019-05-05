package demos.expmind.andromeda;

import org.junit.Before;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import javax.xml.stream.XMLStreamException;

import demos.expmind.andromeda.data.TranscriptDTO;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

public class SimpleXMLTest {

    private Serializer serializer;

    @Before
    public void setup() {
        serializer = new Persister();
    }

    @Test
    public void read_givenTranscriptXML_returnsTranscriptDTO() {
        TranscriptDTO transcript = null;
        try {
            transcript = serializer.read(TranscriptDTO.class, XMLProvider.REGULAR_TRANSCRIPT);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(36, transcript.getCaptions().size());
    }

    /**
     * BE AWARE!! If expected xml is an empty string , then serializer throws {@link XMLStreamException}
     *
     * @throws XMLStreamException
     */
    @Test(expected = XMLStreamException.class)
    public void read_givenEmptyXML_returnsEmptyString() throws Exception {

        serializer.read(TranscriptDTO.class, XMLProvider.EMPTY_XML);

    }

    @Test
    public void read_givenEmptyTranscriptXML_returnsNullCaptionlist() throws Exception {
        TranscriptDTO transcript = serializer.read(TranscriptDTO.class, XMLProvider.EMPTY_TRANSCRIPT);
        assertNull(transcript.getCaptions());
    }

}

class XMLProvider {

    static final String EMPTY_XML = "";

    static final String EMPTY_TRANSCRIPT =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<transcript>\n" +
                    "</transcript>";

    static final String REGULAR_TRANSCRIPT =
            "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<transcript>\n" +
                    "    <text start=\"1.472\" dur=\"2.251\">\n" +
                    "        - [Narrator] Popcorn, movies, a match made\n" +
                    "    </text>\n" +
                    "    <text start=\"3.723\" dur=\"1.941\">\n" +
                    "        in theater heaven.\n" +
                    "    </text>\n" +
                    "    <text start=\"5.664\" dur=\"3.5\">\n" +
                    "        But there was a time when this was banned.\n" +
                    "    </text>\n" +
                    "    <text start=\"13.92\" dur=\"3.008\">\n" +
                    "        Popcorn has been around\n" +
                    "for over 8,000 years,\n" +
                    "    </text>\n" +
                    "    <text start=\"16.928\" dur=\"2.517\">\n" +
                    "        but when it hit American\n" +
                    "streets in the mid-1800s,\n" +
                    "    </text>\n" +
                    "    <text start=\"19.445\" dur=\"1.6\">\n" +
                    "        it took off.\n" +
                    "    </text>\n" +
                    "    <text start=\"21.045\" dur=\"2.432\">\n" +
                    "        It was cheap and could be\n" +
                    "mass produced on the go,\n" +
                    "    </text>\n" +
                    "    <text start=\"23.477\" dur=\"2.571\">\n" +
                    "        not to mention it smelled amazing.\n" +
                    "    </text>\n" +
                    "    <text start=\"26.048\" dur=\"1.803\">\n" +
                    "        It was the go-to snack at circuses,\n" +
                    "    </text>\n" +
                    "    <text start=\"27.851\" dur=\"1.706\">\n" +
                    "        sporting events and fairs.\n" +
                    "    </text>\n" +
                    "    <text start=\"29.557\" dur=\"2.56\">\n" +
                    "        In fact the only place\n" +
                    "you wouldn&amp;#39;t find popcorn\n" +
                    "    </text>\n" +
                    "    <text start=\"32.117\" dur=\"1.5\">\n" +
                    "        was at the movies.\n" +
                    "    </text>\n" +
                    "    <text start=\"34.581\" dur=\"1.974\">\n" +
                    "        You see, going to the movies used to be\n" +
                    "    </text>\n" +
                    "    <text start=\"36.555\" dur=\"1.941\">\n" +
                    "        a major event.\n" +
                    "    </text>\n" +
                    "    <text start=\"38.496\" dur=\"2.24\">\n" +
                    "        The only people that went\n" +
                    "were fancy rich folks\n" +
                    "    </text>\n" +
                    "    <text start=\"40.736\" dur=\"2.453\">\n" +
                    "        because you had to be\n" +
                    "educated enough to read.\n" +
                    "    </text>\n" +
                    "    <text start=\"43.189\" dur=\"3.2\">\n" +
                    "        Fancy, like there was even a coat check.\n" +
                    "    </text>\n" +
                    "    <text start=\"46.389\" dur=\"2.443\">\n" +
                    "        But there was definitely\n" +
                    "no concession stand.\n" +
                    "    </text>\n" +
                    "    <text start=\"48.832\" dur=\"2.165\">\n" +
                    "        There was, however, popcorn street vendors\n" +
                    "    </text>\n" +
                    "    <text start=\"50.997\" dur=\"1.931\">\n" +
                    "        who set up shop outside theaters.\n" +
                    "    </text>\n" +
                    "    <text start=\"52.928\" dur=\"2.848\">\n" +
                    "        They made a killing selling\n" +
                    "to waiting theater goers.\n" +
                    "    </text>\n" +
                    "    <text start=\"55.776\" dur=\"1.425\">\n" +
                    "        Theater goers that started smuggling\n" +
                    "    </text>\n" +
                    "    <text start=\"57.201\" dur=\"1.685\">\n" +
                    "        their popped treats inside.\n" +
                    "    </text>\n" +
                    "    <text start=\"58.886\" dur=\"1.248\">\n" +
                    "        Not cool.\n" +
                    "    </text>\n" +
                    "    <text start=\"60.134\" dur=\"2.144\">\n" +
                    "        Early movie theaters kindly asked patrons\n" +
                    "    </text>\n" +
                    "    <text start=\"62.278\" dur=\"2.102\">\n" +
                    "        check their popcorn before entering.\n" +
                    "    </text>\n" +
                    "    <text start=\"64.38\" dur=\"3.253\">\n" +
                    "        Then, in 1927, films started adding sound,\n" +
                    "    </text>\n" +
                    "    <text start=\"67.633\" dur=\"2.08\">\n" +
                    "        meaning everyone went to the movies.\n" +
                    "    </text>\n" +
                    "    <text start=\"69.713\" dur=\"1.835\">\n" +
                    "        The Great Depression\n" +
                    "followed making movies\n" +
                    "    </text>\n" +
                    "    <text start=\"71.548\" dur=\"1.237\">\n" +
                    "        a cheap escape.\n" +
                    "    </text>\n" +
                    "    <text start=\"72.785\" dur=\"2.613\">\n" +
                    "        Huge crowds plus crunch\n" +
                    "muffling sound equaled\n" +
                    "    </text>\n" +
                    "    <text start=\"75.398\" dur=\"2.912\">\n" +
                    "        another revenue opportunity\n" +
                    "for theater owners.\n" +
                    "    </text>\n" +
                    "    <text start=\"78.31\" dur=\"2.934\">\n" +
                    "        By 1945, over half the popcorn consumed\n" +
                    "    </text>\n" +
                    "    <text start=\"81.244\" dur=\"2.762\">\n" +
                    "        in America was being eaten at the movies.\n" +
                    "    </text>\n" +
                    "    <text start=\"84.006\" dur=\"2.262\">\n" +
                    "        A marriage that has continued ever since.\n" +
                    "    </text>\n" +
                    "    <text start=\"86.268\" dur=\"3.583\">\n" +
                    "        ♫ To get ourselves a treat\n" +
                    "    </text>\n" +
                    "</transcript>";
}
