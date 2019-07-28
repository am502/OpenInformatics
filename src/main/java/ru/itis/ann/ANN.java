package ru.itis.ann;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class ANN {
	private double[][] x;
	private double[][] y;
	private int m;
	private int iterations;

	private double[][] w1;
	private double[][] b1;

	private double[][] w2;
	private double[][] b2;

	public ANN(double[][] x, double[][] y, int nodes, int iterations) {
		this.m = x.length;
		this.iterations = iterations;

		this.x = np.T(x);
		this.y = np.T(y);

		w1 = np.random(nodes, 2);
		b1 = new double[nodes][m];

		w2 = np.random(1, nodes);
		b2 = new double[1][m];
	}

	public List<String> train(int logIterations) {
		List<String> logs = new LinkedList<>();
		for (int i = 0; i < iterations; i++) {
			double[][] z1 = np.add(np.dot(w1, x), b1);
			double[][] a1 = np.sigmoid(z1);

			double[][] z2 = np.add(np.dot(w2, a1), b2);
			double[][] a2 = np.sigmoid(z2);

			double cost = np.cross_entropy(m, y, a2);

			double[][] dz2 = np.subtract(a2, y);
			double[][] dw2 = np.divide(np.dot(dz2, np.T(a1)), m);
			double[][] db2 = np.divide(dz2, m);

			double[][] dz1 = np.multiply(np.dot(np.T(w2), dz2), np.subtract(1.0, np.power(a1, 2)));
			double[][] dw1 = np.divide(np.dot(dz1, np.T(x)), m);
			double[][] db1 = np.divide(dz1, m);

			w1 = np.subtract(w1, np.multiply(0.01, dw1));
			b1 = np.subtract(b1, np.multiply(0.01, db1));

			w2 = np.subtract(w2, np.multiply(0.01, dw2));
			b2 = np.subtract(b2, np.multiply(0.01, db2));

			if (i % logIterations == 0) {
				logs.add(String.format("Cost: %f\nw1: %s\nw2: %s\nb1: %s\nb2: %s\nPredictions: %s",
						cost, Arrays.deepToString(w1), Arrays.deepToString(w2),
						Arrays.deepToString(b1), Arrays.deepToString(b2),
						Arrays.deepToString(a2)));
			}
		}
		return logs;
	}

	public String predict(double[][] point) {
		point = np.T(point);
		double[][] z1 = np.add(np.dot(w1, point), b1);
		double[][] a1 = np.sigmoid(z1);
		double[][] z2 = np.add(np.dot(w2, a1), b2);
		double[][] a2 = np.sigmoid(z2);
		return Arrays.deepToString(a2);
	}
}
