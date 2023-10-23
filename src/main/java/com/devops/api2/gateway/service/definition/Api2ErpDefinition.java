package com.devops.api2.gateway.service.definition;

import com.devops.api2.gateway.config.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:internal-api-config.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "api.erp")
public class Api2ErpDefinition {
    private String baseUrl;
    private String baseInfo;
    private String dept;
    private String company;
    private String acntinfo;
    private String acntsmap;
    private String arcollects;
    private String vendorbonds ;
    private String slipinfos;
    private String vendors;
    private String vendorscharges;
    private String magaminfocenerp;
    private String magamvendorbonds;
    private String orderdeptmove;

    private String dirctpersexp;
    private String deptacntinfo;
    private String magaminfofinalcenerp;
    private String projectamtinfo;

    private String magaminfocenpcs;
    private String taxes;
    private String magamplbonds;
    private String hometaxstatus;

    private String costprojectinfo;

    private String reversetaxes;

    private String deptpersexp;
    private String exchrateinfo;

    private String exchrateinfo2;



    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(String baseInfo) {
        this.baseInfo = baseInfo;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getAcntinfo() {
        return acntinfo;
    }

    public void setAcntinfo(String acntinfo) {
        this.acntinfo = acntinfo;
    }

    public String getAcntsmap() {
        return acntsmap;
    }

    public void setAcntsmap(String acntsmap) {
        this.acntsmap = acntsmap;
    }

    public String getArcollects() {
        return arcollects;
    }

    public void setArcollects(String arcollects) {
        this.arcollects = arcollects;
    }

    public String getVendorbonds() {
        return vendorbonds;
    }

    public void setVendorbonds(String vendorbonds) {
        this.vendorbonds = vendorbonds;
    }

    public String getSlipinfos() {
        return slipinfos;
    }

    public void setSlipinfos(String slipinfos) {
        this.slipinfos = slipinfos;
    }

    public String getVendors() {
        return vendors;
    }

    public void setVendors(String vendors) {
        this.vendors = vendors;
    }

    public String getVendorscharges() {
        return vendorscharges;
    }

    public void setVendorscharges(String vendorscharges) {
        this.vendorscharges = vendorscharges;
    }

    public String getMagaminfocenerp() {
        return magaminfocenerp;
    }

    public void setMagaminfocenerp(String magaminfocenerp) {
        this.magaminfocenerp = magaminfocenerp;
    }

    public String getMagamvendorbonds() {
        return magamvendorbonds;
    }

    public void setMagamvendorbonds(String magamvendorbonds) {
        this.magamvendorbonds = magamvendorbonds;
    }

    public String getOrderdeptmove() {
        return orderdeptmove;
    }

    public void setOrderdeptmove(String orderdeptmove) {
        this.orderdeptmove = orderdeptmove;
    }

    public String getDirctpersexp() {
        return dirctpersexp;
    }

    public void setDirctpersexp(String dirctpersexp) {
        this.dirctpersexp = dirctpersexp;
    }

    public String getDeptacntinfo() {
        return deptacntinfo;
    }

    public void setDeptacntinfo(String deptacntinfo) {
        this.deptacntinfo = deptacntinfo;
    }

    public String getMagaminfofinalcenerp() {
        return magaminfofinalcenerp;
    }

    public void setMagaminfofinalcenerp(String magaminfofinalcenerp) {
        this.magaminfofinalcenerp = magaminfofinalcenerp;
    }

    public String getProjectamtinfo() {
        return projectamtinfo;
    }

    public void setProjectamtinfo(String projectamtinfo) {
        this.projectamtinfo = projectamtinfo;
    }

    public String getMagaminfocenpcs() {
        return magaminfocenpcs;
    }

    public void setMagaminfocenpcs(String magaminfocenpcs) {
        this.magaminfocenpcs = magaminfocenpcs;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public String getMagamplbonds() {
        return magamplbonds;
    }

    public void setMagamplbonds(String magamplbonds) {
        this.magamplbonds = magamplbonds;
    }

    public String getHometaxstatus() {
        return hometaxstatus;
    }

    public void setHometaxstatus(String hometaxstatus) {
        this.hometaxstatus = hometaxstatus;
    }

    public String getCostprojectinfo() {
        return costprojectinfo;
    }

    public void setCostprojectinfo(String costprojectinfo) {
        this.costprojectinfo = costprojectinfo;
    }

    public String getReversetaxes() {
        return reversetaxes;
    }

    public void setReversetaxes(String reversetaxes) {
        this.reversetaxes = reversetaxes;
    }

    public String getDeptpersexp() {
        return deptpersexp;
    }

    public void setDeptpersexp(String deptpersexp) {
        this.deptpersexp = deptpersexp;
    }

    public String getExchrateinfo() {
        return exchrateinfo;
    }

    public void setExchrateinfo(String exchrateinfo) {
        this.exchrateinfo = exchrateinfo;
    }


    public String getExchrateinfo2() {
        return exchrateinfo2;
    }

    public void setExchrateinfo2(String exchrateinfo2) {
        this.exchrateinfo2 = exchrateinfo2;
    }

}
