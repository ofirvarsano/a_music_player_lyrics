package com.example.musicplayer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityTest2 extends Activity
{

	class Layout
	{
		public Layout()
		{
			txtLyricss = (TextView)findViewById(R.id.txtLyricss);
			txtSongTitle = (TextView)findViewById(R.id.txtSongTitle);
		}
		TextView txtLyricss, txtSongTitle;
	}
	Layout l;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		l = new Layout();

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
	    	   setLyrics(lyricsLink);

	       } 

	    }.execute("http://www.songtexte.com/search?q=stan&c=all");
	    
	}
	
	void setLyrics(String link)
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

		    	   l.txtSongTitle.setText(songTitle);
		    	   
		    	   l.txtLyricss.setText(list.get(0).text());
		       } 

		    }.execute(link);
	}
}
