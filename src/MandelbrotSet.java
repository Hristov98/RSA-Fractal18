import org.apache.commons.math3.complex.Complex;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MandelbrotSet extends JComponent {
    private final int WIDTH = 640;
    private final int HEIGHT = 480;
    private final int ITERATIONS = 200;
    private final float SCALE_HORIZONTAL = 4.0f;
    private final float SCALE_VERTICAL = 4.0f;
    private BufferedImage buffer;

    public void initialiseBuffer() {
        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
    }

    public void renderImage() {
        for (int realCoordinate = 0; realCoordinate < WIDTH; realCoordinate++) {
            for (int imaginaryCoordinate = 0; imaginaryCoordinate < HEIGHT; imaginaryCoordinate++) {
                float constantReal = (realCoordinate - WIDTH / 2.0f) * SCALE_HORIZONTAL / WIDTH;
                float constantImaginary = (imaginaryCoordinate - HEIGHT / 2.0f) * SCALE_VERTICAL / WIDTH;

                int color = calculateColor(constantReal, constantImaginary);

                buffer.setRGB(realCoordinate, imaginaryCoordinate, color);
            }
        }
    }

    public int calculateColor(float real, float imaginary) {
        Complex constant = new Complex(real, imaginary);
        Complex zFunction = Complex.ZERO;

        for (int i = 0; i < ITERATIONS; i++) {
            zFunction = constant.multiply(zFunction.cos());

            if (zFunction.getReal() * zFunction.getReal() +
                    zFunction.getImaginary() * zFunction.getImaginary() > 4) {
                return Color.HSBtoRGB((float) i / ITERATIONS, 0.5f, 1);
            }
        }

        return 0x00000000;
    }

    public void displayImage() {
        JFrame frame = new JFrame("RSA Project 18");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().add(this);
        frame.pack();
        frame.setVisible(true);
    }

    public void saveImage() {
        try {
            ImageIO.write(buffer, "png", new File("mandelbrot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNotify() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public void paint(Graphics graphics) {
        graphics.drawImage(buffer, 0, 0, null);
    }
}
