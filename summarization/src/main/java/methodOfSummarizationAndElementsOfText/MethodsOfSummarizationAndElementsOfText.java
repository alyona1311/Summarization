/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodOfSummarizationAndElementsOfText;

import methodOfSummarization.MethodPositionAndSymmetric;
import methodOfSummarization.MethodPosition;
import methodOfSummarization.MethodSymmetric;
import methodOfSummarization.MethodStatistic;
import methodOfSummarization.MethodIndicatorAndStatistic;
import methodOfSummarization.MethodPositionAndStatistic;
import methodOfSummarization.MethodIndicatorAndSymmetric;
import methodOfSummarization.MethodIndicator;
import methodIntegration.MethodIntegrationRandom;
import methodIntegration.MethodIntegration;
import elementsOfText.SentenceProcessing;
import elementsOfText.KeyWords;
import elementsOfText.Text;
import java.util.ArrayList;
import ru.library.text.word.Word;

import java.util.List;
import java.util.Map;

/**
 *
 * @author Kasatkina Alyona
 */
public class MethodsOfSummarizationAndElementsOfText {

    public Map<Integer, String> getNumberAndParagraphs(String inputText) {
        Text text = new Text();
        Map<Integer, String> numberAndParagraphs = text.splitTextByParagraphs(inputText);
        return numberAndParagraphs;
    }

    public Map<Integer, String> getNumberAndSentence(String inputText) {
        Map<Integer, String> numberAndParagraphs = getNumberAndParagraphs(inputText);
        Text text = new Text();
        Map<Integer, String> numberAndSentence = text.splitTextBySentences(numberAndParagraphs);
        return numberAndSentence;
    }

    public List<List<String>> getWordsInSentences (String inputText){
        Map<Integer, String> numberAndSentence = getNumberAndSentence(inputText);
        SentenceProcessing sentence = new SentenceProcessing();
        List<List<String>> wordsInSentence = sentence.splitSentenceByWords(numberAndSentence);
        return wordsInSentence;
    }
    
    public List<List<Word>> getWordsInSentencesInInitialForm(String inputText){
        List<List<String>> wordsInSentence = getWordsInSentences(inputText);
        SentenceProcessing sentence = new SentenceProcessing();
        List<List<Word>> wordsInSentencesInInitialForm = sentence.splitSentenceByWordsInInitialForm(wordsInSentence);
        return wordsInSentencesInInitialForm;
    }
    
    public List<Word> getWordsInText (String inputText){
        List<List<Word>> wordsInSentencesInInitialForm = getWordsInSentencesInInitialForm(inputText);
        Text text = new Text();
        List<Word> wordsInText = text.splitTextByWords(wordsInSentencesInInitialForm);
        return wordsInText;
    }
    
    public List<Word> getKeyWords(String inputText) {
        List<Word> wordsInText = getWordsInText(inputText);
        int numberOfKeyWords = 10;
        KeyWords keyWord = new KeyWords();
        List<Word> keyWords = keyWord.extractKeyWords(wordsInText, numberOfKeyWords, inputText);
        return keyWords;
    }

    public List<List<Word>> getKeyWordsInSentences(String inputText) {
        List<List<Word>> wordsInSentencesInInitialForm = getWordsInSentencesInInitialForm(inputText);
        List<Word> keyWords = getKeyWords(inputText);
        SentenceProcessing sentence = new SentenceProcessing();
        List<List<Word>> keyWordsInSentences = sentence.extractKeyWordsInSentence(wordsInSentencesInInitialForm, keyWords);
        return keyWordsInSentences;
    }

    public Map<Integer, String> getReferatOfMethodStatistic(String inputText, int procentOfText) {
        Map<Integer, String> numberAndSentenceSt = getNumberAndSentence(inputText);
        List<List<Word>> keyWordsInSentenceSt = getKeyWordsInSentences(inputText);

        MethodStatistic methodStatistic = new MethodStatistic();
        Map<Integer, String> referatOfMethodStatistic = methodStatistic.getReferat(keyWordsInSentenceSt, numberAndSentenceSt, procentOfText);
        return referatOfMethodStatistic;
    }

    public Map<Integer, String> getReferatOfMethodSymmetric(String inputText, int procentOfText) {
        Map<Integer, String> numberAndSentenceSm = getNumberAndSentence(inputText);
        List<List<Word>> keyWordsInSentenceSm = getKeyWordsInSentences(inputText);

        MethodSymmetric methodSimmetr = new MethodSymmetric();
        Map<Integer, String> referatOfMethodSimmetr = methodSimmetr.getReferat(keyWordsInSentenceSm, numberAndSentenceSm, procentOfText);
        return referatOfMethodSimmetr;
    }

    public Map<Integer, String> getReferatOfMethodPosition(String inputText) {
        Map<Integer, String> numberAndParagraphsPos = getNumberAndParagraphs(inputText);
        Map<Integer, String> numberAndSentencePos = getNumberAndSentence(inputText);

        MethodPosition methodPosition = new MethodPosition();
        Map<Integer, String> referatOfMethodPosition = methodPosition.getReferat(numberAndParagraphsPos, numberAndSentencePos);
        return referatOfMethodPosition;
    }

    public Map<Integer, String> getReferatOfMethodPositionAndStatistic(String inputText, int procentOfText) {
        Map<Integer, String> numberAndParagraphsPosSt = getNumberAndParagraphs(inputText);
        Map<Integer, String> numberAndSentencePosSt = getNumberAndSentence(inputText);
        List<List<Word>> keyWordsInSentencePosSt = getKeyWordsInSentences(inputText);

        MethodPositionAndStatistic methodPositionAndStatistic = new MethodPositionAndStatistic();
        Map<Integer, String> referatOfMethodPositionAndStatistic = methodPositionAndStatistic.getReferat(numberAndParagraphsPosSt, keyWordsInSentencePosSt, numberAndSentencePosSt, procentOfText);
        return referatOfMethodPositionAndStatistic;
    }

    public Map<Integer, String> getReferatOfMethodPositionAndSymmetric(String inputText, int procentOfText) {
        Map<Integer, String> numberAndParagraphsPosSm = getNumberAndParagraphs(inputText);
        Map<Integer, String> numberAndSentencePosSm = getNumberAndSentence(inputText);
        List<List<Word>> keyWordsInSentencePosSm = getKeyWordsInSentences(inputText);

        MethodPositionAndSymmetric methodPositionAndSimmetr = new MethodPositionAndSymmetric();
        Map<Integer, String> referatOfMethodPositionAndStatistic = methodPositionAndSimmetr.getReferat(numberAndParagraphsPosSm, keyWordsInSentencePosSm, numberAndSentencePosSm, procentOfText);
        return referatOfMethodPositionAndStatistic;
    }
    
    public Map<Integer, String> getReferatOfMethodIndicator(String inputText, String markers, int procentOfText){
        Map<Integer, String> numberAndSentenceInd = getNumberAndSentence(inputText);
        List<List<Word>> wordsInSentencesInInitialFormInd = getWordsInSentencesInInitialForm(inputText);
        
        MethodIndicator methodIndicator = new MethodIndicator();
        Map<Integer, String> referatOfMethodIndicator = methodIndicator.getReferat(numberAndSentenceInd, wordsInSentencesInInitialFormInd, markers, procentOfText);
        return referatOfMethodIndicator;
    }
    
    public Map<Integer, String> getReferatOfMethodIndicatorAndStatistic(String inputText, String markers, int procentOfText){
        Map<Integer, String> numberAndSentenceIndAndSt = getNumberAndSentence(inputText);
        List<List<Word>> wordsInSentencesInInitialFormIndAndSt = getWordsInSentencesInInitialForm(inputText);
        List<List<Word>> keyWordsInSentencesIndAndSt = getKeyWordsInSentences(inputText);
        
        MethodIndicatorAndStatistic methodIndicatorAndStatistic = new MethodIndicatorAndStatistic();
        Map<Integer, String> referatOfMethodIndicatorAndStatistic = methodIndicatorAndStatistic.getReferat(numberAndSentenceIndAndSt, wordsInSentencesInInitialFormIndAndSt,keyWordsInSentencesIndAndSt, markers, procentOfText);
        return referatOfMethodIndicatorAndStatistic;
    }
    
    public Map<Integer, String> getRefeartOfMethodIndicatorAndSymmetric(String inputText, String markers, int procentOfText){
        Map<Integer, String> numberAndSentenceIndAndSym = getNumberAndSentence(inputText);
        List<List<Word>> wordsInSentencesInInitialFormIndAndSym = getWordsInSentencesInInitialForm(inputText);
        List<List<Word>> keyWordsInSentencesIndAndSym = getKeyWordsInSentences(inputText);
        
        MethodIndicatorAndSymmetric methodIndicatorAndSymmetric = new MethodIndicatorAndSymmetric();
        Map<Integer, String> referatOfMethodIndicatorAndSymmetric = methodIndicatorAndSymmetric.getReferat(numberAndSentenceIndAndSym, wordsInSentencesInInitialFormIndAndSym,keyWordsInSentencesIndAndSym, markers, procentOfText);
        return referatOfMethodIndicatorAndSymmetric;
    }
    public Map<Integer, String> getReferatOfMethodIntegration(String inputText, String markers, int procentOfText) {
        Map<Integer, String> numberAndParagraphsIn = getNumberAndParagraphs(inputText);
        Map<Integer, String> numberAndSentenceIn = getNumberAndSentence(inputText);
        List<List<Word>> keyWordsInSentenceIn = getKeyWordsInSentences(inputText);
        List<List<Word>> wordsInSentencesInInitialFormIn = getWordsInSentencesInInitialForm(inputText);

        MethodIntegration methodIntegration = new MethodIntegration();
        Map<Integer, String> referatOfMethodIntegration = methodIntegration.getReferat(numberAndParagraphsIn, wordsInSentencesInInitialFormIn, keyWordsInSentenceIn, numberAndSentenceIn, markers, procentOfText);
        return referatOfMethodIntegration;
    }

    public Map<Integer, String> getReferatOfMethodIntegrationRandom(String inputText, String markers, int procentOfText) {
        Map<Integer, String> numberAndParagraphsInRan = getNumberAndParagraphs(inputText);
        Map<Integer, String> numberAndSentenceInRan = getNumberAndSentence(inputText);
        List<List<Word>> keyWordsInSentenceInRan = getKeyWordsInSentences(inputText);
        List<List<Word>> wordsInSentencesInInitialFormInR = getWordsInSentencesInInitialForm(inputText);

        MethodIntegrationRandom methodIntegrationRandom = new MethodIntegrationRandom();
        Map<Integer, String> referatOfMethodIntegration = methodIntegrationRandom.getReferat(numberAndParagraphsInRan, wordsInSentencesInInitialFormInR, keyWordsInSentenceInRan, numberAndSentenceInRan, markers, procentOfText);
        return referatOfMethodIntegration;
    }
    public String getReferatToString(Map<Integer, String> referatToMap){
        List<String> referatToListString = new ArrayList<>(referatToMap.values());
        String referatToString = String.join(" ", referatToListString);
        return referatToString;
    }
}
