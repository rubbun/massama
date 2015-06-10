package com.steelbuzz.db;

import com.steelbuzz.constant.Constants;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TestCreate {

	public static final String TABLE_MEMBER_CREATE = "CREATE TABLE "
			+ Constants.MEMBER_TABLE + " (" + Constants.MEMBER_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.MEMBER_LIST
			+ " TEXT)";
	
	public static final String TABLE_CATAGORY_CREATE = "CREATE TABLE "
			+ Constants.CATAGORY_TABLE + " (" + Constants.CATAGORY_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.CATAGORY_LIST
			+ " TEXT)";
	
	public static final String TABLE_CATAGORY_DETAIL_CREATE = "CREATE TABLE "
			+ Constants.CATAGORY_DETAIL_TABLE + " (" + Constants.CATAGORY_DETAIL_ID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ Constants.CATAGORY_DETAIL_LIST +" TEXT)";
	
	public static void  onCreate(SQLiteDatabase db){
		db.beginTransaction();
		Log.i("Table created", "Table created");
		try{
			db.execSQL(TABLE_MEMBER_CREATE);
			db.execSQL(TABLE_CATAGORY_CREATE);
			db.execSQL(TABLE_CATAGORY_DETAIL_CREATE);
			db.setTransactionSuccessful();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			db.endTransaction();
		}
	}
	
	public static void  onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		Log.w(TestCreate.class.getName(), "Upgrade database from version "+oldVersion+" to "+newVersion+"");
		db.execSQL("DROP TABLE IF EXISTS "+Constants.MEMBER_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "+Constants.CATAGORY_TABLE);
		db.execSQL("DROP TABLE IF EXISTS "+Constants.CATAGORY_DETAIL_TABLE);
		onCreate(db);
	}
}
