package by.Andrey.jis3telegram.statistic;

import by.Andrey.jis3telegram.Service.WordService.WordService;
import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.data.service.DataService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static by.Andrey.jis3telegram.Service.WordService.WordService.*;
import static by.Andrey.jis3telegram.data.service.DataService.*;
import static org.junit.Assert.*;

public class StatisticTest {
    List<Word> words;

    @Before
    public void setup() throws IOException {
        words = getListWordsFromListString(getListStringWordsFromFile("wordsCopyTest.txt"));
    }

    @Test
    public void sortBynumberOfRepeTitionsThenByName(){
        Statistic statistic = new Statistic(words);
        statistic.sortBynumberOfRepeTitionsThenByName();
        statistic.getWords().stream().forEach(System.out::println);
    }
}