package by.Andrey.jis3telegram.command;

import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.data.dataFromWebSite.DataFromWebSite;
import by.Andrey.jis3telegram.data.service.ValidateService;
import by.Andrey.jis3telegram.data.togoogletranslate.ToGoogleTranslate;

import java.io.IOException;

import static by.Andrey.jis3telegram.Service.WordService.WordService.getListWordsFromListString;
import static by.Andrey.jis3telegram.Service.WordService.WordService.getRandomWord;
import static by.Andrey.jis3telegram.data.service.DataService.getListStringWordsFromFile;
import static by.Andrey.jis3telegram.data.service.DataService.rewriteFieldNumberOfRepetitionToFile;

public class CommandService {

    public static final String dictinary = "https://dictionary.cambridge.org/dictionary/english/";

    public static String getMeaningsFromWebSite(String word){
        String[] words = word.split(" ");
        if (words.length == 1){
            return dictinary.concat(word);
        } else {
            return dictinary + words[0] + "-" + words[1];
        }
    }

    public static String commandGetRandomWord(){
        Word word = new Word();
        String urlGoogletransEx = "\n Listen to examples with google translate: \n";
        String urlGoogletransMoreEx = "\n Listen to more examples with google translate: \n";
        try {
            word = getRandomWord(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
            urlGoogletransEx = urlGoogletransEx + ToGoogleTranslate.getUrlGoogleWithCorrectText(word.getExample());
            rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", word);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataFromWebSite moreExamplesFromWebSite = new DataFromWebSite(word.getName());
        String text = moreExamplesFromWebSite.pullOutMoreExamplesFromWebSite();
        String moreEx = ValidateService.getAmazingViewMoreExamples(text);
        urlGoogletransMoreEx = urlGoogletransMoreEx + ToGoogleTranslate.getUrlGoogleWithCorrectText(text);
        if (moreEx.equals("")){
            return word.getAmazingView() + urlGoogletransEx + "/n";
        } else {
            return word.getAmazingView() + "More Examples:" + "\n" + moreEx + urlGoogletransEx + urlGoogletransMoreEx;
        }
    }




}
