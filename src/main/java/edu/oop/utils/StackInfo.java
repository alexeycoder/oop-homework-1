package edu.oop.utils;

import java.util.Optional;
import java.util.stream.Collectors;

public class StackInfo {
	private final Integer fromLevel;
	private final String fromMethodName;

	protected StackInfo(String fromMethodName, Integer fromLevel) {
		if (fromMethodName == null || fromMethodName.isBlank()) {
			this.fromMethodName = null;
		} else {
			this.fromMethodName = fromMethodName;
		}

		if (fromLevel == null || fromLevel.intValue() < 0) {
			this.fromLevel = 0;
		} else {
			this.fromLevel = fromLevel;
		}
	}

	public static StackInfo of(int fromLevel) {
		return new StackInfo(null, fromLevel);
	}

	public static StackInfo of(String fromMethodName) {
		return new StackInfo(fromMethodName, 0);
	}

	public static StackInfo ofExecutingMethod() {
		Optional<String> methodNameOpt = StackWalker.getInstance()
				.walk(frames -> frames.skip(1).findFirst()
						.map(StackWalker.StackFrame::getMethodName));

		if (methodNameOpt.isPresent()) {
			return of(methodNameOpt.get());
		} else {
			return of(0);
		}

	}

	@Override
	public String toString() {
		if (fromMethodName != null) {
			var str = StackWalker.getInstance()
					.walk(
							s -> s.dropWhile(frame -> !fromMethodName.equals(frame.getMethodName()))
									.map(Object::toString)
									.collect(Collectors.joining(", ", "[ ", " ]")));
			return str;
		} else {
			var str = StackWalker.getInstance()
					.walk(
							s -> s.skip(fromLevel.longValue())
									.map(Object::toString)
									.collect(Collectors.joining(", ", "[ ", " ]")));
			return str;
		}

	}
}
