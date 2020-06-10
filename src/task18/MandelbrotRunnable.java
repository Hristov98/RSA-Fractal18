package task18;

import org.apache.commons.math3.complex.Complex;

import java.awt.*;

public class MandelbrotRunnable implements Runnable {

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        printStartMessage();
        renderRows();
        printEndMessage(startTime);
    }

    private void printStartMessage() {
        if (!MandelbrotSet.isQuiet) {
            System.out.println(Thread.currentThread().getName() + " started.");
        }
    }

    private void renderRows() {
        while (true) {
            int segment = getCurrentSegment();
            if (segment >= MandelbrotSet.segmentCount) {
                return;
            } else {
                incrementSegment();
                renderCurrentSegment(segment);
            }
        }
    }

    private synchronized int getCurrentSegment() {
        return MandelbrotSet.segmentToRender;
    }

    private synchronized void incrementSegment() {
        MandelbrotSet.segmentToRender++;
    }

    private void renderCurrentSegment(int segment) {
        int start = segment * MandelbrotSet.rowsInOneSegment;
        int end = start + MandelbrotSet.rowsInOneSegment;
        if (start > MandelbrotSet.height) {
            return;
        }
        if (end > MandelbrotSet.height) {
            end = MandelbrotSet.height;
        }

        if (!MandelbrotSet.isQuiet && MandelbrotSet.rowsInOneSegment!=1) {
            System.out.printf(Thread.currentThread().getName() + " is rendering segment %d with rows %d to %d. \n",
                    segment, start, end - 1);
        }

        for (int y = start; y < end; y++) {
            renderCurrentRow(y);
        }
    }

    private void renderCurrentRow(int row) {
        for (int realCoordinate = 0; realCoordinate < MandelbrotSet.width; realCoordinate++) {
            float constantReal = getConstantReal(realCoordinate);
            float constantImaginary = getConstantImaginary(row);
            int color = calculateColor(constantReal, constantImaginary);

            MandelbrotSet.buffer.setRGB(realCoordinate, row, color);
        }
    }

    private float getConstantReal(int realCoordinate) {
        return realCoordinate * (MandelbrotSet.realUpperLimit - MandelbrotSet.realLowerLimit) / MandelbrotSet.width
                + MandelbrotSet.realLowerLimit;
    }

    private float getConstantImaginary(int imaginaryCoordinate) {
        return imaginaryCoordinate * (MandelbrotSet.imaginaryUpperLimit - MandelbrotSet.imaginaryLowerLimit) / MandelbrotSet.height
                + MandelbrotSet.imaginaryLowerLimit;
    }

    private int calculateColor(float real, float imaginary) {
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

    private boolean isOutOfBounds(Complex z) {
        return (z.getReal() * z.getReal() + z.getImaginary() * z.getImaginary()) > 100;
    }

    private void printEndMessage(long startTime) {
        if (!MandelbrotSet.isQuiet) {
            System.out.println(Thread.currentThread().getName() + " stopped.");
            System.out.println(Thread.currentThread().getName() + " execution time was (millis): " +
                    (System.currentTimeMillis() - startTime));
        }
    }
}
