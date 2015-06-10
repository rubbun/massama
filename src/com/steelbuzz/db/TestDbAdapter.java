package com.steelbuzz.db;

import org.json.JSONArray;
import org.json.JSONException;

import com.steelbuzz.constant.Constants;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class TestDbAdapter {
	
	public static Context sContext;
	public static SQLiteDatabase sDb;
	public static TestHelper mHelper;
	public static TestDbAdapter sInstance;
	
	private TestDbAdapter(Context sContext) {
		TestDbAdapter.sContext = sContext;
	}
	
	public static TestDbAdapter createInstance(Context sContext){
		if(sInstance == null){
			sInstance = new TestDbAdapter(sContext);
			open();
		}
		return sInstance;
	}

	private static void open() {
		mHelper  = new TestHelper(sContext);
		sDb = mHelper.getWritableDatabase();
		
	}
	private  void close() {
		mHelper.close();
	}
	
	public long insertMemberListValue(String name){
	  ContentValues values = new ContentValues();
	  values.put(Constants.MEMBER_LIST, name);
	  try{
		  sDb.beginTransaction();
		  final long state   = sDb.insert(Constants.MEMBER_TABLE, null, values);
		  sDb.setTransactionSuccessful();
		  return state;
	  }catch(SQLException e){
		  throw e;
	  }finally{
		  sDb.endTransaction();
	  }
	}
	
	public long insertCatagoryListValue(String name){
		  ContentValues values = new ContentValues();
		  values.put(Constants.CATAGORY_LIST, name);
		  try{
			  sDb.beginTransaction();
			  final long state   = sDb.insert(Constants.CATAGORY_TABLE, null, values);
			  sDb.setTransactionSuccessful();
			  return state;
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  sDb.endTransaction();
		  }
		}
	
	public long insertCatagoryDetailListValue(String name){
		  ContentValues values = new ContentValues();
		  values.put(Constants.CATAGORY_DETAIL_LIST, name);
		  try{
			  sDb.beginTransaction();
			  final long state   = sDb.insert(Constants.CATAGORY_DETAIL_TABLE, null, values);
			  sDb.setTransactionSuccessful();
			  return state;
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  sDb.endTransaction();
		  }
		}
	
	public JSONArray fetchMemberListValue(){
		JSONArray arr = new JSONArray();
		Cursor cursor = sDb.rawQuery("select * from " +Constants.MEMBER_TABLE, null);		
		if(cursor.getCount()>0){
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				String list = cursor.getString(1);
				try {
					arr = new JSONArray(list);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				cursor.moveToNext();
			}
		}
		cursor.close();
		return arr;
	}
	
	public JSONArray fetchCatagoryList(){
		JSONArray arr = new JSONArray();
		Cursor cursor = sDb.rawQuery("select * from " +Constants.CATAGORY_TABLE , null);		
		if(cursor.getCount()>0){
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				String list = cursor.getString(1);
				try {
					arr = new JSONArray(list);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				cursor.moveToNext();
			}
		}
		cursor.close();
		return arr;
	}
	
	public JSONArray fetchCatagoryDetailsList(){
		JSONArray arr = new JSONArray();
		Cursor cursor = sDb.rawQuery("select * from " +Constants.CATAGORY_DETAIL_TABLE , null);		
		if(cursor.getCount()>0){
			cursor.moveToFirst();
			while(!cursor.isAfterLast()){
				String list = cursor.getString(1);
				try {
					arr = new JSONArray(list);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				cursor.moveToNext();
			}
		}
		cursor.close();
		return arr;
	}
}
