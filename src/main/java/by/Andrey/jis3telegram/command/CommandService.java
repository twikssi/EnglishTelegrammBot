package by.Andrey.jis3telegram.command;

import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.data.dataFromWebSite.DataFromWebSite;
import by.Andrey.jis3telegram.data.service.ValidateService;

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
        try {
            word = getRandomWord(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
            rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", word);
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataFromWebSite moreExamplesFromWebSite = new DataFromWebSite(word.getName());
        String moreEx = ValidateService.getAmazingViewMoreExamples(moreExamplesFromWebSite.pullOutMoreExamplesFromWebSite());
        if (moreEx.equals("")){
            return word.getAmazingView();
        } else {
            return word.getAmazingView() + "More Examples:" + "\n" + moreEx;
        }
    }




}
