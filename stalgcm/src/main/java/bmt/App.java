package bmt;

import java.io.File;
import java.net.URISyntaxException;

import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.language.Tagalog;
import org.languagetool.rules.RuleMatch;
import org.languagetool.rules.patterns.PatternRuleLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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

        // test exisiting rules

        langTool.getAllActiveRules().forEach(rule -> {
            System.out.println("Loaded Rule ID: " + rule.getId());
        });
        

        String text = "Siya ay nagpunta at at nagtrabaho.";
        System.out.println("Text being tested : " + text);

        try {
            List<RuleMatch> matches = langTool.check(text);

            boolean entered = false;
            for (RuleMatch match : matches) {
                entered = true;
                System.out.println("Issue at " + match.getFromPos() + "-" + match.getToPos() + ": " + match.getMessage());
            }
            System.out.println(entered);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
