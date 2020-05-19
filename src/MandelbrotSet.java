import org.apache.commons.math3.complex.Complex;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MandelbrotSet {
    private int width;
    private int height;
    private float realUpperLimit;
    private float realLowerLimit;
    private float imaginaryUpperLimit;
    private float imaginaryLowerLimit;
    private int numberOfThreads;
    private String outputName;
    private final int ITERATIONS = 500;
    private boolean isQuiet;
    private BufferedImage buffer;

    MandelbrotSet() {
        width = 640;
        height = 480;
        realUpperLimit = 2.0f;
        realLowerLimit = -2.0f;
        imaginaryUpperLimit = 2.0f;
        imaginaryLowerLimit = -2.0f;
        numberOfThreads = 1;
        outputName = "zad18.png";
        isQuiet = false;
    }

    public void initialiseBuffer() {
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void renderImage() {
        for (int realCoordinate = 0; realCoordinate < width; realCoordinate++) {
            for (int imaginaryCoordinate = 0; imaginaryCoordinate < height; imaginaryCoordinate++) {
                float constantReal = getConstantReal(realCoordinate);
                float constantImaginary = getConstantImaginary(imaginaryCoordinate);
                int color = calculateColor(constantReal, constantImaginary);

                buffer.setRGB(realCoordinate, imaginaryCoordinate, color);
            }
        }
    }

    private float getConstantReal(int realCoordinate) {
        return realCoordinate * (realUpperLimit - realLowerLimit) / width
                + realLowerLimit;
    }

    private float getConstantImaginary(int imaginaryCoordinate) {
        return imaginaryCoordinate * (imaginaryUpperLimit - imaginaryLowerLimit) / height
                + imaginaryLowerLimit;
    }

    private int calculateColor(float real, float imaginary) {
        Complex constant = new Complex(real, imaginary);
        Complex zFunction = Complex.ZERO;

        for (int currentIterations = 0; currentIterations < ITERATIONS; currentIterations++) {
            zFunction = constant.multiply(zFunction.cos());

            if (isOutOfBounds(zFunction)) {
                return Color.HSBtoRGB((float) currentIterations / ITERATIONS, 0.67f, 1);
            }
        }

        return 0x00000000;
    }

    private boolean isOutOfBounds(Complex z) {
        return z.getReal() * z.getReal() + z.getImaginary() * z.getImaginary() > 4;
    }

    public void saveImage() {
        try {
            ImageIO.write(buffer, "png", new File(outputName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setImageSize(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void setRectangleBoundaries(float realLowerLimit, float realUpperLimit,
                                   float imaginaryLowerLimit,float imaginaryUpperLimit) {
        this.realLowerLimit = realLowerLimit;
        this.realUpperLimit = realUpperLimit;
        this.imaginaryLowerLimit = imaginaryLowerLimit;
        this.imaginaryUpperLimit = imaginaryUpperLimit;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void setOutputName(String outputName) {
        this.outputName = outputName;
    }

    public void setQuiet() {
        isQuiet = true;
    }
}
