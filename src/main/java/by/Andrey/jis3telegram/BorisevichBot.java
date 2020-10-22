package by.Andrey.jis3telegram;

import by.Andrey.jis3telegram.Service.WordService.WordService;
import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.command.CommandService;
import by.Andrey.jis3telegram.data.service.DataService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.IOException;

import static by.Andrey.jis3telegram.Service.WordService.WordService.*;
import static by.Andrey.jis3telegram.command.CommandService.*;
import static by.Andrey.jis3telegram.data.service.DataService.*;


@Component
public class BorisevichBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "BorisevichBot";
    }

    @Override
    public String getBotToken() {
        return "1332611656:AAEoQL_vpni2ACJxJ0_3_kEx_2gGio4kukE";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                if (checkCommand(message.getText().toLowerCase())) {
                    try {
                        String word = getWordFromCommand(message.getText().toLowerCase());
                        String respons = searchWord(
                                getAllList(getListWords("pv.txt"), getListWords("wordsoldversion.txt")),
                                        word
                                        );
                        sendMesg(message, respons);
                        Thread.sleep(2500);
                        sendMesg(message,"you can find it on " + CommandService.getMeaningsFromWebSite(word));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
                switch (message.getText().toLowerCase()) {
                    case "/get word":
                        try {
                            Word word = getRandomWord(getListOnlyWords(getListWordsFromListString(getListStringWordsFromFile("words.txt"))));
                            sendMesg(message, "Do you 'member word '" + word.getName() + "'?");
                            Thread.sleep(5000);
                            sendMesg(message, word.getAmazingView());
                            rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt",word);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "/get pv":
                        try {
                            Word word = getRandomWord(getListOnlyPhrasalVerbs(getListWordsFromListString(getListStringWordsFromFile("words.txt"))));
                            sendMesg(message, "Do you 'member word '" + word.getName() + "'?");
                            Thread.sleep(5000);
                            sendMesg(message, word.getAmazingView());
                            rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt",word);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "/get":
                        try {
                            Word word = getRandomWord(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
                            sendMesg(message, "Do you 'member word '" + word.getName() + "'?");
                            Thread.sleep(5000);
                            sendMesg(message, word.getAmazingView());
                            rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt",word);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        break;
                }
            }
        }

    }

    public void sendMesg(Message message, String response) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId());
        sendMessage.setText(response);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
