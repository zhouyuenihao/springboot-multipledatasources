package com.mzd.multipledatasources.datasource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author zhouyue01
 * @date 2022/8/11
 */
@ConfigurationProperties("customer-database")
@Component
public class Config {
    private Map<String, MetaData> dataSources;

    public Map<String, MetaData> getDataSources() {
        return dataSources;
    }

    public void setDataSources(Map<String, MetaData> dataSources) {
        this.dataSources = dataSources;
    }

    /**
     * @author zhouyue01
     */
    public static class MetaData {
        private boolean primary = false;
        private String url;
        private String username;
        private String password;
        private String driverClassName;

        public boolean isPrimary() {
            return primary;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getDriverClassName() {
            return driverClassName;
        }

        public void setDriverClassName(String driverClassName) {
            this.driverClassName = driverClassName;
        }
    }
}


