package com.devops.api2.gateway.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "gateway_logs")
public class GatewayLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_id")
    private String requestId;

    @Column(name = "request_path")
    private String requestPath;

    @Column(name = "request_queryparam")
    private String requestQueryParam;
    @Column(name = "request_bodyparam")
    private String requestBodyParam;
    @Column(name = "request_method")
    private String requestMethod;

    @Column(name="request_header")
    private String requestHeader;

    @Column(name = "response_status")
    private Integer responseStatus;

    @Column(name = "remote_address")
    private String remoteAddress;

    @Column(name = "host_name")
    private String hostName;

    @Column(name = "regdate")
    private LocalDateTime timestamp = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }


    public String getRequestQueryParam() {
        return requestQueryParam;
    }

    public void setRequestQueryParam(String requestQueryParam) {
        this.requestQueryParam = requestQueryParam;
    }

    public String getRequestBodyParam() {
        return requestBodyParam;
    }

    public void setRequestBodyParam(String requestBodyParam) {
        this.requestBodyParam = requestBodyParam;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public Integer getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(Integer responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getRemoteAddress() {
        return remoteAddress;
    }

    public void setRemoteAddress(String remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
