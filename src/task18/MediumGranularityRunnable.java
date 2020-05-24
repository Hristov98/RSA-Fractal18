package task18;

public class MediumGranularityRunnable implements Runnable {
    private static int rowToRender = 0;

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
            int row = getCurrentRow();
            if (row >= MandelbrotSet.height) {
                return;
            } else {
                incrementRow();
                renderCurrentRow(row);
            }
        }
    }

    private synchronized int getCurrentRow() {
        return rowToRender;
    }

    private synchronized void incrementRow() {
        rowToRender++;
    }

    private void renderCurrentRow(int row) {
        for (int realCoordinate = 0; realCoordinate < MandelbrotSet.width; realCoordinate++) {
            float constantReal = MandelbrotSet.getConstantReal(realCoordinate);
            float constantImaginary = MandelbrotSet.getConstantImaginary(row);
            int color = MandelbrotSet.calculateColor(constantReal, constantImaginary);

            MandelbrotSet.buffer.setRGB(realCoordinate, row, color);
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