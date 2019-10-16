
package com.example.musicplayer.objects;

import java.util.LinkedList;

import com.example.musicplayer.R;

public class Data 
{
	public Data()
	{
		songs = new LinkedList<Song>();
		albums = new LinkedList<Album>();
		artists = new LinkedList<Artist>();
	}
	
	public LinkedList<Song> songs;
	public LinkedList<Album> albums;
	public LinkedList<Artist> artists;
	
	
	public void stub()
	{
		
		Artist eminem = new Artist("Eminem", R.drawable.eminem);
    	
        Album a = new Album("The Eminem Show", eminem);
        Album b = new Album("Encore", eminem);
        
        this.artists.add(eminem);
        
        this.albums.add(a);
        this.albums.add(b);
        
      
        this.songs.add(new Song("Superman", a));
        this.songs.add(new Song("Till I Collapse", a));
        this.songs.add(new Song("My Dads Gone Crazy", a));
        
        this.songs.add(new Song("Mockingbird", b));
	}
}
