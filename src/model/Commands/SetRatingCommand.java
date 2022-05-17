package model.Commands;

import model.Album.Album;
import model.SoundClip;
import view.MusicOrganizerWindow;

import java.util.HashMap;
import java.util.List;

public class SetRatingCommand extends Command{

    private final List<SoundClip> soundClips;  // clips to set rating on
    private final HashMap<SoundClip, Integer> previousSoundClipRatings;  // keep track of what the ratings were for undo
    private final int rating;  //

    public SetRatingCommand(Album album, MusicOrganizerWindow view, List<SoundClip> soundClips, int rating) {
        super(album, view);
        this.soundClips = soundClips;
        this.rating = rating;
        this.previousSoundClipRatings = new HashMap<>();
    }

    @Override
    public void execute() {
        for (SoundClip clip: this.soundClips){
            this.previousSoundClipRatings.put(clip, clip.getRating());
            clip.setRating(this.rating);
        }
        this.view.onClipsUpdated();
    }

    @Override
    public void undo() {
        for (SoundClip clip: this.soundClips){
            clip.setRating(this.previousSoundClipRatings.get(clip));
        }
        this.view.onClipsUpdated();
    }

    @Override
    public void redo() {
        execute();
    }
}
