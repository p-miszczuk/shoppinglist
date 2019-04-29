package com.example.wlasnalista;

import java.util.ArrayList;

import java.util.EmptyStackException;
import java.util.List;

import android.app.Activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;

import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	
	 ArrayList<String> lista;
	 public ListView listView1;
	 public TextView tV;
	 RowAdapter adapter;
	 private EditText editText = null;
	 public Button delete, add;
	 public int licznik=0;
	 
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	        tV = (TextView)findViewById(R.id.txtTitle);
	        lista = new ArrayList<String>();
	        delete = (Button) findViewById(R.id.deleteall);
	        add = (Button) findViewById(R.id.add);
	        readBase();
	        listView1 = (ListView)findViewById(R.id.Lista);
	        adapter = new RowAdapter(this,R.layout.lista, lista);
			adapter.setCustomButtonListner(MainActivity.this);
			listView1.setAdapter(adapter);
		    
	    }
	    
	    
	 public void readBase(){
		 
		 BazaSql dataBase = new BazaSql(MainActivity.this);
		 int amountOfColumns = dataBase.amountOfColumns();
		 
		 if(amountOfColumns!=0){
	     lista = dataBase.wyswietlWartosc();
	     setAmountOfRepeat(amountOfColumns);

	     }

	 }
	 
	 public void saveBase(){
		 BazaSql dataBase = new BazaSql(MainActivity.this);
		 
		 
		 int rowAmount = lista.size(); 								//rozmiar(ilosc pol) listy arrayList	
	     int a = dataBase.amountOfColumns();						// dlugosc (ilosc pol) bazy danych
		 if(rowAmount>0){ 											//jesli lista istnieje zapisujemy dane
			 if(a==0){												// jezeli baza nie istnieje dodajemy npwe rekordy
				 for(int i=0; i<rowAmount; i++){
					dataBase.dodajElementListy(i+1,pobierzElementListy(i)); 
				 }
			 }
			 if(a>0 && a==rowAmount){
				 for(int i=0; i<rowAmount; i++){
				 dataBase.aktualizujBaze(i+1, pobierzElementListy(i));
				 }
			 }
			 if(a>0 && a<rowAmount){
				 for(int i=0; i<a; i++){
					  dataBase.aktualizujBaze(i+1, pobierzElementListy(i));
				 }
				 for(int i=a; i<rowAmount; i++){
					 dataBase.dodajElementListy(i+1,pobierzElementListy(i));
				 }
			 }
			 if(a>0 && a>rowAmount){
				 
				 for(int i=0; i<rowAmount; i++){
					  dataBase.aktualizujBaze(i+1, pobierzElementListy(i));
				 }
				 for(int i=rowAmount; i<a; i++){
					 dataBase.deleteRows(i+1);
				 }
				
			 }
		 
			 
		 }												//zakonczenie pierwszgo warunku - jesli lista posiada elementy wykonuj dalsze instrukcje
		 else{
			 if(rowAmount==0 && a>0){
				 for(int i=rowAmount; i<a; i++){
					 dataBase.deleteRows(i+1);
				 }
			 }
		 }
	 }
	 
	 													// pobranie elementu z listy arrayList i zwrocenie go w postaci tekstowej
	private String pobierzElementListy(int i){
		return lista.get(i).toString();
	}
	
	
	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		try{
		saveBase();
		super.onStop();
		}
		catch(ExceptionInInitializerError e){}
	}
	   
	    
	public void wprowadzDane(View v){
		   try{
			   String p ="";   
			   editText = (EditText) findViewById(R.id.editText1);
			   String pobierzText = editText.getText().toString();
				   if(!pobierzText.equals(p))
				   {
				   lista.add(0,pobierzText);
				   Toast.makeText(getApplicationContext(), "Added: "+pobierzText, 3000).show();
				   adapter.notifyDataSetChanged();
		           addAmountOfRepeat();
		           resetEditText();
				   }
			   
			   else{}
		   	}
		   catch(EmptyStackException e){}
	   }
	   
	public void usunWszystko(View w){
		   delete.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
					
				   lista.removeAll(lista);
				   adapter.notifyDataSetChanged();
				   resetAmountOfRepeat();
				return false;
			}
		});
		   
	   }
	   
	   public void setAmountOfRepeat(int a){
		   licznik =a;
	   }
	   
	   public void dockAmountOfRepeat(){
		   licznik--;
	   }
	   
	   public void addAmountOfRepeat(){
		  licznik++;
	   }
	   
	   public void resetAmountOfRepeat(){
		   licznik=0;
	   }
	   
	   public int getAmountOfRepeat(){
		   return licznik;
	   }
	   
	   public void resetEditText(){
		   editText.setText("");
	   }
	
}



