package by.Andrey.jis3telegram.controllers;

import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.command.CommandService;
import by.Andrey.jis3telegram.ui.Keyboard;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;
import java.util.List;

import static by.Andrey.jis3telegram.ui.Menu.*;

public class MenuController {
    public static String secondMenuCommand = "";
    public static String lastMessage = "";
    public static Word lastWord = null;
    public static int counterLearnedWords = -1;
    public static List<String> listWordsEndOfTheDay = new ArrayList<>();

    public SendMessage getCallBackMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);


        if (message.equals("/start")) {
            sendMessage.setReplyMarkup(new Keyboard().createMainMenu());
            sendMessage.setText("Hi");
            return sendMessage;
        } else if (message.equals(mainMenuStatistic)) {
            sendMessage.setReplyMarkup(new Keyboard().createMainMenu());
            sendMessage.setText(CommandService.CommandGetStatistic());
            return sendMessage;
        } else if (message.equals(menuCommandBack)) {
            sendMessage.setReplyMarkup(new Keyboard().createMainMenu());
            sendMessage.setText(CHOOSE_COMMAND);
            secondMenuCommand = "";
            lastMessage="";
            return sendMessage;
        } else if (message.equals(mainMenuAddWord)) {
            sendMessage.setReplyMarkup(new Keyboard().createMenuSetupWords());
            secondMenuCommand = mainMenuAddWord;
            sendMessage.setText(CHOOSE_COMMAND);
            return sendMessage;
        } else if (message.equals(mainMenuRandom)) {
            sendMessage.setText(CommandService.commandGetRandomWord());
            sendMessage.setReplyMarkup(new Keyboard().createMainMenu()).setReplyMarkup(Keyboard.InlineKeyBoardMessage(lastWord));
            return sendMessage;
        } else if (message.equals(mainMenuSearch)) {
            sendMessage.setReplyMarkup(new Keyboard().createMainMenu());
            sendMessage.setText("Input word");
            secondMenuCommand = "search word";
            return sendMessage;
        }


        if (secondMenuCommand.equals("search word")) {
            secondMenuCommand = "";
            sendMessage.setReplyMarkup(new Keyboard().createMainMenu());
            sendMessage.setText(CommandService.commandSearchExecute(message));
            return sendMessage;
        } else if (secondMenuCommand.equals(mainMenuAddWord) && message.equals(menuAddWordInputWord)){
            lastMessage = menuAddWordInputWord;
            sendMessage.setReplyMarkup(new Keyboard().createMenuSetupWords());
            sendMessage.setText("input word which you want to add");
            return sendMessage;
        }else if (secondMenuCommand.equals(mainMenuAddWord) && message.equals(menuAddWordDelete)){
            lastMessage = menuAddWordDelete;
            sendMessage.setReplyMarkup(new Keyboard().createMenuSetupWords());
            sendMessage.setText("input word which you want to delete");
            return sendMessage;
        }

        if (lastMessage.equals(menuAddWordInputWord)){
            lastMessage = "";
            sendMessage.setReplyMarkup(new Keyboard().createMenuSetupWords());
            sendMessage.setText(CommandService.CommandAddWord(message));
            return sendMessage;
        } else if (lastMessage.equals(menuAddWordDelete)){
            lastMessage = "";
            sendMessage.setReplyMarkup(new Keyboard().createMenuSetupWords());
            sendMessage.setText(CommandService.CommandDeleteWord(message));
            return sendMessage;
        } else {
            sendMessage.setText(NO_MENU_COMMAND);
            return sendMessage;
        }

    }
}
