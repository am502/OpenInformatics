package ru.itis.kmeans;

import ru.itis.utils.Utils;

import java.util.Scanner;

public class Task2 {
	public static void main(String[] args) {
		KMeans kmeans = new KMeans(Utils.processData(Utils.readData("task2/input.csv")), 2);
		kmeans.fit();
		Utils.writeData(kmeans.getResult(), "task2/output.csv");

		Scanner in = new Scanner(System.in);
		System.out.println("Введите координаты точки:");
		while (in.hasNextLine()) {
			try {
				System.out.println("Класс: " + kmeans.predict(Utils.processLine(in.nextLine())));
			} catch (Exception e) {
				System.out.println("Некорректный ввод");
			}
		}
	}
}
