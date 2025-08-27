package tr.setur.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class CsvReader {
	private CsvReader() {}

	public static String readFirstValue(String resourcePath, String column) {
		try (InputStream is = CsvReader.class.getClassLoader().getResourceAsStream(resourcePath)) {
			if (is == null) throw new IllegalArgumentException("Resource not found: " + resourcePath);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
				String header = br.readLine();
				if (header == null) throw new IllegalStateException("CSV empty: " + resourcePath);
				String[] cols = header.split(",");
				Map<String,Integer> colIndex = new HashMap<>();
				for (int i = 0; i < cols.length; i++) colIndex.put(cols[i].trim(), i);
				Integer idx = colIndex.get(column);
				if (idx == null) throw new IllegalArgumentException("Column not found: " + column);
				String row = br.readLine();
				if (row == null) throw new IllegalStateException("CSV has header only: " + resourcePath);
				String[] vals = row.split(",");
				if (idx >= vals.length) throw new IllegalStateException("Row has fewer columns than expected");
				return vals[idx].trim();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
