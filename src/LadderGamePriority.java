import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class LadderGamePriority extends LadderGame {
    private ArrayList<ArrayList<String>> canonDict = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> dictionary = new ArrayList<ArrayList<String>>();

    public LadderGamePriority(String dictionaryFile) {
        super();
        readDictionary(dictionaryFile);
    }
    public void play(String start, String end) {
        deepCloneDict();
        int counter = 0;
        if (start.length() == end.length() && dictionary.get(start.length() - 1).contains(start) && dictionary.get(end.length() - 1).contains(end)) {

            WordInfo ladder = new WordInfo(start, counter);
            Queue<WordInfo> partialSolution = new Queue<>();
            partialSolution.enqueue(ladder);

            while ( !partialSolution.isEmpty() && !ladder.getWord().equals(end)) {
                WordInfo curLadder = partialSolution.dequeue();
                ArrayList<String> oneAways = oneAway(curLadder.getWord(), true);
                for(String word : oneAways) {
                    if(word.equals(end)) {
                        ladder = new WordInfo(word, curLadder.getMoves() + 1, curLadder.getHistory() + " " + word);
                        System.out.println(start + " -> " + end + " : " + ladder.getMoves() + " Moves [" + ladder.getHistory() + "] " + counter + " total enqueues" );
                        break;
                    } else {
                        WordInfo newLadder = new WordInfo(word, curLadder.getMoves() + 1, curLadder.getHistory() + " " + word);
                        partialSolution.enqueue(newLadder);
                        ladder = newLadder;
                        counter++;
                    }
                }
            }
            if(partialSolution.isEmpty()) {
                System.out.println(start + " -> " + end + " : No ladder was found");
            }
        }
    }

    public ArrayList<String> oneAway(String word, boolean withRemoval) {
        if (withRemoval) {
            dictionary.get(word.length() - 1).remove(word);
        }
        ArrayList<String> words = new ArrayList<>();
        int wordSize = word.length();
        for (int n = 0; n < dictionary.get(wordSize - 1).size(); n++) {
            int counter = 0;
            for (int m = 0; m < wordSize; m++) {
                if (dictionary.get(wordSize - 1).get(n).charAt(m) == word.charAt(m)) {
                } else {
                    counter++;
                }
            }
            if (counter == 1) {
                words.add(dictionary.get(wordSize - 1).get(n));
            }
        }
        if (withRemoval) {
            for (int n = 0; n < words.size() - 1; n++) {
                dictionary.get(word.length() - 1).remove(words.get(n));
            }
        }
        return words;
    }

    public void listWords(int length, int howMany) {
        for (int n = 0; n < howMany; n++) {
            System.out.println(dictionary.get(length - 1).get(n));
        }
    }

    private void deepCloneDict() {
        dictionary = new ArrayList<>();
        for(ArrayList entry : canonDict ) dictionary.add((ArrayList) entry.clone());
    }

    /*
        Reads a list of words from a file, putting all words of the same length
into the same array.
     */
    private void readDictionary(String dictionaryFile) {
        File file = new File(dictionaryFile);
        ArrayList<String> allWords = new ArrayList<>();
        canonDict = new ArrayList<>();
        //
        // Track the longest word, because that tells us how big to make the array.
        int longestWord = 0;
        try (Scanner input = new Scanner(file)) {
            //
            // Start by reading all the words into memory.
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                allWords.add(word);
                longestWord = Math.max(longestWord, word.length());
            }
            for (int n = 1; n <= longestWord; n++) {
                ArrayList<String> groups = new ArrayList<>();
                for (int m = 0; m < allWords.size(); m++) {
                    if (allWords.get(m).length() == n) {
                        groups.add(allWords.get(m).toLowerCase());
                    }
                }
                canonDict.add(groups);
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the dictionary: "
                    + ex);
        }
        deepCloneDict();
    }
}
