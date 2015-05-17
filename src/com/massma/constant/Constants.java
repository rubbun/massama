package com.massma.constant;

import java.util.ArrayList;

import com.massma.bean.Member;

public class Constants {

	public static int tab_host_no = 0; 
	public static final String BASE_URL = "http://devlpconsole.com/";
	public static final String CATEGORY_LIST = BASE_URL+"isteel/index.php/category/findcategories";
	public static final String CATEGORY_MEMBER_LIST = BASE_URL+"isteel/index.php/category/categorydetails";
	public static ArrayList<Member> memberArr = new ArrayList<Member>();
	public static ArrayList<Member> tempArr = new ArrayList<Member>();

}
