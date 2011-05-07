package com.fakelg.weatherwig;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;

public class MainActivity extends Activity {
	private Button btnStartService;
	private Button btnStopService;
	private Button btnUpdate;
	private Button btnSearch;
	private Button btnRestore;
	private Button btnGPS;
	private EditText etCity;
	private String dowidg[]={" "," "," "," "};
	private Date myDate;
	private Boolean noc;
	private Long ostup;
	private String ostu;
	private PendingIntent pendingIntent;
	private TextView txLastu; 
	private ProgressDialog dialog;
	private Boolean net;
	private ConnectivityManager connManager;
	
	private OnClickListener startServiceListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			//startService(new Intent(MainActivity.this, MyService.class));
			Long Intval;
			Date sync;
			int sek;
			int synch;

			sync = new Date();
			sek = sync.getSeconds();
			synch = 60 - sek;
			//Log.v("sek", Integer.toString(sek)+ " "+ Integer.toString(synch));
			
			Intent myIntent = new Intent(MainActivity.this, MyService.class);
			pendingIntent = PendingIntent.getService(MainActivity.this, 0, myIntent, 0);

            AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
            Intval = Long.valueOf("60000");
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, synch);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),Intval, pendingIntent);
            Log.v("alarm","Start Alarm");
		}
	};
	
	private OnClickListener stopServiceListener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			//stopService(new Intent(MainActivity.this, MyService.class));
			AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.cancel(pendingIntent);
			stopService(new Intent(MainActivity.this, MyService.class));

			Log.v("alarm","Cancel!");
		}
	};
	
	private OnClickListener restorelistener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {

			Long exist;
			
			exist = ostatniupdate("cache.txt");
			
			if (exist != 0)
			{
				String[] danecache;
				boolean noc;
				int godz;
				
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
	};
	
	private OnClickListener updatelistener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
        	dialog = ProgressDialog.show(MainActivity.this, "","Trwa aktualizcja pogody, Proszê czekaæ", true);
			
			String danecache[];
			
			myDate = new Date();
			final int godz = myDate.getHours();
			long mili1970 = myDate.getTime();
			
			final Intent intentw = new Intent(MyAppWidget.UPDATE_WEATHER);
			
			ostup = ostatniupdate("cache.txt");
			//Log.v("testkesz", Long.toString(mili1970) + " <-obecny plik-> " + Long.toString(ostup));
			
			net = NetOnOff();
			if (net == true)
			{
				
				if ((ostup + 10/*3600000*/) > mili1970 && ostup != 0)
				{
					
			        Toast.makeText(getApplicationContext(),"Prognoza pogody wczytana z keszu",Toast.LENGTH_LONG).show();
					danecache=readcache("cache.txt");
					//Log.v("testkesz", danecache[0]+danecache[1]+danecache[2]+danecache[3]);
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
					dialog.dismiss();
				}
				
				else
				{
					
						new Thread() 
							{
								public void run() 
								{
						  
									String name[];
									String temp[];
									String wcode[];
									String wdesc[];
									String fullw[];
									String hilo[];
									String dzien[];	
									//xml z sieci
									
									try 
									{
										String szukaj[];
										Long ex;
										URL url;
										
										ex = ostatniupdate("szukaj.txt");
										
										if (ex != 0) 
										{
											szukaj = readcache("szukaj.txt");
											url = new URL("http://api.wxbug.net/getForecastRSS.aspx?ACode=A4232456352&citycode=" + szukaj[0] + "&unittype=1&Outputtype=1");
										}
										else
										{
											url = new URL("http://api.wxbug.net/getForecastRSS.aspx?ACode=A4232456352&citycode=73095&unittype=1&Outputtype=1");
										}
										
										//http://api.wxbug.net/getForecastRSS.aspx?ACode=A4232456352&citycode=szukaj[0]&unittype=1&Outputtype=1
										//URL url = new URL("http://dl.dropbox.com/u/15624346/wbtest.xml");
										DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
										DocumentBuilder db = dbf.newDocumentBuilder();
										Document doc = db.parse(new InputSource(url.openStream()));
										doc.getDocumentElement().normalize();
	
										NodeList miastonode = doc.getElementsByTagName("aws:location");
	
										/** Assign textview array lenght by arraylist size */
										name = new String[miastonode.getLength()];
											
										for (int i = 0; i < miastonode.getLength(); i++) 
										{
	
											Node node = miastonode.item(i);
	
											Element fstElmnt = (Element) node;
											NodeList nameList = fstElmnt.getElementsByTagName("aws:city");
											Element nameElement = (Element) nameList.item(0);
											nameList = nameElement.getChildNodes();
											name[i] = ((Node) nameList.item(0)).getNodeValue();
											
											dowidg[0] = name[0];
											Log.v("miasto", dowidg[0]);
											
											intentw.putExtra("miasto", dowidg[0]);
											
										}
										
										
										NodeList pogodanode = doc.getElementsByTagName("aws:forecast");
	
										/** Assign textview array lenght by arraylist size */
										temp = new String[7];
										wcode = new String[7];
										wdesc = new String[7];
										fullw = new String[7];
										hilo = new String[7];
										dzien = new String[7];
										
										for (int i = 0; i < pogodanode.getLength(); i++) 
										{
	
											Node node = pogodanode.item(i);
	
											Element tempElmnt = (Element) node;
											NodeList tempList = tempElmnt.getElementsByTagName("aws:high");
											Element tempElement = (Element) tempList.item(0);
											tempList = tempElement.getChildNodes();
											temp[i] = ((Node) tempList.item(0)).getNodeValue();
											dowidg[1] = temp[0];
											
											Element wdescElmnt = (Element) node;
											NodeList wdescList = wdescElmnt.getElementsByTagName("aws:short-prediction");
											Element wdescElement = (Element) wdescList.item(0);
											wdescList = wdescElement.getChildNodes();
											wdesc[i] = ((Node) wdescList.item(0)).getNodeValue();
											dowidg[3] = wdesc[0]; 
											
											Element fullwElmnt = (Element) node;
											NodeList fullwList = fullwElmnt.getElementsByTagName("aws:prediction");
											Element fullwElement = (Element) fullwList.item(0);
											fullwList = fullwElement.getChildNodes();
											fullw[i] = ((Node) fullwList.item(0)).getNodeValue();
											
											Element hiloElmnt = (Element) node;
											NodeList hiloList = hiloElmnt.getElementsByTagName("aws:low");
											Element hiloElement = (Element) hiloList.item(0);
											hiloList = hiloElement.getChildNodes();
											hilo[i] = ((Node) hiloList.item(0)).getNodeValue();
											
											Element dzienElmnt = (Element) node;
											NodeList dzienList = dzienElmnt.getElementsByTagName("aws:title");
											Element dzienElement = (Element) dzienList.item(0);
											dzienList = dzienElement.getChildNodes();
											dzien[i] = ((Node) dzienList.item(0)).getNodeValue();
											
											wcode[i] = standarizewcode(wdesc[i]);
											dowidg[2] = wcode[0]; 
											
											generatecache("cache.txt", dowidg);
											generatecache2("fullcache.txt", fullw,temp,hilo,dzien);
											
											//Log.v("wcode", dowidg[2]);
											//Log.v("wdesc", dowidg[3]);
											//Log.v("fullw", fullw[i]);
											
											intentw.putExtra("temp", dowidg[1]);
											intentw.putExtra("wcode", dowidg[2]);
											intentw.putExtra("wdesc", dowidg[3]);
											
										}
										
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
									
									catch (Exception e) 
									{
										e.printStackTrace();
									}
									
									dialog.dismiss();
	
								}
							}.start();
					
				}
					
				ostu = "Ostatni update prognozy pogody odby³ siê o " + Integer.toString(myDate.getHours()) + ":" + Integer.toString(myDate.getMinutes()) + " " + Integer.toString(myDate.getDate()) + "." + Integer.toString(myDate.getMonth()+1) + "." + Integer.toString(myDate.getYear()+1900);
				generatecache3("ostatniup.txt", ostu);
	        	txLastu.setText(ostu);
			}
			
			else
			{
				
				dialog.dismiss();

                // Create the alert box
               AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);

               // Set the message to display
               alertbox.setMessage("Po³¹cz siê z internetem aby zaktualizowaæ prognozê");

               // Add a neutral button to the alert box and assign a click listener
               alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

                   // Click listener on the neutral button of alert box
                   public void onClick(DialogInterface arg0, int arg1) {

                       // The neutral button was clicked
                       //Toast.makeText(getApplicationContext(), "'OK' button clicked", Toast.LENGTH_LONG).show();
                   }
               });

                // show the alert box
               alertbox.setIcon(R.drawable.icon);
               alertbox.setTitle("Brak po³¹czenia z internetem");
               alertbox.show();
				
			}
				
		}

	};
	
	private OnClickListener searchlistener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
        	dialog = ProgressDialog.show(MainActivity.this, "","Trwa wyszukiwanie, Proszê czekaæ", true);
        	
			net = NetOnOff();
			if (net == true)
			{
        	
	            new Thread() 
	            {
	                public void run() 
	                {
	          
	                	String fraza;
	        			URL url;
	        			
	        			fraza = etCity.getText().toString();
	        			
	        			try
	                    { 
	
	        	        	url = new URL("http://api.wxbug.net/getLocationsXML.aspx?ACode=A4232456352&SearchString=" + fraza);
	        				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        				DocumentBuilder db = dbf.newDocumentBuilder();
	        				Document doc = db.parse(new InputSource(url.openStream()));
	        				doc.getDocumentElement().normalize();
	
	        				NodeList szukajnode = doc.getElementsByTagName("aws:locations");
	        				
	        				String wynik[];
	        				String code[];
	        				String country[];
	        				
	        				for (int i = 0; i < szukajnode.getLength(); i++) 
	        				{
	
	        					Node node = szukajnode.item(i);
	
	        					
	        					Element fstElmnt = (Element) node;
	        					NodeList nameList2 = fstElmnt.getElementsByTagName("aws:location");
	        					
	        					//Log.v("lennl", String.valueOf(nameList2.getLength()));
	        					int max = nameList2.getLength();
	        					
	        					wynik = new String[max];
	        					code = new String[max];
	        					country = new String[max];
	        					
	        					Element nameElement;
	        					//int j=1;
	        					for (int j=0; j < max ; j++)
	        					{
	        						NodeList nameList = fstElmnt.getElementsByTagName("aws:location");
	        						nameElement = (Element) nameList.item(j);
	        						nameList = nameElement.getChildNodes();
	        						wynik[j] = nameElement.getAttribute("cityname");
	        						code[j] = nameElement.getAttribute("citycode");
	        						country[j] = nameElement.getAttribute("countryname");
	        						
	        						//Log.v("licznik", String.valueOf(j));
	        						//Log.v("miasto", wynik[j]);
	        						//Log.v("citycode", code[j]);
	        						//Log.v("kraj", country[j]);
	        					}
	        					
	        					generatecache("szukajwynik.txt", wynik);
	        					generatecache("szukajcode.txt", code);
	        					generatecache("szukajkraj.txt", country);
	        					
	        					startActivity(new Intent(MainActivity.this, Szukajact.class));
	        					
	        				}
	        				
	        			} 
	        			
	        			catch (Exception e) 
	        			{
	       	                
	    	                e.printStackTrace();
	        			}
	                	
	                        // Dismiss the Dialog
	                    dialog.dismiss();
	                }
	                
	            }.start();
	            
		}
		
		else
		{
			
			dialog.dismiss();

            // Create the alert box
           AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);

           // Set the message to display
           alertbox.setMessage("Po³¹cz siê z internetem aby pobraæ wyniki wyszukiwania");

           // Add a neutral button to the alert box and assign a click listener
           alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

               // Click listener on the neutral button of alert box
               public void onClick(DialogInterface arg0, int arg1) {

                   // The neutral button was clicked
                   //Toast.makeText(getApplicationContext(), "'OK' button clicked", Toast.LENGTH_LONG).show();
               }
           });

            // show the alert box
           alertbox.setIcon(R.drawable.icon);
           alertbox.setTitle("Brak po³¹czenia z internetem");
           alertbox.show();
			
		}
			
			
		}
			
	};
	
	private OnClickListener gpslistener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
        	dialog = ProgressDialog.show(MainActivity.this, "","Trwa wyszukiwanie, Proszê czekaæ", true);
        	final LocationManager lm;
        	
	        lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	        
	        if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER))
	        {
	        	
				net = NetOnOff();
				if (net == true)
				{
	        	
	                new Thread() 
					{
						public void run() 
						{
				  
							double lat;
							double lon;
							
								Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				                
				        		if(loc == null) 
				        		{
				        			lat = 0.0;
				        			lon = 0.0;
				        		} 
				        		
				        		else 
				        		{
				        			lat= round2(loc.getLatitude());
				        			lon= round2(loc.getLongitude());
				        		}
				                			                
				               // http://api.wxbug.net/getForecastRSS.aspx?ACode=A4232456352&lat=50.06&long=21.75&OutputType=1
				            try
				            { 
								URL url = new URL("http://api.wxbug.net/getForecastRSS.aspx?ACode=A4232456352&lat=" + lat + "&long=" + lon + "&OutputType=1");
								DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
								DocumentBuilder db = dbf.newDocumentBuilder();
								Document doc = db.parse(new InputSource(url.openStream()));
								doc.getDocumentElement().normalize();
				
								NodeList szukajnode = doc.getElementsByTagName("aws:location");
								
								String wynik[];
								String code[];
								String country[];
								wynik = new String[szukajnode.getLength()];
								code = new String[szukajnode.getLength()];
								country = new String[szukajnode.getLength()];
								
								for (int i = 0; i < szukajnode.getLength(); i++) 
								{
				
									Node node = szukajnode.item(i);
				
									Element fstElmnt = (Element) node;
									NodeList nameList = fstElmnt.getElementsByTagName("aws:city");
									Element nameElement = (Element) nameList.item(0);
									nameList = nameElement.getChildNodes();
									wynik[i] = ((Node) nameList.item(0)).getNodeValue();
									
									Element fst2Elmnt = (Element) node;
									NodeList codeList = fst2Elmnt.getElementsByTagName("aws:citycode");
									Element codeElement = (Element) codeList.item(0);
									codeList = codeElement.getChildNodes();
									code[i] = ((Node) codeList.item(0)).getNodeValue();
									
									Element fst3Elmnt = (Element) node;
									NodeList krajList = fst3Elmnt.getElementsByTagName("aws:country");
									Element krajElement = (Element) krajList.item(0);
									krajList = krajElement.getChildNodes();
									country[i] = ((Node) krajList.item(0)).getNodeValue();
									
									generatecache("szukajwynik.txt", wynik);
									generatecache("szukajcode.txt", code);
									generatecache("szukajkraj.txt", country);
				
									startActivity(new Intent(MainActivity.this, Szukajact.class));
									
								}
								
							} 
							
							catch (Exception e) 
							{
				                e.printStackTrace();
							}
							
							dialog.dismiss();
							
						}
					}.start();
					
				}
				
				else
				{
					
					dialog.dismiss();

		            // Create the alert box
		           AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);

		           // Set the message to display
		           alertbox.setMessage("Po³¹cz siê z internetem aby pobraæ wyniki wyszukiwania");

		           // Add a neutral button to the alert box and assign a click listener
		           alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

		               // Click listener on the neutral button of alert box
		               public void onClick(DialogInterface arg0, int arg1) {

		                   // The neutral button was clicked
		                   //Toast.makeText(getApplicationContext(), "'OK' button clicked", Toast.LENGTH_LONG).show();
		               }
		           });

		            // show the alert box
		           alertbox.setIcon(R.drawable.icon);
		           alertbox.setTitle("Brak po³¹czenia z internetem");
		           alertbox.show();
					
				}
				
	        }
	        else
	        {
	        	
	        	/** check whether the alert button has been clicked */
					dialog.dismiss();

	                 // Create the alert box
	                AlertDialog.Builder alertbox = new AlertDialog.Builder(MainActivity.this);

	                // Set the message to display
	                alertbox.setMessage("Proszê w³¹czyæ odbiornik GPS");

	                // Add a neutral button to the alert box and assign a click listener
	                alertbox.setNeutralButton("Ok", new DialogInterface.OnClickListener() {

	                    // Click listener on the neutral button of alert box
	                    public void onClick(DialogInterface arg0, int arg1) {

	                        // The neutral button was clicked
	                        //Toast.makeText(getApplicationContext(), "'OK' button clicked", Toast.LENGTH_LONG).show();
	                    }
	                });

	                 // show the alert box
	                alertbox.setIcon(R.drawable.icon);
	                alertbox.setTitle("GPS jest wy³¹czony");
	                alertbox.show();
	        	
	        }
	        
		}
		
	};
	
	private void generatecache(String sFileName, String dane[])
    {
	 	try
	 	{

	 		String root = "/data/data/com.fakelg.weatherwig";
	 		File gpxfile = new File(root, sFileName);
	 		FileWriter writer = new FileWriter(gpxfile);

	 		for (int j=0;j<dane.length;j++)
	 		{
	 			
	 			writer.append(dane[j]+"\n");

	 		}
	 		
	 	    writer.flush();
	 	    writer.close();
	 	}
	 	catch(IOException e)
	 	{
	 	     e.printStackTrace();
	 	}
    }
	
	private void generatecache2(String sFileName, String datafull[], String high[], String low[], String dzien[])
    {
	 	try
	 	{

	 		String root = "/data/data/com.fakelg.weatherwig";
	 		File gpxfile = new File(root, sFileName);
	 		FileWriter writer = new FileWriter(gpxfile);

	 		for (int j=0;j<datafull.length;j++)
	 		{
	 			
	 			writer.append(datafull[j]+"\n");

	 		}
	 		
	 		for (int j=0;j<high.length;j++)
	 		{
	 			
	 			writer.append(high[j]+"\n");

	 		}
	 		
	 		for (int j=0;j<low.length;j++)
	 		{
	 			
	 			writer.append(low[j]+"\n");

	 		}
	 		
	 		for (int j=0;j<dzien.length;j++)
	 		{
	 			
	 			writer.append(dzien[j]+"\n");

	 		}
	 		
	 	    writer.flush();
	 	    writer.close();
	 	}
	 	catch(IOException e)
	 	{
	 	     e.printStackTrace();
	 	}
    }
	
	private void generatecache3(String sFileName, String dane)
    {
	 	try
	 	{

	 		String root = "/data/data/com.fakelg.weatherwig";
	 		File gpxfile = new File(root, sFileName);
	 		FileWriter writer = new FileWriter(gpxfile);
	 		writer.append(dane);
	 	    writer.flush();
	 	    writer.close();
	 	    
	 	}
	 	catch(IOException e)
	 	{
	 	     e.printStackTrace();
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
	
	public String standarizewcode (String prognoza)
	{
		String wcode = "11";
		String[] opis;
		String[] wkod;
		
		Resources res = getResources();
		opis = res.getStringArray(R.array.opis);
		wkod = res.getStringArray(R.array.wkod);
		
		for (int i=0;i<opis.length;i++)
		{
			
			if(prognoza.equalsIgnoreCase(opis[i]) == true)
			{
				wcode = wkod[i];
			}
			
		}
		
		return wcode;
	}
	
	public boolean NetOnOff ()
	{
		boolean net;
		String networkService = Context.CONNECTIVITY_SERVICE;
		connManager = (ConnectivityManager)getSystemService(networkService);
		
	    if(connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected() || connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) 
	    {
	    	net=true;
	    } 
	    else 
	    {
	    	net=false;     
	    }
		
		return net;
	}
	
	public static double round2(double value) 
	{
		double result = value * 100;
		result = Math.round(result);
		result = result / 100;
		return result;
	}

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        btnStartService 	= (Button)findViewById(R.id.btnStartSevice);
        btnStopService 		= (Button)findViewById(R.id.btnStopSevice);
        btnUpdate 			= (Button)findViewById(R.id.btnUpdate);
        btnSearch			= (Button)findViewById(R.id.btnSearch);
        btnRestore			= (Button)findViewById(R.id.btnRestore);
        btnGPS				= (Button)findViewById(R.id.btnGps);
        etCity				= (EditText)findViewById(R.id.etCity);
        txLastu				= (TextView)findViewById(R.id.txLastu);
        
        btnStartService.setOnClickListener(startServiceListener);
        btnStopService.setOnClickListener(stopServiceListener);
        btnUpdate.setOnClickListener(updatelistener);
        btnSearch.setOnClickListener(searchlistener);
        btnRestore.setOnClickListener(restorelistener);
        btnGPS.setOnClickListener(gpslistener);
        
        Long uw = ostatniupdate("ostatniup.txt");
        
        if (uw != 0)
        {
        	String ost1[];
        	ost1=readcache("ostatniup.txt");
        	txLastu.setText(ost1[0]);
        }
        
    }
}
