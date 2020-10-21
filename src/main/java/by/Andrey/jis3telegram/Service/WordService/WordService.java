package by.Andrey.jis3telegram.Service.WordService;

import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.enums.PartsOfSpeech;

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
}
