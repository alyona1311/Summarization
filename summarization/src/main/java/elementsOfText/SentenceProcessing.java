/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementsOfText;

import ru.library.text.word.Word;
import ru.textanalysis.tfwwt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tfwwt.jmorfsdk.load.JMorfSdkLoad;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author Kasatkina Alyona
 */
public class SentenceProcessing {

    private final List<List<String>> wordsInSentence = new ArrayList<>();
    private final List<List<Word>> wordsInSentenceInInitialForm = new ArrayList<>();
    private final List<List<Word>> keyWordsInSentence = new ArrayList<>();
    private final Logger log = Logger.getLogger("system");
   
    public List<List<String>> splitSentenceByWords(Map<Integer, String> numberAndSentence) {
        List<String> numberAndSentenceValues = new ArrayList<>(numberAndSentence.values());
        String wordToLowerCase;
        Pattern pattern = Pattern.compile(", |\\.\\.?\\.? ?|! ?|\\? ?| —?\\(?\\[?\\{?<?<?«? ?|; |: |\\)?\\]?\\}?>?>?»?,?\\.?;?:?!?\\?? ");
        for (int i = 0; i < numberAndSentenceValues.size(); i++) {
            wordsInSentence.add(new ArrayList<String>());
            String[] words = pattern.split(numberAndSentenceValues.get(i));
            for (String word : words) {
                wordToLowerCase = word.toLowerCase();
                wordsInSentence.get(i).add(wordToLowerCase);
            }
        }
        return wordsInSentence;
    }
    
    public List<List<Word>> splitSentenceByWordsInInitialForm (List<List<String>> wordsInSentence){
        Logger logger = Logger.getLogger("loggerWords");
        JMorfSdk jMorfSdk = JMorfSdkLoad.loadFullLibrary();
        String wordInInitialForm;       
        int quantity;
        for(int i = 0; i < wordsInSentence.size(); i++){
            wordsInSentenceInInitialForm.add(new ArrayList<Word>());
            quantity = 0;
            for(int j = 0; j < wordsInSentence.get(i).size(); j++){
                List<String> initialWords = jMorfSdk.getStringInitialForm(wordsInSentence.get(i).get(quantity));
                if (!initialWords.isEmpty()) {
                   wordInInitialForm = initialWords.get(0);
                } else {                   
                    logger.log(Level.WARNING, "В словаре отсутствет слово: " + wordsInSentence.get(i).get(quantity));
                    wordInInitialForm = wordsInSentence.get(i).get(quantity);
                }
                Word oneWord = new Word(wordInInitialForm);
                wordsInSentenceInInitialForm.get(i).add(oneWord);
                quantity++;
            }           
        }
     return wordsInSentenceInInitialForm;
    }

    public List<List<Word>> extractKeyWordsInSentence(List<List<Word>> wordsInSentenceInInitialForm, List<Word> keyWords) {
        for (int i = 0; i < wordsInSentenceInInitialForm.size(); i++) {
            keyWordsInSentence.add(new ArrayList<Word>());
            for (int j = 0; j < keyWords.size(); j++) {
                for (int k = 0; k < wordsInSentenceInInitialForm.get(i).size(); k++) {
                    if (keyWords.get(j).getWord().compareTo(wordsInSentenceInInitialForm.get(i).get(k).getWord()) == 0) {
                        keyWordsInSentence.get(i).add(keyWords.get(j));
                    }
                }
            }

        }
        return keyWordsInSentence;
    }
}
