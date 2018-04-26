package erpv2;


public class App_mf_spare_parts {
    
    private int device_id;
    private String device_name;
    private String dealer;
    private String location;
    private String date;
    private String state;
    private String type;
    private String notice;
    
    public App_mf_spare_parts(int partDeviceId, String partDeviceName, String partDealer,
            String partLocation, String partDate, String partState,
            String partType, String partNotice) {
        this.device_id = partDeviceId;
        this.device_name = partDeviceName;
        this.dealer = partDealer;
        this.location = partLocation;
        this.date = partDate;
        this.state = partState;
        this.type = partType;
        this.notice = partNotice;
    }
    
    public int getPartDevice_id() {
        return device_id;
    }    
    public String getPartDevice_name() {
        return device_name;
    }    
    public String getPartDealer() {
        return dealer;
    }    
    public String getPartLocation() {
        return location;
    }    
    public String getPartDate() {
        return date;
    }    
    public String getPartState() {
        return state;
    }    
    public String getPartType() {
        return type;
    }
    public String getPartNotice() {
        return notice;
    }
    
}
