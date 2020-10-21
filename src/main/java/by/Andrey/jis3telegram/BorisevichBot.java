package by.Andrey.jis3telegram;

import by.Andrey.jis3telegram.command.CommandService;
import by.Andrey.jis3telegram.data.service.DataService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.IOException;

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
                                getAllList(getListWords("pv.txt"), getListWords("words.txt")),
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
                            String[] respons = getRandomWord(getListWords("words.txt"));
                            sendMesg(message, "Do you 'member word '" + respons[0] + "'?");
                            Thread.sleep(5000);
                            sendMesg(message, respons[0] + " - " + respons[1]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "/get pv":
                        try {
                            String[] respons = getRandomWord(getListWords("pv.txt"));
                            sendMesg(message, "Do you 'member phrasal verb '" + respons[0] + "'?");
                            Thread.sleep(4000);
                            sendMesg(message, respons[0] + " - " + respons[1]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "/get":
                        try {
                            String[] respons =
                                    getRandomWord(getAllList(getListWords("pv.txt"), getListWords("words.txt")));
                            sendMesg(message, "What does it mean '" + respons[0] + "'?");
                            Thread.sleep(4000);
                            sendMesg(message, respons[0] + " - " + respons[1]);
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
