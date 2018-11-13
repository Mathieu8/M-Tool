package src.login;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Token {
//	File file = new File("token.txt");
	// Files f = new Files();
	private Path p = Paths.get("C:\\Users\\Mathieu\\eclipse-workspace\\Javafx\\M-Tool\\client\\token.txt");

	private List<Character> readFile() {
		List<Character> temp = new ArrayList<>();
//		StringBuilder temp = new StringBuilder();
		try (InputStream in = new BufferedInputStream(Files.newInputStream(p))) {
			int i = 0;
			do {
				i = in.read();
				if (i != -1) {
					temp.add((char) i);
				}
			} while (i != -1);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return temp;

	}

	/**
	 * 
	 * @return
	 */
	private String[] getMetadata() {
		String[] data = new String[3];

		try {
			Object object = Files.getAttribute(p, "creationTime", LinkOption.NOFOLLOW_LINKS);
			data[0] = object.toString();
			data[1] = object.toString();
			data[2] = object.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}

	char[] getToken() {
		List<Character> token = readFile();
		String[] meta = getMetadata();
		for (int i = 0; i < meta[1].length(); i++) {
			token.add(meta[1].charAt(i));
		}

		char[] temp = new char[token.size()];
		for (int i = 0; i < token.size(); i++) {
			temp[i] = token.get(i);
		}

		return temp;
	}

	void createFile(String s) {
		byte data[] = s.getBytes();

		try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(p))) {
			out.write(data);// , 0, data.length);
			Files.setAttribute(p, "dos:hidden", true);
		} catch (IOException x) {
			System.err.println(x);
		}
	}

	void deleteFile() {
		try {
			Files.delete(p);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
