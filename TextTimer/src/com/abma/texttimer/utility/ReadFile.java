package com.abma.texttimer.utility;

import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
 
	@SuppressWarnings("resource")
	public static String readEntireFile(final String filename) throws IOException {
        FileReader in = new FileReader(filename);
        StringBuilder contents = new StringBuilder();
        char[] buffer = new char[4096];
        int read = 0;
        do {
            contents.append(buffer, 0, read);
            read = in.read(buffer);
        } while (read >= 0);
        return contents.toString();
    }
}
