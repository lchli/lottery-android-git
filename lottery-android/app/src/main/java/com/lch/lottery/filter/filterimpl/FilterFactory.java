package com.lch.lottery.filter.filterimpl;



public class FilterFactory {
	
	
	public static Filter newFilter(FilterType type,String conditions){ 
	
		switch(type){
		case FILTER_DING_DANMA:
			return new DingDanMaFilter(conditions); 
		case FILTER_SHA_MA:
			return new ShaMaFilter(conditions); 
		case FILTER_DING_HEWEI:
			return new DingHeWeiFilter(conditions);
		case FILTER_DING_ERMA:
			return new DingErMaFilter(conditions);
		case FILTER_DING_KUADU:
			return new DingKuaduFilter(conditions);
		default:
			return null;
		
		}
		
		
	}
	
	public  enum FilterType{
		FILTER_DING_DANMA,FILTER_SHA_MA,FILTER_DING_HEWEI,FILTER_DING_ERMA,FILTER_DING_KUADU;
		
	}
}
