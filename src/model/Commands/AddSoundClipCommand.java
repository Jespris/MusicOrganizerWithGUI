package model.Commands;

import model.Album.Album;
import model.Album.RootAlbum;
import model.SoundClip;
import view.MusicOrganizerWindow;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddSoundClipCommand extends Command {

    private HashMap<Album, SoundClip[]> clipsToAddToAlbums;  // Some albums (parents to the selected album)
    // can already contain a portion of the added clips,
    // we therefore need to keep track of which soundClips are added to which albums
    private Album selectedAlbum;  // the initial album to where the sound clips are added
    private final List<SoundClip> soundClips;  // clips to be added
    private final List<SoundClip> addedClips;  // clips that are actually added,
    // used for the undo method to not accidentally remove clips that were initially already in the album
    private final MusicOrganizerWindow view;  // variable to access the onClipsUpdated method

    public AddSoundClipCommand(final Album selectedAlbum, final List<SoundClip> soundClips, final MusicOrganizerWindow view){
        this.selectedAlbum = selectedAlbum;
        if (this.selectedAlbum == null){
            this.selectedAlbum = RootAlbum.get();
        }
        this.soundClips = soundClips;
        this.addedClips = new ArrayList<SoundClip>();
        this.view = view;

        assert invariant();
    }

    private boolean invariant() {
        return this.selectedAlbum != null && this.view != null;
    }

    @Override
    public void execute() {
        for (SoundClip clip: this.soundClips){
            if (this.selectedAlbum.add(clip)){ // adds the clip to the album
                this.addedClips.add(clip);
            }
        }
        // TODO: keep track of to which parent album soundclips are added
        this.view.onClipsUpdated();
    }

    @Override
    public void undo() {
        for (SoundClip clip: this.addedClips){
            this.selectedAlbum.remove(clip);
        }
        // TODO: remove from parent albums the added clips (from hashmap)
        this.view.onClipsUpdated();
    }

    @Override
    public void redo() {
        execute();
    }
}
