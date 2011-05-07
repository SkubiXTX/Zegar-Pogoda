package com.fakelg.weatherwig;

import java.util.Date;
//import java.util.Timer;
//import java.util.TimerTask;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.String;
import java.lang.Character;

import android.app.Service;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	private Date myDate;
	private int godz;
	private int min;
	public String hl1,hr2,ml1,mr2,dataw;
	
	@Override
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		int dl,dl2,dl3,dl4,dzien,dzient,mies,rok;
	    char wm[];
		char wh[];
		String ws,ws2,cm,ch,rs;
		String nazwadnia[] = {"NIE","PON","WTO","ŒRO","CZW","PI¥","SOB"};
		String[] danecache;
		boolean noc;
		
		Intent intentu = new Intent(MyAppWidget.UPDATE_ONDEMAND);
		Intent intentw = new Intent(MyAppWidget.UPDATE_WEATHER);
		myDate = new Date();
		godz = myDate.getHours();
		min = myDate.getMinutes();
		dzien = myDate.getDate();
		mies = myDate.getMonth()+1;
		rok = myDate.getYear()+1900;
		dzient = myDate.getDay();
		String s =  new String(Integer.toString(godz));
		String s2 = new String(Integer.toString(min));
		dl = s.length();
		dl2 = s2.length();
		
		if (godz == 19 && min ==0)
		{
			
			danecache=readcache("cache.txt");
			intentw.putExtra("miasto", danecache[0]);
			intentw.putExtra("temp", danecache[1]);
			intentw.putExtra("wcode", danecache[2]);
			intentw.putExtra("wdesc", danecache[3]);
			noc = true;
			intentw.putExtra("noc", noc);
			sendBroadcast(intentw);
			
		}
		
		if (godz == 7 && min ==0)
		{
			
			danecache=readcache("cache.txt");
			intentw.putExtra("miasto", danecache[0]);
			intentw.putExtra("temp", danecache[1]);
			intentw.putExtra("wcode", danecache[2]);
			intentw.putExtra("wdesc", danecache[3]);
			noc = false;
			intentw.putExtra("noc", noc);
			sendBroadcast(intentw);
			
		}
		
		if (dl!=2)
		{
			ws = "0" + s;
		}
		else
		{
			ws = s;
		}
		
		if (dl2!=2)
		{
			ws2 = "0" + s2;
		}
		else
		{
			ws2 = s2;
		}
		
		cm = new String(ws2);
		ch = new String(ws);
		
		wm = cm.toCharArray();
		wh = ch.toCharArray();
		
		hl1 = Character.toString(wh[0]);
		hr2 = Character.toString(wh[1]);
		ml1 = Character.toString(wm[0]);
		mr2 = Character.toString(wm[1]);
		rs = Integer.toString(rok);
		
		String s3 =  new String(Integer.toString(dzien));
		String s4 =  new String(Integer.toString(mies));
		dl3 = s3.length();
		dl4 = s4.length();
		
		if (dl3!=2)
		{
			s3 = "0" + s3;
		}
		
		if (dl4!=2)
		{
			s4 = "0" + s4;
		}
		
		dataw = s3 + "." + s4 + "." + rs + " " + nazwadnia[dzient];
		
		intentu.putExtra("hl", hl1);
		intentu.putExtra("hr", hr2);
		intentu.putExtra("ml", ml1);
		intentu.putExtra("mr", mr2);
		intentu.putExtra("data", dataw);
		//Log.v("Usllg", hl1+hr2+":"+ml1+mr2);
		//intentu.putExtra("WidgetText",ws+":"+ws2);
		sendBroadcast(intentu);
		
	}

	public String[] readcache(String sFileName)     
	 {        
		String dane2[] = new String[12]; //= {" "," "," "," "};
		 try       
		 {        
			String root = "/data/data/com.fakelg.weatherwig/";
			File gpxfile = new File(root, sFileName);
			FileReader f = new FileReader(gpxfile);     
			BufferedReader in = new BufferedReader(f);         

			
			for (int i=0; i<=3; i++)
			{
				
			   dane2[i] = in.readLine();    
			   //Log.v("readfile", dane2[i]);
			   
			}
			
		    in.close();  
		   
		  }
		     catch(Exception ex)     
		  {           
		    	 ex.printStackTrace();
		  }  
		     
	   return dane2;
		     
	} 
	
	@Override
	public void onCreate() {
		super.onCreate();
		Long exist;
		
		exist = ostatniupdate("cache.txt");
		
		if (exist != 0)
		{
			String[] danecache;
			boolean noc;
			
			Intent intentw = new Intent(MyAppWidget.UPDATE_WEATHER);
			myDate = new Date();
			godz = myDate.getHours();
			
			danecache=readcache("cache.txt");
			intentw.putExtra("miasto", danecache[0]);
			intentw.putExtra("temp", danecache[1]);
			intentw.putExtra("wcode", danecache[2]);
			intentw.putExtra("wdesc", danecache[3]);
			if (godz > 6 && godz < 19)
			{
				noc = false;
				intentw.putExtra("noc", noc);
			}
			else
			{
				noc = true;
				intentw.putExtra("noc", noc);
			}
			
			sendBroadcast(intentw);
		}
		Log.v("Usllg", "Service start");
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		super.onConfigurationChanged(newConfig);
		//Log.v("Usllg", "onConfigurationChanged");
		String[] danecache;
		boolean noc;
		Long exist;
		
		exist = ostatniupdate("cache.txt");
		
		if (exist != 0)
		{
			Intent intentw = new Intent(MyAppWidget.UPDATE_WEATHER);
			myDate = new Date();
			godz = myDate.getHours();
			
			danecache=readcache("cache.txt");
			intentw.putExtra("miasto", danecache[0]);
			intentw.putExtra("temp", danecache[1]);
			intentw.putExtra("wcode", danecache[2]);
			intentw.putExtra("wdesc", danecache[3]);
			if (godz > 6 && godz < 19)
			{
				noc = false;
				intentw.putExtra("noc", noc);
			}
			else
			{
				noc = true;
				intentw.putExtra("noc", noc);
			}
			
			sendBroadcast(intentw);
		}
	}
	
	public Long ostatniupdate (String sFileName)
	{
		Long ost;
		String root = "/data/data/com.fakelg.weatherwig/";
		File gpxfile = new File(root, sFileName);
		ost = gpxfile.lastModified();
		
		return ost;
	}
	
	@Override
	public void onDestroy() {
		Log.v("Usllg", "Service stop");
		super.onDestroy();
	}
	
	@Override
	public IBinder onBind(Intent arg0) { return null; }
}
