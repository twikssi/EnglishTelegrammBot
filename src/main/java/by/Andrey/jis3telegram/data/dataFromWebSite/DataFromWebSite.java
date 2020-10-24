package by.Andrey.jis3telegram.data.dataFromWebSite;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.Andrey.jis3telegram.command.CommandService.dictinary;

public class DataFromWebSite {
    Document doc;

    public DataFromWebSite(String wordName) {
        try {
            this.doc = Jsoup.connect(dictinary + wordName).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String pullOutPartOfSpeechFromWebSite(){
        Elements element = doc.getElementsByClass("pos dpos");
        return Jsoup.parse(element.toString()).text();
    }

    public String pullOutTranscriptionFromWebSite(){
        Elements element = doc.getElementsByClass("ipa dipa lpr-2 lpl-1");
        return Jsoup.parse(element.toString()).text();
    }

    public String pullOutMeaningFromWebSite(){
        Elements element = doc.getElementsByClass("def ddef_d db");
        return Jsoup.parse(element.toString()).text();
    }

    public String pullOutExampleFromWebSite(){
        Elements element = doc.getElementsByClass("examp dexamp");
        return Jsoup.parse(element.toString()).text();
    }

    public List<String> getListNoFormatFieldOfWord (){
        List<String> listNoFormatFieldsOfWord = new ArrayList<>();
        listNoFormatFieldsOfWord.add(pullOutPartOfSpeechFromWebSite());
        listNoFormatFieldsOfWord.add(pullOutTranscriptionFromWebSite());
        listNoFormatFieldsOfWord.add(pullOutMeaningFromWebSite());
        listNoFormatFieldsOfWord.add(pullOutExampleFromWebSite());
        return listNoFormatFieldsOfWord;
    }
}
