package ru.itis.kmeans;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class KMeans {
	private static final double EPS = 0.01;
	private static final int MAX_ITERATIONS = 10;

	private Point2D[] points;
	private int clusters;
	private Point2D[] centroids;
	private Map<Integer, List<Integer>> labels;

	public KMeans(Point2D[] points, int clusters) {
		this.points = points;
		this.clusters = clusters;
		this.centroids = getRandomCentroids();
	}

	private Point2D[] getRandomCentroids() {
		Point2D[] array = Arrays.copyOf(points, points.length);
		for (int i = array.length - 1; i > 0; i--) {
			int j = ThreadLocalRandom.current().nextInt(0, i);
			Point2D temp = array[i];
			array[i] = array[j];
			array[j] = temp;
		}
		return Arrays.copyOf(array, clusters);
	}

	private void distributeData() {
		labels = new HashMap<>(centroids.length);
		for (int i = 0; i < points.length; i++) {
			int index = getCentroidAssign(points[i]);
			if (labels.containsKey(index)) {
				labels.get(index).add(i);
			} else {
				List<Integer> indexes = new LinkedList<>();
				indexes.add(i);
				labels.put(index, indexes);
			}
		}
	}

	private void recalculateCentroids() {
		for (Map.Entry<Integer, List<Integer>> entry : labels.entrySet()) {
			double x = 0;
			double y = 0;
			for (Integer index : entry.getValue()) {
				Point2D current = points[index];
				x += current.getX();
				y += current.getY();
			}
			int n = entry.getValue().size();
			centroids[entry.getKey()] = new Point2D.Double(x / n, y / n);
		}
	}

	public void fit() {
		for (int i = 1; i < MAX_ITERATIONS; i++) {
			Point2D[] oldCentroids = Arrays.copyOf(centroids, centroids.length);
			distributeData();
			recalculateCentroids();
			double max = distance(centroids[0], oldCentroids[0]);
			for (int j = 1; j < centroids.length; j++) {
				double dist = distance(centroids[j], oldCentroids[j]);
				if (dist > max) {
					max = dist;
				}
			}
			if (max < EPS) {
				break;
			}
		}
	}

	private int getCentroidAssign(Point2D point) {
		int index = 0;
		double min = distance(point, centroids[0]);
		for (int i = 1; i < centroids.length; i++) {
			if (distance(point, centroids[i]) < min) {
				index = i;
			}
		}
		return index;
	}

	private double distance(Point2D f, Point2D s) {
		return (s.getX() - f.getX()) * (s.getX() - f.getX()) + (s.getY() - f.getY()) * (s.getY() - f.getY());
	}

	private String pointToString(Point2D point) {
		return "(" + point.getX() + "; " + point.getY() + ")";
	}

	public List<String> getResult() {
		List<String> result = new LinkedList<>();
		for (Map.Entry<Integer, List<Integer>> entry : labels.entrySet()) {
			StringBuilder sb = new StringBuilder();
			entry.getValue().forEach(index -> {
				sb.append(" ");
				sb.append(pointToString(points[index]));
			});
			result.add(pointToString(centroids[entry.getKey()]) + " " + entry.getKey() + ":" + sb.toString());
		}
		return result;
	}

	public int predict(Point2D point) {
		return getCentroidAssign(point);
	}
}
