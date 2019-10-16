package com.example.musicplayer.objects;

public class Song 
{
    public Song(String name, Album album) 
    {
		this.name = name;
		this.album = album;
	}
    
	String name;
    Album album;
    
	public String getName() 
	{
		return name;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public Album getAlbum() 
	{
		return album;
	}
	public void setAlbum(Album album) 
	{
		this.album = album;
	}
}