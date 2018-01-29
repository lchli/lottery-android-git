package com.lch.lottery.filter.cores;

import java.util.List;


public class DingKuaduFilter extends DingDanMaFilter {

	public DingKuaduFilter(String danMa) { 
		super(danMa);
	}


	protected boolean isInclude(String item, List<String> dmList) {

		if(dmList.contains(getItemKuadu(item))){
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
	
	private static String getItemKuadu(String item){
		
		int len=item.length();
		int max=-1;
		int min=Integer.MAX_VALUE;
		for(int i=0;i<len;i++){
			int temp=Integer.parseInt(item.substring(i, i+1)); 
			if(temp>max)
				max=temp;
			if(temp<min)
				min=temp;
			
		}
		int ret=max-min;
		ret%=10;
		return String.valueOf(ret); 
	}
	
	
	

}
