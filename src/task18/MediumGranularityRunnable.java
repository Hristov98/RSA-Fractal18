package task18;

public class MediumGranularityRunnable implements Runnable {
    private final int threadNumber;
    private final int rowToRender;

    MediumGranularityRunnable(int threadNumber,int rowToRender) {
        this.threadNumber=threadNumber;
        this.rowToRender=rowToRender;
    }

    @Override
    public void run() {
        MandelbrotSet.threadIsActive[threadNumber] = true;
        long startTime = System.currentTimeMillis();

        printStartMessage();
        renderRow();
        printEndMessage(startTime);

        MandelbrotSet.threadIsActive[threadNumber] = false;
    }

    private void printStartMessage() {
        if (!MandelbrotSet.isQuiet) {
            System.out.println(Thread.currentThread().getName() + " started.");
        }
    }

    private void renderRow() {
        for (int realCoordinate = 0; realCoordinate < MandelbrotSet.width; realCoordinate++) {
                float constantReal = MandelbrotSet.getConstantReal(realCoordinate);
                float constantImaginary = MandelbrotSet.getConstantImaginary(rowToRender);
                int color = MandelbrotSet.calculateColor(constantReal, constantImaginary);

                MandelbrotSet.buffer.setRGB(realCoordinate, rowToRender, color);
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