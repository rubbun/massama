package com.steelbuzz.db;

import com.steelbuzz.constant.Constants;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestHelper extends SQLiteOpenHelper{

	public TestHelper(Context context) {
		super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		TestCreate.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		TestCreate.onUpgrade(db, oldVersion, newVersion);	
	}
}
