package by.Andrey.jis3telegram.bean;

import by.Andrey.jis3telegram.enums.PartsOfSpeech;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordVocabulary {
    private String name;
    private String partsOfSpeech;
    private String transcription;
    private String meaning;
    private String examples;
    private String moreExample;

    @Override
    public String toString() {
        return "WordVocabulary{" + "\n" +
                "name='" + name + '\'' + "\n" +
                ", partsOfSpeech='" + partsOfSpeech + '\'' + "\n" +
                ", transcription='" + transcription + '\'' + "\n" +
                ", meaning='" + meaning + '\'' + "\n" +
                ", examples='" + examples + '\'' + "\n" +
                ", moreExample='" + moreExample + '\'' + "\n" +
                '}';
    }
}
