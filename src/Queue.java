import java.util.LinkedList;
public class Queue<E> {
    private LinkedList<E> list = new LinkedList<>();

    public void enqueue(E value) {
        list.addLast(value);
    }

    public E dequeue() {
        return list.pop();
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }
}
