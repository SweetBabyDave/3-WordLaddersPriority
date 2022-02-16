import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class LadderGamePriority extends LadderGame {
    private ArrayList<ArrayList<String>> canonDict = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> dictionary = new ArrayList<ArrayList<String>>();

    public LadderGamePriority(String dictionaryFile) {
        readDictionary(dictionaryFile);
    }
    public void play(String start, String end) {
        deepCloneDict();
        int enqueueCounter = 0;
//        if (start.length() == end.length() && dictionary.get(start.length() - 1).contains(start) && dictionary.get(end.length() - 1).contains(end)) return;

        int priority = findPriority(start, end);
        boolean solutionFound = false;
        AVLTree<WordInfoPriority> priorityQueue = new AVLTree<>();
        priorityQueue.insert(new WordInfoPriority(start, 0, priority));
        enqueueCounter++;

        while (!solutionFound) {
            var highestPriority = priorityQueue.deleteMin();
            ArrayList<String> oneAways = this.oneAway(highestPriority.getWord(), false);
            for (String word : oneAways) {
                if (word.equals(end)) {
                    solutionFound = true;
                    System.out.println("Seeking A* solution from " + start + " -> " + end + "\n [" + highestPriority.getHistory() + " " +  end + "] " + enqueueCounter + " total enqueues");
                    return;
                }
                if (word == null) {
                    System.out.println("big gay");
                }
                priorityQueue.insert(new WordInfoPriority(word, 0, findPriority(word, end), highestPriority.getHistory() + " " + word));
            }
        }
    }

//        queue.enqueue(ladder);
//            boolean solutionFound = false;
//            Queue<WordInfoPriority> partialSolution = new Queue<>();
//            partialSolution.enqueue(ladder, 1, 2);
//            enqueueCounter++;
//
//            while (!partialSolution.isEmpty() && !solutionFound) {
//                WordInfoPriority curLadder = partialSolution.dequeue();
//                ArrayList<String> oneAways = this.oneAway(curLadder.getWord(), false);
//                for(String word : oneAways) {
//                    ladder = new WordInfoPriority(word, curLadder.getMoves() + 1, curLadder.getHistory() + " " + word);
//                    if (word.equals(end)) {
//                        solutionFound = true;
//                        System.out.println("Seeking A* solution from " + start + " -> " + end + "\n [" + ladder.getHistory() + "] " + enqueueCounter + " total enqueues");
//                        break;
//                    } else {
//                        partialSolution.enqueue(ladder);
//                        enqueueCounter++;
//                    }
//                }
//            }
//            if (partialSolution.isEmpty()) {
//                System.out.println(start + " -> " + end + " : No ladder was found");
//            }

    public int findPriority(String word, String goal) {
        int wordSize = word.length();
        int counter = 0;
        for (int m = 0; m < wordSize; m++) {
            if (word.charAt(m) != goal.charAt(m)) {
                counter++;
            }
        }
        return counter;
    }

    public ArrayList<String> oneAway(String word, boolean withRemoval) {

        ArrayList<String> words = new ArrayList<>();
        int wordSize = word.length();
        for (int n = 0; n < dictionary.get(wordSize - 1).size(); n++) {
            int counter = 0;
            for (int m = 0; m < wordSize; m++) {
                if (dictionary.get(wordSize - 1).get(n).charAt(m) != word.charAt(m)) {
                    counter++;
                }
            }
            if (counter == 1) {
                words.add(dictionary.get(wordSize - 1).get(n));
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
