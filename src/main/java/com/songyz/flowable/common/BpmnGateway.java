package com.songyz.flowable.common;

import java.util.List;

public class BpmnGateway {

    public static class GatewayCondition {
        private String name;
        private String value;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

    }

    private String name;
    private String key;
    private List<GatewayCondition> conditions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<GatewayCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<GatewayCondition> conditions) {
        this.conditions = conditions;
    }

}
