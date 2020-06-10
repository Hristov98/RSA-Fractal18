package task18;

import org.apache.commons.cli.*;
import java.awt.image.BufferedImage;

public class Project {
    public static void main(String[] args) {
        long startProgram = System.currentTimeMillis();

        MandelbrotSet set = new MandelbrotSet();

        try {
            processArguments(args);
        } catch (ParseException e) {
            System.out.println("ParseException thrown.");
        }

        set.calculateSegments();
        set.renderImage();

        if (!MandelbrotSet.isQuiet) {
            System.out.println("Threads used in current run: " + MandelbrotSet.numberOfThreads);
        }

        System.out.println("Total execution time for current run (millis): "
                + (System.currentTimeMillis() - startProgram));

        set.saveImage();
    }

    private static void processArguments(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("s", true, "sets the size of the image " +
                "(if missing or invalid, the default value is 640x480)");
        options.addOption("r", true, "sets the rectangle which will be observed" +
                "(if missing or invalid, the default value is -2.0:2.0:-2.0:2.0)");
        options.addOption("t", true, "sets the number of threads that will be executed " +
                "(if missing or invalid, the default value is 1)");
        options.addOption("o", true, "sets the name of the file saved after calculations are finished" +
                "(if missing or invalid, the default value is zad18.png)");
        options.addOption("g", true, "sets the granularity (if missing or invalid, " +
                "each thread will render 1 row of the image per iteration)");
        options.addOption("q", false, "runs the program in quiet mode finished which hides all messages " +
                "excluding the execution time (if missing or invalid, the default value is zad18.png)");

        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse(options, args);

        if (line.hasOption("s")) {
            String[] dimensions = line.getOptionValue("s").split("x");
            int newWidth = Integer.parseInt(dimensions[0]);
            int newHeight = Integer.parseInt(dimensions[1]);
            MandelbrotSet.width = newWidth;
            MandelbrotSet.height = newHeight;

            MandelbrotSet.buffer = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        }
        if (line.hasOption("r")) {
            String[] dimensions = line.getOptionValue("r").split(":");

            float newRealLowerLimit = Float.parseFloat(dimensions[0]);
            float newRealUpperLimit = Float.parseFloat(dimensions[1]);
            float newImaginaryLowerLimit = Float.parseFloat(dimensions[2]);
            float newImaginaryUpperLimit = Float.parseFloat(dimensions[3]);

            MandelbrotSet.realLowerLimit = newRealLowerLimit;
            MandelbrotSet.realUpperLimit = newRealUpperLimit;
            MandelbrotSet.imaginaryLowerLimit = newImaginaryLowerLimit;
            MandelbrotSet.imaginaryUpperLimit = newImaginaryUpperLimit;
        }
        if (line.hasOption("t")) {
            MandelbrotSet.numberOfThreads = Integer.parseInt(line.getOptionValue("t"));
        }

        if (line.hasOption("o")) {
            MandelbrotSet.outputName = line.getOptionValue("o");
        }
        if (line.hasOption("g")) {
            MandelbrotSet.granularity = Integer.parseInt(line.getOptionValue("g"));
        }
        if (line.hasOption("q")) {
            MandelbrotSet.isQuiet = true;
        }
    }
}
