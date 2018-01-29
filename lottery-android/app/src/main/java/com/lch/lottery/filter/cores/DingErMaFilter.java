package com.lch.lottery.filter.cores;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



public class DingErMaFilter extends BaseFilter {
	private final String condition;

	public DingErMaFilter(String condition) {

		this.condition = condition;
	}

	@Override
	public void filter(List<String> input) {
		// TODO Auto-generated method stub
		Iterator<String> iterInput = input.iterator();
		while (iterInput.hasNext()) {
			String item = iterInput.next();
			if (!isInclude(item, condition))
				iterInput.remove();

		}
		handleToNext(input);
	}

	private ArrayList<String> itemList = new ArrayList();

	protected boolean isInclude(String item, String condition) {
		int len = item.length();
		itemList.clear();
		for (int i = 0; i < len; i++) {
			itemList.add(item.substring(i, i + 1));
		}

		for (int i = 0; i < len; i++) {
			for (int j = i + 1; j < len; j++) {
				if (condition.contains(String.format("%s%s", itemList.get(i),
						itemList.get(j)))) {
					if (!isUnFilter)
						return true;
					else
						return false;
				}

			}

		}

		if (!isUnFilter)
			return false;
		else
			return true;

	}

}
