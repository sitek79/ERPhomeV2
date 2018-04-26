package erpv2;


public class App_mfprinters {
    
    private int device_id;
    private String device_name;
    private String dealer;
    private String location;
    private String date;
    private String state;
    private String toner_cartridge;
    private String drum_cartridge;
    private String roller;
    private String waste_toner_container;
    private String notice;
    
    public App_mfprinters(int printersDeviceId, String printersDeviceName, String printersDealer,
            String printersLocation, String printersDate, String printersState,
            String printersCartridge, String printersDrum, String printersRoller,
            String printersWaste_toner, String printersNotice) {
        this.device_id = printersDeviceId;
        this.device_name = printersDeviceName;
        this.dealer = printersDealer;
        this.location = printersLocation;
        this.date = printersDate;
        this.state = printersState;
        this.toner_cartridge = printersCartridge;
        this.drum_cartridge = printersDrum;
        this.roller = printersRoller;
        this.waste_toner_container = printersWaste_toner;
        this.notice = printersNotice;
    }
    
    public int getPrintersDevice_id() {
        return device_id;
    }    
    public String getPrintersDevice_name() {
        return device_name;
    }    
    public String getPrintersDealer() {
        return dealer;
    }    
    public String getPrintersLocation() {
        return location;
    }    
    public String getPrintersDate() {
        return date;
    }    
    public String getPrintersState() {
        return state;
    }    
    public String getPrintersCartridge() {
        return toner_cartridge;
    }
    public String getPrintersDrum() {
        return drum_cartridge;
    }
    public String getPrintersRoller() {
        return roller;
    }
    public String getPrintersWasteToner() {
        return waste_toner_container;
    }
    public String getPrintersNotice() {
        return notice;
    }
}
