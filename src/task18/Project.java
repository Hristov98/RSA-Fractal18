package task18;


public class Project {
    public static void main(String[] args) {
        long startProgram = System.currentTimeMillis();

        MandelbrotSet set = new MandelbrotSet();
        set.renderImageWithCoarseGranularity();

        System.out.println("Threads used in current run: " + MandelbrotSet.numberOfThreads);
        System.out.println("Total execution time for current run (millis): "
                + (System.currentTimeMillis() - startProgram));

        set.saveImage();
    }
}
