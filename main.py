import language_tool_python

tool = language_tool_python.LanguageTool('fil-PH')

# need define grammar rules here!

def grammarChecker(text):
    matches = tool.check(text)

    if not matches:
        print('No Errors found! ')
        return

    print(f"\nFound {len(matches)} potential issue(s):\n")
    
    for match in matches:
        print(f"📝 Error: {match.ruleIssueType.upper()} ({match.ruleId})")
        print(f"🔍 Message: {match.message}")
        print(f"📍 At position: {match.offset} to {match.offset + match.errorLength}")
        print(f"💡 Suggestions: {match.replacements}\n")
        print("-" * 40)


if __name__ == "__main__":
    print("📚 Filipino Grammar Checker (powered by LanguageTool)\n")
    
    while True:
        user_input = input("Enter a sentence (or type 'exit' to quit): ")
        if user_input.lower() == "exit":
            break
        check_text(user_input)


