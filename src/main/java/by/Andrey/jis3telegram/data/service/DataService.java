package by.Andrey.jis3telegram.data.service;

import by.Andrey.jis3telegram.Service.WordService.WordService;
import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.enums.PartsOfSpeech;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DataService {
    public static final String filePath = "./src/main/resources/";

    public static void copyFiles(String left, String right){
        Path from = Paths.get(filePath.concat(left));
        Path to = Paths.get(filePath.concat(right));
        try {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getListStringWordsFromFile(String fileName) throws IOException{
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath.concat(fileName)), "UTF8"));
        List<String> EnglishWords = new ArrayList<>();

        while (in.ready()){
            EnglishWords.add(in.readLine());
        }
        in.close();
        return EnglishWords;
    }

    public static void writeListWordsToFile (String fileName, List<Word> words, boolean isAppend) throws IOException {
        BufferedWriter  out = new BufferedWriter(new FileWriter(filePath.concat(fileName), isAppend));
        for(Word word:words){
            out.write(word.viewToWriteFile());
            out.newLine();
        }
        out.close();
    }

    public static void rewriteFieldNumberOfRepetitionToFile(String fileName, String fileToCopy, Word word) throws IOException {
        List<Word> listWords = WordService.getListWordsFromListString(getListStringWordsFromFile(fileName));
        copyFiles(fileName, fileToCopy);
        WordService.increaseNumberOfRepetitions(listWords, word);
        writeListWordsToFile(fileName, listWords, false);
    }

    public static void writeNewWordToFile(String fileName, String fileToCopy, Word word) throws IOException {
        copyFiles(fileName, fileToCopy);
        List<Word> listWords = new ArrayList<>();
        listWords.add(word);
        writeListWordsToFile(fileName, listWords, true);
    }

    public static void deleteWordFromFile(List<Word> listWords, String fileName, String fileToCopy, Word word) throws IOException {
        copyFiles(fileName, fileToCopy);
        List<Word> listWordsToFile = new ArrayList<>();
        listWordsToFile.addAll(listWords);
        listWordsToFile.remove(word);
        writeListWordsToFile(fileName, listWordsToFile, false);
    }
}
