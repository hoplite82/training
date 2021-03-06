package hack.dequeue;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Scanner;

public class Solution {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		Deque<Integer> deque = new ArrayDeque<>();
		HashSet<Integer> set = new HashSet<>();

		int n = in.nextInt();
		int m = in.nextInt();
		int max = 0;

		for (int i = 0; i < n; i++) {
			int input = in.nextInt();

			deque.add(input);
			set.add(input);

			if (deque.size() >= m) {
				if (set.size() > max)
					max = set.size();
				int first = deque.remove();
				if (set.contains(first) && !deque.contains(first))
					set.remove(first);
			}

		}
		in.close();
		System.out.println(max);
	}
}
