package model.Commands;

import model.Album.Album;
import model.Album.RootAlbum;
import model.Album.SubAlbum;
import view.MusicOrganizerWindow;

public class AddAlbumCommand extends Command {
    private Album parentAlbum;
    private String newAlbumName;
    private final MusicOrganizerWindow view;
    private Album newAlbum;

    public AddAlbumCommand(Album parent, String newAlbumName, final MusicOrganizerWindow view){
        this.parentAlbum = parent;
        if (this.parentAlbum == null){
            this.parentAlbum = RootAlbum.get();
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
        this.newAlbum = new SubAlbum(this.newAlbumName, this.parentAlbum);
        this.view.onAlbumAdded(this.newAlbum);
    }

    @Override
    public void undo() {
        this.parentAlbum.remove(this.newAlbum);
        this.view.onAlbumRemoved();
    }

    @Override
    public void redo() {
        execute();
    }
}
