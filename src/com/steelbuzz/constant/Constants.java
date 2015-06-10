package com.steelbuzz.constant;

import java.util.ArrayList;

import com.steelbuzz.bean.Member;

public class Constants {

	public static int tab_host_no = 0; 
	public static final String BASE_URL = "http://sbecpl.com/";
	public static final String ALL_MEMBER_LIST = BASE_URL+"isteel/index.php/category/findMemberList";
	public static final String CATEGORY_LIST = BASE_URL+"isteel/index.php/category/findcategories";
	public static final String CATEGORY_MEMBER_LIST = BASE_URL+"isteel/index.php/category/categorydetails";
	public static final String CATEGORY_MEMBER_DETAIL_LIST = BASE_URL+"isteel/index.php/category/findAllCategories";
	
	public static ArrayList<Member> memberArr = new ArrayList<Member>();
	
	public static final String DATABASE_NAME = "masama.db";
	public static final int DATABASE_VERSION  = 1;
	
	public static final String MEMBER_TABLE = "MEMBER";
	public static final String MEMBER_ID = "ID";
	public static final String MEMBER_LIST = "MEMBER_LIST";
	
	public static final String CATAGORY_TABLE = "CATAGORY";
	public static final String CATAGORY_ID = "ID";
	public static final String CATAGORY_LIST = "CATAGORY_LIST";
	
	public static final String CATAGORY_DETAIL_TABLE = "CATAGORY_DETAIL";
	public static final String CATAGORY_DETAIL_ID = "ID";
	public static final String CATAGORY_DETAIL_LIST = "CATAGORY_DETAIL_LIST";
	
}
