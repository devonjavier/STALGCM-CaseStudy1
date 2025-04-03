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
import java.io.PrintWriter;
import java.util.List;

import java.util.ArrayList;

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

        // narin
        test_strings.add("Dumating narin si Tatay mula sa trabaho.");
        test_strings.add("Kumain narin kami bago umalis.");
        test_strings.add("Tapos narin ang proyekto natin para bukas.");
        test_strings.add("Pwede narin tayong umuwi dahil wala nang gagawin.");
        test_strings.add("Nandito narin ang iba nating kaibigan.");

        // parin
        test_strings.add("Hindi parin niya sinasagot ang tawag ko.");
        test_strings.add("Ang lakas parin ng ulan!");
        test_strings.add("Hinihintay ko parin sagot mo.");
        test_strings.add("Sarap parin ng luto ni lola!");
        test_strings.add("Iniisip parin kita araw-araw.");

        // nalang
        test_strings.add("Wag nalang natin ituloy ang plano, baka umulan pa.");
        test_strings.add("Tulog ka nalang, mamaya na lang tayo mag-usap.");
        test_strings.add("Puwede bang ikaw nalang ang bumili ng pagkain?");
        test_strings.add("Huwag nalang tayo mag-away, masyado nang mainit ulo ko.");
        test_strings.add("Hingi nalang tayo ng ekstensyon para sa deadline upang may oras pa tayo.");
;

        // palang
        test_strings.add("Dito palang kami sa Quirino station.");
        test_strings.add("Kakagising mo palang, pagod ka na kaagad?");
        test_strings.add("Ngayon ko palang natikman ang ganitong klaseng tamis na mangga!");
        test_strings.add("Sobrang saya ko, ikaw palang ang unang tao na nagbigay sa akin ng ganitong regalo.");
        test_strings.add("Alam mo ba na parating palang ang bagyo?");
        
        // nanaman
        test_strings.add("nanaman");
        test_strings.add("Dito nanaman tayo.");
        test_strings.add("Nahihilo nanaman ako.");
        test_strings.add("May assignment nanaman tayo?");
        test_strings.add("Bakit umiinom ka nanaman?!");
        test_strings.add("Ikaw at ako, nandito nanaman tayo.");
        
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
        test_strings.add("Naisulat mo ba ang ating abstrakt?");
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
