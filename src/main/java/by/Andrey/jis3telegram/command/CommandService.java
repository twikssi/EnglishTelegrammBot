package by.Andrey.jis3telegram.command;

import by.Andrey.jis3telegram.Service.WordService.WordService;
import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.data.dataFromWebSite.DataFromWebSite;
import by.Andrey.jis3telegram.data.service.DataService;
import by.Andrey.jis3telegram.data.service.ValidateService;
import by.Andrey.jis3telegram.data.togoogletranslate.ToGoogleTranslate;
import by.Andrey.jis3telegram.statistic.Statistic;

import java.io.IOException;
import java.util.List;

import static by.Andrey.jis3telegram.Service.WordService.WordService.*;
import static by.Andrey.jis3telegram.controllers.MenuController.*;
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
            lastWord = word;
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataFromWebSite moreExamplesFromWebSite = new DataFromWebSite(word.getName());
        String text = moreExamplesFromWebSite.pullOutMoreExamplesFromWebSite();
        String moreEx = ValidateService.getAmazingViewMoreExamples(text);
        urlGoogletransMoreEx = urlGoogletransMoreEx + ToGoogleTranslate.getUrlGoogleWithCorrectText(text);
        if (moreEx.equals("")){
            return word.getAmazingView() + urlGoogletransEx;
        } else {
            return word.getAmazingView() + "More Examples:" + "\n" + moreEx + urlGoogletransEx + urlGoogletransMoreEx;
        }
    }

    public static String commandSearchExecute(String msg){
        String word = msg.toLowerCase().trim();
        try {
                List<Word> listWords = getListWordsFromListString(getListStringWordsFromFile("words.txt"));
                if (WordService.isWordExistInList(listWords, word)) {
                    Word responsWord = searchWordWithName(listWords, word);
                    return responsWord.getAmazingView();
                } else {

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return "There isn`t this word. You can see it on: \n" + CommandService.getMeaningsFromWebSite(word);
    }

    public static String CommandAddWord(String msg){

            String word = msg.toLowerCase().trim();
            DataFromWebSite wordFromWebSite = new DataFromWebSite(word);
            List<String> noFormatListString = wordFromWebSite.getListNoFormatFieldOfWord();
            Word newWord = createNewWordFromNoFormatStringList(noFormatListString);
            try {
                if (validateWord(newWord)){
                    newWord.setNumberOfRepetitions(1);
                    DataService.writeNewWordToFile("words.txt", "wordsCopy.txt", newWord);
                    return newWord.getAmazingViewAddWord();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
         return "I didn`t find out this word";
        }

    public static String CommandDeleteWord(String msg){
        String wordString = msg.toLowerCase().trim();
        try {
                List<String> listString = getListStringWordsFromFile("words.txt");
                List<Word> listWords = getListWordsFromListString(listString);
                if (WordService.isWordExistInList(listWords, wordString)){
                    Word word = WordService.searchWordWithName(listWords,wordString);
                    DataService.deleteWordFromFile(listWords, "words.txt", "wordsCopy.txt", word);
                    return "Word '" + wordString + "' has been deleted";
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        return "I haven`t this word";
    }

    public static String CommandGetStatistic(){
        try {
                Statistic statistic = new Statistic(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
                String response = statistic.returnShortStatistic();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        return "Sorry, my fault";
    }

    public static void CommandLearnedWord(String word){
        listWordsEndOfTheDay.add(word);
        List<String> listString = null;
        try {
            listString = getListStringWordsFromFile("words.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Word> listWords = getListWordsFromListString(listString);

        if (WordService.isWordExistInList(listWords, word)){
            Word searchWord = WordService.searchWordWithName(listWords, word);
            try {
                counterLearnedWords++;
                rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", searchWord);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

