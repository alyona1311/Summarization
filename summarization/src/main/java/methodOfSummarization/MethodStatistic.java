/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodOfSummarization;

import methodSupporting.MethodSupporting;
import ru.library.text.word.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kasatkina Alyona
 */
public class MethodStatistic {

    private final Map<Integer, Float> numberAndWeightOfSentence = new HashMap<>();
    private final MethodSupporting methodSupporting = new MethodSupporting();
    private final Logger log = Logger.getLogger("system");

    public Map<Integer, String> getReferat(List<List<Word>> keyWordsInSentence, Map<Integer, String> numberAndSentence, int procentOfText) {
        Map<Integer, Float> weightOfSentence;
        weightOfSentence = getWeightOfSentences(keyWordsInSentence, numberAndSentence);
        List<Integer> keysNumberAndSentence = new ArrayList<>(numberAndSentence.keySet());
        List<Integer> keysWeightOfSentence = new ArrayList<>(weightOfSentence.keySet());
        Map<Integer, String> referat = new HashMap<>();
        int size = numberAndSentence.size();
        int sizeReferat = (int) ((size * procentOfText) / 100);
        for (int i = 0; i < sizeReferat; i++) {
            for (int j = 0; j < keysNumberAndSentence.size(); j++) {
                if (keysWeightOfSentence.get(i).equals(keysNumberAndSentence.get(j))) {
                    referat.put(keysWeightOfSentence.get(i), numberAndSentence.get(keysNumberAndSentence.get(j)));
                }
            }
        }
        Map<Integer, String> sortReferatStatistic;
        sortReferatStatistic = methodSupporting.sortByNumberOfSentence(referat);
        if (sortReferatStatistic.isEmpty()){
             log.log(Level.WARNING, "Не удалось сформировать реферат статистическим методом");
        }
        return sortReferatStatistic;
    }

    public Map<Integer, Float> getWeightOfSentences(List<List<Word>> keyWordsInSentence, Map<Integer, String> numberAndSentence) {
        List<Integer> keysNumberAndSentance = new ArrayList<>(numberAndSentence.keySet());
        float weightSentence;
        for (int i = 0; i < keyWordsInSentence.size(); i++) {
            weightSentence = 0;
            for (int j = 0; j < keyWordsInSentence.get(i).size(); j++) {
                weightSentence = weightSentence + keyWordsInSentence.get(i).get(j).getScope();
            }
            numberAndWeightOfSentence.put(keysNumberAndSentance.get(i), weightSentence);
        }
        Map<Integer, Float> sortWeightSentence;
        sortWeightSentence = methodSupporting.sortByWeightOfSentences(numberAndWeightOfSentence);
        return sortWeightSentence;
    }
    
     public Map<Integer, Integer> getRangOfWeightSentences(Map<Integer, Float> sortWeightOfSentence) {
        List<Integer> numberOfSentences = new ArrayList<>(sortWeightOfSentence.keySet());
        List<Float> weightOfSentences = new ArrayList<>(sortWeightOfSentence.values());
        Map<Integer, Integer> rangWeightOfSentences = new HashMap<>();
        int counter = 1;
        int x = 0;
        for (int i = 0; i < numberOfSentences.size(); i++) {
            float weight = weightOfSentences.get(i);
            int temp = 0;
            for (int j = i; j < numberOfSentences.size(); j++) {
                if (weightOfSentences.get(j).equals(weight)) {
                    rangWeightOfSentences.put(numberOfSentences.get(j), counter);
                    x++;
                    temp++;
                }
            }
            i = x - 1;
            counter += temp;
        }
        Map<Integer, Integer> sortRang = methodSupporting.sortByWeightOfSentencesIntegerDescending(rangWeightOfSentences);
        return sortRang;
    }

}
