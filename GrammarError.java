public class GrammarError{

    String ruleId;
    String message;
    String suggestion;

    GrammarError(String ruleId, String message, String suggestion) {
        this.ruleId = ruleId;
        this.message = message;
        this.suggestion = suggestion;
    }

    void display() {
        System.out.println("Rule ID: " + ruleId);
        System.out.println("Message: " + message);
        System.out.println("Suggestion: " + suggestion);
        System.out.println("--------------------------------");
    }
}