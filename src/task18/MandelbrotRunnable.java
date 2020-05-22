package task18;

public class MandelbrotRunnable implements Runnable {
    private final int renderFrom;
    private final int renderTo;

    MandelbrotRunnable(int threadNumber, int rowsToRender) {
        renderFrom = threadNumber * rowsToRender;
        renderTo = threadNumber * rowsToRender + rowsToRender;
    }

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
        for (int imaginaryCoordinate = renderFrom; imaginaryCoordinate < renderTo; imaginaryCoordinate++) {
            for (int realCoordinate = 0; realCoordinate < MandelbrotSet.width; realCoordinate++) {
                float constantReal = MandelbrotSet.getConstantReal(realCoordinate);
                float constantImaginary = MandelbrotSet.getConstantImaginary(imaginaryCoordinate);
                int color = MandelbrotSet.calculateColor(constantReal, constantImaginary);

                MandelbrotSet.buffer.setRGB(realCoordinate, imaginaryCoordinate, color);
            }
        }
    }

    private void printEndMessage(long startTime) {
        if (!MandelbrotSet.isQuiet) {
            System.out.println(Thread.currentThread().getName() + " stopped.");
            System.out.println(Thread.currentThread().getName() + " execution time was (millis): " +
                    (System.currentTimeMillis() - startTime));
        }
    }
}