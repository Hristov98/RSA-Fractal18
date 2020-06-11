package task18;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MandelbrotSet {
    public static final int ITERATIONS = 500;
    public static int width;
    public static int height;
    public static float realUpperLimit;
    public static float realLowerLimit;
    public static float imaginaryUpperLimit;
    public static float imaginaryLowerLimit;
    public static int numberOfThreads;
    public static String outputName;
    public static boolean isQuiet;
    public static int granularity;
    public static int rowsInOneSegment;
    public static int segmentCount;
    public static int segmentToRender;
    public static BufferedImage buffer;

    MandelbrotSet() {
        width = 2000;
        height = 2000;
        realLowerLimit = -0.0f;
        realUpperLimit = 2.0f;
        imaginaryLowerLimit = -1.5f;
        imaginaryUpperLimit = 1.5f;
        numberOfThreads = 6;
        outputName = "zad18-gran.png";
        isQuiet = false;

        granularity = 0;
        segmentToRender = 0;

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void calculateSegments() {
        if (MandelbrotSet.granularity > 0) {
            segmentCount = numberOfThreads * granularity;
            rowsInOneSegment = height / segmentCount;
            int leftoverRows = height % segmentCount;

            if (leftoverRows != 0) {
                rowsInOneSegment++;
            }
        } else {
            segmentCount= height;
            rowsInOneSegment = 1;
        }
    }

    public void renderImage() {
        Thread[] threads = new Thread[numberOfThreads];
        for (int threadIndex = 0; threadIndex < numberOfThreads; threadIndex++) {
            threads[threadIndex] = new Thread(new MandelbrotRunnable());
            threads[threadIndex].setName("Thread " + threadIndex);
            threads[threadIndex].start();
        }

        for (int i = 0; i < numberOfThreads; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                System.out.println(threads[i].getName() + " has thrown InterruptedException.");
            }
        }
    }

    public void saveImage() {
        try {
            ImageIO.write(buffer, "png", new File(outputName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
