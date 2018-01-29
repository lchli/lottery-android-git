package com.lch.lottery.filter.cores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DingDanMaFilter extends BaseFilter {

	private final List<String> danMaList=new ArrayList();

	public DingDanMaFilter(String danMa) {
		int len=danMa.length();
		for(int i=0;i<len;i++){
			danMaList.add(danMa.substring(i, i+1)); 
		}
	}

	@Override
	public void filter(List<String> input) {
		final List<String> dmList = danMaList;
		Iterator<String> iterInput = input.iterator();
		while (iterInput.hasNext()) {
			String item = iterInput.next();
			if (!isInclude(item, dmList))
				iterInput.remove();

		}
		handleToNext(input);

	}

	protected boolean isInclude(String item, List<String> dmList) {

		for (String e : dmList) {
			if (item.contains(e)) {
				if (!isUnFilter)
					return true;
				else
					return false;
			}

		}
		if (!isUnFilter)
			return false;
		else
			return true;

	}

}
