package by.Andrey.jis3telegram.Service.WordService;

import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.enums.PartsOfSpeech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WordService {

    public static Word createNewWord(String name, PartsOfSpeech partsOfSpeech, String transcription, String meaning, String example, boolean isDaily){
        return Word.builder()
                .name(name)
                .partsOfSpeech(partsOfSpeech)
                .transcription(transcription)
                .meaning(meaning)
                .example(example)
                .isDaily(isDaily).build();
    }

    public static List<Word> getListOnlyWords(List<Word> words){
        List<Word> onlyWords = words.stream().filter(a -> a.getPartsOfSpeech() != PartsOfSpeech.PHRASAL_VERB)
                .collect(Collectors.toList());
        return onlyWords;
    }

    public static List<Word> getListOnlyPhrasalVerbs(List<Word> words){
        List<Word> onlyWords = words.stream().filter(a -> a.getPartsOfSpeech() == PartsOfSpeech.PHRASAL_VERB)
                .collect(Collectors.toList());
        return onlyWords;
    }

    public static Word getRandomWord (List<Word> words){
        List<Word> wordNoImmutable = new ArrayList<>();
        wordNoImmutable.addAll(words);
        Collections.shuffle(wordNoImmutable);
        return wordNoImmutable.get(0);
    }

    public static void printList(List<Word> words){
       words.stream().forEach(a -> System.out.println(a.getAmazingView()));
    }
}
