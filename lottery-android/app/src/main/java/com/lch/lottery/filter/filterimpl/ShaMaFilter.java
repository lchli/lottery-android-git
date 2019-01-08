package com.lch.lottery.filter.filterimpl;

import java.util.List;

public class ShaMaFilter extends DingDanMaFilter {

	public ShaMaFilter(String danMa) {
		super(danMa);
	}

	@Override
	protected boolean isInclude(String item, List<String> dmList) {
		for (String e : dmList) {
			if (item.contains(e))
				if (!isUnFilter)
					return false;
				else
					return true;

		}
		if (!isUnFilter)
			return true;
		else
			return false;
	}

}
