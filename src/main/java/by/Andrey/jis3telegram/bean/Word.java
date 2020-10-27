package by.Andrey.jis3telegram.bean;

import by.Andrey.jis3telegram.enums.Emoji;
import by.Andrey.jis3telegram.enums.PartsOfSpeech;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Word implements Serializable {
    private String name;
    private PartsOfSpeech partsOfSpeech;
    private String transcription;
    private String meaning;
    private String example;
    private long numberOfRepetitions = 0;
    private boolean isDaily = false;

    public Word(String name, PartsOfSpeech partsOfSpeech, String transcription, String meaning, String example, boolean isDaily) {
        this.name = name;
        this.partsOfSpeech = partsOfSpeech;
        this.transcription = transcription;
        this.meaning = meaning;
        this.example = example;
        this.isDaily = isDaily;
    }

    public String getAmazingView(){
        String view = "" + getName() + " ("+ getPartsOfSpeech().toString().toLowerCase() + ") - " + getTranscription() + " - " + getMeaning() + "\n";
        String[] exampleParts = breakIntoPeacesFieldExample();
        for (int i=0; i< exampleParts.length;  i++){
            view = view.concat("- " + exampleParts[i] + "\n");
        }
        return view;
    }

    private String[] breakIntoPeacesFieldExample(){
        return example.split("/");
    }

    public String viewToWriteFile(){
        return getName() + "=" +
                getPartsOfSpeech().toString() + "=" +
                getTranscription() + "=" +
                getMeaning() + "=" +
                getExample() + "=" +
                getNumberOfRepetitions() + "=" +
                isDaily();
    }

    public String viewToStatistic(){
        return "Word '" + getName() + "' was repeated " + getNumberOfRepetitions() + " times.";
    }
}
