public class Queue<E> {
    private QueueNode head;
    private QueueNode tail;

    public void enqueue(E value) {
        QueueNode node = new QueueNode(value);
        if (head == null) {
            head = tail = node;
        } else {
            this.tail.next = node;
            this.tail = node;
        }
    }

    public E dequeue() {
        QueueNode front = head;
        head = head.next;
        if (head == null) {
            tail = null;
        }
        return front.value;
    }

    public boolean isEmpty() {
        return head == null;
    }

    private class QueueNode {
        public E value;
        public QueueNode next;

        public QueueNode(E value) {
            this.value = value;
        }
    }

}
