package by.Andrey.jis3telegram.data.togoogletranslate;

import org.junit.Test;

import static org.junit.Assert.*;

public class ToGoogleTranslateTest {

    @Test
    public void getUrlGoogleWithCorrectText() {
        System.out.println(ToGoogleTranslate.getUrlGoogleWithCorrectText("hi how are you"));
    }
}