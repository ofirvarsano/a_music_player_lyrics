package com.example.musicplayer.util;


import java.util.LinkedList;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;


public class Query<T>
{
	public interface Map
	{
		<T>T item(Cursor cur);
	}
	
	public Query(Context ctx, Uri table)
	{
		this.ctx = ctx;
		uri = table;
		
	}
	
	Context ctx;
	Uri uri;
	
	@SuppressWarnings("unchecked")
	public LinkedList<T> run(LinkedList<T> list, Map map)
	{
		Cursor cur = ctx.getContentResolver().query(uri,  new String[] {}, null, null, null);       
		
	    if (cur == null) return list;
		
		while (cur.moveToNext())
		{
			Object obj = map.item(cur);
		
			if (obj != null) list.add((T)obj);
		}
		
		return list;
	}
}