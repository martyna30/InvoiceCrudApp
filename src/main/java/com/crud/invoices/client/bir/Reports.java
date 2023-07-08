package com.crud.invoices.client.bir;

import org.springframework.stereotype.Component;

@Component
public class Reports {

    //private String generalReport;
    //private String pkdReport;
    private String basicData;
    //private String additionalReport;

    public Reports() {}
    //public Reports(String basicData) {
        //this.basicData = basicData;
   // }
    public Reports(String basicData) {
        this.basicData = basicData;
    }



    /*public String getGeneralReport() {
        return generalReport;
    }

    public String getPkdReport() {
        return pkdReport;
    }*/

    public String getBasicData() {
        return basicData;
    }

    public void setBasicData(String basicData) {
        this.basicData = basicData;
    }

    /*public String getAdditionalReport() {
        return additionalReport;
    }

    public boolean hasAdditionalData() {
        return null != additionalReport && additionalReport.length() > 0;
    }*/
}
