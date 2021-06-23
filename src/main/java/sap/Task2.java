package sap;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Task2 {

	public static void main(String args[]) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Please enter the token:\n");
			String token = "FortifyToken " + br.readLine().trim();
			System.out.println("token: " + token);
			ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
			String filename = "MaintainanceModeLog.txt";
			FileWriter fw = new FileWriter(filename, true);
			fw.write("Application started from " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + " Monitoring has begun\n");
			fw.close();
			Runnable task = new Runnable() {
				
				public void run() {
					try {
						ExternalRestHelper.checkApplicationState();
					} catch (IOException e) {
						System.out.println("Encountered exception at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()));
						e.printStackTrace();
					}
				}
			};   
			executor.schedule(task, 60, TimeUnit.SECONDS);
		} catch (IOException e) {
			System.out.println("Encounted error while making REST call to create user (Task 1)");
			e.printStackTrace();
		}
	}
}