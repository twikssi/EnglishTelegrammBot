package by.Andrey.jis3telegram.data.service;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DataService {
    public static final String filePath = "./src/main/resources/";

    public static List<String> getListWords(String fileName) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream(filePath.concat(fileName)), "UTF8"));
        List<String> mapEnglishWords = new ArrayList<>();

        while (in.ready()){
            mapEnglishWords.add(in.readLine());
        }
        return mapEnglishWords;
    }

    public static List<String> getAllList(List<String> file1, List<String> file2){
        List<String> allWords = new ArrayList<>();
        allWords.addAll(file1);
        allWords.addAll(file2);
        return allWords;
    }

    public static String[] getRandomWord(List<String> words){
        Collections.shuffle(words);
        return words.get(0).split("=");
    }


    public static Map<String, List<String>> returnWordsWithoutValues(List<String> words){
       return words.stream()
                .collect(Collectors.groupingBy(
                        a->a.split("=")[0].trim(),
                        Collectors.mapping(p->p,Collectors.toList())
                ));
    }

    public static String searchWord (List<String> words, String word){
       Map<String, List<String>>  names = returnWordsWithoutValues(words);

       if (names.containsKey(word)){
            return names.get(word).toString();
       }
        return "I haven`t this word";
    }
}
