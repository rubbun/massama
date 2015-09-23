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
	public static final String BUZZ_LIST = "http://www.steelbuzz.com/sb/app/buzzsettings.php";
	public static final String REGISTER = "http://steelbuzz.com/sb/app/register.php";
	
	public static final String REGISTER1 = "http://steelbuzz.com/steelbuzz/api/api.php?apiname=register";
	
	public static final String LOGIN = "http://www.steelbuzz.com/sb/app/login.php";
	public static final String SOCIAL_LOGOUT = "http://steelbuzz.com/sb/app/social_logout.php";
	public static final String IMAGE_LINK = "http://www.steelbuzz.com/sb/buzz_image/";
	public static final String FORGOTPASSWORD_EMAIL_VERIFICATION = "http://steelbuzz.com/sb/app/forgotpassword.php";
	public static final String RESET_PASSWORD = "http://steelbuzz.com/sb/app/setpassword.php";
	
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
	
	public static String PHONE_NO = "";
	public static String EMAIL_ID = "";
	public static String TAB_NAME = "";
	
	public enum values {
		USRINFO, USERID, FIRSTNAME,LASTNAME, EMAIL,SESSION,LOGINTYPE,TRIALPERIODSTATUS,STARTTIME
	}
}
