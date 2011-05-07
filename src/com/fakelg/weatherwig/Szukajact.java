package com.fakelg.weatherwig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
//import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Szukajact extends ListActivity 
{

	public class MyCustomAdapter extends ArrayAdapter<String> {

		public MyCustomAdapter(Context context, int textViewResourceId,
				String[] objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			//return super.getView(position, convertView, parent);
			LayoutInflater inflater=getLayoutInflater();
			View row=inflater.inflate(R.layout.row, parent, false);
			TextView label=(TextView)row.findViewById(R.id.wynik);
			label.setText(wynik[position]);
			ImageView icon=(ImageView)row.findViewById(R.id.icon);
			
			icon.setImageResource(R.drawable.szukaj);
			//icon.setImageDrawable(Drawable.createFromPath(new File(Environment.getExternalStorageDirectory(), "test3.png").getAbsolutePath()));

			return row;
		}
	}
	
	String[] wynik; //= {"warszawa","sztokholm"};
	String[] code; //= {"123","345"};
	String[] kraj; //= {"Poland","Sweden"};
	
	public String[] readcache(String sFileName)     
	 {        
		String dane2[] = new String[30]; //= {" "," "," "," "};
		 try       
		 {        
			String root = "/data/data/com.fakelg.weatherwig/";
			File szfile = new File(root, sFileName);
			FileReader fr = new FileReader(szfile);     
			BufferedReader input = new BufferedReader(fr);         

			
			for (int i=0; i<=20; i++)
			{
				
			   dane2[i] = input.readLine();    
			   //Log.v("readfile", dane2[i]);
			   
			}
			
		    input.close();  
		   
		  }
		     catch(Exception ex)     
		  {           
		    	 ex.printStackTrace();
		  }  
		     
	   return dane2;
		     
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) 
	{
		
		String selection = l.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),"Wybrano: " + selection + ", " +String.valueOf(code[position]) + "\nNacisnij teraz wstecz",Toast.LENGTH_LONG).show();
		generatecache("szukaj.txt", code[position]);
		
	}
	
	private void generatecache(String sFileName, String dane)
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        wynik = readcache("szukajwynik.txt");
        code = readcache("szukajcode.txt");
        kraj = readcache("szukajkraj.txt");
        
        List<String> stringList = new ArrayList<String>();
        List<String> stringList2 = new ArrayList<String>();
        List<String> stringList3 = new ArrayList<String>();
        
        for(String string : wynik) {
           if(string != null && string.length() > 0) {
              stringList.add(string);
           }
        }

        for(String string : code) {
            if(string != null && string.length() > 0) {
               stringList2.add(string);
            }
         }
        
        for(String string : kraj) {
            if(string != null && string.length() > 0) {
               stringList3.add(string);
            }
         }
        
        wynik = stringList.toArray(new String[stringList.size()]);
        code = stringList2.toArray(new String[stringList.size()]);
        kraj = stringList3.toArray(new String[stringList.size()]);

        
        for (int f=0; f<wynik.length;f++)
        {
        	wynik[f] = wynik[f] + ", " + kraj[f];
        }
          
        setListAdapter(new MyCustomAdapter(Szukajact.this, R.layout.row, wynik));
        
	}
	
}
