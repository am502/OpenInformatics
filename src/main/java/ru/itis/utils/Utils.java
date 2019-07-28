package ru.itis.utils;

import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
	public static final String PATH_TO_RESOURCES = "src/main/resources/";
	public static final Pattern PATTERN = Pattern.compile("-?\\d+(\\.\\d+)?");

	public static List<String> readData(String file) {
		List<String> lines = new LinkedList<>();
		try (Scanner in = new Scanner(new FileInputStream(PATH_TO_RESOURCES + file))) {
			while (in.hasNextLine()) {
				lines.add(in.nextLine());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lines;
	}

	public static void writeData(List<String> result, String file) {
		try (PrintWriter out = new PrintWriter(PATH_TO_RESOURCES + file)) {
			for (String line : result) {
				out.println(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static Point2D[] processData(List<String> data) {
		List<Point2D> points = new LinkedList<>();
		data.forEach(line -> points.add(processLine(line)));
		return points.toArray(new Point2D[0]);
	}

	public static Point2D processLine(String line) {
		Matcher matcher = PATTERN.matcher(line);
		double[] coordinates = new double[2];
		for (int i = 0; i < coordinates.length; i++) {
			if (matcher.find()) {
				coordinates[i] = Double.parseDouble(matcher.group());
			} else {
				throw new RuntimeException("Incorrect input");
			}
		}
		return new Point2D.Double(coordinates[0], coordinates[1]);
	}
}
