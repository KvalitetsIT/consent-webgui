package dk.kvalitetsit.consentservice;

import dk.kvalitetsit.consentservice.controller.ConsentController;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GivenJsonWithUidKeyTest {

    @Test
    public void CorrectUidIsExtractedFromBase64Json() throws IOException {
        int expected = 123456;
        String uidKey = "cprnumber";
        String testJson = "{\"UserAttributes\": {" + "\n" + "  " + "\"" + uidKey + "\": [\n" + "\"" + expected + "\"\n]}\n}";
        String uid = new ConsentController().getUid(uidKey, Base64.encodeBase64String(testJson.getBytes()));
        Assertions.assertEquals(Integer.toString(expected), uid);
    }
}