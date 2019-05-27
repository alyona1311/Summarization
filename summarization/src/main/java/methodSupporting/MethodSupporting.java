/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methodSupporting;

import java.util.*;

/**
 *
 * @author Kasatkina Alyona
 */
public class MethodSupporting {

    public Map<Integer, String> sortByNumberOfSentence(Map<Integer, String> referat) {
        List<Map.Entry<Integer, String>> listReferat = new LinkedList(referat.entrySet());
        Collections.sort(listReferat, new Comparator<Map.Entry<Integer, String>>() {
            public int compare(Map.Entry<Integer, String> a, Map.Entry<Integer, String> b) {
                return (a.getKey()).compareTo(b.getKey());
            }
        });
        Map<Integer, String> sortMapReferat = new LinkedHashMap<>();
        for (Map.Entry<Integer, String> entry : listReferat) {
            sortMapReferat.put(entry.getKey(), entry.getValue());
        }
        return sortMapReferat;
    }

    public Map<Integer, Float> sortByWeightOfSentences(Map<Integer, Float> numberAndWeightOfSentence) {
        List<Map.Entry<Integer, Float>> listNumberAndWeight = new LinkedList(numberAndWeightOfSentence.entrySet());
        Collections.sort(listNumberAndWeight, new Comparator<Map.Entry<Integer, Float>>() {
            public int compare(Map.Entry<Integer, Float> a, Map.Entry<Integer, Float> b) {
                return (b.getValue()).compareTo(a.getValue());
            }
        });
        Map<Integer, Float> sortMapSentence = new LinkedHashMap<>();
        for (Map.Entry<Integer, Float> entry : listNumberAndWeight) {
            sortMapSentence.put(entry.getKey(), entry.getValue());
        }
        return sortMapSentence;
    }
    
    public Map<Integer, Integer> sortByWeightOfSentencesInteger(Map<Integer, Integer> numberAndWeightOfSentence) {
        List<Map.Entry<Integer, Integer>> listNumberAndWeight = new LinkedList(numberAndWeightOfSentence.entrySet());
        Collections.sort(listNumberAndWeight, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                return (b.getValue()).compareTo(a.getValue());
            }
        });
        Map<Integer, Integer> sortMapSentenceInteger = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : listNumberAndWeight) {
            sortMapSentenceInteger.put(entry.getKey(), entry.getValue());
        }
        return sortMapSentenceInteger;
    }
    
    public Map<Integer, Integer> sortByWeightOfSentencesIntegerDescending(Map<Integer, Integer> numberAndWeightOfSentence) {
        List<Map.Entry<Integer, Integer>> listNumberAndWeight = new LinkedList(numberAndWeightOfSentence.entrySet());
        Collections.sort(listNumberAndWeight, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                return (a.getValue()).compareTo(b.getValue());
            }
        });
        Map<Integer, Integer> sortMapSentenceInteger = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : listNumberAndWeight) {
            sortMapSentenceInteger.put(entry.getKey(), entry.getValue());
        }
        return sortMapSentenceInteger;
    }

    public Map<Integer, Integer> sortByReiteration(Map<Integer, Integer> mapKeyWordsAllRepeat) {
        List<Map.Entry<Integer, Integer>> listKeyWords = new LinkedList(mapKeyWordsAllRepeat.entrySet());
        Collections.sort(listKeyWords, new Comparator<Map.Entry<Integer, Integer>>() {
            public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
                return (b.getValue()).compareTo(a.getValue());
            }
        });
        Map<Integer, Integer> sortMapKeyWords = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : listKeyWords) {
            sortMapKeyWords.put(entry.getKey(), entry.getValue());
        }
        return sortMapKeyWords;
    }

    public List<Integer> getListThresholdValues(List<Integer> quantutyOfReiteration) {
        Map<Integer, Integer> mapReiteration = new HashMap<>();
        for (int i = 0; i < quantutyOfReiteration.size(); i++) {
            int reiteration = quantutyOfReiteration.get(i);
            if (!mapReiteration.containsKey(reiteration)) {
                mapReiteration.put(reiteration, 1);
            } else {
                mapReiteration.put(reiteration, mapReiteration.get(reiteration) + 1);
            }
        }
        Map<Integer, Integer> sortOfRepeat;
        sortOfRepeat = sortByReiteration(mapReiteration);
        List<Integer> sortOfRereatValues = new ArrayList<>(sortOfRepeat.keySet());
        return sortOfRereatValues;
    }
}
