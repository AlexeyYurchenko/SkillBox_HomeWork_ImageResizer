import java.io.File;

public class Main {
    private static final String SRC_FOLDER = "Multithreading/ImageResizer/data/SRC_FOLDER";
    private static final String DST_FOLDER = "Multithreading/ImageResizer/data/DST_FOLDER";
    private static final int NEW_WIDTH = 300;

    public static void main(String[] args) {

        File srcDir = new File(SRC_FOLDER);
        File[] files = srcDir.listFiles();
        long start = System.currentTimeMillis();

        int core = Runtime.getRuntime().availableProcessors();
        int remainder = files.length % core;
        int size = files.length / core;
        int srsPos = 0;
        for (int i = 0; i < core; i++) {
            if (i == core - 1) {
                size += remainder;
            }
            File[] newFile = new File[size];
            if (i > 0) {
                if (i == core - 1) {
                    srsPos -= remainder;
                }
                srsPos += newFile.length;
            }
            System.arraycopy(files, srsPos, newFile, 0, newFile.length);
            new Thread(new ImageResizer(newFile, NEW_WIDTH, DST_FOLDER, start)).start();
        }
    }
}