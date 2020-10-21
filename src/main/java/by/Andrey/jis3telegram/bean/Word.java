package by.Andrey.jis3telegram.bean;

import by.Andrey.jis3telegram.enums.PartsOfSpeech;
import lombok.Data;

@Data
public class Word {
    private String name;
    private PartsOfSpeech partsOfSpeech;
    private String transcription;
    private String meaning;
    private long numberOfRepetitions = 0;
    private boolean isDaily = false;
}
