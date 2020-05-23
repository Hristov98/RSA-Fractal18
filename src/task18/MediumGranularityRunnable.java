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
        while (rowToRender < MandelbrotSet.height) {
            int index = MandelbrotSet.threadIsActive.indexOf(false);
            MandelbrotSet.threadIsActive.set(index, true);

            int row = getCurrentRowAndIncrement();
            renderRow(row);

            MandelbrotSet.threadIsActive.set(index, false);
        }
    }

    private synchronized int getCurrentRowAndIncrement() {
        int currentRow = rowToRender;
        rowToRender++;

        return currentRow;
    }

    private void renderRow(int row) {
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