package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Input {
	private final File file;

	public Input(String filename) throws IOException {
		file = new File(filename);
		if (! file.exists()) {
			throw new IOException("File not found : " + filename);
		}
	}
	
	public Input(File file) {
		this.file = file;
	}
	
	public String getText() throws IOException {
		final FileReader fr = new FileReader(file);
		final String content = slurpFile(fr);
		return content;
	}
	
	public static String slurpFile (final Reader fr) throws IOException {
        final StringBuffer sb = new StringBuffer();
        final BufferedReader br = new BufferedReader(fr);
        final char[] buffer = new char[4096];
        while ( true ) {
            int count = br.read(buffer);
            if (count < 0) {
                return sb.toString();
            }
            sb.append(buffer, 0, count);
        }
    }

}
