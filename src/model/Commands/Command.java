package model.Commands;

import model.Album.Album;

public abstract class Command {

    public abstract void execute();
    public abstract void undo();
    public abstract void redo();
}
