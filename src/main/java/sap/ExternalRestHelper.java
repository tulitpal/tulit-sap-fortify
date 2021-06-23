package sap;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the class that is called at regular intervals to make REST calls to the application-state-controller
 */

public class ExternalRestHelper {

    public static void checkApplicationState() throws IOException {
    	// The CURL command is specified along with the CIToken generated earlier
        String command = "curl -X GET \"https://mini.fortify-dev.tools.sap/ssc/api/v1/applicationState\" -H \"accept: application/json\" -H \"Authorization: FortifyToken YmQwMzA5Y2QtYzJhNy00YTQ1LWE3Y2ItZGIwYWM2ZmNhZDVh\"";// "curl
        Process process = Runtime.getRuntime().exec(command);

        InputStream inputStream = process.getInputStream();
        String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        System.out.println("Response received: " + text); // Every response from the application-state-controller is printed
        ApplicationStateDto dto = new ObjectMapper().readValue(text, ApplicationStateDto.class);
        if (dto.getData().getMaintenanceMode()) { // The response is serialized and the value of maintenanceMode is checked
            String filename = "MaintainanceModeLog.txt";
            FileWriter fw = new FileWriter(filename, true); // The file is opened in append mode
            String log = "ALERT CREATED ||| Received at " + DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now()) + " ||| Response from application-state-controller: " + text;
            System.out.println(log);
            fw.write(log + "\n"); // An entry is made every time the value of maintenanceMode is true
            fw.close();
        }
    }
}