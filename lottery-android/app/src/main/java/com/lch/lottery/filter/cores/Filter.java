package com.lch.lottery.filter.cores;

import java.util.List;

public interface Filter {

	void filter(List<String> input);
	
	void setNext(Filter filter);
	
	void handleToNext(List<String> input);
	
	void setUnFilter(boolean b);
}
