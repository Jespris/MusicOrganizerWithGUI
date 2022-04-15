package controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.Album.Album;
import model.Album.RootAlbum;
import model.Album.SubAlbum;
import model.SoundClip;
import model.SoundClipBlockingQueue;
import model.SoundClipLoader;
import model.SoundClipPlayer;
import view.MusicOrganizerWindow;

import javax.swing.*;

public class MusicOrganizerController {

	private MusicOrganizerWindow view;
	private SoundClipBlockingQueue queue;
	
	public MusicOrganizerController() {
		
		// Create the blocking queue
		queue = new SoundClipBlockingQueue();
				
		// Create a separate thread for the sound clip player and start it
		
		(new Thread(new SoundClipPlayer(queue))).start();
	}
	
	/**
	 * Load the sound clips found in all subfolders of a path on disk. If path is not
	 * an actual folder on disk, has no effect.
	 */
	public Set<SoundClip> loadSoundClips(String path) {
		// TODO: Add the loaded sound clips to the root album

		return SoundClipLoader.loadSoundClips(path);
	}
	
	public void registerView(MusicOrganizerWindow view) {
		this.view = view;
	}
	
	/**
	 * Returns the root album
	 */
	public Album getRootAlbum(){
		return RootAlbum.get();
	}
	
	/**
	 * Adds an album to the Music Organizer
	 */
	public void addNewAlbum(){ //TODO Update parameters if needed -
		//  - e.g. you might want to give the currently selected album as parameter
		// TODO: Add your code here
		// the currently selected album as parent,
		// if none selected => parent album is null => root album is parent (defined in SubAlbum constructor)
		// albumName comes from ? => Set album name to default "New Album"?
		Album parent = view.getSelectedAlbum();  // get parent album
		String albumName = JOptionPane.showInputDialog("Album name: ");
		Album newAlbum = new SubAlbum(albumName, parent);
		view.onAlbumAdded(newAlbum);
	}
	
	/**
	 * Removes an album from the Music Organizer
	 */
	public void deleteAlbum(){ //TODO Update parameters if needed
		// TODO: Add your code here
		// delete currently selected album (and all it's subAlbums?)
		Album album = view.getSelectedAlbum();
		if (album == RootAlbum.get()){

		} else {
			for (SubAlbum subAlbum : album.getSubAlbums()) {
				album.remove(subAlbum);
			}
			album.getParentAlbum().remove(album);
		}
	}
	
	/**
	 * Adds sound clips to an album
	 */
	public void addSoundClips(){ //TODO Update parameters if needed
		// TODO: Add your code here
		// add sound clip to currently selected album (which automatically adds the clip to all parents)
		// if no album is selected => add to root?
	}
	
	/**
	 * Removes sound clips from an album
	 */
	public void removeSoundClips(){ //TODO Update parameters if needed
		// TODO: Add your code here
		// remove from currently selected album currently selected soundclip(s)
	}
	
	/**
	 * Puts the selected sound clips on the queue and lets
	 * the sound clip player thread play them. Essentially, when
	 * this method is called, the selected sound clips in the 
	 * SoundClipTable are played.
	 */
	public void playSoundClips(){
		List<SoundClip> l = view.getSelectedSoundClips();
		queue.enqueue(l);
		for (SoundClip soundClip : l) {
			view.displayMessage("Playing " + soundClip);
		}
	}
}
