package by.Andrey.jis3telegram.command;

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




}
