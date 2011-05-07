package com.fakelg.weatherwig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
//import android.util.Log;

public class MyAppWidget extends AppWidgetProvider {

	public static final String UPDATE_ONDEMAND = "com.fakelg.weatherwi.UPDATE_ONDEMAND";
	public static final String UPDATE_WEATHER = "com.fakelg.weatherwi.UPDATE_WEATHER";
	public static final String LAUNCHER_RESTART = "com.motorola.blur.home.ACTION_SET_WIDGET_SIZE";
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		for (int i = 0; i < appWidgetIds.length; i++) {
			int appWidgetId = appWidgetIds[i];

			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
		    Intent intent = new Intent(context, Fullwact.class);
		    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		    views.setOnClickPendingIntent(R.id.widget_currentIcon, pendingIntent);

			appWidgetManager.updateAppWidget(appWidgetId, views);
			
			Long exist;
			
			exist = ostatniupdate("cache.txt");
			
			if (exist != 0)
			{
				String[] danecache;
				boolean noc;
				int godz;
				
				Date myDate = new Date();
				godz = myDate.getHours();
				
				danecache=readcache("cache.txt");

				if (godz > 6 && godz < 19)
				{
					noc = false;
				}
				else
				{
					noc = true;
				}

				updateweather(context,danecache[1],danecache[0],danecache[2], danecache[3],noc);
	
			}
			
		}
	}

	public void GUIRestart(Context context) {
		// TODO Auto-generated method stub
		
		Long exist;
		
		exist = ostatniupdate("cache.txt");
		
		if (exist != 0)
		{
			String[] danecache;
			boolean noc;
			int godz;
			
			Date myDate = new Date();
			godz = myDate.getHours();
			
			danecache=readcache("cache.txt");

			if (godz > 6 && godz < 19)
			{
				noc = false;
			}
			else
			{
				noc = true;
			}

			updateweather(context,danecache[1],danecache[0],danecache[2], danecache[3],noc);

		}
		
		//Log.v("widget", "onenablewidget");
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		
		if(intent.getAction().equals(UPDATE_ONDEMAND)) {
			String hourl = intent.getStringExtra("hl");
			String hourr = intent.getStringExtra("hr");
			String minl = intent.getStringExtra("ml");
			String minr = intent.getStringExtra("mr");
			String data = intent.getStringExtra("data");
			updateWidget(context,hourl,hourr,minl,minr,data);
		}
		
		if(intent.getAction().equals(UPDATE_WEATHER)) {
			String temp = intent.getStringExtra("temp");
			String miasto = intent.getStringExtra("miasto");
			String wcode = intent.getStringExtra("wcode");
			String wdesc = intent.getStringExtra("wdesc");
			Boolean czynoc = intent.getBooleanExtra("noc", false);
			updateweather(context,temp,miasto,wcode,wdesc,czynoc);
		}
		
		if(intent.getAction().equals(LAUNCHER_RESTART)) {
			GUIRestart(context);
		}
		
		//Log.v("onrec", intent.getAction().toString());
	}
	
	
	public void updateweather(Context context, String temp, String miasto, String wcode, String wdesc, Boolean czynoc)
	{
		
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		ComponentName thisWidged = new ComponentName(context, MyAppWidget.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidged);

		for (int i = 0; i < appWidgetIds.length; i++) {
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
			views.setTextViewText(R.id.widget_current_temp, temp);
			views.setTextViewText(R.id.widget_empty_city, miasto);
			views.setTextViewText(R.id.widget_weather_desc, wdesc);
			
			if (czynoc == false)
			{
				
			switch (Integer.parseInt(wcode))
			{
			
			case 8:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_01_day_sunny_large);
			break;	
			
			case 11:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_04_day_intermittent_clouds_large);
			break;	
			
			case 10:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_07_day_cloudy_large);
			break;	
			
			case 16:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_08_day_dreamy_large);
			break;	
			
			case 17:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_05_day_hazy_sunshine_large);
			break;
			
			case 9:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_11_day_fog_large);
			break;
			
			case 7:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_12_day_shower_large);
			break;
			
			case 2:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_14_day_mostly_sunny_with_showers_large);
			break;
			
			case 12:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_15_day_thunderstorms_large);
			break;
			
			case 6:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_18_day_rain_large);
			break;
			
			case 15:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_32_day_wind_large);
			break;
			
			case 3:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_21_day_partly_sunny_with_flurries_large);
			break;
			
			case 5:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_22_day_snow_large);
			break;
			
			case 4:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_23_day_mostly_cloudy_with_snow_large);
			break;
			
			case 18:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_24_day_ice_large);
			break;
			
			case 1:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_25_day_sleet_large);
			break;
			
			case 13:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_31_day_cold_large);
			break;
			
			case 14:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_30_day_hot_large);
			break;
			
			}
			
		}
		
		else
		{
			
			switch (Integer.parseInt(wcode))
			{
			
			case 8:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_33_night_clear_large);
			break;	
			
			case 11:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_36_night_intermittent_clouds_large);
			break;	
			
			case 10:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_07_night_cloudy_large);
			break;	
			
			case 16:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_08_night_dreamy_large);
			break;	
			
			case 17:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_37_night_hazy_large);
			break;
			
			case 9:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_11_night_fog_large);
			break;
			
			case 7:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_12_night_shower_large);
			break;
			
			case 2:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_40_night_mostly_cloudy_with_showers_large);
			break;
			
			case 12:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_15_night_thunderstorms_large);
			break;
			
			case 6:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_18_night_rain_large);
			break;
			
			case 15:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_42_night_mostly_cloudy_with_thunder_showers_large);
			break;
			
			case 3:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_19_night_flurries_large);
			break;
			
			case 5:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_22_night_snow_large);
			break;
			
			case 4:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_44_night_mostly_cloudy_with_snow_large);
			break;
			
			case 18:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_24_night_ice_large);
			break;
			
			case 1:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_25_night_sleet_large);
			break;

			case 13:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_30_night_hot_large);
			break;
				
			case 14:
				views.setImageViewResource(R.id.widget_currentIcon, R.drawable.widget_weather_31_night_cold_large);
			break;
			
			}
			
		}		
				
		appWidgetManager.updateAppWidget(appWidgetId, views);
			
		}
		
	}
	
	//On demand updater
	public void updateWidget(Context context, String hl, String hr, String ml, String mr, String data) 
	{
		//Log.v("widget", hl + hr + ":" + ml + mr);
		//Get AppWidgetManager
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
		//Get widget IDs
		ComponentName thisWidged = new ComponentName(context, MyAppWidget.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidged);
		//Update all widget copies
		for (int i = 0; i < appWidgetIds.length; i++) {
			int appWidgetId = appWidgetIds[i];
			//Get RemoteViews
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
			
			//Update layout
			//Log.v("widget", hl+hr +":"+ml+mr);
		
			//views.setTextViewText(R.id.tvWidgetText, myText);
			//views.setImageViewResource(R.id.widget_time_hour_l, R.drawable.widget_weather_clock_1_1_large);
			views.setTextViewText(R.id.widget_date, data);
			
			switch(Integer.parseInt(hl))
			{
			
			case 0:
				views.setImageViewResource(R.id.widget_time_hour_l, R.drawable.widget_weather_clock_1_0_large);
			break;
			
			case 1:
				views.setImageViewResource(R.id.widget_time_hour_l, R.drawable.widget_weather_clock_1_1_large);
			break;
			
			case 2:
				views.setImageViewResource(R.id.widget_time_hour_l, R.drawable.widget_weather_clock_1_2_large);
			break;
			
			}
			
			switch(Integer.parseInt(hr))
			{
			
			case 0:
				views.setImageViewResource(R.id.widget_time_hour_r, R.drawable.widget_weather_clock_2_0_large);
			break;
			
			case 1:
				views.setImageViewResource(R.id.widget_time_hour_r, R.drawable.widget_weather_clock_2_1_large);
			break;
			
			case 2:
				views.setImageViewResource(R.id.widget_time_hour_r, R.drawable.widget_weather_clock_2_2_large);
			break;
			
			case 3:
				views.setImageViewResource(R.id.widget_time_hour_r, R.drawable.widget_weather_clock_2_3_large);
			break;
			
			case 4:
				views.setImageViewResource(R.id.widget_time_hour_r, R.drawable.widget_weather_clock_2_4_large);
			break;
			
			case 5:
				views.setImageViewResource(R.id.widget_time_hour_r, R.drawable.widget_weather_clock_2_5_large);
			break;
			
			case 6:
				views.setImageViewResource(R.id.widget_time_hour_r, R.drawable.widget_weather_clock_2_6_large);
			break;
			
			case 7:
				views.setImageViewResource(R.id.widget_time_hour_r, R.drawable.widget_weather_clock_2_7_large);
			break;
			
			case 8:
				views.setImageViewResource(R.id.widget_time_hour_r, R.drawable.widget_weather_clock_2_8_large);
			break;
			
			case 9:
				views.setImageViewResource(R.id.widget_time_hour_r, R.drawable.widget_weather_clock_2_9_large);
			break;
			
			}
			
			switch(Integer.parseInt(ml))
			{
			
			case 0:
				views.setImageViewResource(R.id.widget_time_min_l, R.drawable.widget_weather_clock_3_0_large);
			break;			
			
			case 1:
				views.setImageViewResource(R.id.widget_time_min_l, R.drawable.widget_weather_clock_3_1_large);
			break;
			
			case 2:
				views.setImageViewResource(R.id.widget_time_min_l, R.drawable.widget_weather_clock_3_2_large);
			break;
			
			case 3:
				views.setImageViewResource(R.id.widget_time_min_l, R.drawable.widget_weather_clock_3_3_large);
			break;
			
			case 4:
				views.setImageViewResource(R.id.widget_time_min_l, R.drawable.widget_weather_clock_3_4_large);
			break;
			
			case 5:
				views.setImageViewResource(R.id.widget_time_min_l, R.drawable.widget_weather_clock_3_5_large);
			break;
			
			}
			
			switch(Integer.parseInt(mr))
			{
			
			case 0:
				views.setImageViewResource(R.id.widget_time_min_r, R.drawable.widget_weather_clock_4_0_large);
			break;
			
			case 1:
				views.setImageViewResource(R.id.widget_time_min_r, R.drawable.widget_weather_clock_4_1_large);
			break;
			
			case 2:
				views.setImageViewResource(R.id.widget_time_min_r, R.drawable.widget_weather_clock_4_2_large);
			break;
			
			case 3:
				views.setImageViewResource(R.id.widget_time_min_r, R.drawable.widget_weather_clock_4_3_large);
			break;
			
			case 4:
				views.setImageViewResource(R.id.widget_time_min_r, R.drawable.widget_weather_clock_4_4_large);
			break;
			
			case 5:
				views.setImageViewResource(R.id.widget_time_min_r, R.drawable.widget_weather_clock_4_5_large);
			break;
			
			case 6:
				views.setImageViewResource(R.id.widget_time_min_r, R.drawable.widget_weather_clock_4_6_large);
			break;
			
			case 7:
				views.setImageViewResource(R.id.widget_time_min_r, R.drawable.widget_weather_clock_4_7_large);
			break;
			
			case 8:
				views.setImageViewResource(R.id.widget_time_min_r, R.drawable.widget_weather_clock_4_8_large);
			break;
			
			case 9:
				views.setImageViewResource(R.id.widget_time_min_r, R.drawable.widget_weather_clock_4_9_large);
			break;
			
			}
			
			//Save changes
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
	
	public String[] readcache(String sFileName)     
	 {        
		String dane2[] = new String[20]; //= {" "," "," "," "};
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
	
	public Long ostatniupdate (String sFileName)
	{
		Long ost;
		String root = "/data/data/com.fakelg.weatherwig/";
		File gpxfile = new File(root, sFileName);
		ost = gpxfile.lastModified();
		
		return ost;
	}
}