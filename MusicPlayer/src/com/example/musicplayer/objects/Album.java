package com.example.musicplayer.objects;

public class Album 
{
	public Album(String title, Artist artist) 
	{
		this.title = title;
		this.artist = artist;
	}

	public Artist getArtist()
	{
		return artist;
	}

	public void setArtist(Artist artist)
	{
		this.artist = artist;
	}

	String title;
	Artist artist;

	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}
}
