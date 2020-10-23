package by.Andrey.jis3telegram.statistic;

import by.Andrey.jis3telegram.bean.Word;
import by.Andrey.jis3telegram.enums.PartsOfSpeech;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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


    public long countWordsInList(){
        return words.stream().count();
    }


    public Map<PartsOfSpeech, Long> returnCountPartOfSpeech(){
       return  words.stream()
                .collect(Collectors.groupingBy(word -> word.getPartsOfSpeech(), Collectors.counting()));
    }

    public String returnShortStatistic(){
        sortBynumberOfRepeTitionsThenByName();
        String shortStatistic = "";
        for (Word word: words){
            shortStatistic = shortStatistic.concat(word.viewToStatistic()) + "\n";
        }
        return shortStatistic;
    }

    public String returnLongStatistic(){
        String longStatistic = returnShortStatistic()+ "\n" + "Number of words in base are " + countWordsInList() + "\n\n";
        Map<PartsOfSpeech, Long> mapPartOfSpeech = returnCountPartOfSpeech();
        for(Map.Entry<PartsOfSpeech, Long> partsOfSpeech: mapPartOfSpeech.entrySet()){
            longStatistic = longStatistic +
                   "Number of " + partsOfSpeech.getKey().toString().toLowerCase() + " are " +
                    partsOfSpeech.getValue() + "\n";
        }
        return longStatistic;
    }
}
