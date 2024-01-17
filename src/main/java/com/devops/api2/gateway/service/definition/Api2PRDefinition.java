package com.devops.api2.gateway.service.definition;


import com.devops.api2.gateway.config.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:internal-api-config.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "api.pr")
public class Api2PRDefinition {

    private String baseUrl;
    private String getVendorBuyerInfo;
    private String getVendorNomalInfo;
    private String getVendorManagerInfo;
    private String getContractInfo;
    private String getContractMonthlyPayInfo;
    private String getFIPurchaseCost;
    private String getBusinessIncomePayConfirmInfo;
    private String doBusinessIncomePayConfirm;

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getGetVendorBuyerInfo() {
        return getVendorBuyerInfo;
    }

    public void setGetVendorBuyerInfo(String getVendorBuyerInfo) {
        this.getVendorBuyerInfo = getVendorBuyerInfo;
    }

    public String getGetVendorNomalInfo() {
        return getVendorNomalInfo;
    }

    public void setGetVendorNomalInfo(String getVendorNomalInfo) {
        this.getVendorNomalInfo = getVendorNomalInfo;
    }

    public String getGetVendorManagerInfo() {
        return getVendorManagerInfo;
    }

    public void setGetVendorManagerInfo(String getVendorManagerInfo) {
        this.getVendorManagerInfo = getVendorManagerInfo;
    }

    public String getGetContractInfo() {
        return getContractInfo;
    }

    public void setGetContractInfo(String getContractInfo) {
        this.getContractInfo = getContractInfo;
    }

    public String getGetContractMonthlyPayInfo() {
        return getContractMonthlyPayInfo;
    }

    public void setGetContractMonthlyPayInfo(String getContractMonthlyPayInfo) {
        this.getContractMonthlyPayInfo = getContractMonthlyPayInfo;
    }

    public String getGetFIPurchaseCost() {
        return getFIPurchaseCost;
    }

    public void setGetFIPurchaseCost(String getFIPurchaseCost) {
        this.getFIPurchaseCost = getFIPurchaseCost;
    }

    public String getGetBusinessIncomePayConfirmInfo() {
        return getBusinessIncomePayConfirmInfo;
    }

    public void setGetBusinessIncomePayConfirmInfo(String getBusinessIncomePayConfirmInfo) {
        this.getBusinessIncomePayConfirmInfo = getBusinessIncomePayConfirmInfo;
    }

    public String getDoBusinessIncomePayConfirm() {
        return doBusinessIncomePayConfirm;
    }

    public void setDoBusinessIncomePayConfirm(String doBusinessIncomePayConfirm) {
        this.doBusinessIncomePayConfirm = doBusinessIncomePayConfirm;
    }
}
