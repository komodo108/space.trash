package python.middleware;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ActionQueue {
    private Queue<ActionString> queue;

    public ActionQueue() {
        this.queue = new ConcurrentLinkedQueue<>();
    }

    public void add(ActionString as) {
        queue.add(as);
    }

    public ActionString peek() {
        return queue.peek();
    }

    public ActionString remove() {
        return queue.remove();
    }
}
