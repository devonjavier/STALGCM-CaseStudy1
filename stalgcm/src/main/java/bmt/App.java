package bmt;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URISyntaxException;

import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.language.Tagalog;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.patterns.PatternRuleLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import java.util.ArrayList;
/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        Language lang = new Tagalog();
        JLanguageTool langTool = new JLanguageTool(lang);

   


        try {
            File file = new File(App.class.getClassLoader().getResource("custom-rules/grammar.xml").toURI());
            PatternRuleLoader loader = new PatternRuleLoader();
            loader.getRules(file, null).forEach(rule -> langTool.addRule(rule));
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }

        try{
            File file = new File(App.class.getClassLoader().getResource("custom-rules/grammar.xml").toURI());
            if (!file.exists()) {
                System.out.println("ERROR: grammar.xml file not found at expected location.");
            } else {
                System.out.println("Successfully found grammar.xml at: " + file.getAbsolutePath());
            }
        } catch (URISyntaxException e) {
            System.out.println("ERROR: Unable to access grammar.xml file.");
            e.printStackTrace();
        }
    
    
        List<String> test_strings = new ArrayList<>();

        // -ng vs na test
        test_strings.add("Doon sila sa malaki na bahay.");
        test_strings.add("Binigyan mo siya ng mabango na bulaklak?");
        test_strings.add("Nagkaroon ng magulo na usapan tungkol sa paksa.");
        test_strings.add("Doon na lang tayo sa nagbebenta ng mura na bilhin.");
        test_strings.add("Huwag tayo sa mahaba na pila.");
        

        // between errors test

        // nalang
        test_strings.add("Huwag nalang tayo magkita.");

        // parin
        test_strings.add("Hindi parin ako nakarating diyan.");
        
        // repeated_ka test
        test_strings.add("Kakabili lang namin ng bagong kotse.");
        test_strings.add("Kakagawa ko lang ng assignment ko bago ka dumating.");
        test_strings.add("Kakapalit ko lang ng damit ko.");
        test_strings.add("Basa pa ang lababo dahil kakahugas ko lang ng pinggan.");
        test_strings.add("Kakauwi pa lang nila galing probinsya.");

        // kt_ct_test
        test_strings.add("Ikaw ba ay ang aking kontakt para sa araw na ito?");
        test_strings.add("Iyan ang korekt na sagot sa tanong.");
        test_strings.add("Hindi ako adikt sa droga!");
        test_strings.add("Naisulat mo na ba ang ating abstrakt?");
        test_strings.add("Ilagay mo lang ang iyong signatura dito sa kontrakt");

        langTool.disableRule("MORFOLOGIK_RULE_TL"); // to see clearly which of the rules added are correct 

        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter("output.txt")))) {
            for (String text : test_strings) {
                List<RuleMatch> matches = langTool.check(text);

                writer.println("Text being tested: " + text);
                boolean errorDetected = false;

                for (RuleMatch match : matches) {
                    errorDetected = true;
                    writer.println(" -- Rule ID: " + match.getRule().getId());
                    writer.println(" -- Issue at " + match.getFromPos() + "-" + match.getToPos() + ": " + match.getMessage());
                    writer.println(" -- Suggested correction: " + match.getSuggestedReplacements());
                }

                writer.println("Error Detected : " + errorDetected);
                writer.println();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
