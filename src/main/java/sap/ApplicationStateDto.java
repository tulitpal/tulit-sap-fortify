package sap;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationStateDto implements Serializable {

  /**
   * Example of JSON
   *
   * {
   *  "data": {
   *    "restartRequired": false,
   *    "configVisitRequired": false,
   *    "maintenanceMode": true,
   *    "autoConfigurationMode": true
   *  },
   *  "responseCode": 200
   *}
   */

    private static final long serialVersionUID = 4257111302198542994L;

    private Data data;

    private Integer responseCode;

    public ApplicationStateDto() {
        super();
    }

    public ApplicationStateDto(Data data, Integer responseCode) {
        super();
        this.data = data;
        this.responseCode = responseCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    @JsonInclude(Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    class Data implements Serializable {

        private static final long serialVersionUID = -7814025847460126150L;

        private Boolean restartRequired;

        private Boolean configVisitRequired;

        private Boolean maintenanceMode;

        private Boolean autoConfigurationMode;

        public Data() {
            super();
        }

        public Data(Boolean restartRequired, Boolean configVisitRequired, Boolean maintenanceMode,
                Boolean autoConfigurationMode) {
            super();
            this.restartRequired = restartRequired;
            this.configVisitRequired = configVisitRequired;
            this.maintenanceMode = maintenanceMode;
            this.autoConfigurationMode = autoConfigurationMode;
        }

        public Boolean getRestartRequired() {
            return restartRequired;
        }

        public void setRestartRequired(Boolean restartRequired) {
            this.restartRequired = restartRequired;
        }

        public Boolean getConfigVisitRequired() {
            return configVisitRequired;
        }

        public void setConfigVisitRequired(Boolean configVisitRequired) {
            this.configVisitRequired = configVisitRequired;
        }

        public Boolean getMaintenanceMode() {
            return maintenanceMode;
        }

        public void setMaintenanceMode(Boolean maintenanceMode) {
            this.maintenanceMode = maintenanceMode;
        }

        public Boolean getAutoConfigurationMode() {
            return autoConfigurationMode;
        }

        public void setAutoConfigurationMode(Boolean autoConfigurationMode) {
            this.autoConfigurationMode = autoConfigurationMode;
        }
    }
}