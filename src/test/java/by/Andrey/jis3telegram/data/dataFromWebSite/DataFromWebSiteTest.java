package by.Andrey.jis3telegram.data.dataFromWebSite;

import org.junit.Test;

import java.util.Arrays;

public class DataFromWebSiteTest {

    @Test
    public void catchPartOfSpeechFromWebSite() {
        DataFromWebSite fromWebSite = new DataFromWebSite("pleasant");
        String[] result = fromWebSite.breakStringOnPeaceWithSpace(fromWebSite.pullOutPartOfSpeechFromWebSite());
        System.out.println(result[0]);
    }

    @Test
    public void pullOutTranscriptionFromWebSite() {
        DataFromWebSite fromWebSite = new DataFromWebSite("settle");
        String result = (fromWebSite.pullOutTranscriptionFromWebSite());
        System.out.println(result);
    }

    @Test
    public void pullOutMeaningFromWebSite() {
        DataFromWebSite fromWebSite = new DataFromWebSite("kiss");
        String result = (fromWebSite.pullOutMeaningFromWebSite());
        String[] resultAr = fromWebSite.breakStringOnPeaceWithTwoDotes(result);
        System.out.println(resultAr[0]);
    }

    @Test
    public void pullOutExampleFromWebSite() {
        DataFromWebSite fromWebSite = new DataFromWebSite("money");
        String result = (fromWebSite.pullOutExampleFromWebSite());
        String resultAr = fromWebSite.breakStringOnPeaceWithDotAndSpace(result);
        System.out.println(resultAr);
    }
}