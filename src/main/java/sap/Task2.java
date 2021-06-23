package sap;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

public class Task2 {

    private final static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public static void main(String args[]) {
        try {
            final String filename = "MaintainanceModeLog.txt";
            FileWriter openingNotes = new FileWriter(filename, true);
            openingNotes.write("Application started at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + "- Monitoring has begun\n");
            openingNotes.close();

            final Runnable task = new Runnable() {
                public void run() {
                    try {
                        ExternalRestHelper.checkApplicationState();
                    } catch (Exception e) {
                        System.out.println("Encountered exception inside run() at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
                        e.printStackTrace();
                    }
                }
            };

            final ScheduledFuture<?> executorHandle = executor.scheduleAtFixedRate(task, 10, 10, MINUTES);
            executor.schedule(new Runnable() {
                public void run() {
                    executorHandle.cancel(true);
                    try {
                        FileWriter endingNotes = new FileWriter(filename, true);
                        endingNotes.write("Application ended at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + "- Monitoring has stopped\n");
                        endingNotes.close();
                    } catch (Exception e) {
                        System.out.println("Encounted exception inside executorHandle at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
                        e.printStackTrace();
                    }
                    executor.shutdown();
                }
            }, 40, MINUTES);

        } catch (IOException e) {
            System.out.println("Encounted exception at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
            e.printStackTrace();
        }
    }
}