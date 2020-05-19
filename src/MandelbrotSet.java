import org.apache.commons.math3.complex.Complex;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MandelbrotSet {
    private final int WIDTH;
    private final int HEIGHT;
    private final float REAL_UPPER_LIMIT;
    private final float REAL_LOWER_LIMIT;
    private final float IMAGINARY_UPPER_LIMIT;
    private final float IMAGINARY_LOWER_LIMIT;
    private final int ITERATIONS = 500;
    private BufferedImage buffer;

    MandelbrotSet() {
        WIDTH = 640;
        HEIGHT = 480;
        REAL_UPPER_LIMIT = 2.0f;
        REAL_LOWER_LIMIT = -2.0f;
        IMAGINARY_UPPER_LIMIT = 2.0f;
        IMAGINARY_LOWER_LIMIT = -2.0f;
    }

    MandelbrotSet(int width, int height, float realLower, float realUpper,
                  float imaginaryLower, float imaginaryUpper) {
        WIDTH = width;
        HEIGHT = height;
        REAL_UPPER_LIMIT = realUpper;
        REAL_LOWER_LIMIT = realLower;
        IMAGINARY_UPPER_LIMIT = imaginaryUpper;
        IMAGINARY_LOWER_LIMIT = imaginaryLower;
    }

    public void initialiseBuffer() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public void renderImage() {
        for (int realCoordinate = 0; realCoordinate < WIDTH; realCoordinate++) {
            for (int imaginaryCoordinate = 0; imaginaryCoordinate < HEIGHT; imaginaryCoordinate++) {
                float constantReal = getConstantReal(realCoordinate);
                float constantImaginary = getConstantImaginary(imaginaryCoordinate);
                int color = calculateColor(constantReal, constantImaginary);

                buffer.setRGB(realCoordinate, imaginaryCoordinate, color);
            }
        }
    }

    private float getConstantReal(int realCoordinate) {
        return realCoordinate * (REAL_UPPER_LIMIT - REAL_LOWER_LIMIT) / WIDTH
                + REAL_LOWER_LIMIT;
    }

    private float getConstantImaginary(int imaginaryCoordinate) {
        return imaginaryCoordinate * (IMAGINARY_UPPER_LIMIT - IMAGINARY_LOWER_LIMIT) / HEIGHT
                + IMAGINARY_LOWER_LIMIT;
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
            ImageIO.write(buffer, "png", new File("result.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
