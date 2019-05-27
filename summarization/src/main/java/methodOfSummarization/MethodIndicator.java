/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodOfSummarization;

import methodSupporting.MethodSupporting;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import ru.library.text.word.Word;
import ru.textanalysis.tfwwt.jmorfsdk.JMorfSdk;
import ru.textanalysis.tfwwt.jmorfsdk.load.JMorfSdkLoad;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Kasatkina Alyona
 */
public class MethodIndicator {

    private final Map<Integer, Integer> numberAndWeightOfSentence = new HashMap<>();
    private final Logger log = Logger.getLogger("system");
    private final MethodSupporting methodSupporting = new MethodSupporting();
    private final JMorfSdk jMorfSdk = JMorfSdkLoad.loadFullLibrary();

    
    public Map<Integer, String> getReferat(Map<Integer, String> numberAndSentence, List<List<Word>> wordsInSentenceInInitialForm, String markers, int procentOfText){
        Map<Integer, Integer> weightOfSentence = getWeightOfSentences(numberAndSentence, wordsInSentenceInInitialForm, markers);   
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
        Map<Integer, String> sortReferatIndicator;
        sortReferatIndicator = methodSupporting.sortByNumberOfSentence(referat);
        if (sortReferatIndicator.isEmpty()){
             log.log(Level.WARNING, "Не удалось сформировать реферат индикаторным методом");
        }
        return sortReferatIndicator;
    }
    
    public Map<Integer, Integer> getWeightOfSentences (Map<Integer, String> numberAndSentence, List<List<Word>> wordsInSentenceInInitialForm, String markers){
        List<Integer> indicatorsWeight = getWeightOfSentenceByIndicators(numberAndSentence);
        List<Integer> markersWeight = getWeightOfSentenceByMarkers(wordsInSentenceInInitialForm, markers);
        for(int i = 0; i < numberAndSentence.size(); i++){
            numberAndWeightOfSentence.put(i, indicatorsWeight.get(i) + markersWeight.get(i));
        }
        Map<Integer, Integer> sortWeightOfSentence = methodSupporting.sortByWeightOfSentencesInteger(numberAndWeightOfSentence);
        return sortWeightOfSentence;
    }

    public List<Integer> getWeightOfSentenceByIndicators(Map<Integer, String> numberAndSentence) {
        List<Integer> weightOfSentenceByIndicators = new ArrayList<>();
        List<String> introductoryWords;
        String inputFile = "";
        introductoryWords = readIndicatorsFromResources();
        int weightSentence;
        List<String> numberAndSentenceValues = new ArrayList<>(numberAndSentence.values());               
        for(int i = 0; i < numberAndSentenceValues.size(); i++){
            String lineToLowerCase = numberAndSentenceValues.get(i).toLowerCase();
            weightSentence = 0;
            for (int j = 0; j < introductoryWords.size(); j++) {
                Pattern pattern = Pattern.compile("\\b" + introductoryWords.get(j) + "\\b");
                Matcher matcher = pattern.matcher(lineToLowerCase);
                while(matcher.find()){
                    weightSentence = weightSentence + 1;
                }
            }
            weightOfSentenceByIndicators.add(weightSentence);
        }
        return weightOfSentenceByIndicators;
    }

    public List<Integer> getWeightOfSentenceByMarkers(List<List<Word>> wordsInSentenceInInitialForm, String markers) {
        List<Integer> weightOfSentenceByMarkers = new ArrayList<>();
        if (markers.isEmpty()) {
            System.out.println("Вы не ввели ключевые слова");
            int emptyMarkers = 0;
            for (int i = 0; i < wordsInSentenceInInitialForm.size(); i++) {
                weightOfSentenceByMarkers.add(emptyMarkers);
            }
        } else {
            Pattern pattern = Pattern.compile(",? ");
            String[] listMarkers = pattern.split(markers);
            weightOfSentenceByMarkers = countWeightOfSentenceByMarkers(listMarkers, wordsInSentenceInInitialForm);
        }
        return weightOfSentenceByMarkers;
    }


    public List<Integer> countWeightOfSentenceByMarkers(String[] listWords, List<List<Word>> wordsInSentenceInInitialForm) {
        List<Integer> weightOfSentences = new ArrayList<>();
        String wordToLowerCase;
        List<Word> words = new ArrayList<>();
        String wordInInitialForm;
        for (int i = 0; i < listWords.length; i++) {
            wordToLowerCase = listWords[i].toLowerCase();
            List<String> wordsInInitialForm = jMorfSdk.getStringInitialForm(wordToLowerCase);
            if (!wordsInInitialForm.isEmpty()) {
                wordInInitialForm = wordsInInitialForm.get(0);
            } else {
                log.log(Level.WARNING,( "В словаре отсутстует слово: "  + wordToLowerCase + "или слово написано неправильно"));
                wordInInitialForm = wordToLowerCase;
            }
            Word oneWord = new Word(wordInInitialForm);
            words.add(oneWord);
        }
        for (int i = 0; i < words.size(); i++) {
            System.out.println(i + " " + words.get(i));
        }
        int weightSentence;
        for (int i = 0; i < wordsInSentenceInInitialForm.size(); i++) {
            weightSentence = 0;
            for (int k = 0; k < wordsInSentenceInInitialForm.get(i).size(); k++) {
                for (int j = 0; j < words.size(); j++) {
                    if (wordsInSentenceInInitialForm.get(i).get(k).getWord().equals(words.get(j).getWord())) {
                        weightSentence = weightSentence + 1;
                        }
                    }
                }
             weightOfSentences.add(weightSentence);
            }
        return weightOfSentences;
    }
    
    public Map<Integer, Integer> getRangOfWeightSentences(Map<Integer, Integer> sortWeightOfSentence) {
        List<Integer> numberOfSentences = new ArrayList<>(sortWeightOfSentence.keySet());     
        List<Integer> weightOfSentences = new ArrayList<>(sortWeightOfSentence.values());
        Map<Integer, Integer> rangWeightOfSentences = new HashMap<>();
        int counter = 1;
        int x = 0;
        for(int i = 0; i < numberOfSentences.size(); i++){
            int weight = weightOfSentences.get(i);
           int temp = 0;
            for(int j = i; j < numberOfSentences.size(); j++){
                if(weightOfSentences.get(j).equals(weight)){
                    rangWeightOfSentences.put(numberOfSentences.get(j), counter);
                    x++;
                    temp++;
                } 
            }
            i=x-1;
           counter += temp;
        }
        Map<Integer, Integer> sortRang = methodSupporting.sortByWeightOfSentencesIntegerDescending(rangWeightOfSentences);
        return sortRang;
    }
    
     public List<String> readIndicatorsFromResources() {
        BufferedReader reader;
        String line;
        List<String> allText = new ArrayList<>();
        try {
            
            reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/indicators/indicators.txt"), "UTF-8"));
            while ((line = reader.readLine()) != null) {
                String str = line.trim();
                allText.add(str);               
            }
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, "File not found. jar: /indicators/indicators.txt", ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Could not open file. Name of file: jar: /indicators/indicators.txt", ex);
            ex.printStackTrace();
        }
        return allText;
    }
     
     public List<String> readIndicatorsFromFile(String inputFile) {
        BufferedReader reader;
        String line;
        List<String> allText = new ArrayList<>();
        try {
            
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
            while ((line = reader.readLine()) != null) {
                String str = line.trim();
                allText.add(str);               
            }
        } catch (FileNotFoundException ex) {
            log.log(Level.SEVERE, "File not found. Name of file: " + inputFile, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Could not open file. Name of file: " + inputFile, ex);
            ex.printStackTrace();
        }
        return allText;
    }
}

