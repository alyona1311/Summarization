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
public class MethodIntegrationRandom {

    private final MethodSupporting methodSupporting = new MethodSupporting();
    private final Logger log = Logger.getLogger("system");

    public Map<Integer, String> getReferat(Map<Integer, String> numberAndParagraphs, List<List<Word>> wordsInSentenceInInitialForm, List<List<Word>> keyWordsInSentence, Map<Integer, String> numberAndSentence, String markers, int procentOfText) {
        MethodStatistic methodStatistic = new MethodStatistic();
        Map<Integer, String> referatOfMethodStatistic;
        referatOfMethodStatistic = methodStatistic.getReferat(keyWordsInSentence, numberAndSentence, procentOfText);

        MethodSymmetric methodSimmetr = new MethodSymmetric();
        Map<Integer, String> referatOfMethodSymmetric;
        referatOfMethodSymmetric = methodSimmetr.getReferat(keyWordsInSentence, numberAndSentence, procentOfText);

        MethodPosition methodPosition = new MethodPosition();
        Map<Integer, String> referatOfMethodPosition;
        referatOfMethodPosition = methodPosition.getReferat(numberAndParagraphs, numberAndSentence);

        MethodPositionAndStatistic methodPositionAnadStatistic = new MethodPositionAndStatistic();
        Map<Integer, String> referatOfMethodPositionAndStatistic;
        referatOfMethodPositionAndStatistic = methodPositionAnadStatistic.getReferat(numberAndParagraphs, keyWordsInSentence, numberAndSentence, procentOfText);

        MethodPositionAndSymmetric methodPositionAndSimmetr = new MethodPositionAndSymmetric();
        Map<Integer, String> referatOfMethodPositionAndSymmetric;
        referatOfMethodPositionAndSymmetric = methodPositionAndSimmetr.getReferat(numberAndParagraphs, keyWordsInSentence, numberAndSentence, procentOfText);

         MethodIndicator methodIndicator = new MethodIndicator();
        Map<Integer, String> referatOfMethodindicator;
        referatOfMethodindicator = methodIndicator.getReferat(numberAndSentence, wordsInSentenceInInitialForm, markers, procentOfText);
        
        MethodIndicatorAndStatistic methodIndicatorAndStatistic = new MethodIndicatorAndStatistic();
        Map<Integer, String> referatOfMethodIndicatorAndStatistic;
        referatOfMethodIndicatorAndStatistic = methodIndicatorAndStatistic.getReferat(numberAndSentence, wordsInSentenceInInitialForm, keyWordsInSentence, markers, procentOfText);

        MethodIndicatorAndSymmetric methodIndicatorAndSymmetric = new MethodIndicatorAndSymmetric();
        Map<Integer, String> referatOfMethodIndicatorAndSymmetric;
        referatOfMethodIndicatorAndSymmetric = methodIndicatorAndSymmetric.getReferat(numberAndSentence, wordsInSentenceInInitialForm, keyWordsInSentence, markers, procentOfText);
        
        List<Integer> sentenceInStatistic = new ArrayList<>(referatOfMethodStatistic.keySet());
        List<Integer> sentenceInSymmetric = new ArrayList<>(referatOfMethodSymmetric.keySet());
        List<Integer> sentenceInPosition = new ArrayList<>(referatOfMethodPosition.keySet());
        List<Integer> sentenceInPositionAndStatistic = new ArrayList<>(referatOfMethodPositionAndStatistic.keySet());
        List<Integer> sentenceInPositionAndSymmetric = new ArrayList<>(referatOfMethodPositionAndSymmetric.keySet());
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
        int thresholdValue = thresholdValues.get(0) - 1;

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
            referat = getReferatLessIntegration(referat, sizeIntegration);
        } else {
            if (referat.size() < sizeReferat) {
                sizeIntegration = sizeReferat - referat.size();
                referat = getReferatMoreIntegration(referat, mapSentenceAll, numberAndSentence, sizeIntegration);
            }
        }
        Map<Integer, String> sortReferatIntegration;
        sortReferatIntegration = methodSupporting.sortByNumberOfSentence(referat);
        if (sortReferatIntegration.isEmpty()){
             log.log(Level.WARNING, "Не удалось сформировать реферат интегральным (случайный выбор) методом");
        }
        return sortReferatIntegration;
    }

    private Map<Integer, String> getReferatLessIntegration(Map<Integer, String> referat, int sizeIntegration) {
        List<Integer> keysReferat = new ArrayList<>(referat.keySet());
        for (int i = 0; i < sizeIntegration; i++) {
            int oneKey = keysReferat.get(new Random().nextInt(keysReferat.size()));
            referat.remove(oneKey);
            keysReferat.remove(Integer.valueOf(oneKey));
        }
        return referat;
    }

    private Map<Integer, String> getReferatMoreIntegration(Map<Integer, String> referat, Map<Integer, Integer> mapSentenceAll, Map<Integer, String> numberAndSentence, int sizeIntegration) {
        List<Integer> keysReferat = new ArrayList<>(referat.keySet());
        for (int i = 0; i < keysReferat.size(); i++) {
            mapSentenceAll.remove(keysReferat.get(i));
        }

        List<Integer> keysSentenceAll = new ArrayList<>(mapSentenceAll.keySet());
        for (int i = 0; i < sizeIntegration; i++) {
            int oneKey = keysSentenceAll.get(new Random().nextInt(keysSentenceAll.size()));
            referat.put(oneKey, numberAndSentence.get(oneKey));
            keysSentenceAll.remove(Integer.valueOf(oneKey));
        }
        return referat;
    }
}
