package com.example.musicplayer.objects;

public class Artist
{
	public Artist(String name, int icon)
	{
		this.name = name;
		this.icon = icon;
	}
	
	String name;
	int icon;
	
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getIcon()
	{
		return icon;
	}
	public void setIcon(int icon)
	{
		this.icon = icon;
	}
}
