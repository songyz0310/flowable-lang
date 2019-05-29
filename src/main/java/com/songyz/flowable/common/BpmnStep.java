package com.songyz.flowable.common;

import java.util.Objects;

import com.songyz.flowable.ProcessUtil;

public class BpmnStep {
    private String stepKey;
    private String type;
    private String stepName;

    public BpmnStep() {
    }

    public BpmnStep(String id, String type, String stepName) {
        this.stepKey = id;
        this.type = type;
        this.stepName = stepName;
    }

    public String getStepKey() {
        return stepKey;
    }

    public void setStepKey(String stepKey) {
        this.stepKey = stepKey;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    @Override
    public int hashCode() {
        if (Objects.equals(type, ProcessUtil.END_EVENT)) {
            return -1;
        }
        else {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((stepKey == null) ? 0 : stepKey.hashCode());
            result = prime * result + ((type == null) ? 0 : type.hashCode());
            return result;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        BpmnStep other = (BpmnStep) obj;

        if (Objects.equals(other.type, ProcessUtil.END_EVENT) && Objects.equals(this.type, ProcessUtil.END_EVENT)) {
            return true;
        }
        else {
            if (stepKey == null) {
                if (other.stepKey != null)
                    return false;
            }
            else if (!stepKey.equals(other.stepKey))
                return false;
            if (type == null) {
                if (other.type != null)
                    return false;
            }
            else if (!type.equals(other.type))
                return false;
            return true;
        }

    }

}
