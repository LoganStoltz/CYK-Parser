This program uses the Cocke–Younger–Kasami (CYK) algorithm to process a text input containing Context-Free Grammars (CFGs) and evaluates strings to determine whether they belong to the languages defined by the corresponding CFGs.
It can parse grammars, constructing parse trees, and apply language membership tests based on CYK parsing algorithms.
The program has a time complexity of O(n^3 · |G|), where n is the length of the input string and |G| is the size ofthe grammar.

This will have an input text file that can have multiple Context-free Grammars each will multiple string to test and this will determine if any of them are accepted or not. Time Complexity should be O(n^3)

Input: a text file called "input.txt" located in the project's base directory (use a relative path to open)
- The first line is a single integer with the number of grammars with lists of queries to follow
- The second line is blank
- The next n lines are productions of the first grammar (you can assume each production is on its own line, so no A -> aA | a)
- The next line is a blank line
- The next line contains a single inter m, the number of query strings for the first grammar
- The next m lines contain query strings for the first grammar, one per line
- The next line is blank
- If there is a second grammar, the next lines will contain the productions
- (Continue this progression until end-of-file)
- Example at the bottom

This program assumes that:
- All nonterminals will be a single uppercase letter
- All terminals will be a single lowercase letter
- Each production is on its own line. Meaning you should not use | to seperate productions from the same Nonterminal, put eash production on a sepearte line.
- Arrows will be made up of the exact string "-->"
- The first grammar production left hand side is the start nonterminal (even if it is not 'S')
- The text file input.txt follows the input inscructions.

Example input.txt:
3

S --> aSb
S --> A
A --> bAa
A --> c

3
abcab
aabcabb
aabac

S --> aB
S --> aaC
B --> aB
B --> bc
C --> abC
C --> a

1
aaabb

S --> AB
S --> cS
A --> aA
A --> λ
B --> bBAd
B --> λ

2
caabbdad
aabbbabdad
