package sap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Task2 {

	public static void main(String args[]) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			System.out.println("Please enter the token:\n");
			String token = "FortifyToken " + br.readLine().trim();
			System.out.println("token: " + token);
		} catch (IOException e) {
			System.out.println("Encounted error while making REST call to create user (Task 1)");
			e.printStackTrace();
		}
	}
}