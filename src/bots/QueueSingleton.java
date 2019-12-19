package bots;

import python.middleware.ActionQueue;

public class QueueSingleton {
    private static QueueSingleton ourInstance = new QueueSingleton();
    public static QueueSingleton getInstance() {
        return ourInstance;
    }

    private ActionQueue queue;

    private QueueSingleton() {
        queue = new ActionQueue();
    }

    public ActionQueue getQueue() {
        return queue;
    }
}
