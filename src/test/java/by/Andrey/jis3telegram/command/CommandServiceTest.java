package by.Andrey.jis3telegram.command;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class CommandServiceTest {

//    @Test
//    public void getCommand() {
//        System.out.println(CommandService.getWordFromCommand("/get word put over"));
//    }


    @Test
    public void getMeaningsFromWebSite() {
        System.out.println(CommandService.getMeaningsFromWebSite("put "));
    }

//    @Test
//    public void checkCommand() {
//        boolean expected = CommandService.checkCommand("/get pv get");
//        System.out.println(expected);
//    }
//
//    @org.junit.Test
//    public void getWordFromCommandAdded() {
//        String result = CommandService.getWordFromCommandAdd("/added get off");
//        System.out.println(result);
//    }
}