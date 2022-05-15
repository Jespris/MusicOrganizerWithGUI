package model.Album;

import model.SoundClip;

import java.util.ArrayList;
import java.util.List;

public abstract class SearchBasedAlbum extends Album{

    public SearchBasedAlbum(String albumName) {
        super(albumName);
    }

    public abstract void add(ArrayList<SoundClip> clips);
    public abstract void remove(ArrayList<SoundClip> clips);
    public abstract List<SoundClip> getSoundClips();

    @Override
    public boolean add(Album album){
        return false;
    }

    @Override
    public boolean add(final SoundClip clip){
        return false;
    }

    @Override
    public boolean remove(Album album){
        return false;
    }

    @Override
    public boolean remove(final SoundClip clip){
        return false;
    }

    @Override
    public boolean isRootAlbum() {
        return false;
    }

    @Override
    public Album getParentAlbum() {
        return null;
    }
}
