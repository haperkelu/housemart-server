/**
 * Created on 2013-4-22
 * 
 */
package org.housemart.server.util;

public class PageUtils {
	private static int DEFAULT_PAGE_SIZE = 30;

	public static int generateSkipNumber(int pageIndex, int pageSize) {
		if (pageIndex <= 0)
			pageIndex = 1;
		if (pageSize <= 0)
			pageSize = DEFAULT_PAGE_SIZE;
		return (pageIndex - 1) * pageSize;
	}

	public static int generateSize(int total, int skip, int pageSize) {
		if (pageSize <= 0)
			pageSize = DEFAULT_PAGE_SIZE;
		if (skip >= total)
			return 0;

		return ((total - skip) < pageSize) ? (total - skip) : pageSize;
	}

}
