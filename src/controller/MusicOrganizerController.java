package controller;

import java.util.List;
import java.util.Set;

import model.*;
import model.Album.Album;
import model.Album.RootAlbum;
import model.Commands.AddAlbumCommand;
import model.Commands.AddSoundClipCommand;
import model.Commands.RemoveAlbumCommand;
import view.MusicOrganizerWindow;

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

		Set<SoundClip> clips = SoundClipLoader.loadSoundClips(path);

		for (SoundClip clip: clips){
			System.out.println("Adding clip: " + clip.toString());
			RootAlbum.get().add(clip);
		}

		// for testing purposes, print nr of sound clips loaded to root
		System.out.println("Root album has " + RootAlbum.get().getSoundClips().size() + " sound clips");
		return clips;
	}
	
	public void registerView(MusicOrganizerWindow view) {
		this.view = view;
	}
	
	/**
	 * Returns the root album
	 */
	public Album getRootAlbum(){
		// returns the RootAlbum singleton
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
		// albumName comes from the input prompt

		String albumName = view.promptForAlbumName();
		AddAlbumCommand command = new AddAlbumCommand(view.getSelectedAlbum(), albumName, this.view);
		command.execute();
		CommandController.get().addNewCommand(command);
	}
	
	/**
	 * Removes an album from the Music Organizer
	 */
	public void deleteAlbum(){ //TODO Update parameters if needed
		// TODO: Add your code here
		// delete currently selected album (and all it's subAlbums?)
		Album removedAlbum = view.getSelectedAlbum();
		if (!removedAlbum.isRootAlbum()) {
			RemoveAlbumCommand command = new RemoveAlbumCommand(removedAlbum.getParentAlbum(), removedAlbum, this.view);
			command.execute();
			CommandController.get().addNewCommand(command);
		} else {
			this.view.displayMessage("Cannot remove root album!");
		}
	}
	
	/**
	 * Adds sound clips to an album
	 */
	public void addSoundClips(){ //TODO Update parameters if needed
		// TODO: Add your code here
		// add sound clip to currently selected album (which automatically adds the clip to all parents)
		// if no album is selected => add to root
		Album selectedAlbum = view.getSelectedAlbum();
		if (selectedAlbum == null){
			selectedAlbum = RootAlbum.get();
		}
		AddSoundClipCommand command = new AddSoundClipCommand(selectedAlbum, this.view.getSelectedSoundClips(), this.view);
		command.execute();
		CommandController.get().addNewCommand(command);
		view.onClipsUpdated();  // update view
	}
	
	/**
	 * Removes sound clips from an album
	 */
	public void removeSoundClips(){ //TODO Update parameters if needed
		// TODO: Add your code here
		// remove from currently selected album currently selected soundclip(s)
		Album album = view.getSelectedAlbum();
		if (album != null) {  // check that selection isn't null
			for (SoundClip clip : view.getSelectedSoundClips()) {
				album.remove(clip);  // remove clip
			}
			view.onClipsUpdated();  // update view
		} else {
			view.displayMessage("Cannot remove sound clip(s) without an album selected!");
		}
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
