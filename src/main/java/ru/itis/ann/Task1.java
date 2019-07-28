package ru.itis.ann;

import ru.itis.utils.Utils;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

public class Task1 {
	public static void main(String[] args) {
		List<String> data = Utils.readData("task1/input.csv");
		double[][] x = new double[data.size()][2];
		double[][] y = new double[data.size()][1];

		Iterator<String> iterator = data.iterator();
		for (int i = 0; iterator.hasNext(); i++) {
			Matcher matcher = Utils.PATTERN.matcher(iterator.next());
			x[i][0] = matcher.find() ? Double.parseDouble(matcher.group()) : 0;
			x[i][1] = matcher.find() ? Double.parseDouble(matcher.group()) : 0;
			y[i][0] = matcher.find() ? Double.parseDouble(matcher.group()) : 0;
		}

		ANN ann = new ANN(x, y, 400, 10000);
		Utils.writeData(ann.train(2000), "task1/output.csv");
		System.out.println(ann.predict(new double[][]{{20, 20}}));
	}
}