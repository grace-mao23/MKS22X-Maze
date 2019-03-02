import java.io.*;
import java.util.*;

public class MazeGen {

	private boolean[][] fill, linksv, linksh;
	private int rows, cols;
	private boolean generated;

	private static final int[][] DIR = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

	public MazeGen( int height, int width) {
		rows = height;
		cols = width;
		generated = false;
	}

	public String toString() {
		if (!generated) throw new IllegalStateException("Use generate() before calling writeToFile() or toString()");
		StringBuilder sb = new StringBuilder();

		for (int c = 0; c < cols*2+1; c++) sb.append('#');
		sb.append('\n');

		for (int r = 0; r < rows; r++) {
			sb.append('#');
			for (int c = 0; c < cols-1; c++) {
				if (r == 0 && c == 0) sb.append('S');
				else sb.append(' ');

				sb.append(linksh[r][c] ? ' ' : '#');
			}
			if (r == rows-1) sb.append("E#\n");
			else sb.append(" #\n");
			for (int c = 0; c < cols; c++) {
				sb.append('#');
				sb.append(r != rows-1 && linksv[r][c] ? ' ' : '#');
			}
			sb.append('#');
			if (r != rows-1) sb.append('\n');
		}

		return new String(sb);
	}

	public void generate() {
		// setting up data
		fill = new boolean[rows][cols];
		linksh = new boolean[rows][cols-1];
		linksv = new boolean[rows-1][cols];
		generated = true;

		// recursive solve
		solve(0, 0);
	}

	private void solve(int r, int c) {
		fill[r][c] = true;
		if (r == rows-1 && c == cols-1) return;
		List<Integer> dir = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			int[] pair = DIR[i];
			if (r+pair[0] >= 0 && c+pair[1] >= 0 && r+pair[0] < rows && c+pair[1] < cols)
				dir.add(i);
		}
		Collections.shuffle(dir);

		for (int i : dir) {
			int[] pair = DIR[i];
			if (!fill[r+pair[0]][c+pair[1]]) {
				if (pair[0] == 0) linksh[r][pair[1] == 1 ? c : c-1] = true;
				else linksv[pair[0] == 1 ? r : r-1][c] = true;
				solve(r+pair[0], c+pair[1]);
			}
		}
	}

	public void writeToFile(String path) throws IOException {
		FileWriter f = new FileWriter(path);
		f.write(toString());
		f.close();
	}
}
