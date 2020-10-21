package by.Andrey.jis3telegram.command;

public class CommandService {
    public static final String dictinary = "https://dictionary.cambridge.org/dictionary/english/";

    public static String getWordFromCommand(String command){
        String[] wordsCommandGet = command.split("/get word");
        String[] wordsCommandPv = command.split("/get pv");
        if (wordsCommandGet.length > 1){
            return wordsCommandGet[1].trim();
        } else {
            return wordsCommandPv[1].trim();
        }

    }

    public static boolean checkCommand(String command){
        String[] wordsCommand = command.split(" ");
        if (wordsCommand.length > 2 &&
                wordsCommand[0].equals("/get") &&
                (wordsCommand[1].equals("word") || wordsCommand[1].equals("pv"))){
            return true;
        }
        return false;
    }

    public static String getMeaningsFromWebSite(String word){
        String[] words = word.split(" ");
        if (words.length == 1){
            return dictinary.concat(word);
        } else {
            return dictinary + words[0] + "-" + words[1];
        }

    }


}