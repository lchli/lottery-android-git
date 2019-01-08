package com.lch.lottery.filter.filterimpl;

import java.util.List;




public class DingHeWeiFilter extends DingDanMaFilter {

	public DingHeWeiFilter(String danMa) { 
		super(danMa);
	}


	protected boolean isInclude(String item, List<String> dmList) {

		if(dmList.contains(getItemHeWei(item))){
			if (!isUnFilter)
				return true;
			else
				return false;
		}
		
		
		if (!isUnFilter)
			return false;
		else
			return true;

	}
	
	private static String getItemHeWei(String item){
		int ret=0;
		int len=item.length();
		for(int i=0;i<len;i++){
			ret+=Integer.parseInt(item.substring(i, i+1)); 
			
		}
		ret%=10;
		return String.valueOf(ret); 
	}
	
	
	

}
