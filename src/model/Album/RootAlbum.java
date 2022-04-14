package model.Album;

public class RootAlbum extends Album {

    // THIS CLASS IS A SINGLETON, BECAUSE THERE CANNOT BE MULTIPLE ROOT ALBUMS,
    // AND WE DO NOT WANT TO BE ABLE TO ACCIDENTALLY MAKE MULTIPLE ROOT ALBUMS BY MAKING A PUBLIC CONSTRUCTOR

    // instance variable "instance" calls the constructor
    private static final RootAlbum instance = new RootAlbum("All Sound Clips");

    private RootAlbum(final String albumName){
        // constructor, which calls super
        super(albumName);
        assert invariant();
    }

    public static RootAlbum get(){
        // this returns the rootAlbum
        return instance;
    }

    @Override
    public boolean isRootAlbum() {
        // Overrides the isRootAlbum method
        return true;
    }

    @Override
    public Album getParentAlbum() {
        // returns null because a root cannot have a parent
        return null;
    }
}
