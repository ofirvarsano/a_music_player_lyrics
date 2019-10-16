package com.example.musicplayer.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Handler;

public class Task 
{
	public static void delay(Runnable run, int ms)
	{
		new Handler().postDelayed(run, ms);
	}
	
	public static void fixed(Runnable run, int period, TimeUnit unit)
	{
		ScheduledExecutorService handler = Executors.newScheduledThreadPool(4); // 4 = number of threads in pool
		
		handler.scheduleAtFixedRate(run, 0, period, unit);
	}
	
	public static void fixedOnUI(final Activity ctx, final Runnable run, int period, TimeUnit unit)
	{
		ScheduledExecutorService handler = Executors.newScheduledThreadPool(4); // 4 = number of threads in pool
		
		handler.scheduleAtFixedRate(new Runnable() 
		{
			@Override
			public void run() 
			{
				ctx.runOnUiThread(run);
			}
			
		}, 0, period, unit);
		
	}
}
