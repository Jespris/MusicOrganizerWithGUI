package model.Commands;

import model.Album.Album;
import view.MusicOrganizerWindow;

public class SetRatingCommand extends Command{
    public SetRatingCommand(Album album, MusicOrganizerWindow view) {
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
