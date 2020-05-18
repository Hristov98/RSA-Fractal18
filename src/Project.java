public class Project {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        MandelbrotSet set = new MandelbrotSet();
        set.initialiseBuffer();
        set.renderImage();
        set.saveImage();

        long endTime = System.currentTimeMillis();
        System.out.println("Execution took " + (endTime - startTime) + " milliseconds");
    }
}
