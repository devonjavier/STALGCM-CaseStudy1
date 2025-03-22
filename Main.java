import org.languagetool.JLanguageTool;
import org.languagetool.language.Filipino;
import org.languagetool.rules.RuleMatch;

import java.util.List;
import java.util.Scanner;

public class Main{
    public static void main(String args[]){
        System.out.println(" -- Running Program -- ");

        Scanner scanner = new Scanner(System.in);
        String input = null;
        JLanguageTool langTool = new JLanguageTool(new Filipino()); // language tool object for filo

        boolean running = true;

        while(running){
            System.out.print("Enter a Filipino sentence or type exit: ");
            input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }

            List<RuleMatch> word_matches = langTool.check(input);

            if (word_matches.isEmpty()) {
                System.out.println("✅ No grammar issues found!");
            } else {
                for (RuleMatch match : word_matches) {
                    GrammarError err = new GrammarError(
                        match.getRule().getId(),
                        match.getMessage(),
                        String.join(", ", match.getSuggestedReplacements())
                    );
                    err.display();
                }
            }

        }  
        
        System.out.println("Salamat!");

    }
}