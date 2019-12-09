package gui.implementation;

import python.middleware.*;

import static main.Constants.KEY;

public class IConsole implements PythonImplementation {
    private ActionQueue queue;

    public IConsole() {
        this.queue = new ActionQueue();
    }

    @Pythond
    public void error(String message) {
        message = "ERROR > " + message + "\n";
        for(String line : message.split("\n")) {
            queue.add(new ActionString(line, Actions.ERROR));
        }
    }

    @Pythond
    public void print(String message) {
        message = "> " + message + "\n";
        for(String line : message.split("\n")) {
            queue.add(new ActionString(line, Actions.PRINT));
        }
    }

    @Pythond
    public void help() {
        String message = "Please see the help page on [TODO]" + "\n";
        for(String line : message.split("\n")) {
            queue.add(new ActionString(line, Actions.HELP));
        }
    }

    @Override
    public ActionQueue getQueue(String key) {
        if(key.equals(KEY)) {
            return queue;
        } else return null;
    }
}