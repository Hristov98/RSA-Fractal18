import org.apache.commons.math3.complex.Complex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MandelbrotSet {
    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    private final int ITERATIONS = 500;
    private final float SCALE_HORIZONTAL = 4.0f;
    private final float SCALE_VERTICAL = 4.0f;
    private BufferedImage buffer;

    public void initialiseBuffer() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public void renderImage() {
        for (int realCoordinate = 0; realCoordinate < WIDTH; realCoordinate++) {
            for (int imaginaryCoordinate = 0; imaginaryCoordinate < HEIGHT; imaginaryCoordinate++) {
                float constantReal = (realCoordinate - WIDTH / 2f) * SCALE_HORIZONTAL / WIDTH;
                float constantImaginary = (imaginaryCoordinate - HEIGHT / 2f) * SCALE_VERTICAL / WIDTH;

                int color = calculateColor(constantReal, constantImaginary);

                buffer.setRGB(realCoordinate, imaginaryCoordinate, color);
            }
        }
    }

    public int calculateColor(float real, float imaginary) {
        Complex constant = new Complex(real, imaginary);
        Complex zFunction = Complex.ZERO;

        for (int currentIterations = 0; currentIterations < ITERATIONS; currentIterations++) {
            zFunction = constant.multiply(zFunction.cos());

            if (isOutOfBounds(zFunction)) {
                return Color.HSBtoRGB((float) currentIterations / ITERATIONS, 0.5f, 1);
            }
        }

        return 0x00000000;
    }

    private boolean isOutOfBounds(Complex z) {
        return z.getReal() * z.getReal() + z.getImaginary() * z.getImaginary() > 4;
    }

    public void saveImage() {
        try {
            ImageIO.write(buffer, "png", new File("mandelbrot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
