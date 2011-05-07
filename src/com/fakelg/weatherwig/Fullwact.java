package com.fakelg.weatherwig;

import java.net.URL;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;

public class Fullwact extends Activity {
	Button btnFullwupd;
	ProgressDialog dialog;
	Boolean net;
	ConnectivityManager connManager;
	TextView miasto;
	TextView dzien1;
	TextView desc1;
	TextView temp1;
	TextView wind1;
	TextView winddir1;
	TextView rain;
	TextView hum1;
	TextView chmury;
	TextView rosa;
	TextView press;
	TextView wszasl;
	TextView dzien2;
	TextView desc2;
	TextView temp2;
	TextView wind2;
	TextView windir2;
	TextView hum2;
	TextView dzien3;
	TextView desc3;
	TextView temp3;
	TextView wind3;
	TextView windir3;
	TextView hum3;
	TextView dzien4;
	TextView desc4;
	TextView temp4;
	TextView wind4;
	TextView windir4;
	TextView hum4;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullwact);
		String zkeszu[];
		String zfkeszu[];
		String zfkeszu2[];
		String zfkeszu3[];
		String zfkeszu4[];
		
		miasto = (TextView)findViewById(R.id.textView1);
		dzien1 = (TextView)findViewById(R.id.textView3);
		desc1 = (TextView)findViewById(R.id.textView7);
		temp1 = (TextView)findViewById(R.id.textView5);
		wind1 = (TextView)findViewById(R.id.textView9);
		winddir1 = (TextView)findViewById(R.id.textView11);
		rain = (TextView)findViewById(R.id.textView13);
		hum1 = (TextView)findViewById(R.id.textView15);
		chmury = (TextView)findViewById(R.id.textView17);
		rosa = (TextView)findViewById(R.id.textView19);
		press = (TextView)findViewById(R.id.textView21);
		wszasl = (TextView)findViewById(R.id.textView23);
		dzien3 = (TextView)findViewById(R.id.textView37);
		desc3 = (TextView)findViewById(R.id.textView43);
		temp3 = (TextView)findViewById(R.id.textView44);
		wind3 = (TextView)findViewById(R.id.textView45);
		windir3 = (TextView)findViewById(R.id.textView46);
		hum3 = (TextView)findViewById(R.id.textView47);
		dzien2 = (TextView)findViewById(R.id.textView25);
		desc2 = (TextView)findViewById(R.id.textView31);
		temp2 = (TextView)findViewById(R.id.textView32);
		wind2 = (TextView)findViewById(R.id.textView33);
		windir2 = (TextView)findViewById(R.id.textView34);
		hum2 = (TextView)findViewById(R.id.textView35);
		dzien4 = (TextView)findViewById(R.id.textView49);
		desc4 = (TextView)findViewById(R.id.textView55);
		temp4 = (TextView)findViewById(R.id.textView56);
		wind4 = (TextView)findViewById(R.id.textView57);
		windir4 = (TextView)findViewById(R.id.textView58);
		hum4 = (TextView)findViewById(R.id.textView59);
		
		btnFullwupd = (Button)findViewById(R.id.btnFullwupd);
		btnFullwupd.setOnClickListener(fullwupdlistener);		

		Long ex,ex2,ex3,ex4,ex5;
		
		ex =ostatniupdate("cache.txt");
		ex2 =ostatniupdate("fullcache.txt");
		ex3 =ostatniupdate("fullcache2.txt");
		ex4 =ostatniupdate("fullcache3.txt");
		ex5 =ostatniupdate("fullcache4.txt");
		
		if (ex != 0 && ex2!=0 && ex3 != 0 && ex4!=0 && ex5 != 0)
		{
		
			zkeszu =  readcache("cache.txt");
			zfkeszu = readcache("fullcache.txt");
			zfkeszu2 = readcache("fullcache2.txt");
			zfkeszu3 = readcache("fullcache3.txt");
			zfkeszu4 = readcache("fullcache4.txt");
			
			miasto.setText(zkeszu[0]);
			dzien1.setText(zfkeszu[21]);
			temp1.setText(zfkeszu[14]+" <> "+zfkeszu[7]);
			desc1.setText(zfkeszu3[21]);
			wind1.setText(zfkeszu3[0]);
			winddir1.setText(zfkeszu3[14]);
			rain.setText(zfkeszu2[3]);
			hum1.setText(zfkeszu3[7]);
			chmury.setText(zfkeszu2[1]);
			rosa.setText(zfkeszu2[0]);
			press.setText(zfkeszu2[2]);
			wszasl.setText(zfkeszu4[0]+":"+zfkeszu4[1] + " <> " + zfkeszu4[2]+":"+zfkeszu4[3]);
			dzien2.setText(zfkeszu[22]);
			desc2.setText(zfkeszu3[22]);
			temp2.setText(zfkeszu[15]+" <> "+zfkeszu[8]);
			wind2.setText(zfkeszu3[1]);
			windir2.setText(zfkeszu3[15]);
			hum2.setText(zfkeszu3[8]);
			dzien3.setText(zfkeszu[23]);
			desc3.setText(zfkeszu3[23]);
			temp3.setText(zfkeszu[16]+" <> "+zfkeszu[9]);
			wind3.setText(zfkeszu3[2]);
			windir3.setText(zfkeszu3[16]);
			hum3.setText(zfkeszu3[9]);
			dzien4.setText(zfkeszu[24]);
			desc4.setText(zfkeszu3[24]);
			temp4.setText(zfkeszu[17]+" <> "+zfkeszu[10]);
			wind4.setText(zfkeszu3[3]);
			windir4.setText(zfkeszu3[17]);
			hum4.setText(zfkeszu3[10]);
			
		}
		
		//
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private OnClickListener fullwupdlistener = new OnClickListener() {
		@Override
		public void onClick(View arg0) {
			
        	dialog = ProgressDialog.show(Fullwact.this, "","Trwa pobieranie danych, Proszê czekaæ", true);
			
			net = NetOnOff();
			if (net == true)
			{
        	
	            new Thread() 
	            {
	                public void run() 
	                {   
	                	String dw[],elew[],press[],rain[],hsr[],msr[],hss[],mss[];
	                		try 
	            			{
	                			
	                			String szukaj[];
	        					Long ex;
	        					URL url;
	        					
	        					ex = ostatniupdate("szukaj.txt");
	        					
	        					if (ex != 0) 
	        					{
	        						szukaj = readcache("szukaj.txt");
	        						url = new URL("http://api.wxbug.net/getLiveWeatherRSS.aspx?ACode=A4232456352&citycode=" + szukaj[0] + "&unittype=1&Outputtype=1");
	        					}
	        					else
	        					{
	        						url = new URL("http://api.wxbug.net/getLiveWeatherRSS.aspx?ACode=A4232456352&citycode=73095&unittype=1&Outputtype=1");
	        					}
	        					
	        					//Log.v("link", url.toString());
	        					
	            				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            				DocumentBuilder db = dbf.newDocumentBuilder();
	            				Document doc = db.parse(new InputSource(url.openStream()));
	            				doc.getDocumentElement().normalize();
	            				
	            				NodeList obnode = doc.getElementsByTagName("aws:ob");
	            				
	            				dw = new String[obnode.getLength()];
	            				elew = new String[obnode.getLength()];
	            				press = new String[obnode.getLength()];
	            				rain = new String[obnode.getLength()];
	            				
	            				for (int i = 0; i < obnode.getLength(); i++) 
	            				{
	
	            					Node node = obnode.item(i);
	
	            					Element fstElmnt = (Element) node;
	            					
	            					NodeList dwList = fstElmnt.getElementsByTagName("aws:dew-point");
	            					Element dwElement = (Element) dwList.item(0);
	            					dwList = dwElement.getChildNodes();
	            					dw[i] = ((Node) dwList.item(0)).getNodeValue();
	
	            					NodeList elewList = fstElmnt.getElementsByTagName("aws:elevation");
	            					Element elewElement = (Element) elewList.item(0);
	            					elewList = elewElement.getChildNodes();
	            					elew[i] = ((Node) elewList.item(0)).getNodeValue();
	            					
	            					NodeList pressList = fstElmnt.getElementsByTagName("aws:pressure");
	            					Element pressElement = (Element) pressList.item(0);
	            					pressList = pressElement.getChildNodes();
	            					press[i] = ((Node) pressList.item(0)).getNodeValue();
	            					
	            					NodeList rainList = fstElmnt.getElementsByTagName("aws:rain-today");
	            					Element rainElement = (Element) rainList.item(0);
	            					rainList = rainElement.getChildNodes();
	            					rain[i] = ((Node) rainList.item(0)).getNodeValue();
	            					
	            					//Log.v("rosa", dw[i]);
	            					//Log.v("chmury", elew[i]);
	            					//Log.v("cisnienie", press[i]);
	            					//Log.v("descz", rain[i]);
	            					
	            				}
	            				
	            				NodeList sunrisenode = doc.getElementsByTagName("aws:sunrise");
	            				
	            				hsr = new String[sunrisenode.getLength()];
	            				msr = new String[sunrisenode.getLength()];
	            				
	            				for (int i = 0; i < sunrisenode.getLength(); i++) 
	            				{
	            				
	            					Node node2 = sunrisenode.item(i);
	
	            					Element fst2Elmnt = (Element) node2;
	            					
	            					NodeList hsrList = fst2Elmnt.getElementsByTagName("aws:hour");
	            					Element hsrElement = (Element) hsrList.item(0);
	            					hsrList = hsrElement.getChildNodes();
	            					hsr[i] = hsrElement.getAttribute("hour-24");
	            					
	            					NodeList msrList = fst2Elmnt.getElementsByTagName("aws:minute");
	            					Element msrElement = (Element) msrList.item(0);
	            					msrList = msrElement.getChildNodes();
	            					msr[i] = msrElement.getAttribute("number");
	            					
	            					//Log.v("hsr", hsr[i]);
	            					//Log.v("msr", msr[i]);
	            				}
	            				
	            				NodeList sunsetnode = doc.getElementsByTagName("aws:sunset");
	            				
	            				hss = new String[sunsetnode.getLength()];
	            				mss = new String[sunsetnode.getLength()];
	            				
	            				for (int i = 0; i < sunsetnode.getLength(); i++) 
	            				{
	            					Node node3 = sunsetnode.item(i);
	
	            					Element fst3Elmnt = (Element) node3;
	            					
	            					NodeList hssList = fst3Elmnt.getElementsByTagName("aws:hour");
	            					Element hssElement = (Element) hssList.item(0);
	            					hssList = hssElement.getChildNodes();
	            					hss[i] = hssElement.getAttribute("hour-24");
	            					
	            					NodeList mssList = fst3Elmnt.getElementsByTagName("aws:minute");
	            					Element mssElement = (Element) mssList.item(0);
	            					mssList = mssElement.getChildNodes();
	            					mss[i] = mssElement.getAttribute("number");
	            					
	            					//Log.v("hss", hss[i]);
	            					//Log.v("mss", mss[i]);
	            				}
	            				
	            			    generatecache("fullcache2.txt", dw, elew, press, rain);
	            			    generatecache("fullcache4.txt", hsr, msr, hss, mss);
	            			} 
	            			
	            			catch (Exception e) 
	            			{
	            				e.printStackTrace();
	            			}
	            			
	            			String zfkeszu[];
	            			String desc[];
	            			String desc2[];
	            			String humidity[];
	            			String wind[];
	            			String temp[];
	            			String temp2[];
	            			String winddir[];
	            			
	            			desc = new String[7];
	            			wind = new String[7];
	            			humidity = new String[7];
	            			temp = new String[7];
	            			temp2 = new String[7];
	            			winddir = new String[7];
	            			
	            			zfkeszu=readcache("fullcache.txt");
	            			Pattern p = Pattern.compile("(\\d{1,3})");
	            			Pattern p2 = Pattern.compile("(Winds\\s\\w{1,3})");
	            			
	            			//Log.v("miasto", zkeszu[0]);
	            			
	            			for(int i=0; i<7; i++)
	            			{
	            			
	            				desc2 = zfkeszu[i].split("\\.");
	            				desc[i] = desc2[0];
	            				
	            				Matcher m = p.matcher(zfkeszu[i]);
	            				int l = 0;
	            				while (m.find()) { 
	            				     String name = m.group(1); 
	            				     temp[l++] = name;
	            				     //Log.v("regex", name + " temp --> " + temp[l-1] +  " licznik--> " + l);
	            				}
	            				
	            				Log.v("temp", String.valueOf(l));
	            				
	            				if (l == 6)
	            				{
	            					wind[i] = temp[2];
	            					humidity[i]= temp[3];
	            				}
	            				else
	            				{
	            					wind[i] = temp[1];
	                				humidity[i]= temp[2];
	            				}
	            				
	            				//Log.v("wind", wind[i]);
	            				//Log.v("humidity", humidity[i]);
	            				
	            				Matcher m2 = p2.matcher(zfkeszu[i]);
	            				int k = 0;
	            				while (m2.find()) { 
	            				     String name2 = m2.group(1); 
	            				     temp[k++] = name2;
	            				    // Log.v("regex", name2 + " temp --> " + temp[k-1] +  " licznik--> " + k);
	            				}
	            				
	            				temp2 = temp[0].split("\\s");
	            				winddir[i] = temp2[1];
	            			    //Log.v("winddir", winddir[i]);
	            			               			    
	            			}
	            			 generatecache("fullcache3.txt", wind, humidity, winddir, desc);             		
	                        
	                        // Dismiss the Dialog
	                    dialog.dismiss();
	                }
	            }.start();
            
			}	
			
			else
			{
				
				dialog.dismiss();

                // Create the alert box
               AlertDialog.Builder alertbox = new AlertDialog.Builder(Fullwact.this);

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
	
	private void generatecache(String sFileName, String datafull[], String high[], String low[], String dzien[])
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
	
	public String[] readcache(String sFileName)     
	 {        
		String dane2[] = new String[30]; //= {" "," "," "," "};
		 try       
		 {        
			String root = "/data/data/com.fakelg.weatherwig/";
			File gpxfile = new File(root, sFileName);
			FileReader f = new FileReader(gpxfile);     
			BufferedReader in = new BufferedReader(f);         

			
			for (int i=0; i<=29; i++) 
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
	
	public Long ostatniupdate (String sFileName)
	{
		Long ost;
		String root = "/data/data/com.fakelg.weatherwig/";
		File gpxfile = new File(root, sFileName);
		ost = gpxfile.lastModified();
		
		return ost;
	}
	
}
