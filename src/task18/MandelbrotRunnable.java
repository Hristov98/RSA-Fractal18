package task18;

public class MandelbrotRunnable implements Runnable {
    private static int segmentToRender = 0;
    private final int rowsPerSegment = MandelbrotSet.rowsInOneSegment;

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
        return segmentToRender;
    }

    private synchronized void incrementSegment() {
        segmentToRender++;
    }

    private void renderCurrentSegment(int segment) {
        int start = segment * rowsPerSegment;
        int end = start + rowsPerSegment;
        if (start > MandelbrotSet.height) {
            return;
        }
        if (end > MandelbrotSet.height) {
            end = MandelbrotSet.height;
        }

        if (!MandelbrotSet.isQuiet && rowsPerSegment!=1) {
            System.out.printf(Thread.currentThread().getName() + " is rendering segment %d with rows %d to %d. \n",
                    segment, start, end - 1);
        }

        for (int y = start; y < end; y++) {
            renderCurrentRow(y);
        }
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
