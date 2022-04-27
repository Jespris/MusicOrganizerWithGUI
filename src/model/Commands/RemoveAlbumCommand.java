package model.Commands;

import model.Album.Album;
import view.MusicOrganizerWindow;

public class RemoveAlbumCommand extends Command {
    Album parentAlbum;
    Album removedAlbum;
    MusicOrganizerWindow view;

    public RemoveAlbumCommand(Album parentAlbum, Album removedAlbum, MusicOrganizerWindow view){
        this.parentAlbum = parentAlbum;
        this.removedAlbum = removedAlbum;
        this.view = view;

        assert this.parentAlbum != null && !this.removedAlbum.isRootAlbum() && this.view != null;
    }

    @Override
    public void execute() {
        this.parentAlbum.remove(this.removedAlbum);  // remove self from parent list of sub album
        // TODO: store all removed subAlbums of removed Album?
        this.view.onAlbumRemoved();  // update view
    }

    @Override
    public void undo() {
        this.parentAlbum.add(this.removedAlbum);
        // TODO: add back all stored subAlbums of removed Album?
        this.view.onAlbumAdded(this.removedAlbum);
    }

    @Override
    public void redo() {
        execute();
    }
}
