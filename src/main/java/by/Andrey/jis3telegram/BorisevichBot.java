package by.Andrey.jis3telegram;

import by.Andrey.jis3telegram.Service.WordService.WordService;
import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.command.CommandService;
import by.Andrey.jis3telegram.data.dataFromWebSite.DataFromWebSite;
import by.Andrey.jis3telegram.data.service.DataService;
import by.Andrey.jis3telegram.enums.Emoji;
import by.Andrey.jis3telegram.statistic.Statistic;
import by.Andrey.jis3telegram.ui.Menu;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


import java.io.IOException;
import java.util.*;

import static by.Andrey.jis3telegram.Service.WordService.WordService.*;
import static by.Andrey.jis3telegram.data.service.DataService.*;
import static by.Andrey.jis3telegram.ui.Menu.*;


@Component
public class BorisevichBot extends TelegramLongPollingBot {
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
    String lastMessage = "";
    String secondMenuCommand = "";
    private long chaId;
    int threadCounter = 0;

    @Override
    public String getBotUsername() {
        return "EnglishAndrewBot";
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
            } else if (lastMessage.equals(mainMenuRandom)) {
                sendMessage.setText(getMenuWords(text));
                execute(sendMessage);
            } else if (lastMessage.equals(mainMenuStatistic)) {
                sendMessage.setText(getMenuStatistic(text));
                execute(sendMessage);
            } else if (lastMessage.equals(mainMenuSearch)) {
                sendMessage.setText(getMenuSearch(text));
                execute(sendMessage);
            } else if (lastMessage.equals(mainMenuAddWord)) {
                sendMessage.setText(getMenuAddWord(text));
                execute(sendMessage);
            } else {
                sendMessage.setText(NO_MENU_COMMAND);
                execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        if (threadCounter == 0){
            new Thread(() -> scheduleRun()).start();
        }
    }

    public void createKeyboard(){
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
    }

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
            return CHOOSE_COMMAND;
        }
        if (msg.equals(mainMenuRandom)) {
            lastMessage = msg;
            createMenuWords(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            return CHOOSE_COMMAND;
        }
        if (msg.equals(mainMenuStatistic)) {
            lastMessage = msg;
            createMenuStatistic(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            return CHOOSE_COMMAND;
        }
        if (msg.equals(mainMenuSearch)) {
            lastMessage = msg;
            createMenuSearch(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            return CHOOSE_COMMAND;
        }
        if (msg.equals(mainMenuAddWord)) {
            lastMessage = msg;
            createMenuAddWord(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            return CHOOSE_COMMAND;
        }
        if (msg.equals(menuCommandBack)) {
            createMainMenu(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            lastMessage = "";
            return CHOOSE_COMMAND;
        }
        return NO_MENU_COMMAND;
    }

    public void createMainMenu(String msg, List<KeyboardRow> keyboard, KeyboardRow keyboardFirstRow, KeyboardRow keyboardSecondRow) {
        lastMessage = "";
        keyboard.clear();
        keyboardFirstRow.clear();
        // Добавляем кнопки в первую строчку клавиатуры
        keyboardFirstRow.add(mainMenuRandom);
        keyboardFirstRow.add(mainMenuStatistic);

        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(mainMenuSearch);
        keyboardSecondRow.add(mainMenuAddWord);

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
        keyboardFirstRow.add(menuRandomCommandAnyWord);
        keyboardFirstRow.add(menuRandomCommandOnlyWords);

        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(menuRandomCommandOnlyPhrasalVerb);
        keyboardSecondRow.add(menuCommandBack);

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
        keyboardFirstRow.add(menuStatisticCommandShortStatistic);

        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(menuStatisticCommandLongStatistic);
        keyboardSecondRow.add(menuCommandBack);

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
        keyboardFirstRow.add(menuSearchInputWord);

        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(menuCommandBack);

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
        keyboardFirstRow.add(menuAddWordInputWord);
        keyboardFirstRow.add(menuAddWordDelete);
        // Добавляем кнопки во вторую строчку клавиатуры
        keyboardSecondRow.add(menuCommandBack);

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

        if (msg.equals(menuRandomCommandAnyWord)) {
            createMenuWords(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            try {
                Word word = getRandomWord(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
                rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", word);
                return word.getAmazingView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg.equals(menuRandomCommandOnlyWords)) {
            createMenuWords(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            try {
                Word word = getRandomWord(getListOnlyWords(getListWordsFromListString(getListStringWordsFromFile("words.txt"))));
                rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", word);
                return word.getAmazingView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg.equals(menuRandomCommandOnlyPhrasalVerb)) {
            createMenuWords(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            try {
                Word word = getRandomWord(getListOnlyPhrasalVerbs(getListWordsFromListString(getListStringWordsFromFile("words.txt"))));
                rewriteFieldNumberOfRepetitionToFile("words.txt", "wordsCopy.txt", word);
                return word.getAmazingView();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg.equals(menuCommandBack)) {
            getMessage("/start");
            lastMessage = "";
            return CHOOSE_COMMAND;
        }
        return NO_MENU_COMMAND;
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

        if (msg.equals(menuStatisticCommandShortStatistic)) {
            createMenuStatistic(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            try {
                Statistic statistic = new Statistic(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
                String response = statistic.returnShortStatistic();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg.equals(menuStatisticCommandLongStatistic)) {
            createMenuStatistic(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            try {
                Statistic statistic = new Statistic(getListWordsFromListString(getListStringWordsFromFile("words.txt")));
                String response = statistic.returnLongStatistic();
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg.equals(menuCommandBack)) {
            getMessage("/start");
            lastMessage = "";
            return CHOOSE_COMMAND;
        }
        return NO_MENU_COMMAND;
    }

    public String getMenuSearch(String msg) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        if (msg.equals(menuSearchInputWord)) {
            createMenuSearch(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            secondMenuCommand = "search word";
            return "input word which you want to find out";
        }
        if (msg.equals(menuCommandBack)) {
            getMessage("/start");
            lastMessage = "";
            secondMenuCommand = "";
            return CHOOSE_COMMAND;
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
        return NO_MENU_COMMAND;
    }

    public String getMenuAddWord(String msg) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        if (msg.equals(menuAddWordInputWord)) {
            createMenuAddWord(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            secondMenuCommand = "add words";
            return "input word which you want to add";
        }

        if (msg.equals(menuAddWordDelete)) {
            createMenuAddWord(msg, keyboard, keyboardFirstRow, keyboardSecondRow);
            secondMenuCommand = "delete";
            return "input word which you want to delete";
        }
        if (msg.equals(menuCommandBack)) {
            getMessage("/start");
            lastMessage = "";
            secondMenuCommand = "";
            return CHOOSE_COMMAND;
        }
        if (secondMenuCommand.equals("add words")) {
            secondMenuCommand = "";

            String word = msg.toLowerCase().trim();
            DataFromWebSite wordFromWebSite = new DataFromWebSite(word);
            List<String> noFormatListString = wordFromWebSite.getListNoFormatFieldOfWord();
            Word newWord = createNewWordFromNoFormatStringList(noFormatListString);
            try {
                if (validateWord(newWord)){
                    newWord.setNumberOfRepetitions(1);
                    DataService.writeNewWordToFile("words.txt", "wordsCopy.txt", newWord);
                    return newWord.getAmazingView();
                } else {
                    return "I didn`t find out this word";
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (secondMenuCommand.equals("delete")) {
            try {
                secondMenuCommand = "";
                String wordString = msg.toLowerCase().trim();
                List<String> listString = getListStringWordsFromFile("words.txt");
                List<Word> listWords = getListWordsFromListString(listString);
                if (WordService.isWordExistInList(listWords, wordString)){
                    Word word = WordService.searchWordWithName(listWords,wordString);
                    DataService.deleteWordFromFile(listWords, "words.txt", "wordsCopy.txt", word);
                    return "Word '" + wordString + "' has been deleted";
                }
                return "I haven`t this word";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return NO_MENU_COMMAND;
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
            if (hour >= 10 && hour <= 23){
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
                    Thread.sleep(3600000);
                    threadCounter = 0;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

}
