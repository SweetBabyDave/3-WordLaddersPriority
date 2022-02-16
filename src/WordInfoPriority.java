public class WordInfoPriority<E extends Comparable<? super E>>{
    private String word;
    private int moves;
    private String history;
    private int estimatedWork;
    private E priority;
    public WordInfoPriority(String word, int moves) {
        this.word = word;
        this.moves = moves;
        this.history = word;
    }
    public WordInfoPriority(String word, int moves, int estimatedWork) {
        this.word = word;
        this.moves = moves;
        this.estimatedWork = estimatedWork;
    }
    public WordInfoPriority(String word, int moves, int estimatedWork, String history) {
        this.word = word;
        this.moves = moves;
        this.estimatedWork = estimatedWork;
        this.history = history;
    }

    public WordInfoPriority(String word, int moves, String history) {
        this.word = word;
        this.moves = moves;
        this.history = history;
    }
    public int getEstimatedWork() {return this.estimatedWork;}
    public String getWord() {
        return this.word;
    }
    public int getMoves() {
        return this.moves;
    }
    public String getHistory() {
        return this.history;
    }
    @Override
    public String toString() {
        return String.format("Word %s Moves %d : History[%s]",
                word, moves, history);
    }
}

