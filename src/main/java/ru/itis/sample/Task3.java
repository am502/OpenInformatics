package ru.itis.sample;

import ru.itis.utils.Utils;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Task3 {
	public static void main(String[] args) {
		Point2D[] points = Utils.processData(Utils.readData("task1/input.csv"));
		VR vr = new VR(Arrays.stream(points).map(Point2D::getX).collect(Collectors.toList()));
		Utils.writeData(Arrays.asList(
				"Выборочное среднее: " + vr.getVS(),
				"Выборочная дисперсия: " + vr.getVD(),
				"Мода: " + vr.getMO(),
				"Медиана: " + vr.getME(),
				"Эксцесс: " + vr.getE(),
				"Асимметричность: " + vr.getA()
		), "task3/output.csv");
	}
}
