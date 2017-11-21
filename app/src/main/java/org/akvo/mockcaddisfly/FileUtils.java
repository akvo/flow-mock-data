package org.akvo.mockcaddisfly;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import timber.log.Timber;

public class FileUtils {

    private static final int BUFFER_SIZE = 2048;

    /**
     * reads data from an InputStream into a string.
     */
    public static String readText(InputStream is) throws IOException {
        ByteArrayOutputStream out = null;
        try {
            out = read(is);
            return out.toString();
        } finally {
            close(out);
        }
    }

    /**
     * reads the contents of an InputStream into a ByteArrayOutputStream.
     */
    private static ByteArrayOutputStream read(InputStream is) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(is, out);
        return out;
    }

    /**
     * Helper function to close a Closeable instance
     */
    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            Timber.e(e.getMessage());
        }
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int size;
        while ((size = in.read(buffer, 0, buffer.length)) != -1) {
            out.write(buffer, 0, size);
        }
    }
}
