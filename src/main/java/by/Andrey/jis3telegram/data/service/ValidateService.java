package by.Andrey.jis3telegram.data.service;

import by.Andrey.jis3telegram.enums.PartsOfSpeech;

import java.util.ArrayList;
import java.util.List;

public class ValidateService {
    public static String[] breakStringOnPeaceWithSpace(String line){
        return line.split(" ");
    }

    public static String[] breakStringOnPeaceWithTwoDotes(String line){
        return line.split(":");
    }

    public static String[] breakStringOnPeaceWithSlash(String line){
        return line.split("/");
    }

    public static String replaceDotersSlashesQuestionSymbolAndAttentionSymbolToSlashAndNewLine(String line){
        String resultText = line.replace("/", ", ");
        resultText = resultText.replace(".", "./");
        resultText = resultText.replace("?","?/");
        resultText = resultText.replace("!", "!/");
        resultText = resultText.replace("=", " ");
        return resultText;
    }

    public static String replaceAllEquallyOnSpace(String text){
        return text.replace("=", " ")        ;
    }

    public static PartsOfSpeech getPartOfSpeechToWriteInObject(String partOfSpeechNoFormat){
        String[] partOfSpeechNoFormatArray = breakStringOnPeaceWithSpace(partOfSpeechNoFormat);
        switch(replaceAllEquallyOnSpace(partOfSpeechNoFormatArray[0].toLowerCase().trim())){
            case "noun":
                return PartsOfSpeech.NOUN;
            case "phrasal":
                return PartsOfSpeech.PHRASAL_VERB;
            case "adjective":
                return PartsOfSpeech.ADJECTIVE;
            case "verb":
                return PartsOfSpeech.VERB;
            case "pronoun":
                return PartsOfSpeech.PRONOUN;
            case "preposition":
                return PartsOfSpeech.PREPOSITION;
            case "adverb":
                return PartsOfSpeech.ADVERB;
            case "article":
                return PartsOfSpeech.ARTICLE;
            case "conjunction":
                return PartsOfSpeech.CONJUNCTION;
            case "interjection":
                return PartsOfSpeech.INTERJECTION;
            default:
                return PartsOfSpeech.NO_PART_OF_SPEECH;
        }
    }

    public static String getTranscriptionToWriteInObject(String transcriptionNoFormat){
        String[] transcriptionNoFormatArr = breakStringOnPeaceWithSpace(replaceAllEquallyOnSpace(transcriptionNoFormat));
        return "[" + transcriptionNoFormatArr[0] +"]";
    }

    public static String getMeaningToWriteInObject(String meaningNoFormat){
        String[] meaningNoFormatArr = breakStringOnPeaceWithTwoDotes(replaceAllEquallyOnSpace(meaningNoFormat));
        if (meaningNoFormatArr.length >= 2){
            return meaningNoFormatArr[0] + " Also meaning: " + meaningNoFormatArr[1];
        } else {
            return meaningNoFormatArr[0];
        }
    }

    public static String getExampleToWriteInObject(String exampleNoFormat){
        String[] exampleNoFormatArr = breakStringOnPeaceWithSlash(
                replaceDotersSlashesQuestionSymbolAndAttentionSymbolToSlashAndNewLine(replaceAllEquallyOnSpace(exampleNoFormat)));
        int iterator;
        String exampleFormat = "";

        if (exampleNoFormatArr.length >= 10){
            iterator = 10;
        } else {
            iterator = exampleNoFormatArr.length;
        }

        for (int i = 0; i < iterator; i++){
                exampleFormat = exampleFormat + exampleNoFormatArr[i] + "/";
        }
        return exampleFormat;
    }

    public static List<String> getListFieldsOfWord(List<String> listNoFormatFieldsOfWord){
        List<String> listFieldsOfWord = new ArrayList<>();
        listFieldsOfWord.add(listNoFormatFieldsOfWord.get(0).toLowerCase().trim());
        listFieldsOfWord.add(getPartOfSpeechToWriteInObject(listNoFormatFieldsOfWord.get(1)).toString());
        listFieldsOfWord.add(getTranscriptionToWriteInObject(listNoFormatFieldsOfWord.get(2)));
        listFieldsOfWord.add(getMeaningToWriteInObject(listNoFormatFieldsOfWord.get(3)));
        listFieldsOfWord.add(getExampleToWriteInObject(listNoFormatFieldsOfWord.get(4)));

        return listFieldsOfWord;

    }

}
