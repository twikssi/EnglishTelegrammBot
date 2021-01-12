package by.Andrey.jis3telegram.data.dataFromWebSite;

import by.Andrey.jis3telegram.data.service.ValidateService;
import org.junit.Test;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataFromWebSiteTest {


//    @Test
//    public void pullOutMoreExamplesFromWebSiteNew() {
//        DataFromWebSite fromWebSite = new DataFromWebSite("settle");
//        List<String> result = (fromWebSite.pullOutPartOfSpeechFromWebSiteNew());
//        result.forEach(System.out::println);
//        System.out.println(result.size());
//    }
//
//    @Test
//    public void pullOutTranscriptionFromWebSite() {
//        DataFromWebSite fromWebSite = new DataFromWebSite("catch up");
//        List<String> result = (fromWebSite.pullOutMeaningsFromWebSiteNew());
//
//        result.forEach(System.out::println);
//        System.out.println(result.size());
//    }
//
//    @Test
//    public void pullOutExamplesFromWebSiteNew() {
//        DataFromWebSite fromWebSite = new DataFromWebSite("settle");
//        List<String> result = (fromWebSite.pullOutExamplesFromWebSiteNew());
//
//        result.forEach(System.out::println);
//        System.out.println(result.size());
//    }

    @Test
    public void addWordVocabularyInList() {
        DataFromWebSite fromWebSite = new DataFromWebSite("take off");
        fromWebSite.addWordVocabularyInList();


        fromWebSite.getWordVocabularyList().forEach(System.out::println);

    }
}