package com.github.lmoraes7.tcc.uva.recruitment.selection.infrastructure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "config.password.change.request")
public class ConfigPasswordChangeRequestProperties {
    private Long limitTime;

    public Long getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(Long limitTime) {
        this.limitTime = limitTime;
    }

}
