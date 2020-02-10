package dlms;

public class SimSettings {

    // set the default values here

    // Server Settings
    private static final String defalutServerList = "./inputs/devices.csv";
    private static int dataGatherSleepTime = 8000;

    // Simulation Settings
    private static int physicalDeviceCount = 5;

    // Physical Device Settings
    private static int logicalDeviceStartIndex = 20;
    private static int logicalDeviceCount = 3;

    // Logical Device Settings
    private static int cosemObjectCount = 5;
    private static int cosemObjectStartIndex = 0;
    private static String obisFormat = "1.%d.12.0.0.255";

    // Cosem Object Settings
    private static int cosemClassId = 99; // can not be changed since we have created our objects with this class id
    private static int changeRate = 500;
    private static float volMin = 4.5f;
    private static float volMax = 5.5f;


    // Database Settings
    // "mongodb+srv://aicam:021021@loraserver-g7s1o.azure.mongodb.net/test?retryWrites=true&w=majority"
    private static String mongoAddress = "mongodb+srv://aicam:021021@loraserver-g7s1o.azure.mongodb.net/test?retryWrites=true&w=majority";
    private static String databaseName = "dlms";
    private static String collectionName = "eu4725";

    //
    // Getters and Setters
    //


    public static String getDefalutServerList() {
        return defalutServerList;
    }

    public static int getCosemObjectStartIndex() {
        return cosemObjectStartIndex;
    }

    public static void setCosemObjectStartIndex(int cosemObjectStartIndex) {
        SimSettings.cosemObjectStartIndex = cosemObjectStartIndex;
    }

    public static int getDataGatherSleepTime() {
        return dataGatherSleepTime;
    }

    public static void setDataGatherSleepTime(int dataGatherSleepTime) {
        SimSettings.dataGatherSleepTime = dataGatherSleepTime;
    }

    public static int getPhysicalDeviceCount() {
        return physicalDeviceCount;
    }

    public static void setPhysicalDeviceCount(int physicalDeviceCount) {
        SimSettings.physicalDeviceCount = physicalDeviceCount;
    }

    public static int getLogicalDeviceStartIndex() {
        return logicalDeviceStartIndex;
    }

    public static void setLogicalDeviceStartIndex(int logicalDeviceStartIndex) {
        SimSettings.logicalDeviceStartIndex = logicalDeviceStartIndex;
    }

    public static int getLogicalDeviceCount() {
        return logicalDeviceCount;
    }

    public static void setLogicalDeviceCount(int logicalDeviceCount) {
        SimSettings.logicalDeviceCount = logicalDeviceCount;
    }

    public static int getCosemObjectCount() {
        return cosemObjectCount;
    }

    public static void setCosemObjectCount(int cosemObjectCount) {
        SimSettings.cosemObjectCount = cosemObjectCount;
    }

    public static String getObisFormat() {
        return obisFormat;
    }

    public static void setObisFormat(String obisFormat) {
        SimSettings.obisFormat = obisFormat;
    }

    public static int getCosemClassId() {
        return cosemClassId;
    }

    public static void setCosemClassId(int cosemClassId) {
        SimSettings.cosemClassId = cosemClassId;
    }

    public static int getChangeRate() {
        return changeRate;
    }

    public static void setChangeRate(int changeRate) {
        SimSettings.changeRate = changeRate;
    }

    public static float getVolMin() {
        return volMin;
    }

    public static void setVolMin(float volMin) {
        SimSettings.volMin = volMin;
    }

    public static float getVolMax() {
        return volMax;
    }

    public static void setVolMax(float volMax) {
        SimSettings.volMax = volMax;
    }

    public static String getMongoAddress() {
        return mongoAddress;
    }

    public static void setMongoAddress(String mongoAddress) {
        SimSettings.mongoAddress = mongoAddress;
    }

    public static String getDatabaseName() {
        return databaseName;
    }

    public static void setDatabaseName(String databaseName) {
        SimSettings.databaseName = databaseName;
    }

    public static String getCollectionName() {
        return collectionName;
    }

    public static void setCollectionName(String collectionName) {
        SimSettings.collectionName = collectionName;
    }
}
