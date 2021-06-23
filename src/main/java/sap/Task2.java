package sap;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * This is the main driver class and is responsible for scheduling one call to
 * the application-state-controller every 1 minute for 30 minutes
 */

public class Task2 {

    private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void main(String args[]) {
        try {
        	// Name of the output file is specified to be MaintainanceModeLog.txt
            final String filename = "MaintainanceModeLog.txt";
            FileWriter openingNotes = new FileWriter(filename, true);
            // Printing the starting time of the application to the MaintainanceModeLog.txt file
            openingNotes.write("Application started at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + " - Monitoring has begun\n");
            openingNotes.close();

            final Runnable task = new Runnable() {
                public void run() {
                    try {
                        ExternalRestHelper.checkApplicationState();
                    } catch (Exception e) { // Any exception thrown is printed along with the time-stamp and absorbed in order to not hamper the code flow
                        System.out.println("Encountered exception inside run() at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
                        e.printStackTrace();
                    }
                }
            };

            // Scheduler is run initially after 1 minute, and subsequently after an interval of a minute
            final ScheduledFuture<?> executorHandle = executor.scheduleAtFixedRate(task, 1, 1, MINUTES);
            executor.schedule(new Runnable() {
                public void run() {
                    executorHandle.cancel(true);
                    try {
                        FileWriter endingNotes = new FileWriter(filename, true);
                        // Printing the ending time of the application to the MaintainanceModeLog.txt file
                        endingNotes.write("Application ended at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + " - Monitoring has stopped\n");
                        endingNotes.close();
                    } catch (Exception e) { // Any exception thrown is printed along with the time-stamp and absorbed in order to not hamper the code flow
                        System.out.println("Encounted exception inside executorHandle at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
                        e.printStackTrace();
                    }
                    executor.shutdown();
                }
            }, 30, MINUTES); // Time limit of 30 minutes is specified

        } catch (IOException e) {
            System.out.println("Encounted exception at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
            e.printStackTrace();
        }
    }
}