package by.Andrey.jis3telegram;

import by.Andrey.jis3telegram.Service.WordService.WordService;
import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.command.CommandService;
import by.Andrey.jis3telegram.data.dataFromWebSite.DataFromWebSite;
import by.Andrey.jis3telegram.data.service.DataService;
import by.Andrey.jis3telegram.enums.Emoji;
import by.Andrey.jis3telegram.statistic.Statistic;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.Andrey.jis3telegram.Service.WordService.WordService.*;
import static by.Andrey.jis3telegram.data.service.DataService.*;


@Component
public class BorisevichBot extends TelegramLongPollingBot {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
    String lastMessage = "";
    String secondMenuCommand = "";
    private long chaId;

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
        try {
            update.getUpdateId();
            SendMessage sendMessage = new SendMessage().setChatId(update.getMessage().getChatId());
            chaId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);

            if (lastMessage.equals("")) {
                sendMessage.setText(getMessage(text));
                execute(sendMessage);
            } else if (lastMessage.equals("words" + Emoji.ARROW_DOWN.toString())) {
                sendMessage.setText(getMenuWords(text));
                execute(sendMessage);
            } else if (lastMessage.equals("statistic " + Emoji.STATISTIC.toString())) {
                sendMessage.setText(getMenuStatistic(text));
                execute(sendMessage);
            } else if (lastMessage.equals("search")) {
                sendMessage.setText(getMenuSearch(text));
                execute(sendMessage);
            } else if (lastMessage.equals("add words")) {
                sendMessage.setText(getMenuAddWord(text));
                execute(sendMessage);
            } else {
                sendMessage.setText("There isn't such command");
                execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }


//                if (checkCommandAdd(message.getText().toLowerCase())) {
//                    String word = getWordFromCommandAdd(message.getText().toLowerCase());
//                    if (!word.equals("")) {
//                        DataFromWebSite wordFromWebSite = new DataFromWebSite(word);
//                        List<String> noFormatListString = wordFromWebSite.getListNoFormatFieldOfWord();
//                        Word newWord = createNewWordFromNoFormatStringList(noFormatListString);
//                        try {
//                            DataService.writeNewWordToFile("words.txt", "wordsCopy.txt", newWord);
//                            String answer = "Word has been added";
//                            sendMesg(message, answer);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//                if (checkCommand(message.getText().toLowerCase())) {
//                    try {
//                        String word = getWordFromCommand(message.getText().toLowerCase());
//
//                        List<Word> listWords = getListWordsFromListString(getListStringWordsFromFile("words.txt"));
//                        if (WordService.isWordExistInList(listWords, word)) {
//                            Word responsWord = searchWordWithName(listWords, word);
//                            sendMesg(message, responsWord.getAmazingView());
//                            rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", responsWord);
//                            Thread.sleep(2500);
//                        }
//                        sendMesg(message, "you can find it on " + CommandService.getMeaningsFromWebSite(word));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                }

//                    case "/get short statistic":
//                        try {
//                            Statistic statistic = new Statistic(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
//                            String response = statistic.returnShortStatistic();
//                            sendMesg(message, response);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        break;
//                    case "/get long statistic":
//                        try {
//                            Statistic statistic = new Statistic(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
//                            String response = statistic.returnLongStatistic();
//                            sendMesg(message, response);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                        break;
//                    case "/help":
//                        String response = getListCommand();
//                        sendMesg(message, response);
//                        break;
//                    case "/start":
//                        //startBotAndLoadMainMenu(message, "Hi");
//                        break;
//                    default:
//                        break;
//                }
        //           }
    }


//    public void sendMesg(Message message, String response) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(message.getChatId());
//        sendMessage.setText(response);
//        sendMessage.setReplyMarkup(replyKeyboardMarkup);
//        try {
//            execute(sendMessage);
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }
//    }


    public String getMessage(String msg) {
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        if (msg.equals("/start") || msg.equals("Menu")) {
            lastMessage = "";
            createMainMenu(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            return "choose...";
        }
        if (msg.equals("words"+ Emoji.ARROW_DOWN.toString())) {
            lastMessage = msg;
            createMenuWords(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            return "choose...";
        }
        if (msg.equals("statistic " + Emoji.STATISTIC.toString())) {
            lastMessage = msg;
            createMenuStatistic(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            return "choose...";
        }
        if (msg.equals("search")) {
            lastMessage = msg;
            createMenuSearch(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            return "choose...";
        }
        if (msg.equals("add words")) {
            lastMessage = msg;
            createMenuAddWord(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            return "choose...";
        }
        if (msg.equals("<-back")) {
            createMainMenu(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            lastMessage = "";
            return "choose...";
        }
        return "there isn't such menu";
    }

    public void createMainMenu(String msg, List<KeyboardRow> keyboard, KeyboardRow keyboardFirstRow, KeyboardRow keyboardSecondRow) {
        lastMessage = "";
        keyboard.clear();
        keyboardFirstRow.clear();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("words" + Emoji.ARROW_DOWN.toString());
        keyboardFirstRow.add("statistic " + Emoji.STATISTIC.toString());

        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("search");
        keyboardSecondRow.add("add words");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void createMenuWords(String msg, List<KeyboardRow> keyboard, KeyboardRow keyboardFirstRow, KeyboardRow keyboardSecondRow) {
        keyboard.clear();
        keyboardFirstRow.clear();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("any word");
        keyboardFirstRow.add("only words");

        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("only phrasal verb");
        keyboardSecondRow.add("<-back");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void createMenuStatistic(String msg, List<KeyboardRow> keyboard, KeyboardRow keyboardFirstRow, KeyboardRow keyboardSecondRow) {
        keyboard.clear();
        keyboardFirstRow.clear();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("short statistic " + Emoji.STATISTIC_SHORT);

        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("long statistic " + Emoji.STATISTIC_LONG);
        keyboardSecondRow.add("<-back");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void createMenuSearch(String msg, List<KeyboardRow> keyboard, KeyboardRow keyboardFirstRow, KeyboardRow keyboardSecondRow) {
        keyboard.clear();
        keyboardFirstRow.clear();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("input word");

        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("<-back");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public void createMenuAddWord(String msg, List<KeyboardRow> keyboard, KeyboardRow keyboardFirstRow, KeyboardRow keyboardSecondRow) {
        keyboard.clear();
        keyboardFirstRow.clear();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add("input word");

        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add("<-back");

        // Добавляем все строчки клавиатуры в список
        keyboard.add(keyboardFirstRow);
        keyboard.add(keyboardSecondRow);
        // и устанваливаем этот список нашей клавиатуре
        replyKeyboardMarkup.setKeyboard(keyboard);
    }

    public String getMenuWords(String msg) {
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        if (msg.equals("any word")) {
            createMenuWords(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            try {
                Word word = getRandomWord(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
                rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", word);
                return word.getAmazingView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg.equals("only words")) {
            createMenuWords(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            try {
                Word word = getRandomWord(getListOnlyWords(getListWordsFromListString(getListStringWordsFromFile("words.txt"))));
                rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", word);
                return word.getAmazingView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg.equals("only phrasal verb")) {
            createMenuWords(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            try {
                Word word = getRandomWord(getListOnlyPhrasalVerbs(getListWordsFromListString(getListStringWordsFromFile("words.txt"))));
                rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", word);
                return word.getAmazingView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg.equals("<-back")) {
            getMessage("/start");
            lastMessage = "";
            return "choose...";
        }
        return "there isn't such menu";
    }

    public String getMenuStatistic(String msg) {
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        if (msg.equals("short statistic " + Emoji.STATISTIC_SHORT)) {
            createMenuStatistic(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            try {
                Statistic statistic = new Statistic(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
                String response = statistic.returnShortStatistic();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg.equals("long statistic "  + Emoji.STATISTIC_LONG)) {
            createMenuStatistic(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            try {
                Statistic statistic = new Statistic(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
                String response = statistic.returnLongStatistic();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg.equals("<-back")) {
            getMessage("/start");
            lastMessage = "";
            return "choose...";
        }
        return "there isn't such menu";
    }

    public String getMenuSearch(String msg) {
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        if (msg.equals("input word")) {
            createMenuSearch(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            secondMenuCommand = "search word";
            return "input word which you want to find out";
        }
        if (msg.equals("<-back")) {
            getMessage("/start");
            lastMessage = "";
            secondMenuCommand = "";
            return "choose...";
        }
        if (secondMenuCommand.equals("search word")) {
            try {
                secondMenuCommand = "";
                String word = msg.toLowerCase().trim();
                List<Word> listWords = getListWordsFromListString(getListStringWordsFromFile("words.txt"));
                if (WordService.isWordExistInList(listWords, word)) {
                    Word responsWord = searchWordWithName(listWords, word);
                    rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", responsWord);
                    return responsWord.getAmazingView();
                }
                return "There isn`t this word. You can see it on: \n" + CommandService.getMeaningsFromWebSite(word);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "there isn't such menu";
    }

    public String getMenuAddWord(String msg) {
        // Создаем список строк клавиатуры
        List<KeyboardRow> keyboard = new ArrayList<>();
        // Первая строчка клавиатуры
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        // Вторая строчка клавиатуры
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        if (msg.equals("input word")) {
            createMenuAddWord(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            secondMenuCommand = "add words";
            return "input word which you want to add";
        }
        if (msg.equals("<-back")) {
            getMessage("/start");
            lastMessage = "";
            secondMenuCommand = "";
            return "choose...";
        }
        if (secondMenuCommand.equals("add words")) {
            secondMenuCommand = "";

            String word = msg.toLowerCase().trim();
            DataFromWebSite wordFromWebSite = new DataFromWebSite(word);
            List<String> noFormatListString = wordFromWebSite.getListNoFormatFieldOfWord();
            Word newWord = createNewWordFromNoFormatStringList(noFormatListString);
            try {
                DataService.writeNewWordToFile("words.txt", "wordsCopy.txt", newWord);
                return "Word has been added";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "there isn't such menu";
    }

}
