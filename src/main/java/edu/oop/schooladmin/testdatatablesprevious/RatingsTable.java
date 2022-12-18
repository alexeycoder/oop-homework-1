package edu.oop.schooladmin.testdatatablesprevious;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
// import java.util.Arrays;

import edu.oop.schooladmin.model.entities.Rating;

/*
 * LocalDateTime.of(2021,9,15, 0,0).toString()  <---> LocalDateTime.parse()
 */

public class RatingsTable {
	// public static void main(String[] args) {
	// 	System.out.println(ratings());
	// }

	/**
	 * Метод считывания рейтинга из файла
	 */
	public static ArrayList<Rating> ratings() {
		String ratingId, studentId, disciplineId, dateTime, value, commentary;
		ArrayList<Rating> ratings = new ArrayList<>();
		try {
			File file = new File("src/main/java/edu/oop/schooladmin/testdatatables/RatingsTable.txt");
			FileReader fr = new FileReader(file); // создаем объект FileReader для объекта File
			BufferedReader reader = new BufferedReader(fr); // создаем BufferedReader с существующего FileReader для
															// построчного считывания
			String line = reader.readLine(); // считаем сначала первую строку

			while (line != null) {
				ratingId = line.split(" ; ")[0];
				studentId = line.split(" ; ")[1];
				disciplineId = line.split(" ; ")[2];
				dateTime = line.split(" ; ")[3];
				value = line.split(" ; ")[4];
				commentary = line.split(" ; ")[5];
				ratings.add(new Rating(Integer.parseInt(ratingId), Integer.parseInt(studentId),
						Integer.parseInt(disciplineId), LocalDateTime.parse(dateTime),
						Integer.parseInt(value), commentary));

				line = reader.readLine(); // считываем остальные строки в цикле
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ratings;
	}
}

// private static final ArrayList<Rating> ratings = new ArrayList<>(
// Arrays.asList(
// new Rating(1, 7, 2, LocalDateTime.of(2021, 9, 15, 0, 0), 4, ""),
// new Rating(2, 13, 1, LocalDateTime.of(2021, 9, 16, 0, 0), 4, ""),
// new Rating(3, 2, 4, LocalDateTime.of(2021, 9, 15, 0, 0), 5, ""),
// new Rating(4, 19, 7, LocalDateTime.of(2021, 9, 18, 0, 0), 3, ""),
// new Rating(5, 8, 1, LocalDateTime.of(2021, 9, 19, 0, 0), 3, ""),
// new Rating(6, 21, 6, LocalDateTime.of(2021, 9, 20, 0, 0), 4, ""),
// new Rating(7, 14, 7, LocalDateTime.of(2021, 9, 21, 0, 0), 3, ""),
// new Rating(8, 24, 5, LocalDateTime.of(2021, 9, 22, 0, 0), 4, ""),
// new Rating(9, 5, 4, LocalDateTime.of(2021, 9, 23, 0, 0), 4, ""),
// new Rating(10, 9, 2, LocalDateTime.of(2021, 9, 24, 0, 0), 3, ""),
// new Rating(11, 6, 2, LocalDateTime.of(2021, 9, 25, 0, 0), 4, ""),
// new Rating(12, 4, 1, LocalDateTime.of(2021, 9, 26, 0, 0), 4, ""),
// new Rating(13, 20, 2, LocalDateTime.of(2021, 9, 27, 0, 0), 3, ""),
// new Rating(14, 15, 3, LocalDateTime.of(2021, 9, 28, 0, 0), 3, ""),
// new Rating(15, 2, 4, LocalDateTime.of(2021, 9, 29, 0, 0), 5, ""),
// new Rating(16, 4, 4, LocalDateTime.of(2021, 9, 30, 0, 0), 3, ""),
// new Rating(17, 24, 5, LocalDateTime.of(2021, 10, 1, 0, 0), 5, ""),
// new Rating(18, 4, 1, LocalDateTime.of(2021, 10, 2, 0, 0), 4, ""),
// new Rating(19, 2, 1, LocalDateTime.of(2021, 10, 3, 0, 0), 4, ""),
// new Rating(20, 21, 5, LocalDateTime.of(2021, 10, 4, 0, 0), 5, ""),
// new Rating(21, 4, 2, LocalDateTime.of(2021, 10, 5, 0, 0), 5, ""),
// new Rating(22, 3, 4, LocalDateTime.of(2021, 10, 6, 0, 0), 3, ""),
// new Rating(23, 7, 1, LocalDateTime.of(2021, 10, 7, 0, 0), 4, ""),
// new Rating(24, 3, 5, LocalDateTime.of(2021, 10, 8, 0, 0), 4, ""),
// new Rating(25, 24, 7, LocalDateTime.of(2021, 10, 9, 0, 0), 3, ""),
// new Rating(26, 25, 5, LocalDateTime.of(2021, 10, 10, 0, 0), 5, ""),
// new Rating(27, 24, 4, LocalDateTime.of(2021, 10, 11, 0, 0), 5, ""),
// new Rating(28, 4, 1, LocalDateTime.of(2021, 10, 12, 0, 0), 4, ""),
// new Rating(29, 19, 5, LocalDateTime.of(2021, 10, 13, 0, 0), 5, ""),
// new Rating(30, 18, 7, LocalDateTime.of(2021, 10, 14, 0, 0), 4, ""),
// new Rating(31, 6, 1, LocalDateTime.of(2021, 10, 15, 0, 0), 3, ""),
// new Rating(32, 13, 5, LocalDateTime.of(2021, 10, 16, 0, 0), 5, ""),
// new Rating(33, 6, 5, LocalDateTime.of(2021, 10, 17, 0, 0), 3, ""),
// new Rating(34, 8, 4, LocalDateTime.of(2021, 10, 18, 0, 0), 5, ""),
// new Rating(35, 14, 7, LocalDateTime.of(2021, 10, 19, 0, 0), 4, ""),
// new Rating(36, 9, 1, LocalDateTime.of(2021, 10, 20, 0, 0), 4, ""),
// new Rating(37, 1, 3, LocalDateTime.of(2021, 10, 21, 0, 0), 3, ""),
// new Rating(38, 20, 4, LocalDateTime.of(2021, 10, 22, 0, 0), 4, ""),
// new Rating(39, 4, 6, LocalDateTime.of(2021, 10, 23, 0, 0), 3, ""),
// new Rating(40, 19, 5, LocalDateTime.of(2021, 10, 24, 0, 0), 5, ""),
// new Rating(41, 1, 4, LocalDateTime.of(2021, 10, 25, 0, 0), 5, ""),
// new Rating(42, 19, 3, LocalDateTime.of(2021, 10, 26, 0, 0), 4, ""),
// new Rating(43, 22, 6, LocalDateTime.of(2021, 10, 27, 0, 0), 5, ""),
// new Rating(44, 23, 1, LocalDateTime.of(2021, 10, 28, 0, 0), 5, ""),
// new Rating(45, 17, 3, LocalDateTime.of(2021, 10, 29, 0, 0), 5, ""),
// new Rating(46, 3, 6, LocalDateTime.of(2021, 10, 30, 0, 0), 3, ""),
// new Rating(47, 6, 7, LocalDateTime.of(2021, 10, 31, 0, 0), 5, ""),
// new Rating(48, 2, 4, LocalDateTime.of(2021, 11, 1, 0, 0), 3, ""),
// new Rating(49, 6, 4, LocalDateTime.of(2021, 11, 2, 0, 0), 5, ""),
// new Rating(50, 6, 2, LocalDateTime.of(2021, 11, 3, 0, 0), 4, ""),
// new Rating(51, 8, 3, LocalDateTime.of(2021, 11, 4, 0, 0), 5, ""),
// new Rating(52, 11, 5, LocalDateTime.of(2021, 11, 5, 0, 0), 5, ""),
// new Rating(53, 7, 4, LocalDateTime.of(2021, 11, 6, 0, 0), 5, ""),
// new Rating(54, 14, 7, LocalDateTime.of(2021, 11, 7, 0, 0), 5, ""),
// new Rating(55, 3, 2, LocalDateTime.of(2021, 11, 8, 0, 0), 5, ""),
// new Rating(56, 8, 5, LocalDateTime.of(2021, 11, 9, 0, 0), 4, ""),
// new Rating(57, 17, 7, LocalDateTime.of(2021, 11, 10, 0, 0), 4, ""),
// new Rating(58, 17, 6, LocalDateTime.of(2021, 11, 11, 0, 0), 3, ""),
// new Rating(59, 22, 3, LocalDateTime.of(2021, 11, 12, 0, 0), 3, ""),
// new Rating(60, 11, 3, LocalDateTime.of(2021, 11, 13, 0, 0), 3, ""),
// new Rating(61, 11, 3, LocalDateTime.of(2021, 11, 14, 0, 0), 3, ""),
// new Rating(62, 24, 3, LocalDateTime.of(2021, 11, 15, 0, 0), 4, ""),
// new Rating(63, 12, 6, LocalDateTime.of(2021, 11, 16, 0, 0), 3, ""),
// new Rating(64, 19, 6, LocalDateTime.of(2021, 11, 17, 0, 0), 5, ""),
// new Rating(65, 5, 4, LocalDateTime.of(2021, 11, 18, 0, 0), 5, ""),
// new Rating(66, 13, 7, LocalDateTime.of(2021, 11, 19, 0, 0), 5, ""),
// new Rating(67, 23, 4, LocalDateTime.of(2021, 11, 20, 0, 0), 5, ""),
// new Rating(68, 22, 7, LocalDateTime.of(2021, 11, 21, 0, 0), 5, ""),
// new Rating(69, 22, 4, LocalDateTime.of(2021, 11, 22, 0, 0), 4, ""),
// new Rating(70, 15, 2, LocalDateTime.of(2021, 11, 23, 0, 0), 3, ""),
// new Rating(71, 10, 7, LocalDateTime.of(2021, 11, 24, 0, 0), 4, ""),
// new Rating(72, 23, 7, LocalDateTime.of(2021, 11, 25, 0, 0), 4, ""),
// new Rating(73, 2, 1, LocalDateTime.of(2021, 11, 26, 0, 0), 3, ""),
// new Rating(74, 12, 4, LocalDateTime.of(2021, 11, 27, 0, 0), 3, ""),
// new Rating(75, 17, 2, LocalDateTime.of(2021, 11, 28, 0, 0), 4, ""),
// new Rating(76, 23, 6, LocalDateTime.of(2021, 11, 29, 0, 0), 4, ""),
// new Rating(77, 25, 7, LocalDateTime.of(2021, 11, 30, 0, 0), 4, ""),
// new Rating(78, 17, 1, LocalDateTime.of(2021, 12, 1, 0, 0), 5, ""),
// new Rating(79, 4, 3, LocalDateTime.of(2021, 12, 2, 0, 0), 4, ""),
// new Rating(80, 19, 7, LocalDateTime.of(2021, 12, 3, 0, 0), 3, ""),
// new Rating(81, 20, 5, LocalDateTime.of(2021, 12, 4, 0, 0), 3, ""),
// new Rating(82, 24, 5, LocalDateTime.of(2021, 12, 5, 0, 0), 3, ""),
// new Rating(83, 7, 6, LocalDateTime.of(2021, 12, 6, 0, 0), 5, ""),
// new Rating(84, 3, 2, LocalDateTime.of(2021, 12, 7, 0, 0), 5, ""),
// new Rating(85, 24, 4, LocalDateTime.of(2021, 12, 8, 0, 0), 5, ""),
// new Rating(86, 9, 1, LocalDateTime.of(2021, 12, 9, 0, 0), 4, ""),
// new Rating(87, 14, 7, LocalDateTime.of(2021, 12, 10, 0, 0), 3, ""),
// new Rating(88, 14, 4, LocalDateTime.of(2021, 12, 11, 0, 0), 5, ""),
// new Rating(89, 20, 6, LocalDateTime.of(2021, 12, 12, 0, 0), 4, ""),
// new Rating(90, 11, 3, LocalDateTime.of(2021, 12, 13, 0, 0), 3, ""),
// new Rating(91, 20, 7, LocalDateTime.of(2021, 12, 14, 0, 0), 3, ""),
// new Rating(92, 8, 6, LocalDateTime.of(2021, 12, 15, 0, 0), 4, ""),
// new Rating(93, 23, 2, LocalDateTime.of(2021, 12, 16, 0, 0), 3, ""),
// new Rating(94, 14, 1, LocalDateTime.of(2021, 12, 17, 0, 0), 5, ""),
// new Rating(95, 17, 4, LocalDateTime.of(2021, 12, 18, 0, 0), 5, ""),
// new Rating(96, 2, 4, LocalDateTime.of(2021, 12, 19, 0, 0), 5, ""),
// new Rating(97, 21, 2, LocalDateTime.of(2021, 12, 20, 0, 0), 3, ""),
// new Rating(98, 10, 1, LocalDateTime.of(2021, 12, 21, 0, 0), 3, ""),
// new Rating(99, 23, 1, LocalDateTime.of(2021, 12, 22, 0, 0), 5, ""),
// new Rating(100, 2, 1, LocalDateTime.of(2021, 12, 23, 0, 0), 4, ""),
// new Rating(101, 22, 3, LocalDateTime.of(2021, 12, 24, 0, 0), 3, ""),
// new Rating(102, 22, 4, LocalDateTime.of(2021, 12, 25, 0, 0), 5, ""),
// new Rating(103, 3, 7, LocalDateTime.of(2021, 12, 26, 0, 0), 5, ""),
// new Rating(104, 23, 4, LocalDateTime.of(2021, 12, 27, 0, 0), 4, ""),
// new Rating(105, 22, 7, LocalDateTime.of(2021, 12, 28, 0, 0), 4, ""),
// new Rating(106, 11, 3, LocalDateTime.of(2021, 12, 29, 0, 0), 5, ""),
// new Rating(107, 4, 5, LocalDateTime.of(2021, 12, 30, 0, 0), 3, ""),
// new Rating(108, 15, 5, LocalDateTime.of(2021, 12, 31, 0, 0), 3, ""),
// new Rating(109, 15, 2, LocalDateTime.of(2022, 1, 1, 0, 0), 3, ""),
// new Rating(110, 18, 7, LocalDateTime.of(2022, 1, 2, 0, 0), 5, ""),
// new Rating(111, 3, 7, LocalDateTime.of(2022, 1, 3, 0, 0), 5, ""),
// new Rating(112, 14, 6, LocalDateTime.of(2022, 1, 4, 0, 0), 5, ""),
// new Rating(113, 23, 1, LocalDateTime.of(2022, 1, 5, 0, 0), 4, ""),
// new Rating(114, 6, 6, LocalDateTime.of(2022, 1, 6, 0, 0), 3, ""),
// new Rating(115, 6, 1, LocalDateTime.of(2022, 1, 7, 0, 0), 5, ""),
// new Rating(116, 1, 6, LocalDateTime.of(2022, 1, 8, 0, 0), 4, ""),
// new Rating(117, 22, 4, LocalDateTime.of(2022, 1, 9, 0, 0), 5, ""),
// new Rating(118, 20, 7, LocalDateTime.of(2022, 1, 10, 0, 0), 5, ""),
// new Rating(119, 5, 4, LocalDateTime.of(2022, 1, 11, 0, 0), 3, ""),
// new Rating(120, 23, 7, LocalDateTime.of(2022, 1, 12, 0, 0), 3, ""),
// new Rating(121, 1, 3, LocalDateTime.of(2022, 1, 13, 0, 0), 3, ""),
// new Rating(122, 20, 5, LocalDateTime.of(2022, 1, 14, 0, 0), 5, ""),
// new Rating(123, 5, 6, LocalDateTime.of(2022, 1, 15, 0, 0), 4, ""),
// new Rating(124, 6, 6, LocalDateTime.of(2022, 1, 16, 0, 0), 3, ""),
// new Rating(125, 20, 5, LocalDateTime.of(2022, 1, 17, 0, 0), 4, ""),
// new Rating(126, 15, 4, LocalDateTime.of(2022, 1, 18, 0, 0), 5, ""),
// new Rating(127, 25, 5, LocalDateTime.of(2022, 1, 19, 0, 0), 3, ""),
// new Rating(128, 15, 1, LocalDateTime.of(2022, 1, 20, 0, 0), 4, ""),
// new Rating(129, 5, 5, LocalDateTime.of(2022, 1, 21, 0, 0), 5, ""),
// new Rating(130, 2, 5, LocalDateTime.of(2022, 1, 22, 0, 0), 3, ""),
// new Rating(131, 4, 2, LocalDateTime.of(2022, 1, 23, 0, 0), 4, ""),
// new Rating(132, 7, 6, LocalDateTime.of(2022, 1, 24, 0, 0), 4, ""),
// new Rating(133, 13, 1, LocalDateTime.of(2022, 1, 25, 0, 0), 3, ""),
// new Rating(134, 5, 6, LocalDateTime.of(2022, 1, 26, 0, 0), 5, ""),
// new Rating(135, 24, 1, LocalDateTime.of(2022, 1, 27, 0, 0), 3, ""),
// new Rating(136, 20, 5, LocalDateTime.of(2022, 1, 28, 0, 0), 5, ""),
// new Rating(137, 10, 5, LocalDateTime.of(2022, 1, 29, 0, 0), 4, ""),
// new Rating(138, 3, 6, LocalDateTime.of(2022, 1, 30, 0, 0), 4, ""),
// new Rating(139, 22, 5, LocalDateTime.of(2022, 1, 31, 0, 0), 5, ""),
// new Rating(140, 14, 2, LocalDateTime.of(2022, 2, 1, 0, 0), 5, ""),
// new Rating(141, 22, 7, LocalDateTime.of(2022, 2, 2, 0, 0), 4, ""),
// new Rating(142, 22, 3, LocalDateTime.of(2022, 2, 3, 0, 0), 5, ""),
// new Rating(143, 2, 6, LocalDateTime.of(2022, 2, 4, 0, 0), 4, ""),
// new Rating(144, 4, 7, LocalDateTime.of(2022, 2, 5, 0, 0), 3, ""),
// new Rating(145, 23, 7, LocalDateTime.of(2022, 2, 6, 0, 0), 4, ""),
// new Rating(146, 7, 6, LocalDateTime.of(2022, 2, 7, 0, 0), 3, ""),
// new Rating(147, 1, 7, LocalDateTime.of(2022, 2, 8, 0, 0), 5, ""),
// new Rating(148, 1, 7, LocalDateTime.of(2022, 2, 9, 0, 0), 4, ""),
// new Rating(149, 2, 7, LocalDateTime.of(2022, 2, 10, 0, 0), 4, ""),
// new Rating(150, 14, 2, LocalDateTime.of(2022, 2, 11, 0, 0), 3, ""),
// new Rating(151, 2, 6, LocalDateTime.of(2022, 2, 12, 0, 0), 4, ""),
// new Rating(152, 8, 7, LocalDateTime.of(2022, 2, 13, 0, 0), 4, ""),
// new Rating(153, 23, 1, LocalDateTime.of(2022, 2, 14, 0, 0), 3, ""),
// new Rating(154, 6, 1, LocalDateTime.of(2022, 2, 15, 0, 0), 5, ""),
// new Rating(155, 23, 7, LocalDateTime.of(2022, 2, 16, 0, 0), 5, ""),
// new Rating(156, 10, 7, LocalDateTime.of(2022, 2, 17, 0, 0), 5, ""),
// new Rating(157, 9, 4, LocalDateTime.of(2022, 2, 18, 0, 0), 4, ""),
// new Rating(158, 7, 5, LocalDateTime.of(2022, 2, 19, 0, 0), 3, ""),
// new Rating(159, 11, 2, LocalDateTime.of(2022, 2, 20, 0, 0), 4, ""),
// new Rating(160, 10, 4, LocalDateTime.of(2022, 2, 21, 0, 0), 3, ""),
// new Rating(161, 14, 7, LocalDateTime.of(2022, 2, 22, 0, 0), 5, ""),
// new Rating(162, 7, 4, LocalDateTime.of(2022, 2, 23, 0, 0), 3, ""),
// new Rating(163, 14, 5, LocalDateTime.of(2022, 2, 24, 0, 0), 5, ""),
// new Rating(164, 25, 6, LocalDateTime.of(2022, 2, 25, 0, 0), 3, ""),
// new Rating(165, 18, 4, LocalDateTime.of(2022, 2, 26, 0, 0), 5, ""),
// new Rating(166, 9, 3, LocalDateTime.of(2022, 2, 27, 0, 0), 5, ""),
// new Rating(167, 10, 7, LocalDateTime.of(2022, 2, 28, 0, 0), 4, ""),
// new Rating(168, 3, 5, LocalDateTime.of(2022, 3, 1, 0, 0), 3, ""),
// new Rating(169, 8, 3, LocalDateTime.of(2022, 3, 2, 0, 0), 4, ""),
// new Rating(170, 8, 4, LocalDateTime.of(2022, 3, 3, 0, 0), 4, ""),
// new Rating(171, 2, 7, LocalDateTime.of(2022, 3, 4, 0, 0), 4, ""),
// new Rating(172, 8, 4, LocalDateTime.of(2022, 3, 5, 0, 0), 5, ""),
// new Rating(173, 9, 7, LocalDateTime.of(2022, 3, 6, 0, 0), 4, ""),
// new Rating(174, 12, 4, LocalDateTime.of(2022, 3, 7, 0, 0), 3, ""),
// new Rating(175, 9, 2, LocalDateTime.of(2022, 3, 8, 0, 0), 3, ""),
// new Rating(176, 24, 3, LocalDateTime.of(2022, 3, 9, 0, 0), 5, ""),
// new Rating(177, 15, 2, LocalDateTime.of(2022, 3, 10, 0, 0), 3, ""),
// new Rating(178, 9, 1, LocalDateTime.of(2022, 3, 11, 0, 0), 4, ""),
// new Rating(179, 7, 6, LocalDateTime.of(2022, 3, 12, 0, 0), 4, ""),
// new Rating(180, 23, 1, LocalDateTime.of(2022, 3, 13, 0, 0), 5, ""),
// new Rating(181, 9, 5, LocalDateTime.of(2022, 3, 14, 0, 0), 5, ""),
// new Rating(182, 6, 3, LocalDateTime.of(2022, 3, 15, 0, 0), 5, ""),
// new Rating(183, 4, 5, LocalDateTime.of(2022, 3, 16, 0, 0), 5, ""),
// new Rating(184, 2, 4, LocalDateTime.of(2022, 3, 17, 0, 0), 5, ""),
// new Rating(185, 23, 5, LocalDateTime.of(2022, 3, 18, 0, 0), 5, ""),
// new Rating(186, 11, 3, LocalDateTime.of(2022, 3, 19, 0, 0), 4, ""),
// new Rating(187, 17, 2, LocalDateTime.of(2022, 3, 20, 0, 0), 3, ""),
// new Rating(188, 8, 2, LocalDateTime.of(2022, 3, 21, 0, 0), 4, ""),
// new Rating(189, 22, 6, LocalDateTime.of(2022, 3, 22, 0, 0), 5, ""),
// new Rating(190, 20, 1, LocalDateTime.of(2022, 3, 23, 0, 0), 5, ""),
// new Rating(191, 1, 5, LocalDateTime.of(2022, 3, 24, 0, 0), 4, ""),
// new Rating(192, 9, 4, LocalDateTime.of(2022, 3, 25, 0, 0), 4, ""),
// new Rating(193, 23, 3, LocalDateTime.of(2022, 3, 26, 0, 0), 4, ""),
// new Rating(194, 2, 2, LocalDateTime.of(2022, 3, 27, 0, 0), 4, ""),
// new Rating(195, 14, 5, LocalDateTime.of(2022, 3, 28, 0, 0), 5, ""),
// new Rating(196, 14, 6, LocalDateTime.of(2022, 3, 29, 0, 0), 4, ""),
// new Rating(197, 4, 2, LocalDateTime.of(2022, 3, 30, 0, 0), 5, ""),
// new Rating(198, 23, 5, LocalDateTime.of(2022, 3, 31, 0, 0), 5, ""),
// new Rating(199, 6, 5, LocalDateTime.of(2022, 4, 1, 0, 0), 4, ""),
// new Rating(200, 15, 7, LocalDateTime.of(2022, 4, 2, 0, 0), 5, "")));

/*
 * КодОценки КодУченика КодПредмета ВремяПроставления Оценка Комментарий
 * 1 7 2 15.09.21 00:00 4
 * 2 13 1 16.09.21 00:00 4
 * 3 2 4 17.09.21 00:00 5
 * 4 19 7 18.09.21 00:00 3
 * 5 8 1 19.09.21 00:00 3
 * 6 21 6 20.09.21 00:00 4
 * 7 14 7 21.09.21 00:00 3
 * 8 24 5 22.09.21 00:00 4
 * 9 5 4 23.09.21 00:00 4
 * 10 9 2 24.09.21 00:00 3
 * 11 6 2 25.09.21 00:00 4
 * 12 4 1 26.09.21 00:00 4
 * 13 20 2 27.09.21 00:00 3
 * 14 15 3 28.09.21 00:00 3
 * 15 2 4 29.09.21 00:00 5
 * 16 4 4 30.09.21 00:00 3
 * 17 24 5 01.10.21 00:00 5
 * 18 4 1 02.10.21 00:00 4
 * 19 2 1 03.10.21 00:00 4
 * 20 21 5 04.10.21 00:00 5
 * 21 4 2 05.10.21 00:00 5
 * 22 3 4 06.10.21 00:00 3
 * 23 7 1 07.10.21 00:00 4
 * 24 3 5 08.10.21 00:00 4
 * 25 24 7 09.10.21 00:00 3
 * 26 25 5 10.10.21 00:00 5
 * 27 24 4 11.10.21 00:00 5
 * 28 4 1 12.10.21 00:00 4
 * 29 19 5 13.10.21 00:00 5
 * 30 18 7 14.10.21 00:00 4
 * 31 6 1 15.10.21 00:00 3
 * 32 13 5 16.10.21 00:00 5
 * 33 6 5 17.10.21 00:00 3
 * 34 8 4 18.10.21 00:00 5
 * 35 14 7 19.10.21 00:00 4
 * 36 9 1 20.10.21 00:00 4
 * 37 1 3 21.10.21 00:00 3
 * 38 20 4 22.10.21 00:00 4
 * 39 4 6 23.10.21 00:00 3
 * 40 19 5 24.10.21 00:00 5
 * 41 1 4 25.10.21 00:00 5
 * 42 19 3 26.10.21 00:00 4
 * 43 22 6 27.10.21 00:00 5
 * 44 23 1 28.10.21 00:00 5
 * 45 17 3 29.10.21 00:00 5
 * 46 3 6 30.10.21 00:00 3
 * 47 6 7 31.10.21 00:00 5
 * 48 2 4 01.11.21 00:00 3
 * 49 6 4 02.11.21 00:00 5
 * 50 6 2 03.11.21 00:00 4
 * 51 8 3 04.11.21 00:00 5
 * 52 11 5 05.11.21 00:00 5
 * 53 7 4 06.11.21 00:00 5
 * 54 14 7 07.11.21 00:00 5
 * 55 3 2 08.11.21 00:00 5
 * 56 8 5 09.11.21 00:00 4
 * 57 17 7 10.11.21 00:00 4
 * 58 17 6 11.11.21 00:00 3
 * 59 22 3 12.11.21 00:00 3
 * 60 11 3 13.11.21 00:00 3
 * 61 11 3 14.11.21 00:00 3
 * 62 24 3 15.11.21 00:00 4
 * 63 12 6 16.11.21 00:00 3
 * 64 19 6 17.11.21 00:00 5
 * 65 5 4 18.11.21 00:00 5
 * 66 13 7 19.11.21 00:00 5
 * 67 23 4 20.11.21 00:00 5
 * 68 22 7 21.11.21 00:00 5
 * 69 22 4 22.11.21 00:00 4
 * 70 15 2 23.11.21 00:00 3
 * 71 10 7 24.11.21 00:00 4
 * 72 23 7 25.11.21 00:00 4
 * 73 2 1 26.11.21 00:00 3
 * 74 12 4 27.11.21 00:00 3
 * 75 17 2 28.11.21 00:00 4
 * 76 23 6 29.11.21 00:00 4
 * 77 25 7 30.11.21 00:00 4
 * 78 17 1 01.12.21 00:00 5
 * 79 4 3 02.12.21 00:00 4
 * 80 19 7 03.12.21 00:00 3
 * 81 20 5 04.12.21 00:00 3
 * 82 24 5 05.12.21 00:00 3
 * 83 7 6 06.12.21 00:00 5
 * 84 3 2 07.12.21 00:00 5
 * 85 24 4 08.12.21 00:00 5
 * 86 9 1 09.12.21 00:00 4
 * 87 14 7 10.12.21 00:00 3
 * 88 14 4 11.12.21 00:00 5
 * 89 20 6 12.12.21 00:00 4
 * 90 11 3 13.12.21 00:00 3
 * 91 20 7 14.12.21 00:00 3
 * 92 8 6 15.12.21 00:00 4
 * 93 23 2 16.12.21 00:00 3
 * 94 14 1 17.12.21 00:00 5
 * 95 17 4 18.12.21 00:00 5
 * 96 2 4 19.12.21 00:00 5
 * 97 21 2 20.12.21 00:00 3
 * 98 10 1 21.12.21 00:00 3
 * 99 23 1 22.12.21 00:00 5
 * 100 2 1 23.12.21 00:00 4
 * 101 22 3 24.12.21 00:00 3
 * 102 22 4 25.12.21 00:00 5
 * 103 3 7 26.12.21 00:00 5
 * 104 23 4 27.12.21 00:00 4
 * 105 22 7 28.12.21 00:00 4
 * 106 11 3 29.12.21 00:00 5
 * 107 4 5 30.12.21 00:00 3
 * 108 15 5 31.12.21 00:00 3
 * 109 15 2 01.01.22 00:00 3
 * 110 18 7 02.01.22 00:00 5
 * 111 3 7 03.01.22 00:00 5
 * 112 14 6 04.01.22 00:00 5
 * 113 23 1 05.01.22 00:00 4
 * 114 6 6 06.01.22 00:00 3
 * 115 6 1 07.01.22 00:00 5
 * 116 1 6 08.01.22 00:00 4
 * 117 22 4 09.01.22 00:00 5
 * 118 20 7 10.01.22 00:00 5
 * 119 5 4 11.01.22 00:00 3
 * 120 23 7 12.01.22 00:00 3
 * 121 1 3 13.01.22 00:00 3
 * 122 20 5 14.01.22 00:00 5
 * 123 5 6 15.01.22 00:00 4
 * 124 6 6 16.01.22 00:00 3
 * 125 20 5 17.01.22 00:00 4
 * 126 15 4 18.01.22 00:00 5
 * 127 25 5 19.01.22 00:00 3
 * 128 15 1 20.01.22 00:00 4
 * 129 5 5 21.01.22 00:00 5
 * 130 2 5 22.01.22 00:00 3
 * 131 4 2 23.01.22 00:00 4
 * 132 7 6 24.01.22 00:00 4
 * 133 13 1 25.01.22 00:00 3
 * 134 5 6 26.01.22 00:00 5
 * 135 24 1 27.01.22 00:00 3
 * 136 20 5 28.01.22 00:00 5
 * 137 10 5 29.01.22 00:00 4
 * 138 3 6 30.01.22 00:00 4
 * 139 22 5 31.01.22 00:00 5
 * 140 14 2 01.02.22 00:00 5
 * 141 22 7 02.02.22 00:00 4
 * 142 22 3 03.02.22 00:00 5
 * 143 2 6 04.02.22 00:00 4
 * 144 4 7 05.02.22 00:00 3
 * 145 23 7 06.02.22 00:00 4
 * 146 7 6 07.02.22 00:00 3
 * 147 1 7 08.02.22 00:00 5
 * 148 1 7 09.02.22 00:00 4
 * 149 2 7 10.02.22 00:00 4
 * 150 14 2 11.02.22 00:00 3
 * 151 2 6 12.02.22 00:00 4
 * 152 8 7 13.02.22 00:00 4
 * 153 23 1 14.02.22 00:00 3
 * 154 6 1 15.02.22 00:00 5
 * 155 23 7 16.02.22 00:00 5
 * 156 10 7 17.02.22 00:00 5
 * 157 9 4 18.02.22 00:00 4
 * 158 7 5 19.02.22 00:00 3
 * 159 11 2 20.02.22 00:00 4
 * 160 10 4 21.02.22 00:00 3
 * 161 14 7 22.02.22 00:00 5
 * 162 7 4 23.02.22 00:00 3
 * 163 14 5 24.02.22 00:00 5
 * 164 25 6 25.02.22 00:00 3
 * 165 18 4 26.02.22 00:00 5
 * 166 9 3 27.02.22 00:00 5
 * 167 10 7 28.02.22 00:00 4
 * 168 3 5 01.03.22 00:00 3
 * 169 8 3 02.03.22 00:00 4
 * 170 8 4 03.03.22 00:00 4
 * 171 2 7 04.03.22 00:00 4
 * 172 8 4 05.03.22 00:00 5
 * 173 9 7 06.03.22 00:00 4
 * 174 12 4 07.03.22 00:00 3
 * 175 9 2 08.03.22 00:00 3
 * 176 24 3 09.03.22 00:00 5
 * 177 15 2 10.03.22 00:00 3
 * 178 9 1 11.03.22 00:00 4
 * 179 7 6 12.03.22 00:00 4
 * 180 23 1 13.03.22 00:00 5
 * 181 9 5 14.03.22 00:00 5
 * 182 6 3 15.03.22 00:00 5
 * 183 4 5 16.03.22 00:00 5
 * 184 2 4 17.03.22 00:00 5
 * 185 23 5 18.03.22 00:00 5
 * 186 11 3 19.03.22 00:00 4
 * 187 17 2 20.03.22 00:00 3
 * 188 8 2 21.03.22 00:00 4
 * 189 22 6 22.03.22 00:00 5
 * 190 20 1 23.03.22 00:00 5
 * 191 1 5 24.03.22 00:00 4
 * 192 9 4 25.03.22 00:00 4
 * 193 23 3 26.03.22 00:00 4
 * 194 2 2 27.03.22 00:00 4
 * 195 14 5 28.03.22 00:00 5
 * 196 14 6 29.03.22 00:00 4
 * 197 4 2 30.03.22 00:00 5
 * 198 23 5 31.03.22 00:00 5
 * 199 6 5 01.04.22 00:00 4
 * 200 15 7 02.04.22 00:00 5
 * 
 */
