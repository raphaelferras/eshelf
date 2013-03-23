package com.eshelf.util;

public class Common {

	
	private static Common mCommon;
	
	public static final int NO_TOKEN = 0;
	public static final int WITH_TOKEN = 1;

	
	
	public static final String CORRECTBEGIN_AUTH = "http://developers.mercadolibre.com/#access_token=";

	public static final String URL_BASE = "http://developers.mercadolibre.com/#";
	public static final String ACCESS_TOKEN_NAME = "access_token=";
	public static final String EXPIRES_IN_NAME = "expires_in=";
	public static final String DOMAINS_NAME = "domains=";
		
	//User Info
	public String accessToken = "";
	public String expiresIn = "";
	public String domainsName = "";
	
	//Services Info
	public static final String SV_BASE_URL = "https://api.mercadolibre.com/";
	
	public static final String SV_MY_INFO = "users/me";
	public static final String SV_BOOKMARKS = "users/me/bookmarks";
	public static final String SV_ITEM_DETAILS= "items";
	public static final String SV_ADD_BOOKARK = "users/me/bookmarks";

	public void clear() {
		accessToken = "";
		expiresIn = "";
		domainsName = "";
	}
	
	public static Common getInstance(){
		if( mCommon == null){
			mCommon = new Common();
		}
		return mCommon;
	}
	
}
