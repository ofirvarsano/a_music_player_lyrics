package com.example.musicplayer;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;

import java.util.LinkedList;

import com.example.musicplayer.objects.Album;
import com.example.musicplayer.objects.Artist;
import com.example.musicplayer.objects.Data;
import com.example.musicplayer.objects.Song;
import com.example.musicplayer.objects.Songs;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityPlayer extends Activity
{
	class Layout
	{
		public Layout()
		{
			layoutMain = (LinearLayout)findViewById(R.id.layoutMain);
			layoutBar = (LinearLayout)findViewById(R.id.layoutBar);
			layoutAll = (LinearLayout)findViewById(R.id.layoutAll);
			layoutMedia = (LinearLayout)findViewById(R.id.layoutMedia);
			layoutLyrics = (LinearLayout)findViewById(R.id.layoutLyrics);
			
			btnArtist = (Button)findViewById(R.id.btnArtist);
			btnSongs = (Button)findViewById(R.id.btnSongs);
			
			view2 = (ViewMusic)findViewById(R.id.viewMusic1);
		}
		
		LinearLayout layoutMain, layoutMedia, layoutBar, layoutAll, layoutLyrics;
		Button btnArtist , btnSongs;
		ViewMusic view2;
		
	}
	
	class Events
	{
		public Events()
		{		
			l.btnArtist.setOnClickListener(new OnClickListener() 
			{
				
				@Override
				public void onClick(View v)
				{
					fillArtists();
				}
			}); 
			
			l.btnSongs.setOnClickListener(new OnClickListener() 
			{
				
				@Override
				public void onClick(View v)
				{
					addSongs();
				}
			});
		}
	}
	
	Layout l; 
	Events e;
	LinkedList<Songs> list;
	LinkedList<Artist> demoArtists; 

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{ 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		
		l = new Layout();
		e = new Events();	

		//if(checkSelfPermission)
		list = getSongs(); 
		
		addSongs();
		
		l.view2.setParent(ActivityPlayer.this);	
	}  

	    void addSongs()
	    {
	    	l.layoutMain.removeAllViews();
	    	
	    	for (final Songs song : list)
	    	{
	    		TextView view = new TextView(this);
	    		
	    		if(song.artist.equals("<unknown>"))
	    			
	    			view.setText(song.title);
		    	
	    		else
	    			
	    			view.setText(song.title + " - " + song.artist);

		    	view.setTextColor(Color.rgb(224, 224, 224));
		    	
		    	view.setTextSize(23);
		    	
		    	view.setOnClickListener(new OnClickListener()
		    	{
					@Override 
					public void onClick(View v) 
					{
						l.layoutMain.setVisibility(View.GONE);
						l.layoutBar.setVisibility(View.GONE);
						l.layoutMedia.setVisibility(View.VISIBLE);
						int index = list.indexOf(song);
						l.view2.setSongInfo(song, index);
					} 
				});
		    	
		    	l.layoutMain.addView(view);
	    	}
	    }
	    
	    void fillArtists()
	    {
	    	l.layoutMain.removeAllViews();
			for(final Artist artist : demoArtists)
			{
				TextView view = new TextView(ActivityPlayer.this);
				view.setText(artist.getName());
				view.setTextColor(Color.rgb(224, 224, 224));
		    	
		    	view.setTextSize(23);
				
				view.setOnClickListener(new OnClickListener()
				{
					
					@Override
					public void onClick(View v) 
					{
						fillSongsByArtist(artist);
					}
				});
				l.layoutMain.addView(view);
			}
	    }

	    void fillSongsByArtist(Artist artist)
	    {
	    	l.layoutMain.removeAllViews();
	    	
	    	for(final Songs song : list)
	    	{
	    		if(artist.getName().equals(song.artist))
	    		{
	    			TextView view = new TextView(this);
			    	 
	    			if(song.artist.equals("<unknown>"))
		    			
		    			view.setText(song.title);
			    	
		    		else
		    			
		    			view.setText(song.title + " - " + song.artist);
			    	
			    	view.setTextColor(Color.rgb(224, 224, 224));
			    	
			    	view.setTextSize(23);
			    	
			    	view.setOnClickListener(new OnClickListener()
			    	{
						@Override 
						public void onClick(View v) 
						{
							l.layoutMain.setVisibility(View.GONE);
							l.layoutBar.setVisibility(View.GONE);
							l.layoutMedia.setVisibility(View.VISIBLE);
							int index = list.indexOf(song);
							l.view2.setSongInfo(song, index);
						} 
					});
			    	
			    	l.layoutMain.addView(view);
	    		}
	    	}
	    }
	     
	    LinkedList<Songs> getSongs()
	    {
	    	LinkedList<Songs> list = new LinkedList<Songs>();
	    	String t = (MediaStore.Audio.Media.EXTERNAL_CONTENT_URI).getPath();
	    	String s = (MediaStore.Audio.Media.INTERNAL_CONTENT_URI).getPath();

//			String state = Environment.getExternalStorageState();
//			if (Environment.MEDIA_MOUNTED.equals(state)) {
//				Toast.makeText(this,
//						"yes", Toast.LENGTH_LONG).show();
//			}
//			Toast.makeText(this,
//					"no", Toast.LENGTH_LONG).show();

	    	demoArtists = new LinkedList<Artist>();
	    	Uri uri = null;
	    	//uri = Uri.parse("/mnt/sdcard/Download");
			uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
			//uri = Uri.parse("/sdcard/Music");
			//uri = (Uri) Uri.parse(Environment.getExternalStorageDirectory().getPath());
			//uri = (Uri) Uri.parse(String.valueOf(MediaStore.Audio.Media.getContentUriForPath((String.valueOf("/mnt/sdcard/Ringtones/")))));

			//uri = Uri.parse(android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI.getPath());
			//uri = Uri.parse(android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.getPath());
			//String s = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.getPath();
			//uri = Uri.parse(Environment.getExternalStorageDirectory().getPath()+"/mnt/sdcard/Ringtones");
			//Toast.makeText(this,
				//	MediaStore.Audio.Media.EXTERNAL_CONTENT_URI.getPath(), Toast.LENGTH_LONG).show();
			//mnt/sdcard/Eminem - Lose Yourself.mp3

	    	Cursor cur = getContentResolver().query(uri,  new String[] {}, null, null, null);

		    if (cur == null)
			{
				//Toast.makeText(this,
				//		Environment.getExternalStorageState(), Toast.LENGTH_LONG).show();
				return list;
			}

			while (cur.moveToNext())
			{
				Songs obj = new Songs();
				
				 obj.data = cur.getString(cur.getColumnIndex(android.provider.MediaStore.Audio.Media.DATA));
				 obj.title = cur.getString(cur.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE));
				 obj.album = cur.getString(cur.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM));
				 obj.artist = cur.getString(cur.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST));

				 boolean isNew = true;
				 for (Artist current : demoArtists)
				 {
					 if (current.getName().equals(obj.artist))
					 {
						 isNew = false; break;
					 }
				 }
				 
				 if (isNew)
				 {
					 demoArtists.add(new Artist(obj.artist, 0));
				 }
				 
				 list.add(obj);
			} 
			if(list.isEmpty());
				//Toast.makeText(this,
				//		Environment.getExternalStorageState(), Toast.LENGTH_LONG).show();
			return list;
	    }
}
