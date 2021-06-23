package sap;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ExternalRestHelper {

	public static void checkApplicationState() throws IOException {
		String command = "curl -X GET \"https://mini.fortify-dev.tools.sap/ssc/api/v1/applicationState\" -H \"accept: application/json\" -H \"Authorization: FortifyToken YmQwMzA5Y2QtYzJhNy00YTQ1LWE3Y2ItZGIwYWM2ZmNhZDVh\"";// "curl
		Process process = Runtime.getRuntime().exec(command);

		InputStream inputStream = process.getInputStream();
		String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
		System.out.println("Response received: " + text);
		ApplicationStateDto dto = new ObjectMapper().readValue(text, ApplicationStateDto.class);
		if (dto.getData().getMaintenanceMode()) {
			String filename = "MaintainanceModeLog.txt";
			FileWriter fw = new FileWriter(filename, true);
			String log = "ALERT CREATED ||| Received at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + " ||| Response from application-state-controller: " + text;
			System.out.println(log);
			fw.write(log + "\n");
			fw.close();
		}
	}
}