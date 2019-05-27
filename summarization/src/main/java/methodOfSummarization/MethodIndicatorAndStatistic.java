/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodOfSummarization;

import methodSupporting.MethodSupporting;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import ru.library.text.word.Word;

/**
 *
 * @author Kasatkina Alyona
 */
public class MethodIndicatorAndStatistic extends MethodIndicator{
    private final Map<Integer, Integer> numberAndWeightOfSentence = new HashMap<>();
    private final MethodSupporting methodSupporting = new MethodSupporting();
    private final Logger log = Logger.getLogger("system");
    
    public Map<Integer, String> getReferat(Map<Integer, String> numberAndSentence, List<List<Word>> wordsInSentenceInInitialForm, List<List<Word>> keyWordsInSentence, String markers,int procentOfText){
         Map<Integer, Integer> weightOfSentence = getWeightOfSentences(numberAndSentence, wordsInSentenceInInitialForm, keyWordsInSentence, markers);
        List<Integer> keysWeightOfSentence = new ArrayList<>(weightOfSentence.keySet());
        List<Integer> keysNumberAndSentence = new ArrayList<>(numberAndSentence.keySet());
        Map<Integer, String> referat = new HashMap<>();
        int size = numberAndSentence.size();
        int sizeReferat = (int) ((size * procentOfText) / 100);
        for(int i = 0; i < sizeReferat; i++){
            for(int j = 0; j < keysNumberAndSentence.size(); j++){
                if(keysWeightOfSentence.get(i).equals(keysNumberAndSentence.get(j))){
                    referat.put(keysWeightOfSentence.get(i), numberAndSentence.get(keysNumberAndSentence.get(j)));
                }
            }
        }
        Map<Integer, String> sortReferatIndicatorStatistic;
        sortReferatIndicatorStatistic = methodSupporting.sortByNumberOfSentence(referat);
        if (sortReferatIndicatorStatistic.isEmpty()){
             log.log(Level.WARNING, "Не удалось сформировать реферат индикаторным-статистическим методом");
        }
        return sortReferatIndicatorStatistic;
    }
    
    public Map<Integer, Integer> getWeightOfSentences(Map<Integer, String> numberAndSentence, List<List<Word>> wordsInSentenceInInitialForm, List<List<Word>> keyWordsInSentence, String markers){
        Map<Integer, Integer> weightByIndicator = getWeightOfSentences(numberAndSentence, wordsInSentenceInInitialForm, markers);
        Map<Integer, Integer> rangByIndicator = getRangOfWeightSentences(weightByIndicator);
                
        MethodStatistic methodStatistic = new MethodStatistic();
        Map<Integer, Float> weightByStatistic = methodStatistic.getWeightOfSentences(keyWordsInSentence, numberAndSentence);
        Map<Integer, Integer> rangByStatistic = methodStatistic.getRangOfWeightSentences(weightByStatistic);
        
        List<Integer> rangByIndicatorKeys = new ArrayList<>(rangByIndicator.keySet());
        List<Integer> rangByStatisticKeys = new ArrayList<>(rangByStatistic.keySet());
        for (int i = 0; i < weightByIndicator.size(); i++) {
            for (int j = 0; j < weightByStatistic.size(); j++) {
                if (rangByIndicatorKeys.get(i).equals(rangByStatisticKeys.get(j))) {
                    numberAndWeightOfSentence.put(rangByIndicatorKeys.get(i), rangByIndicator.get(rangByIndicatorKeys.get(i)) + rangByStatistic.get(rangByStatisticKeys.get(j)));
                }
            }
        }
        Map<Integer, Integer> sortWeightOfSentence = methodSupporting.sortByWeightOfSentencesIntegerDescending(numberAndWeightOfSentence);
         return sortWeightOfSentence;
    }
}
