package com.example.musicplayer;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.example.musicplayer.objects.Songs;
import com.example.musicplayer.util.Task;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ViewMusic extends LinearLayout
{

	ActivityPlayer parent;
	
	class Layout
	{
		public Layout()
		{
			imgNowPlaying = (ImageView)findViewById(R.id.imgNowPlaying);
			layoutMusic = (LinearLayout)findViewById(R.id.layoutMusic);
			layoutLyrics = (LinearLayout)findViewById(R.id.layoutLyrics);
			seekProgress = (SeekBar)findViewById(R.id.seekProgress);
			imgPlay = (ImageView)findViewById(R.id.imgPlay);
			imgSkipBack = (ImageView)findViewById(R.id.imgSkipBack);
			imgSeekBack = (ImageView)findViewById(R.id.imgSeekBack);
			imgSeekForward = (ImageView)findViewById(R.id.imgSeekForward);
			imgShuffle = (ImageView)findViewById(R.id.imgShuffle);
			imgskipForward = (ImageView)findViewById(R.id.imgskipForward);
			txtTime = (TextView)findViewById(R.id.txtTime);
			txtSongName = (TextView)findViewById(R.id.txtSongName);
			txtArtistName = (TextView)findViewById(R.id.txtArtistName);
			frameAlpha = (FrameLayout)findViewById(R.id.frameAlpha);
			lyricsOnOff = (TextView)findViewById(R.id.lyricsOnOff);
			txtLyricsTitle = (TextView)findViewById(R.id.txtLyricsTitle);
			txtLyricsContent = (TextView)findViewById(R.id.txtLyricsContent);
		}
		
		ImageView imgNowPlaying;
		LinearLayout layoutMusic, layoutLyrics;
		SeekBar seekProgress;
		ImageView imgPlay, imgSkipBack, imgSeekBack, imgSeekForward, imgskipForward, imgShuffle;
		TextView txtSongName, txtArtistName, lyricsOnOff, txtTime, txtLyricsTitle, txtLyricsContent;
		FrameLayout frameAlpha;
	}
	
	class Events
	{
		public Events() 
		{
			l.imgNowPlaying.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v) 
				{
					parent.l.layoutMedia.setVisibility(GONE);
					parent.l.layoutMain.setVisibility(View.VISIBLE);
					parent.l.layoutBar.setVisibility(View.VISIBLE);
				}
			});	 
	 
			l.imgPlay.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v) 
				{ 
					if(play)
					{
						play = false;
						player.pause();
						l.imgPlay.setImageResource(R.drawable.play);
					}
					else
					{
						play = true; 
						player.start();
						l.imgPlay.setImageResource(R.drawable.pause); 
					}
				}
			});
			 
			l.seekProgress.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
			{
				
				@Override
				public void onStopTrackingTouch(SeekBar seekBar) {}
				
				@Override
				public void onStartTrackingTouch(SeekBar seekBar) {}
				
				@Override
				public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) 
				{
					position = progress;
					player.seekTo(position * 1000);
					l.txtTime.setText(time(position)); 
					
					if((position) == duration)
					{
						nextSong();
					}
					
				} 
			});
			
			l.imgskipForward.setOnClickListener(new OnClickListener() 
			{
				
				@Override
				public void onClick(View arg0)
				{
					l.txtLyricsTitle.setText("");
					l.txtLyricsContent.setText("");
					nextSong();
				}
			});
			
			l.imgSkipBack.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					l.txtLyricsTitle.setText("");
					l.txtLyricsContent.setText("");
					if(shuffle)
					{
						Random rand = new Random();
						index = rand.nextInt(parent.list.size());
					}
					else
					{
						index--;
						
						if (index == -1)
							{
								index = parent.list.size() - 1;
							}
					}

					currentSong = parent.list.get(index);
					play = true;
					l.imgPlay.setImageResource(R.drawable.pause);
					setSongInfo(currentSong, index);
					
					lyrics = true;
					lyricss();
				}
			});
			
			l.imgSeekForward.setOnTouchListener(new OnTouchListener()
			{
				
				@Override 
				public boolean onTouch(View v, MotionEvent event) 
				{
					switch (event.getAction())
					{
						case MotionEvent.ACTION_DOWN:

							press = true;
							
							break; 
					
						case MotionEvent.ACTION_MOVE:
							
							press = true;
							
							break;
							
						case MotionEvent.ACTION_UP:
							
							press = false;
							
							break;
					}
					return true;
				}
			});

			
			l.imgSeekBack.setOnTouchListener(new OnTouchListener()
			{
				
				@Override 
				public boolean onTouch(View v, MotionEvent event) 
				{
					switch (event.getAction())
					{
						case MotionEvent.ACTION_DOWN:

							minuspress = true;
							
							break; 
					
						case MotionEvent.ACTION_MOVE:
							
							minuspress = true;
							
							break;
							
						case MotionEvent.ACTION_UP:
							
							minuspress = false;
							
							break;
					}
					return true;
				}
			});
			
			l.lyricsOnOff.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v) 
				{
					lyricss();
				}
			});
			
			l.imgShuffle.setOnClickListener(new OnClickListener()
			{
				
				@Override
				public void onClick(View v)
				{
					if(shuffle == false)
					{
						l.imgShuffle.setImageResource(R.drawable.shuffle_on);
						shuffle = true;
					}
					else
					{
						l.imgShuffle.setImageResource(R.drawable.shuffle);
						shuffle = false;
					}
				}
			});
		}
	}
	
	MediaPlayer player;
	int position;
	boolean play;
	String data;
	String songName;

	Layout l;
	Events e;
	
	Context context;
	int index;
	Songs currentSong;
	boolean isNew;
	
	int count = 0;
	boolean press;
	boolean minuspress;
	
	boolean lyrics;
	boolean shuffle;
	boolean isPlay = false;
	
	int duration;
	
	public ViewMusic(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		inflate(context, R.layout.view_music, this);
		
		this.context = context;
		
		l = new Layout();
		e = new Events();
		
		play = true;
		
		lyrics = false;
		
		shuffle = false;
		
		l.imgPlay.setImageResource(R.drawable.pause);

	}

	void playSong()
    {
		isPlay = true;
		lyrics = true;
		lyricss();
		l.imgPlay.setImageResource(R.drawable.pause);
		play = true;
		player = MediaPlayer.create(context, Uri.parse(data));
    	position = 0;
    	
    	 
    	try 
    	{
			player.prepare();
			count = 0;
		} 
    	catch (IllegalStateException e) 
		{
			e.printStackTrace();
		} 
    	catch (IOException e) 
    	{
			e.printStackTrace();
		}
    	
    	duration = player.getDuration() / 1000;
    	l.seekProgress.setProgress(0);
		l.seekProgress.setMax(duration);
		l.txtTime.setText(time(0));
    	
    	if (!isNew)
    	{
	    	Task.fixedOnUI(parent, new Runnable() 
	    	{  
				@Override
				public void run() 
				{
					
					if(play)
					{
						position++;
					}
					
					if (press)
					{
						count += 3;
					}
					else if(minuspress)
					{
						count -= 3; 
					}
					else
					{
						count = 0;
					}
					player.seekTo(position * 1000 + count * 1000);
					l.seekProgress.setProgress(position + count);
					l.txtTime.setText(time(position + count));
					
				}
			}, 1, TimeUnit.SECONDS);
	    	
	    	isNew = true;
    	}
    	
    	player.start();
    }
	
	void setSongInfo(Songs song, int index)
	{
		l.txtSongName.setText(song.title);
		l.txtArtistName.setText(song.artist);
		this.data = song.data;
		this.index = index;
		currentSong = song;
		songName = song.title;
		songName.replaceAll("-", " ");
		songName.replaceAll("_", " ");
		//songName = "eminem lose yourself";
		if(isPlay)
			player.stop();
		playSong();
	}
	
	void setParent(ActivityPlayer parent)
	{
		this.parent = parent;
	}
	
	void lyricss()
	{
		if(lyrics)
		{
			lyrics = false;
			l.txtLyricsTitle.setVisibility(GONE);
			l.txtLyricsContent.setVisibility(GONE);
			l.frameAlpha.setVisibility(INVISIBLE);
			l.lyricsOnOff.setTextColor(Color.WHITE);
		}
		else 
		{
			lyrics = true;
			l.frameAlpha.setVisibility(VISIBLE);
			searchLyrics();
		//	searchLyrics2();
			l.txtLyricsTitle.setVisibility(VISIBLE);
			l.txtLyricsContent.setVisibility(VISIBLE);
			l.lyricsOnOff.setTextColor(Color.rgb(47, 180, 229));
		}
	}
	
	void nextSong()
	{
		if(shuffle)
		{
			Random rand = new Random();
			index = rand.nextInt(parent.list.size());
		}
		else
		{
			index++;
			
			if (index == parent.list.size()) 
			{
				index = 0;
			}
		}
		currentSong = parent.list.get(index);
		play = true;
		l.imgPlay.setImageResource(R.drawable.pause);
		setSongInfo(currentSong, index);
		
		lyrics = true;
		lyricss();
	}
	
	String time(int seconds)
	{
		int minutes;
		String minx;
		String secx;
		int seconds2 = seconds % 60;
		minutes = seconds / 60;	
		if(seconds2 < 10)
			secx =  "0" + seconds2;
		else secx = String.valueOf(seconds2);
		if(minutes < 10)
			minx =  "0" + minutes;
		else minx = String.valueOf(minutes);
		if(seconds >= 60)
		{
			seconds = 0;
			minutes++;
		}
		return String.format("%s:%s",minx,secx);
	}

	void searchLyrics()
	{
		 new AsyncTask<String,Integer,Document>()
		    {
		    	public Document doInBackground(String... params) 
		    	{
		    	   Document doc = null;
		    	   
		    	   try
		    	   {
		    	     doc = Jsoup.connect(params[0]).get();
		    	   }
		    	   catch (Exception e){}

		    	   return doc;
		       }

		       public void onPostExecute(Document doc)
		       {
		    	   Elements list = doc.select("a[class=topHitLink]");

		    	   String lyricsLink = list.get(0).attr("abs:href");
		    	   if(lyricsLink != null && !lyricsLink.isEmpty())
		    	   {
		    		   setLyricsContent(lyricsLink);
		    	   }

		       } 

		    }.execute("http://www.songtexte.com/search?q="+songName+"&c=all");
	}
	
	void setLyricsContent(String link)
	{
		new AsyncTask<String,Integer,Document>()
	    {
	    	public Document doInBackground(String... params) 
	    	{
	    	   Document doc = null;
	    	   
	    	   try
	    	   {
	    	     doc = Jsoup.connect(params[0]).get();
	    	   }
	    	   catch (Exception e){}

	    	   return doc;
	       }

	       public void onPostExecute(Document doc)
	       {
	    	   Elements title = doc.select("h2");
	    	   
	    	   String songTitle = title.get(0).text();
	    	   
	    	   Elements list = doc.select("div[id=lyrics]");
	    	   
	    	   String songContent = list.get(0).text();
	    	   
	    	   if((songTitle != null && !songTitle.isEmpty())&&(songContent != null && !songContent.isEmpty()))
	    	   {
		    	   l.txtLyricsTitle.setText(songTitle);
		    	   
		    	   l.txtLyricsContent.setText(songContent);
	    	   }
	    	   else
			   {
				   l.txtLyricsTitle.setText("No Lyrics Found");

				   l.txtLyricsContent.setText("");
			   }
	       } 

	    }.execute(link);
	}


	void searchLyrics2()
	{
		 new AsyncTask<String,Integer,Document>()
		    {
		    	public Document doInBackground(String... params) 
		    	{
		    	   Document doc = null;
		    	   
		    	   try
		    	   {
		    	     doc = Jsoup.connect(params[0]).get();
		    	   }
		    	   catch (Exception e){}

		    	   return doc;
		       }

		       public void onPostExecute(Document doc)
		       {
		    	   Elements list = doc.select("li").select("a");

		    	   String lyricsLink = list.get(0).attr("abs:href");
		    	   if(lyricsLink != null && !lyricsLink.isEmpty())
		    	   {
		    		   setLyricsContent2(lyricsLink);
		    	   }

		       } 

		    }.execute("http://www.lyrics007.com/search.php?q="+songName);
	}
	
	
	void setLyricsContent2(String link)
	{
		new AsyncTask<String,Integer,Document>()
	    {
	    	public Document doInBackground(String... params) 
	    	{
	    	   Document doc = null;
	    	   
	    	   try
	    	   {
	    	     doc = Jsoup.connect(params[0]).get();
	    	   }
	    	   catch (Exception e){}

	    	   return doc;
	       }

	       public void onPostExecute(Document doc)
	       {
	    	   Elements title = doc.select("h1");
	    	   
	    	   String songTitle = title.get(0).text();
	    	   
	    	   Elements list = doc.select("div[class=content]");
	    	   
	    	   String songContent = list.get(0).text();
	    	   
	    	   if((songTitle != null && !songTitle.isEmpty())&&(songContent != null && !songContent.isEmpty()))
	    	   {
		    	   l.txtLyricsTitle.setText(songTitle);
		    	   
		    	   l.txtLyricsContent.setText(songContent);
	    	   }
	       } 

	    }.execute(link);
	}
}
