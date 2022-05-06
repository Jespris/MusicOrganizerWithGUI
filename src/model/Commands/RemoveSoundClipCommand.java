package model.Commands;

import model.Album.Album;
import model.SoundClip;
import view.MusicOrganizerWindow;

import java.util.ArrayList;
import java.util.List;

public class RemoveSoundClipCommand extends Command {
    private final List<SoundClip> clips;
    private final List<SoundClip> removedClips;  // keep track of which clips were actually removed
    // (user can maybe try to remove clips that aren't in the selected album somehow maybe possibly?!?)
    private final Album album;
    private final MusicOrganizerWindow view;

    public RemoveSoundClipCommand(final List<SoundClip> clips, final Album album, final MusicOrganizerWindow view){
        this.clips = clips;
        this.removedClips = new ArrayList<SoundClip>();
        this.album = album;
        this.view = view;

        assert invariant();
    }

    private boolean invariant() {
        // Can you remove clips from root album?
        return this.album != null && this.view != null;
    }

    @Override
    public void execute() {
        for (SoundClip clip: this.clips){
            if (this.album.remove(clip)){
                this.removedClips.add(clip);
            }
        }
        // TODO: keep track of the albums sub-albums clip removal?
        this.view.onClipsUpdated();
    }

    @Override
    public void undo() {
        for (SoundClip clip: this.removedClips){
            this.album.add(clip);
        }
        // Add back to albums sub-album? <- IMPLEMENT?!?
        this.view.onClipsUpdated();
    }

    @Override
    public void redo() {
        execute();
    }
}
