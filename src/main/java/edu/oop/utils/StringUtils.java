package edu.oop.utils;

public class StringUtils {
	public static String getRegexContainsAll(String words) {
		if (words == null || words.isEmpty()) {
			return "a^";
		}
		String[] substrings = words.strip().split("\\s+");
		return getRegexContainsAll(substrings);
	}

	public static String getRegexContainsAll(String[] substrings) {
		if (substrings == null || substrings.length == 0) {
			return "a^";
		}
		StringBuilder sb = new StringBuilder("(?iu)^");
		for (String str : substrings) {
			sb.append("(?=.*?").append(str).append(")");
		}
		sb.append(".*");
		return sb.toString();
	}
}
