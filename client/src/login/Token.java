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

public class Token {
//	File file = new File("token.txt");
	// Files f = new Files();
	private Path p = Paths.get("C:\\Users\\Mathieu\\eclipse-workspace\\Javafx\\M-Tool\\client\\token.txt");

	private String readFile() {
		StringBuilder temp = new StringBuilder();
		try (InputStream in = new BufferedInputStream(Files.newInputStream(p))) {
			int i = 0;
			do {
				i = in.read();
				if (i != -1) {
					temp.append((char) i);
				}
			} while (i != -1);
			return temp.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";

	}

	/**
	 * 
	 * @return
	 */
	private String[] getMetadata() {
		String[] data = new String[3];

		try {
//			System.out.println("get root " + p.getRoot());
//			  System.out.println("Absolute path " + p.toAbsolutePath());
//			  System.out.println("Absolute normalize path " + p.toAbsolutePath().normalize());
			Object object = Files.getAttribute(p, "creationTime", LinkOption.NOFOLLOW_LINKS);
//			System.out.println("Creation time: " + object);
			data[0] = object.toString();
//			object = Files.getAttribute(p, "lastModifiedTime", LinkOption.NOFOLLOW_LINKS);
//			System.out.println("Last modified time: " + object);
			data[1] = object.toString();

//			object = Files.getAttribute(p, "dos:hidden", LinkOption.NOFOLLOW_LINKS);
			data[2] = object.toString();
//			System.out.println("isHidden: " + object);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
	
	String getToken() {
		String token = readFile();
		String[] meta = getMetadata();
		return token + " " + meta[1]; 
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
