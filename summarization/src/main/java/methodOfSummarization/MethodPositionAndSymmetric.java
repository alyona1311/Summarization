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
public class MethodPositionAndSymmetric extends MethodPosition {

    private int sizePositionAndSymm = 0;
    private final Logger log = Logger.getLogger("system");

    public Map<Integer, String> getReferat(Map<Integer, String> numberAndParagraphs, List<List<Word>> keyWordsInSentence, Map<Integer, String> numberAndSentence, int procentOfText) {
        Map<Integer, String> referatOfMethodPosition;
        referatOfMethodPosition = getReferat(numberAndParagraphs, numberAndSentence);

        Map<Integer, Integer> weightOfSentenceSymmetric;
        MethodSymmetric mP = new MethodSymmetric();
        weightOfSentenceSymmetric = mP.getWeightOfSentences(keyWordsInSentence, numberAndSentence);

        int size = numberAndSentence.size();
        int sizeReferat = (int) ((size * procentOfText) / 100);
        int sizePosition = referatOfMethodPosition.size();

        Map<Integer, String> referat = new HashMap<>();
        List<Integer> keysWeight = new ArrayList<>(weightOfSentenceSymmetric.keySet());
        if (sizeReferat > sizePosition) {
            sizePositionAndSymm = sizeReferat - sizePosition;
            referat = getReferatMorePosition(sizePositionAndSymm, referatOfMethodPosition, keysWeight, numberAndSentence);
        } else {
            if (sizeReferat < sizePosition) {
                sizePositionAndSymm = sizePosition - sizeReferat;
                referat = getReferatLessPosition(sizePositionAndSymm, referatOfMethodPosition, keysWeight);
            } else {
                if (sizeReferat == sizePosition) {
                    return referatOfMethodPosition;
                }
            }
        }
        Map<Integer, String> sortReferatPositionAndSymmetric;
        sortReferatPositionAndSymmetric = methodSupporting.sortByNumberOfSentence(referat);
         if (sortReferatPositionAndSymmetric.isEmpty()){
             log.log(Level.WARNING, "Не удалось сформировать реферат позиционным-симметричным методом");
        }
        return sortReferatPositionAndSymmetric;
    }
}
