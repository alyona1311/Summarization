/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementsOfText;

import ru.library.text.word.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Kasatkina Alyona
 */
public class Text {
     private final Logger log = Logger.getLogger(Text.class.getName());
     private final Map<Integer, String> numberAndSentence = new HashMap<>();
     private final Map<Integer, String> numberAndParagraphs = new HashMap<>();
     private final List<List<Integer>> sentenceInParagraph = new ArrayList<>();
     private final List<Word> wordsInText = new ArrayList<>();
     
     public Map<Integer, String> splitTextByParagraphs(String lineString) {
         Pattern p = Pattern.compile("\n");
        // String[] parag = {};
         String[] parag = p.split(lineString);
         List<String> paragraph = new ArrayList<>();
         for(int i = 0; i < parag.length; i++){
             paragraph.add(parag[i]);
         }
         for (int i = 0; i < paragraph.size(); i++) {
             if (!paragraph.get(i).isEmpty()) {
                 numberAndParagraphs.put(i, paragraph.get(i));
             }
         }
        return numberAndParagraphs;
    }

    public Map<Integer, String> splitTextBySentences(Map<Integer, String> numberAndParagraphs){
        List<String> sentenceInText = new ArrayList<>();
        Pattern pattern = Pattern.compile("([А-Я]|[\\d*]).+(([А-Я]\\.)+\\s[А-Я].+)?[\\. |\\! |\\? | \\... ]");
        String[] sentencesArray;
        int counter = 0;
        for (int i = 0; i < numberAndParagraphs.size(); i++) {
             try {
            Matcher matcher = pattern.matcher(numberAndParagraphs.get(i));
            sentenceInParagraph.add(new ArrayList<>());
         //try{ 
                while (matcher.find()) {
                    sentencesArray = numberAndParagraphs.get(i).split("(?<=\\. |! |\\? | \\... )");
                    if (sentencesArray.length > 1) {
                        for (int j = 0; j < sentencesArray.length; j++) {
                            sentenceInText.add(sentencesArray[j]);
                            sentenceInParagraph.get(i).add(counter);
                            counter++;
                        }
                    } else {
                        sentenceInText.add(numberAndParagraphs.get(i));
                        sentenceInParagraph.get(i).add(counter);
                        counter++;
                    }
                }
            } catch (Exception ex) {
                log.log(Level.WARNING, "Не удалось распознать абзац: " + numberAndParagraphs.get(i));
            }

        }
        for (int i = 0; i < sentenceInText.size(); i++) {
            String sentenceInTextReplace = sentenceInText.get(i).replaceAll(" +", " ");
            numberAndSentence.put(i, sentenceInTextReplace);
        }
        return numberAndSentence;
        
    }
    
    public List<List<Integer>> getSentencesInParagraph(){
        return sentenceInParagraph;
    }
    
    public List<Word> splitTextByWords(List<List<Word>> wordsInSentenceInInitialForm){
        for (int i = 0; i < wordsInSentenceInInitialForm.size(); i++) {
            for (int j = 0; j < wordsInSentenceInInitialForm.get(i).size(); j++) {
                wordsInText.add(wordsInSentenceInInitialForm.get(i).get(j));
            }
        }
        return wordsInText;
    }

    
}
