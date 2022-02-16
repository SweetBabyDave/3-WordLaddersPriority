import java.util.ArrayList;
abstract class LadderGame {

    abstract void play(String start, String end);
    abstract ArrayList<String> oneAway(String word, boolean withRemoval);
}