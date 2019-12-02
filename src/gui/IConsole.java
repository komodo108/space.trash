package gui;

import python.middleware.ActionQueue;
import python.middleware.ActionString;
import python.middleware.Actions;
import python.middleware.PythonImplementation;

class IConsole implements PythonImplementation {
    public void error(String message) {
        message = "ERROR > " + message + "\n";
        for(String line : message.split("\n")) {
            queue.add(new ActionString(line, Actions.ERROR));
        }
    }

    public void print(String message) {
        message = "> " + message + "\n";
        for(String line : message.split("\n")) {
            queue.add(new ActionString(line, Actions.PRINT));
        }
    }

    public void help() {
        String message = "Help is available from http://help.net/" + "\n";
        for(String line : message.split("\n")) {
            queue.add(new ActionString(line, Actions.HELP));
        }
    }

    ActionQueue getQueue() {
        return queue;
    }
}