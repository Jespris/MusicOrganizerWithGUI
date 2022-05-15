package model.Commands;

import model.Album.Album;
import view.MusicOrganizerWindow;

public class ToggleSoundClipFlagCommand extends Command{
    public ToggleSoundClipFlagCommand(Album album, MusicOrganizerWindow view) {
        super(album, view);
    }

    @Override
    public void execute() {

    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }
}
