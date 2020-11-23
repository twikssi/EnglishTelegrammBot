package by.Andrey.jis3telegram.data.togoogletranslate;

public class ToGoogleTranslate {
    public static final String urlGoogle = "https://translate.google.by/?hl=ru&sl=en&tl=es&text=";

    public static String replaceAllspaceOn20Percent(String text){
        return text.replace(" ", "%20");
    }

    public static String getUrlGoogleWithCorrectText(String correctText){
        return urlGoogle.concat(replaceAllspaceOn20Percent(correctText));
    }
}
