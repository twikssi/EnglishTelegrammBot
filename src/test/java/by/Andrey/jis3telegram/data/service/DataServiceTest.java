package by.Andrey.jis3telegram.data.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

public class DataServiceTest {

    @Test
    public void getMapWords() throws IOException {
        System.out.println(DataService.getListWords("pv.txt"));
    }

    @Test
    void getRandomWord() throws IOException {
        System.out.println(DataService.getRandomWord(DataService.getListWords("pv.txt")));
    }


    @Test
    public void returnWordsWithoutValues() {
        try {
            System.out.println(DataService.returnWordsWithoutValues(DataService.getListWords("words.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchWords(){
        try {
            System.out.println(DataService.searchWord(DataService.getListWords("words.txt"),"tough"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchWordsPhrasalVerbs(){
        try {
            System.out.println(DataService.searchWord(DataService.getListWords("pv.txt"),"stay up"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void searchWordsREturnNothing(){
        try {
            System.out.println(DataService.searchWord(DataService.getListWords("pv.txt"),"stay dfs up"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void copyFiles() {
        DataService.copyFiles(DataService.filePath + "words.txt",DataService.filePath + "wordsCopy.txt");
    }
}