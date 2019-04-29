package com.example.wlasnalista;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;

public class BazaSql extends SQLiteOpenHelper{

	private final static String dataBaseName = "lista102";
	private final static int dataBaseVersion = 1;
	private final static String nameTableBase = "zakupy";
	
	
	
	public BazaSql(Context context) {
		super(context, dataBaseName, null, dataBaseVersion);
		// TODO Auto-generated constructor stub
		
	}
	@Override
	public void onCreate(SQLiteDatabase dataBase){
		dataBase.execSQL("create table "+nameTableBase+"(" + 
										"nr integer," + 
										"dane text);"
										+"");
	}
	
	public void onUpgrade(SQLiteDatabase db, int newVersion, int oldVersion){}
	
	public void deleteRows(int id){
		SQLiteDatabase db = getWritableDatabase();
		String[] args = {""+id};
		db.delete(nameTableBase, "nr=?", args);
		db.releaseReference();
	}
	
	public void dodajElementListy(int nr, String dane){
		SQLiteDatabase db = getWritableDatabase();
		ContentValues wprowadzWartosc = new ContentValues();
		wprowadzWartosc.put("nr", nr);
		wprowadzWartosc.put("dane", dane);
		db.insertOrThrow(nameTableBase,null, wprowadzWartosc);
	}
	
	public int amountOfColumns(){
		SQLiteDatabase db = getReadableDatabase();
		//String[] columns = {"nr","dane"};
		//Cursor cursor = db.query(nameTableBase, columns, null, null, null, null, null);
		String countQuery = "SELECT * FROM "+nameTableBase;
		Cursor count = db.rawQuery(countQuery, null);
		return count.getCount();		
	}
	
	public void aktualizujBaze(int nr, String dane){
    	SQLiteDatabase db = getWritableDatabase();
    	ContentValues cV = new ContentValues();
    	cV.put("nr", nr);
    	cV.put("dane", dane);
    	String args[] = {nr+""};
    	db.update(nameTableBase, cV, "nr=?", args);
    }
	
	public ArrayList<String> wyswietlWartosc(){
		ArrayList<String> lista = new ArrayList<String>();
		String[] columns ={"nr","dane"};
		SQLiteDatabase db = getReadableDatabase();
		Cursor kursor = db.query(nameTableBase, columns, null, null, null, null, null);
		 while(kursor.moveToNext()){
			 	String daneListy = kursor.getString(1);
			 	
			 	
	        	lista.add(daneListy);
			 
	        
		 }
		 
		return lista;
	}

}
