package model.Commands;

import model.Album.Album;
import model.Album.RootAlbum;
import model.Album.SubAlbum;
import view.MusicOrganizerWindow;

public class AddAlbumCommand extends Command {
    // command class for undo-able and redo-able add album action
    private Album parentAlbum;  // album to add a new sub-album to
    private String newAlbumName;  // name for the new album
    private final MusicOrganizerWindow view;  // reference to MusicOrganizerWindow object
    private Album newAlbum;  // the actual album

    public AddAlbumCommand(Album parent, String newAlbumName, final MusicOrganizerWindow view){
        // constructor
        this.parentAlbum = parent;
        if (this.parentAlbum == null){
            this.parentAlbum = RootAlbum.get();  // assigns root album as parent if no parent is assigned
        }
        this.newAlbumName = newAlbumName;
        if (this.newAlbumName == null){
            this.newAlbumName = "New Album";
        }
        this.view = view;
        assert this.view != null;
    }

    @Override
    public void execute() {
        this.newAlbum = new SubAlbum(this.newAlbumName, this.parentAlbum);  // create the new album
        // parent album gets this new album assigned as sub album in constructor
        this.view.onAlbumAdded(this.newAlbum);  // call method to update album tree
    }

    @Override
    public void undo() {
        // removes this new album from the parent album
        this.parentAlbum.remove(this.newAlbum);
        this.view.onAlbumRemoved();  // call method to update album tree
    }

    @Override
    public void redo() {
        execute();
    }
}
