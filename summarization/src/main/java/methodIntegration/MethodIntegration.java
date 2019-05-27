/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodIntegration;

import methodSupporting.MethodSupporting;
import ru.library.text.word.Word;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import methodOfSummarization.MethodIndicator;
import methodOfSummarization.MethodIndicatorAndStatistic;
import methodOfSummarization.MethodIndicatorAndSymmetric;
import methodOfSummarization.MethodPosition;
import methodOfSummarization.MethodPositionAndStatistic;
import methodOfSummarization.MethodPositionAndSymmetric;
import methodOfSummarization.MethodStatistic;
import methodOfSummarization.MethodSymmetric;

/**
 *
 * @author Kasatkina Alyona
 */
public class MethodIntegration {

    private final MethodSupporting methodSupporting = new MethodSupporting();
    private final Logger log = Logger.getLogger("system");

    public Map<Integer, String> getReferat(Map<Integer, String> numberAndParagraphs, List<List<Word>> wordsInSentenceInInitialForm, List<List<Word>> keyWordsInSentence, Map<Integer, String> numberAndSentence, String markers, int procentOfText) {
        MethodStatistic methodStatistic = new MethodStatistic();
        Map<Integer, String> referatOfMethodStatistic;
        referatOfMethodStatistic = methodStatistic.getReferat(keyWordsInSentence, numberAndSentence, procentOfText);

        MethodSymmetric methodSymmetric = new MethodSymmetric();
        Map<Integer, String> referatOfMethodSimmetr;
        referatOfMethodSimmetr = methodSymmetric.getReferat(keyWordsInSentence, numberAndSentence, procentOfText);

        MethodPosition methodPosition = new MethodPosition();
        Map<Integer, String> referatOfMethodPosition;
        referatOfMethodPosition = methodPosition.getReferat(numberAndParagraphs, numberAndSentence);

        MethodPositionAndStatistic methodPositionAnadStatistic = new MethodPositionAndStatistic();
        Map<Integer, String> referatOfMethodPositionAndStatistic;
        referatOfMethodPositionAndStatistic = methodPositionAnadStatistic.getReferat(numberAndParagraphs, keyWordsInSentence, numberAndSentence, procentOfText);

        MethodPositionAndSymmetric methodPositionAndSymmetric = new MethodPositionAndSymmetric();
        Map<Integer, String> referatOfMethodPositionAndSimmetr;
        referatOfMethodPositionAndSimmetr = methodPositionAndSymmetric.getReferat(numberAndParagraphs, keyWordsInSentence, numberAndSentence, procentOfText);
        
        MethodIndicator methodIndicator = new MethodIndicator();
        Map<Integer, String> referatOfMethodindicator;
        referatOfMethodindicator = methodIndicator.getReferat(numberAndSentence, wordsInSentenceInInitialForm, markers,procentOfText);
        
        MethodIndicatorAndStatistic methodIndicatorAndStatistic = new MethodIndicatorAndStatistic();
        Map<Integer, String> referatOfMethodIndicatorAndStatistic;
        referatOfMethodIndicatorAndStatistic = methodIndicatorAndStatistic.getReferat(numberAndSentence, wordsInSentenceInInitialForm, keyWordsInSentence, markers, procentOfText);

        MethodIndicatorAndSymmetric methodIndicatorAndSymmetric = new MethodIndicatorAndSymmetric();
        Map<Integer, String> referatOfMethodIndicatorAndSymmetric;
        referatOfMethodIndicatorAndSymmetric = methodIndicatorAndSymmetric.getReferat(numberAndSentence, wordsInSentenceInInitialForm, keyWordsInSentence, markers, procentOfText);
        
        List<Integer> sentenceInStatistic = new ArrayList<>(referatOfMethodStatistic.keySet());
        List<Integer> sentenceInSymmetric = new ArrayList<>(referatOfMethodSimmetr.keySet());
        List<Integer> sentenceInPosition = new ArrayList<>(referatOfMethodPosition.keySet());
        List<Integer> sentenceInPositionAndStatistic = new ArrayList<>(referatOfMethodPositionAndStatistic.keySet());
        List<Integer> sentenceInPositionAndSymmetric = new ArrayList<>(referatOfMethodPositionAndSimmetr.keySet());
        List<Integer> sentenceInIndicator = new ArrayList<>(referatOfMethodindicator.keySet());
        List<Integer> sentenceInIndicatorAndStatistic = new ArrayList<>(referatOfMethodIndicatorAndStatistic.keySet());
        List<Integer> sentenceInIndicatorAndSymmetric = new ArrayList<>(referatOfMethodIndicatorAndSymmetric.keySet());

        List<Integer> sentenceOfAllMethod = new ArrayList<>();
        sentenceOfAllMethod.addAll(sentenceInStatistic);
        sentenceOfAllMethod.addAll(sentenceInSymmetric);
        sentenceOfAllMethod.addAll(sentenceInPosition);
        sentenceOfAllMethod.addAll(sentenceInPositionAndStatistic);
        sentenceOfAllMethod.addAll(sentenceInPositionAndSymmetric);
        sentenceOfAllMethod.addAll(sentenceInIndicator);
        sentenceOfAllMethod.addAll(sentenceInIndicatorAndStatistic);
        sentenceOfAllMethod.addAll(sentenceInIndicatorAndSymmetric);
                
        Map<Integer, Integer> mapSentenceAll = new HashMap<>();
        for (int i = 0; i < sentenceOfAllMethod.size(); i++) {
            int oneSentence = sentenceOfAllMethod.get(i);
            if (!mapSentenceAll.containsKey(oneSentence)) {
                mapSentenceAll.put(oneSentence, 1);
            } else {
                mapSentenceAll.put(oneSentence, mapSentenceAll.get(oneSentence) + 1);
            }
        }
        List<Integer> keysMapSentenceAll = new ArrayList<>(mapSentenceAll.values());
        List<Integer> thresholdValues = methodSupporting.getListThresholdValues(keysMapSentenceAll);
        int thresholdValue = 0;
        try{
        thresholdValue = thresholdValues.get(0) - 1;
        } catch (IndexOutOfBoundsException ex){
            log.log(Level.WARNING, "Невозможно определить порог пересечения");
        }

        Map<Integer, String> referat = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : mapSentenceAll.entrySet()) {
            if (entry.getValue() > thresholdValue) {
                int oneSentence = entry.getKey();
                referat.put(oneSentence, numberAndSentence.get(oneSentence));
            }
        }

        int size = numberAndSentence.size();
        int sizeReferat = (int) ((size * procentOfText) / 100);
        int sizeIntegration;
        if (referat.size() > sizeReferat) {
            sizeIntegration = referat.size() - sizeReferat;
            referat = getReferatLessIntegration(referat, sizeIntegration, thresholdValues, mapSentenceAll);
        } else {
            if (referat.size() < sizeReferat) {
                sizeIntegration = sizeReferat - referat.size();
                for (int i = 0; i < thresholdValues.size(); i++) {
                    if (thresholdValues.get(i) > thresholdValue) {
                        thresholdValues.remove(Integer.valueOf(thresholdValues.get(i)));
                    }
                }
                referat = getReferatMoreIntegration(referat, sizeIntegration, thresholdValues, mapSentenceAll, numberAndSentence);
            }
        }
        Map<Integer, String> sortReferatIntegration;
        sortReferatIntegration = methodSupporting.sortByNumberOfSentence(referat);
        if (sortReferatIntegration.isEmpty()){
             log.log(Level.WARNING, "Не удалось сформировать реферат интегральным методом");
        }
        return sortReferatIntegration;
    }

    private Map<Integer, String> getReferatLessIntegration(Map<Integer, String> referat, int sizeIntegration, List<Integer> thresholdValues, Map<Integer, Integer> mapSentenceAll) {
        Collections.sort(thresholdValues);
        for (int j = 0; j < thresholdValues.size(); j++) {
            for (Map.Entry<Integer, Integer> entry : mapSentenceAll.entrySet()) {
                if (entry.getValue() == thresholdValues.get(j) && sizeIntegration != 0) {
                    int oneKey = entry.getKey();
                    if (referat.containsKey(oneKey)) {
                        referat.remove(oneKey);
                        sizeIntegration--;
                    }
                }
            }
        }
        return referat;
    }

    private Map<Integer, String> getReferatMoreIntegration(Map<Integer, String> referat, int sizeIntegration, List<Integer> thresholdValues, Map<Integer, Integer> mapSentenceAll, Map<Integer, String> numberAndSentence) {
        List<Integer> keysReferat = new ArrayList<>(referat.keySet());
        for (int i = 0; i < keysReferat.size(); i++) {
            mapSentenceAll.remove(keysReferat.get(i));
        }
        Collections.sort(thresholdValues, Collections.reverseOrder());
        for (int j = 0; j < thresholdValues.size(); j++) {
            for (Map.Entry<Integer, Integer> entry : mapSentenceAll.entrySet()) {
                if (entry.getValue() == thresholdValues.get(j) && sizeIntegration != 0) {
                    int oneKey = entry.getKey();
                    referat.put(oneKey, numberAndSentence.get(oneKey));
                    sizeIntegration--;
                }
            }
        }
        return referat;
    }
}
