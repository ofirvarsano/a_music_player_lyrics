package com.example.musicplayer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

public class ActivityTest extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		

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
	    	   Elements list = doc.select("div[class=sen]");
	    	   
	    	   for (Element item : list)
	    	   {
	    		   Toast.makeText(ActivityTest.this, item.text(), Toast.LENGTH_LONG).show();
	    	   }
	       } 

	    }.execute("http://search.azlyrics.com/search.php?q=hello");
	    	
	
	       

	    
	}
}
