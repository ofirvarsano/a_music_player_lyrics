package com.example.musicplayer;

import java.util.concurrent.TimeUnit;

import com.example.musicplayer.util.Task;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.TextView;

public class Cw extends Activity
{
	class Layout
	{
		public Layout()
		{
			btn = (Button)findViewById(R.id.btn);
			txt = (TextView)findViewById(R.id.txt);
		}
		Button btn;
		TextView txt;
	}
	
	class Events
	{
		public Events()
		{
			l.btn.setOnTouchListener(new OnTouchListener()
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
		}
	}

	Layout l;
	Events e;
	int count = 0;
	boolean press;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cw);
		
		l = new Layout();
		e = new Events();
		
		loop();
	}
	
	void loop()
	{
		Task.fixedOnUI(this, new Runnable() 
		{
			@Override
			public void run() 
			{
				if (press)
				{
					count++;
				}
				else
				{
					count = 0;
				}
				l.txt.setText(String.valueOf(count));
				
			}
		}, 1, TimeUnit.SECONDS);
	}
}
