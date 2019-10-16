package com.example.musicplayer;

import com.example.musicplayer.objects.Artist;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewArtist extends LinearLayout
{
	
	class Layout
	{
		public Layout()
		{
			txtName = (TextView)findViewById(R.id.txtName);
			imgIcon = (ImageView)findViewById(R.id.imgIcon);
		}
		
		TextView txtName;
		ImageView imgIcon;
	}
	
	Layout l;

	public ViewArtist(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		inflate(context, R.layout.view_artist, this);
		
		l = new Layout();
	}
	
	void loadArtist(Artist artist)
	{
		l.txtName.setText(artist.getName());
		l.imgIcon.setImageResource(artist.getIcon());
	}

}
