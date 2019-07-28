package ru.itis.algs;

import java.util.LinkedList;
import java.util.List;

public class RabinKarp {
	private static final int PRIME = 31;
	private static final int R = pow(2, 31);

	public static List<Integer> find(String text, String pattern) {
		List<Integer> indexes = new LinkedList<>();
		int m = pattern.length();
		int multiplier = pow(PRIME, m - 1);
		int patternHash = calculateHash(pattern);
		int currentHash = calculateHash(text.substring(0, m));
		if (currentHash == patternHash) {
			indexes.add(0);
		}
		for (int i = 0; i < text.length() - m; i++) {
			currentHash = recalculateHash(i, m, currentHash, text, multiplier);
			if (currentHash == patternHash) {
				indexes.add(i + 1);
			}
		}
		return indexes;
	}

	private static int calculateHash(String s) {
		int hash = 0;
		for (int i = 0; i < s.length(); i++) {
			hash += s.charAt(i) * pow(PRIME, i);
		}
		return hash % R;
	}

	private static int recalculateHash(int index, int length, int hash, String text, int multiplier) {
		int newHash = hash - text.charAt(index);
		newHash /= PRIME;
		newHash += (text.charAt(index + length) * multiplier);
		return newHash % R;
	}

	private static int pow(int a, int n) {
		if (n == 0) {
			return 1;
		}
		if (n % 2 == 1) {
			return pow(a, n - 1) * a;
		} else {
			return pow(a, n / 2) * pow(a, n / 2);
		}
	}

	public static void main(String[] args) {
		RabinKarp.find("yeminsajidnsa", "nsa").forEach(System.out::println);
	}
}
