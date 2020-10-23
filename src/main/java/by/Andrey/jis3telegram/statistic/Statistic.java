package by.Andrey.jis3telegram.statistic;

import by.Andrey.jis3telegram.bean.Word;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Statistic {
   private List<Word> words;
   private static final Comparator<Word> sortByNumberOfRepetitions = (word, t1) -> (int) (t1.getNumberOfRepetitions() - word.getNumberOfRepetitions());
   private static final Comparator<Word> sortByName = Comparator.comparing(Word::getName);

    public Statistic(List<Word> words) {
        this.words = words;
    }

    public List<Word> getWords() {
        return words;
    }

    public void sortBynumberOfRepeTitionsThenByName (){
        words = words.stream()
                .sorted(sortByName).sorted(sortByNumberOfRepetitions)
                .collect(Collectors.toList());
    }
}
