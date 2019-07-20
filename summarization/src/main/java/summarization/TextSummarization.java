/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package summarization;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;



/**
 *
 * @author Kasatkina Alyona
 */
public class TextSummarization {

    public static String readMarkers(String inputFile) {
        BufferedReader reader;
        String line;
        List<String> allText = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            while ((line = reader.readLine()) != null) {
                String str = line.trim();
                allText.add(str);
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String text = String.join(" ", allText);
        return text;
    }

    public static String readOnlyText(String inputFile) {
        BufferedReader reader;
        String line;
        List<String> allText = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            while ((line = reader.readLine()) != null) {
                if (!line.trim().equals("")) {
                    allText.add(line);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String text = String.join("\n", allText);
        return text;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        

        Logger log = Logger.getLogger("system");
        Logger loggerWords = Logger.getLogger("loggerWords");

        FileHandler fileHandler = null;
        try {
            fileHandler = new FileHandler("result/methodsOfWeight/system.log");
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Error opening or writing to file", ex);
            ex.printStackTrace();
        } catch (SecurityException ex) {
            log.log(Level.SEVERE, "Cannoot perform action", ex);
            ex.printStackTrace();
        }

        FileHandler fileHandlerWords = null;
        try {
            fileHandlerWords = new FileHandler("result/methodsOfWeight/ExcaptionWords.txt");
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Error opening or writing to file", ex);
            ex.printStackTrace();
        } catch (SecurityException ex) {
            log.log(Level.SEVERE, "Cannoot perform action", ex);
            ex.printStackTrace();
        }

        SimpleFormatter sf = new SimpleFormatter();
        fileHandler.setFormatter(sf);

        SimpleFormatter sfWords = new SimpleFormatter();
        fileHandlerWords.setFormatter(sfWords);

        log.addHandler(fileHandler);
        loggerWords.addHandler(fileHandlerWords);


    }

}
