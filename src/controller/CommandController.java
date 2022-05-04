package controller;

import model.Commands.Command;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.Stack;

public class CommandController {
    private Stack<Command> commandStack;
    private Stack<Command> undoStack;
    private static CommandController instance;

    private CommandController(){
        this.commandStack = new Stack<>();
        this.undoStack = new Stack<>();
    }

    public static CommandController get(){
        if (instance == null){
            instance = new CommandController();
        }
        return instance;
    }

    public void addNewCommand(Command command){
        // assume command is executed?
        if (this.commandStack.size() < 10){
            this.commandStack.add(command);
        } else {
            this.commandStack.add(command);
            this.commandStack.remove(0);
        }
        this.undoStack.clear();  // clear undo stack since that now is obsolete
    }

    private void addOldCommand(Command command){
        this.commandStack.add(command);
    }

    public void undoLastCommand(){
        assert this.commandStack.size() != 0;
        Command c = this.commandStack.pop();  // get last command
        assert c != null;
        c.undo();  // undo the command
        this.undoStack.add(c);  // removes last command from commandStack and adds to undoStack
    }

    public void redoLastUndo(){
        assert this.undoStack.size() != 0;
        Command c = this.undoStack.pop();  // gets the last undo command
        assert c != null;
        c.redo();  // redo the command
        addOldCommand(c);  // add command back to command stack
    }

    public boolean hasCommands(){
        return !this.commandStack.isEmpty();
    }

    public boolean hasRedoableCommands(){
        return !this.undoStack.isEmpty();
    }
}
