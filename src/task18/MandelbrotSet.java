package task18;

import org.apache.commons.math3.complex.Complex;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MandelbrotSet {
    public static int width;
    public static int height;
    public static float realUpperLimit;
    public static float realLowerLimit;
    public static float imaginaryUpperLimit;
    public static float imaginaryLowerLimit;
    public static int numberOfThreads;
    public static String outputName;
    public static boolean isQuiet;
    public static final int ITERATIONS = 500;
    public static BufferedImage buffer;

    MandelbrotSet() {
        width = 640;
        height = 480;
        realLowerLimit = -2.0f;
        realUpperLimit = 2.0f;
        imaginaryLowerLimit = -2.0f;
        imaginaryUpperLimit = 2.0f;
        numberOfThreads = 1;
        outputName = "zad18.png";
        isQuiet = false;

        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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

    public static float getConstantReal(int realCoordinate) {
        return realCoordinate * (MandelbrotSet.realUpperLimit - MandelbrotSet.realLowerLimit) / MandelbrotSet.width
                + MandelbrotSet.realLowerLimit;
    }

    public static float getConstantImaginary(int imaginaryCoordinate) {
        return imaginaryCoordinate * (MandelbrotSet.imaginaryUpperLimit - MandelbrotSet.imaginaryLowerLimit) / MandelbrotSet.height
                + MandelbrotSet.imaginaryLowerLimit;
    }

    public static int calculateColor(float real, float imaginary) {
        Complex constant = new Complex(real, imaginary);
        Complex zFunction = Complex.ZERO;

        for (int currentIterations = 0; currentIterations < MandelbrotSet.ITERATIONS; currentIterations++) {
            zFunction = constant.multiply(zFunction.cos());

            if (isOutOfBounds(zFunction)) {
                return Color.HSBtoRGB((float) 10 * currentIterations / MandelbrotSet.ITERATIONS, 0.67f, 1);
            }
        }

        return 0x00000000;
    }

    public static boolean isOutOfBounds(Complex z) {
        return (z.getReal() * z.getReal() + z.getImaginary() * z.getImaginary()) > 100;
    }

    public void saveImage() {
        try {
            ImageIO.write(buffer, "png", new File(outputName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
