package com.lch.lottery.filter.cores;

import java.util.ArrayList;
import java.util.List;


/**
 * no need to interface,because it will not be extend other impl.
 * 
 * @author lich
 * 
 */
public class FilterFacade {
	private static final String TAG = FilterFacade.class.getSimpleName();

	private List<Filter> filters =new ArrayList();

	public int getFilterSize() {

		return filters.size();
	}

	/**
	 * 
	 * @param dataSource
	 * @return the same list has been filtered.
	 */
	public List<String> runFilter(List<String> dataSource) {
		synchronized (filters) {

			if (filters.isEmpty())
				return dataSource;
			filters.get(0).filter(dataSource);
			return dataSource;
		}
	}

	public void addFilter(Filter filter) {
		synchronized (filters) {
			int size = filters.size();
			if (size != 0)
				filters.get(size - 1).setNext(filter);
			filters.add(filter);
		}
	}

	private List<String> out = null;
	private int mLevel;

	private List<String> rongCuoFilterN(List<String> dataSource, int level) {

		final int filterSize = filters.size();
		if (level == 0)
			return runFilter(dataSource);

		out = null;
		mLevel = level;

		rongCuo(level, filterSize, 0, dataSource);
		return out;

	}

	/**
	 * 
	 * @param dataSource
	 * @param levels
	 * @return a new list.
	 */
	public List<String> runRongCuoFilter(List<String> dataSource,
			List<Integer> levels) {
		synchronized (filters) {

			List<String> ret = null;

			for (int level : levels) {
				List<String> temp = rongCuoFilterN(
						new ArrayList(dataSource), level);
				if (ret == null)
					ret = temp;
				else {// union.
					ret.removeAll(temp);
					ret.addAll(temp);

				}

			}

			return ret;
		}
	}

	private void rongCuo(int level, final int filterSize, int start,
			final List<String> dataSource) {

		for (int i = start; i < filterSize; i++) {
			filters.get(i).setUnFilter(true);
			if (level > 0)
				level--;
			if (level > 0) {
				rongCuo(level, filterSize, i + 1, dataSource);
				filters.get(i).setUnFilter(false);
				level = mLevel;
			} else {
				List<String> temp = runFilter(new ArrayList(dataSource));
				filters.get(i).setUnFilter(false);
				if (out == null)
					out = temp;
				else {

					out.removeAll(temp);
					out.addAll(temp);
				}
			}

		}

	}
}
