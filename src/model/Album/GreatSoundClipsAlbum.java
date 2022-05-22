package model.Album;

import model.SoundClip;

import java.util.ArrayList;
import java.util.List;

public class GreatSoundClipsAlbum extends SearchBasedAlbum{

    private static GreatSoundClipsAlbum instance;

    private GreatSoundClipsAlbum(final String albumName) {
        super(albumName);
    }

    @Override
    public void add(ArrayList<SoundClip> clips) {

    }

    @Override
    public void remove(ArrayList<SoundClip> clips) {

    }

    @Override
    public List<SoundClip> getSoundClips() {
        ArrayList<SoundClip> clips = new ArrayList<>();
        for (SoundClip clip: RootAlbum.get().getSoundClips()){
            if (clip.getRating() >= 4){
                clips.add(clip);
            }
        }
        return clips;
    }

    public static GreatSoundClipsAlbum getInstance(){
        if (instance == null){
            instance = new GreatSoundClipsAlbum("Great Sound Clips");
        }
        return instance;
    }


}
