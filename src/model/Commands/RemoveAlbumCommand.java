package model.Commands;

import model.Album.Album;
import view.MusicOrganizerWindow;

public class RemoveAlbumCommand extends Command {
    // command for removing albums from album tree
    private final Album parentAlbum;
    private final Album removedAlbum;
    private final MusicOrganizerWindow view;

    public RemoveAlbumCommand(final Album album, final MusicOrganizerWindow view){
        // constructor
        this.parentAlbum = album.getParentAlbum();
        this.removedAlbum = album;
        this.view = view;

        assert this.parentAlbum != null && !this.removedAlbum.isRootAlbum() && this.view != null;
        // design by contract stuff
    }

    @Override
    public void execute() {
        this.parentAlbum.remove(this.removedAlbum);  // remove self from parent list of sub album
        // TODO: store all removed subAlbums of removed Album?
        this.view.onAlbumRemoved();  // update view
    }

    @Override
    public void undo() {
        this.parentAlbum.add(this.removedAlbum);  // add the removed album back to parent
        // TODO: add back all stored subAlbums of removed Album?
        //  NO! removed album is never actually deleted so all sub-albums are still there!
        this.view.onAlbumAdded(this.removedAlbum);  // update view
    }

    @Override
    public void redo() {
        execute();
    }
}
