<h1>Output of the primary task</h1>

The output of the primary task is contained in the file <a href = "https://github.com/tulitpal/tulit-sap-fortify/blob/main/MaintainanceModeLog.txt">MaintainanceModeLog.txt</a><br>
Data provided in the output file:
<table style="width:80%">
  <tr>
    <td>Application start time</td>
  </tr>
  <tr>
    <td>Monitoring details including timestamp and response only when maintenanceMode is not false</td>
  </tr>
  <tr>
    <td>Application end time</td>
  </tr>
</table>

This output was obtained after running the code for 30 minutes and hitting the application-state-controller after every 1 minute.

<h1>Details of the other classes</h1>
The <a href="https://github.com/tulitpal/tulit-sap-fortify/blob/main/src/main/java/sap/Task2.java">Task2.java</a> file is the main driver class and calls
<a href = "https://github.com/tulitpal/tulit-sap-fortify/blob/main/src/main/java/sap/ExternalRestHelper.java">ExternalRestHelper.java</a> at regular intervals
of 1 minute over a period of 30 minutes. The <a href="https://github.com/tulitpal/tulit-sap-fortify/blob/main/src/main/java/sap/ApplicationStateDto.java">ApplicationStateDto.java</a>
file is used to serialize and parse the response received from the <i>application-state-controller</i>. Finally the value of <b>maintenanceMode</b> is checked
and if it is <b>true</b>, an entry is made into <a href = "https://github.com/tulitpal/tulit-sap-fortify/blob/main/MaintainanceModeLog.txt">MaintainanceModeLog.txt</a>.
