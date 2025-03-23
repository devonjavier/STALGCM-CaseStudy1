package bmt;

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
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        try {
            Language lang = new Tagalog();
            JLanguageTool langTool = new JLanguageTool(lang);

            String text = "Siya ay nagpunta at at nagtrabaho.";
            List<RuleMatch> matches = langTool.check(text);

            Boolean entered = false;
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
