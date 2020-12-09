package by.Andrey.jis3telegram;

import by.Andrey.jis3telegram.Service.WordService.WordService;
import by.Andrey.jis3telegram.command.CommandService;
import by.Andrey.jis3telegram.controllers.MenuController;
import by.Andrey.jis3telegram.ui.Keyboard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.util.Calendar;
import java.util.GregorianCalendar;

import static by.Andrey.jis3telegram.controllers.MenuController.*;
import static by.Andrey.jis3telegram.ui.Menu.*;


@Component
public class BorisevichBot extends TelegramLongPollingBot {
    private Long chaId;
    int threadCounter = 0;
    String text = "";

    @Override
    public String getBotUsername() {
        return "EnglishAndrewBot";
    }

    @Override
    public String getBotToken() {
        return "1332611656:AAEoQL_vpni2ACJxJ0_3_kEx_2gGio4kukE";
    }

//    @Override
//    public void onUpdateReceived(Update update) {
//        if(update.hasMessage()){
//            if(update.getMessage().hasText()){
//                if(update.getMessage().getText().equals("Hello")){
//                    try {
//                        execute(sendInlineKeyBoardMessage(update.getMessage().getChatId()));
//                    } catch (TelegramApiException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
////        else if(update.hasCallbackQuery()){
////            try {
////                execute(new SendMessage().setText(
////                        update.getCallbackQuery().getData())
////                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
////            } catch (TelegramApiException e) {
////                e.printStackTrace();
////            }
////        }
//    }




    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                try {
                    chaId = update.getMessage().getChatId();
                    execute(new MenuController().getCallBackMessage(chaId, update.getMessage().getText()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            try {
                CommandService.CommandLearnedWord(update.getCallbackQuery().getData());
                execute(new SendMessage().setText(
                        "Nice one!")
                        .setChatId(update.getCallbackQuery().getMessage().getChatId()));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

        if (threadCounter == 0){
            new Thread(() -> scheduleRun()).start();
        }
    }



    public void scheduleRun() {
        while (true){
            threadCounter = 1;
            Calendar calendar = new GregorianCalendar();
            calendar.getTime();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            SendMessage send = new SendMessage();
            send.setChatId(chaId);
            send.setText(CommandService.commandGetRandomWord());
            send.setReplyMarkup(Keyboard.InlineKeyBoardMessage(lastWord));
            if (hour >= 10 && hour <= 22){
                try {
                    execute(send);
                    Thread.sleep(3600000); //60000 - one minute
                    threadCounter = 0;
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    if (counterLearnedWords != -1){
                        SendMessage senfCounterLernedWords = new SendMessage().setChatId(chaId);
                        senfCounterLernedWords.setText("You've learned " + String.valueOf(counterLearnedWords + 1) + " today!\n" + listWordsEndOfTheDay.toString());
                        counterLearnedWords = -1;
                        listWordsEndOfTheDay.clear();
                        execute(senfCounterLernedWords);
                    }
                    Thread.sleep(3600000);
                    threadCounter = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

        }

    }
//
//    public static InlineKeyboardMarkup sendInlineKeyBoardMessage() {
//        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
//        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
//        inlineKeyboardButton1.setText("learned");
//        inlineKeyboardButton1.setCallbackData("learned");
//        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
//        keyboardButtonsRow1.add(inlineKeyboardButton1);
//        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
//        rowList.add(keyboardButtonsRow1);
//        inlineKeyboardMarkup.setKeyboard(rowList);
//        //return new SendMessage().setChatId(chatId).setText("Пример").setReplyMarkup(inlineKeyboardMarkup);
//        return inlineKeyboardMarkup;
//    }

}
