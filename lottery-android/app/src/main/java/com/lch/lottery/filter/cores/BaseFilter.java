package com.lch.lottery.filter.cores;

import java.util.List;


abstract class BaseFilter implements Filter{
	private Filter mNextFilter;
	protected boolean isUnFilter=false;
	
	@Override
	public void setNext(Filter filter) {
		mNextFilter=filter;
	}

	@Override
	public void handleToNext(List<String> input) {
		if(mNextFilter!=null)
			mNextFilter.filter(input);
	}

	@Override
	public void setUnFilter(boolean b) {
		isUnFilter=b;
	}
	
}
