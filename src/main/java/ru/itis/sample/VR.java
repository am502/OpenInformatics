package ru.itis.sample;

import java.util.*;

public class VR {
	private Map<Double, Integer> vr;
	private List<Double> data;
	private double vs;
	private double vd;
	private double so;

	public VR(List<Double> data) {
		this.data = data;
		vr = new HashMap<>();
		data.forEach(x -> vr.put(x, vr.getOrDefault(x, 0) + 1));
		this.vs = getRowMoment();
		this.vd = getCentralMoment(2);
		this.so = Math.sqrt(vd);
	}

	public double getVS() {
		return vs;
	}

	public double getVD() {
		return vd;
	}

	public double getMO() {
		Optional<Map.Entry<Double, Integer>> optional = vr.entrySet().stream()
				.min(Map.Entry.comparingByValue(Comparator.reverseOrder()));
		return optional.isPresent() ? optional.get().getKey() : 0;
	}

	public double getME() {
		List<Double> sample = new ArrayList<>(data);
		Collections.sort(sample);
		int middle = sample.size() / 2;
		if (sample.size() % 2 == 0) {
			return (sample.get(middle - 1) + sample.get(middle)) / 2;
		} else {
			return sample.get(middle);
		}
	}

	public double getE() {
		return getCentralMoment(4) / Math.pow(so, 4) - 3;
	}

	public double getA() {
		return getCentralMoment(3) / Math.pow(so, 3);
	}

	private double getCentralMoment(int p) {
		double sum = 0;
		for (Map.Entry<Double, Integer> entry : vr.entrySet()) {
			sum += entry.getValue() * Math.pow(entry.getKey() - vs, p);
		}
		return sum / data.size();
	}

	private double getRowMoment() {
		double sum = 0;
		for (Map.Entry<Double, Integer> entry : vr.entrySet()) {
			sum += entry.getKey() * entry.getValue();
		}
		return sum / data.size();
	}
}
