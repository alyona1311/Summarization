/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodOfSummarization;

import java.util.ArrayList;
import ru.library.text.word.Word;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kasatkina Alyona
 */
public class MethodPositionAndStatistic extends MethodPosition {

    private int sizePositionAndStatistic = 0;
    private final Logger log = Logger.getLogger("system");

    public Map<Integer, String> getReferat(Map<Integer, String> numberAndParagraphs, List<List<Word>> keyWordsInSentence, Map<Integer, String> numberAndSentance, int procentOfText) {
        Map<Integer, String> referatOfMethodPosition;
        referatOfMethodPosition = getReferat(numberAndParagraphs, numberAndSentance);

        Map<Integer, Float> weightOfSentenceStatistic;
        MethodStatistic mS = new MethodStatistic();
        weightOfSentenceStatistic = mS.getWeightOfSentences(keyWordsInSentence, numberAndSentance);

        int size = numberAndSentance.size();
        int sizeReferat = (int) ((size * procentOfText) / 100);

        int sizePosition = referatOfMethodPosition.size();
        Map<Integer, String> referat = new HashMap<>();
        List<Integer> keysWeight = new ArrayList<>(weightOfSentenceStatistic.keySet());
        if (sizeReferat > sizePosition) {
            sizePositionAndStatistic = sizeReferat - sizePosition;
            referat = getReferatMorePosition(sizePositionAndStatistic, referatOfMethodPosition, keysWeight, numberAndSentance);
        } else {
            if (sizeReferat < sizePosition) {
                sizePositionAndStatistic = sizePosition - sizeReferat;
                referat = getReferatLessPosition(sizePositionAndStatistic, referatOfMethodPosition, keysWeight);
            } else {
                if (sizeReferat == sizePosition) {
                    return referatOfMethodPosition;
                }
            }
        }
        Map<Integer, String> sortReferatPositionAndStatistic;
        sortReferatPositionAndStatistic = methodSupporting.sortByNumberOfSentence(referat);
        if (sortReferatPositionAndStatistic.isEmpty()){
             log.log(Level.WARNING, "Не удалось сформировать реферат позиционным-статистическим методом");
        }
        return sortReferatPositionAndStatistic;
    }

}
