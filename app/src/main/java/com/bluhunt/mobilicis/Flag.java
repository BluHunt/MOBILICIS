package com.bluhunt.mobilicis;

public class Flag {

    private static Boolean batteryImageCheck  = Boolean.FALSE;
    private static Boolean modelImageCheck = Boolean.FALSE;
    private static Boolean cameraImageCheck = Boolean.FALSE;
    private static Boolean storageImageCheck = Boolean.FALSE;
    private static Boolean cpugpuImageCheck = Boolean.FALSE;
    private static Boolean sensorImageCheck = Boolean.FALSE;

    public static Boolean getBatteryImageCheck() {
        return batteryImageCheck;
    }

    public static void setBatteryImageCheck(Boolean batteryImageCheck) {
        Flag.batteryImageCheck = batteryImageCheck;
    }

    public static Boolean getModelImageCheck() {
        return modelImageCheck;
    }

    public static void setModelImageCheck(Boolean modelImageCheck) {
        Flag.modelImageCheck = modelImageCheck;
    }

    public static Boolean getCameraImageCheck() {
        return cameraImageCheck;
    }

    public static void setCameraImageCheck(Boolean cameraImageCheck) {
        Flag.cameraImageCheck = cameraImageCheck;
    }

    public static Boolean getStorageImageCheck() {
        return storageImageCheck;
    }

    public static void setStorageImageCheck(Boolean storageImageCheck) {
        Flag.storageImageCheck = storageImageCheck;
    }

    public static Boolean getCpugpuImageCheck() {
        return cpugpuImageCheck;
    }

    public static void setCpugpuImageCheck(Boolean cpugpuImageCheck) {
        Flag.cpugpuImageCheck = cpugpuImageCheck;
    }

    public static Boolean getSensorImageCheck() {
        return sensorImageCheck;
    }

    public static void setSensorImageCheck(Boolean sensorImageCheck) {
        Flag.sensorImageCheck = sensorImageCheck;
    }
}
