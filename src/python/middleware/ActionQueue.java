package python.middleware;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ActionQueue {
    private Queue<ActionString> queue;

    /**
     * A multi-threaded queue of actions to allow communication across threads
     * QoS - Best effort delivery, will drop message if too busy
     */
    public ActionQueue() {
        this.queue = new ConcurrentLinkedQueue<>();
    }

    /**
     * Add an action to the queue
     * @param as an action
     */
    public void add(ActionString as) {
        if(queue.size() >= 300) {
            queue.remove();
        } queue.add(as);
    }

    /**
     * Peek the first action in the queue
     * @return the first action in the queue
     */
    public ActionString peek() {
        return queue.peek();
    }

    /**
     * Remove the first action in the queue
     * @return the first action in the queue
     */
    public ActionString remove() {
        return queue.remove();
    }
}
