package edu.oop.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateTimeUtils {
	public static boolean isInRange(LocalDateTime dateTime, LocalDateTime from, LocalDateTime to) {
		return dateTime != null && from != null && to != null && dateTime.isAfter(from) && dateTime.isBefore(to);
	}

	public static boolean isInRange(LocalDate dateTime, LocalDate from, LocalDate to) {
		return dateTime != null && from != null && to != null && dateTime.isAfter(from) && dateTime.isBefore(to);
	}
}
