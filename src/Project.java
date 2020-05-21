public class Project {
    public static void main(String[] args) {
        long startProgram = System.currentTimeMillis();

        MandelbrotSet set = new MandelbrotSet();
        set.initialiseBuffer();
        set.renderImage();

        System.out.println("Threads used in current run: " + set.getNumberOfThreads());
        System.out.println("Total execution time for current run (millis): "
                + (System.currentTimeMillis() - startProgram));

        set.saveImage();
    }
}
