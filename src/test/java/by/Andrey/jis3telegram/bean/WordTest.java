package by.Andrey.jis3telegram.bean;

import by.Andrey.jis3telegram.enums.PartsOfSpeech;
import org.junit.Test;

import static org.junit.Assert.*;

public class WordTest {

    @Test
    public void getAmazingView() {
        Word word = new Word("get", PartsOfSpeech.VERB ,"[get]", "получать","I get pencil./ iji./ ji.", false);
        System.out.println(word.getAmazingView());
    }
}