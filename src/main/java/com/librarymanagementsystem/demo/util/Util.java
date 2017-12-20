package com.librarymanagementsystem.demo.util;

public class Util {
	/**
	 * Utility to find whether the level is LOCAL or not.
	 * 
	 * @param level - The level.
	 * @return - true, if level is "LOCAL"; false, otherwise
	 */
	public static boolean isLocalLevel(String level) {
		if (level != null && level.length() > 0 && level.equals(Constants.LEVEL_LOCAL)) {
			return true;
		} else {
			return false;
		}
	}
}
