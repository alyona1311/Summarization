/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodOfSummarization;

import methodSupporting.MethodSupporting;
import elementsOfText.Text;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kasatkina Alyona
 */
public class MethodPosition {

    protected MethodSupporting methodSupporting = new MethodSupporting();
    private final Logger log = Logger.getLogger("system");

    public Map<Integer, String> getReferat(Map<Integer, String> numberAndParagraphs, Map<Integer, String> numberAndSentence) {
        Text text = new Text();
        text.splitTextBySentences(numberAndParagraphs);
        List<List<Integer>> sentenceInParagraph = text.getSentencesInParagraph();

        Map<Integer, String> referat = new HashMap<>();
        if (!sentenceInParagraph.isEmpty()) {
            for (int i = 0; i < sentenceInParagraph.size(); i++) {
                if (sentenceInParagraph.get(i).size() > 1) {
                    referat.put(sentenceInParagraph.get(i).get(0), numberAndSentence.get(sentenceInParagraph.get(i).get(0)));
                    referat.put(sentenceInParagraph.get(i).get(sentenceInParagraph.get(i).size() - 1), numberAndSentence.get(sentenceInParagraph.get(i).get(sentenceInParagraph.get(i).size() - 1)));
                } else if (sentenceInParagraph.get(i).size() == 1) {
                    referat.put(sentenceInParagraph.get(i).get(0), numberAndSentence.get(sentenceInParagraph.get(i).get(0)));
                }
            }
        }
        Map<Integer, String> sortReferatPosition;
        sortReferatPosition = methodSupporting.sortByNumberOfSentence(referat);
        if (sortReferatPosition.isEmpty()){
             log.log(Level.WARNING, "Не удалось сформировать реферат позиционным методом");
        }
        return sortReferatPosition;
    }

    protected Map<Integer, String> getReferatMorePosition(int sizePositAndStat, Map<Integer, String> referatOfMethodPosition, List<Integer> keysWeight, Map<Integer, String> numberAndSentence) {
        for (int i = 0; i < keysWeight.size(); i++) {
            if (!referatOfMethodPosition.containsKey(keysWeight.get(i)) && sizePositAndStat != 0) {
                referatOfMethodPosition.put(keysWeight.get(i), numberAndSentence.get(keysWeight.get(i)));
                sizePositAndStat--;
            }
        }
        return referatOfMethodPosition;
    }

    protected Map<Integer, String> getReferatLessPosition(int sizePositAndStat, Map<Integer, String> referatOfMethodPosition, List<Integer> keysWeight) {
        for (int i = 1; i <= keysWeight.size(); i++) {
            if (referatOfMethodPosition.containsKey(keysWeight.get(keysWeight.size() - i)) && sizePositAndStat != 0) {
                referatOfMethodPosition.remove(keysWeight.get(keysWeight.size() - i));
                sizePositAndStat--;
            }
        }
        return referatOfMethodPosition;
    }

}
