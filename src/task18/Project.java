package task18;


import org.apache.commons.cli.*;

public class Project {
    public static void main(String[] args) {
        long startProgram = System.currentTimeMillis();

        MandelbrotSet set = new MandelbrotSet();
        try {
            processArguments(args);
        } catch (ParseException e) {
            System.out.println("ParseException thrown.");
        }
        set.renderRowsWithMediumGranularity();

        if (!MandelbrotSet.isQuiet) {
            System.out.println("Threads used in current run: " + MandelbrotSet.numberOfThreads);
        }

        System.out.println("Total execution time for current run (millis): "
                + (System.currentTimeMillis() - startProgram));

        set.saveImage();
    }

    private static void processArguments(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("s", "sets the size of the image " +
                "(if missing or invalid, the default value is 640x480)");
        options.addOption("r", "sets the rectangle which will be observed" +
                "(if missing or invalid, the default value is -2.0:2.0:-2.0:2.0)");
        options.addOption("t", "sets the number of threads that will be executed " +
                "(if missing or invalid, the default value is 1)");
        options.addOption("o", "sets the name of the file saved after calculations are finished" +
                "(if missing or invalid, the default value is zad18.png)");
        options.addOption("q", "runs the program in quiet mode finished which hides all messages " +
                "excluding the execution time (if missing or invalid, the default value is zad18.png)");

        CommandLineParser parser = new DefaultParser();
        CommandLine line = parser.parse(options, args);

        if (line.hasOption("s")) {
            String[] dimensions = line.getOptionValue("s").split("x");
            int newWidth = Integer.parseInt(dimensions[0]);
            int newHeight = Integer.parseInt(dimensions[1]);
            MandelbrotSet.width = newWidth;
            MandelbrotSet.height = newHeight;
        }

        if (line.hasOption("r")) {
            String[] dimensions = line.getOptionValue("r").split(":");

            int newRealLowerLimit = Integer.parseInt(dimensions[0]);
            int newRealUpperLimit = Integer.parseInt(dimensions[1]);
            int newImaginaryLowerLimit = Integer.parseInt(dimensions[2]);
            int newImaginaryUpperLimit = Integer.parseInt(dimensions[3]);

            MandelbrotSet.realLowerLimit = newRealLowerLimit;
            MandelbrotSet.realUpperLimit = newRealUpperLimit;
            MandelbrotSet.imaginaryLowerLimit = newImaginaryLowerLimit;
            MandelbrotSet.imaginaryUpperLimit = newImaginaryUpperLimit;
        }

        if (line.hasOption("t")) {
            int newThreads = Integer.parseInt(line.getOptionValue("t"));

            MandelbrotSet.numberOfThreads = newThreads;
        }

        if (line.hasOption("o")) {
            MandelbrotSet.outputName = line.getOptionValue("o");
        }

        if (line.hasOption("q")) {
            MandelbrotSet.isQuiet = true;
        }
    }
}
