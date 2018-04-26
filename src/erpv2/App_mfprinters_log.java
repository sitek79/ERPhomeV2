package erpv2;


public class App_mfprinters_log {
    private int action_id;
    private String action;
    private String dealer;
    private String date;
    private String toner_cartridge;
    private String drum_cartridge;
    private String roller;
    private String waste_toner_container;
    private int count;
    private String notice;
    
    public App_mfprinters_log(int logActionId, String logAction, String logDealer,
            String logDate, String logToner, String logDrum,
            String logRoller, String logWaste_toner, int logCount, String logNotice) {
        this.action_id = logActionId;
        this.action = logAction;
        this.dealer = logDealer;
        this.date = logDate;
        this.toner_cartridge = logToner;
        this.drum_cartridge = logDrum;
        this.roller = logRoller;
        this.waste_toner_container = logWaste_toner;
        this.count = logCount;
        this.notice = logNotice;
    }
    
    public int getLogActionId() {
        return action_id;
    }    
    public String getLogAction() {
        return action;
    }    
    public String getLogDealer() {
        return dealer;
    }    
    public String getLogDate() {
        return date;
    }    
    public String getLogToner() {
        return toner_cartridge;
    }    
    public String getLogDrum() {
        return drum_cartridge;
    }    
    public String getLogRoller() {
        return roller;
    }
    public String getLogWaste_toner() {
        return waste_toner_container;
    }
    public int getLogCount() {
        return count;
    }
    public String getLogNotice() {
        return notice;
    }
    
}
