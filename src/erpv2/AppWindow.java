package erpv2;

import calendar.CalendarTest;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class AppWindow extends javax.swing.JFrame {

    /**
     * Creates new form AppWindow
     */
    public AppWindow() {
        initComponents();
        
        //Show_Products_In_JTable();
        Show_Data_In_JTable_mfprinters();
        //Show_Data_In_JTable_mf_spare_parts();        
        //Show_Data_In_JTable_mfprinters_log();
    }
    
    /*
    String ImgPath = null;
    // переменная указывает начало списка в таблице
    int pos = 0;
    */
    //соединение с базой MySQL
    public Connection getConnection() {
        Connection con = null;
        try {
            // 172.24.225.222/erpdb","erpuser", "linAdmin79!!!
            con = DriverManager.getConnection("jdbc:mysql://172.24.225.222/erpdb","erpuser","linAdmin79!!!");
            //con = DriverManager.getConnection("jdbc:mysql://192.168.0.171/erpdb","erpdbusr", "U><er!!!123");
            /*Убрал всплывающее окно Connected          
            JOptionPane.showMessageDialog(null,"Connected");
            */
            
            return con;
        } catch (SQLException ex) {
            Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, ex);
            /*Убрал всплывающее окно Not Connected            
            JOptionPane.showMessageDialog(null,"Not Connected");
            */            
            return null;
        }
    }
    
    // Check Input Fields Printers
    public boolean checkInputsPrinters() {
        if (txt_Printers_device_name.getText() == null
                || txt_Printers_dealer.getText() == null
                || txt_Printers_location.getText() == null
                || txt_Printers_date.getDate() == null) {
            System.out.println("Пожалуйста заполните все поля");
            return false;}
        else{return true;}
    }
    
    
    // Отображение данных из базы в таблице MFP JTable App_mfprinters
    // 1 - Заполнить ArrayList данными
    
    public ArrayList<App_mfprinters> getPrintersList()
    {
        ArrayList<App_mfprinters> dataPrintersList = new ArrayList<App_mfprinters>();
        Connection con = getConnection();
        String query = "SELECT * FROM mfprinters";
            
        Statement st;
        ResultSet rs;
            
        try {           
            st = con.createStatement();
            rs = st.executeQuery(query);
            App_mfprinters printers;
            
            while(rs.next())
            {
                printers = new App_mfprinters(rs.getInt("device_id"),rs.getString("device_name"),                        
                        rs.getString("dealer"),rs.getString("location"),rs.getString("date"),
                        rs.getString("state"),rs.getString("toner_cartridge"),rs.getString("drum_cartridge"),
                        rs.getString("roller"),rs.getString("waste_toner_container"),rs.getString("notice"));
                dataPrintersList.add(printers);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dataPrintersList;
    }
    
    // 2 - Populate The JTable
    // Show_Data_In_JTable_mfprinters();
    
    public void Show_Data_In_JTable_mfprinters()
    {
        ArrayList<App_mfprinters> list = getPrintersList();
        DefaultTableModel model = (DefaultTableModel)jTable_mfprinters.getModel();
        
        // очистка содержимого таблицы
        model.setRowCount(0);
        Object[] row = new Object[11];
        for(int i = 0; i < list.size(); i++)
        {
            row[0] = list.get(i).getPrintersDevice_id();
            row[1] = list.get(i).getPrintersDevice_name();
            row[2] = list.get(i).getPrintersDealer();
            row[3] = list.get(i).getPrintersLocation();
            row[4] = list.get(i).getPrintersDate();
            row[5] = list.get(i).getPrintersState();
            row[6] = list.get(i).getPrintersCartridge();
            row[7] = list.get(i).getPrintersDrum();
            row[8] = list.get(i).getPrintersRoller();
            row[9] = list.get(i).getPrintersWasteToner();
            row[10] = list.get(i).getPrintersNotice();           
            
            model.addRow(row);
        }        
    }
    
    //Показать данные в полях для ввода текста
    public void ShowItem(int index)
    {
            txt_Printers_device_id.setText(Integer.toString(getPrintersList().get(index).getPrintersDevice_id()));
            txt_Printers_device_name.setText(getPrintersList().get(index).getPrintersDevice_name());
            txt_Printers_dealer.setText(getPrintersList().get(index).getPrintersDealer());
            txt_Printers_location.setText(getPrintersList().get(index).getPrintersLocation());            
            txt_Printers_state.setText(getPrintersList().get(index).getPrintersState());
            txt_Printers_toner_cartridge.setText(getPrintersList().get(index).getPrintersCartridge());
            txt_Printers_drum_cartridge.setText(getPrintersList().get(index).getPrintersDrum());
            txt_Printers_roller.setText(getPrintersList().get(index).getPrintersRoller());
            txt_Printers_waste_toner.setText(getPrintersList().get(index).getPrintersWasteToner());
            txt_Printers_notice.setText(getPrintersList().get(index).getPrintersNotice());
        try {            
            Date addDate = null;
            addDate = new SimpleDateFormat("yyyy-MM-dd").parse((String)getPrintersList().get(index).getPrintersDate());
            txt_Printers_date.setDate(addDate);
        } catch (ParseException ex) {
            Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*здесь я сделал ошибку, было:
        lbl_image.setIcon(ResizeImage(null), getProductList().get(index).getImage()));
      
        lbl_image.setIcon(ResizeImage(null, getProductList().get(index).getImage()));
        */
        
    }
    
    // Отображение данных из базы в таблице MFP JTable App_mf_spare_parts
    // 1 - Заполнить ArrayList данными
    
    public ArrayList<App_mf_spare_parts> getPartsList()
    {
        ArrayList<App_mf_spare_parts> dataPartsList = new ArrayList<App_mf_spare_parts>();
        Connection con = getConnection();
        String query = "SELECT * FROM mf_spare_parts";
            
        Statement st;
        ResultSet rs;
            
        try {           
            st = con.createStatement();
            rs = st.executeQuery(query);
            App_mf_spare_parts parts;
            
            while(rs.next())
            {
                parts = new App_mf_spare_parts(rs.getInt("device_id"),rs.getString("device_name"),                        
                        rs.getString("dealer"),rs.getString("location"),rs.getString("date"),
                        rs.getString("state"),rs.getString("type"),rs.getString("notice"));                        
                dataPartsList.add(parts);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dataPartsList;
    }
    
    // 2 - Populate The JTable
    //Show_Data_In_JTable_mf_spare_parts();    
    public void Show_Data_In_JTable_mf_spare_parts() {
        ArrayList<App_mf_spare_parts> list = getPartsList();
        DefaultTableModel model = (DefaultTableModel)jTable_mf_spare_parts.getModel();
        
        // очистка содержимого таблицы
        model.setRowCount(0);
        Object[] row = new Object[8];
        for(int i = 0; i < list.size(); i++)
        {
            row[0] = list.get(i).getPartDevice_id();
            row[1] = list.get(i).getPartDevice_name();
            row[2] = list.get(i).getPartDealer();
            row[3] = list.get(i).getPartLocation();
            row[4] = list.get(i).getPartDate();
            row[5] = list.get(i).getPartState();
            row[6] = list.get(i).getPartType();
            row[7] = list.get(i).getPartNotice();                      
            
            model.addRow(row);
        }
        
    }
    // Отображение данных из базы в таблице MFP JTable App_mfprinters_log
    // 1 - Заполнить ArrayList данными
    
    public ArrayList<App_mfprinters_log> getPrintersLogList()
    {
        ArrayList<App_mfprinters_log> dataPrintersLogList = new ArrayList<App_mfprinters_log>();
        Connection con = getConnection();
        String query = "SELECT * FROM mfprinters_log";
            
        Statement st;
        ResultSet rs;
            
        try {           
            st = con.createStatement();
            rs = st.executeQuery(query);
            App_mfprinters_log printersLogList;
            
            while(rs.next())
            {
                printersLogList = new App_mfprinters_log(rs.getInt("action_id"),rs.getString("action"),                        
                        rs.getString("dealer"),rs.getString("date"),rs.getString("toner_cartridge"),rs.getString("drum_cartridge"),
                        rs.getString("roller"),rs.getString("waste_toner_container"),rs.getInt("count"),
                        rs.getString("notice"));
                dataPrintersLogList.add(printersLogList);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(AppWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return dataPrintersLogList;
    }
    // 2 - Populate The JTable
    //Show_Data_In_JTable_mfprinters_log();    
    public void Show_Data_In_JTable_mfprinters_log() {
        ArrayList<App_mfprinters_log> list = getPrintersLogList();
        DefaultTableModel model = (DefaultTableModel)jTable_mfprinters_log.getModel();
        
        // очистка содержимого таблицы
        model.setRowCount(0);
        Object[] row = new Object[11];
        for(int i = 0; i < list.size(); i++)
        {
            row[0] = list.get(i).getLogActionId();
            row[1] = list.get(i).getLogAction();
            row[2] = list.get(i).getLogDealer();
            row[3] = list.get(i).getLogDate();
            row[4] = list.get(i).getLogToner();
            row[5] = list.get(i).getLogDrum();
            row[6] = list.get(i).getLogRoller();
            row[7] = list.get(i).getLogWaste_toner();
            row[8] = list.get(i).getLogCount();
            row[9] = list.get(i).getLogNotice();                       
            
            model.addRow(row);
        }
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        button1 = new java.awt.Button();
        button2 = new java.awt.Button();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_mfprinters = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        txt_Printers_device_id = new javax.swing.JTextField();
        txt_Printers_device_name = new javax.swing.JTextField();
        txt_Printers_dealer = new javax.swing.JTextField();
        txt_Printers_location = new javax.swing.JTextField();
        txt_Printers_state = new javax.swing.JTextField();
        txt_Printers_drum_cartridge = new javax.swing.JTextField();
        txt_Printers_roller = new javax.swing.JTextField();
        txt_Printers_toner_cartridge = new javax.swing.JTextField();
        txt_Printers_notice = new javax.swing.JTextField();
        txt_Printers_waste_toner = new javax.swing.JTextField();
        txt_Printers_date = new com.toedter.calendar.JDateChooser();
        btn_Printers_insert = new javax.swing.JButton();
        btn_Printers_refresh = new javax.swing.JButton();
        btn_Printers_delete = new javax.swing.JButton();
        btn_Printers_next = new javax.swing.JButton();
        btn_Printers_first = new javax.swing.JButton();
        btn_Printers_back = new javax.swing.JButton();
        btn_Printers_last = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_mf_spare_parts = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable_mfprinters_log = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jTextField21 = new javax.swing.JTextField();
        jTextField22 = new javax.swing.JTextField();
        jTextField24 = new javax.swing.JTextField();
        jTextField25 = new javax.swing.JTextField();
        jTextField26 = new javax.swing.JTextField();
        jTextField27 = new javax.swing.JTextField();
        jTextField28 = new javax.swing.JTextField();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jPanel7 = new javax.swing.JPanel();
        canvas1 = new java.awt.Canvas();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1280, 960));
        setResizable(false);

        jPanel1.setAlignmentX(0.1F);
        jPanel1.setAlignmentY(0.1F);
        jPanel1.setAutoscrolls(true);
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(1280, 724));

        jPanel2.setAlignmentX(0.1F);
        jPanel2.setAlignmentY(0.1F);
        jPanel2.setName(""); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(300, 43));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setFont(new java.awt.Font("Comic Sans MS", 0, 10)); // NOI18N

        button1.setLabel("button1");
        jToolBar1.add(button1);

        button2.setLabel("button2");
        jToolBar1.add(button2);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(jToolBar1, gridBagConstraints);

        jPanel3.setAlignmentX(0.1F);
        jPanel3.setAlignmentY(0.1F);

        jTabbedPane1.setAlignmentX(0.1F);
        jTabbedPane1.setAlignmentY(0.1F);
        jTabbedPane1.setMinimumSize(new java.awt.Dimension(800, 587));
        jTabbedPane1.setName("mfp"); // NOI18N
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(900, 966));

        jPanel4.setAlignmentX(0.1F);
        jPanel4.setAlignmentY(0.1F);
        jPanel4.setName("mfp"); // NOI18N
        jPanel4.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setAlignmentX(0.1F);
        jScrollPane1.setAlignmentY(0.1F);

        jTable_mfprinters.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "device_id", "device_name", "dealer", "location", "date", "состояние", "toner_cartridge", "drum_cartridge", "roller", "waste_toner_container", "notice"
            }
        ));
        jTable_mfprinters.setAlignmentX(0.1F);
        jTable_mfprinters.setAlignmentY(0.1F);
        jTable_mfprinters.setName(""); // NOI18N
        jScrollPane1.setViewportView(jTable_mfprinters);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1582;
        gridBagConstraints.ipady = 342;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel4.add(jScrollPane1, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Название:");
        jLabel1.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 38, 0, 0);
        jPanel4.add(jLabel1, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Локация:");
        jLabel2.setAlignmentX(0.1F);
        jLabel2.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 42, 0, 0);
        jPanel4.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Ролик:");
        jLabel3.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 81, 0, 0);
        jPanel4.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Дата:");
        jLabel4.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 64, 0, 0);
        jPanel4.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Сост.:");
        jLabel5.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 34, 0, 0);
        jPanel4.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Тонер:");
        jLabel6.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(19, 79, 0, 0);
        jPanel4.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Барабан:");
        jLabel7.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 69, 0, 0);
        jPanel4.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Поставщик:");
        jLabel8.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 29, 0, 0);
        jPanel4.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Бункер:");
        jLabel9.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 75, 0, 0);
        jPanel4.add(jLabel9, gridBagConstraints);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Комментарий:");
        jLabel10.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 39, 0, 0);
        jPanel4.add(jLabel10, gridBagConstraints);

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setText("ID устройства:");
        jLabel29.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(19, 10, 0, 0);
        jPanel4.add(jLabel29, gridBagConstraints);

        txt_Printers_device_id.setAlignmentX(0.1F);
        txt_Printers_device_id.setAlignmentY(0.1F);
        txt_Printers_device_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Printers_device_idActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.insets = new java.awt.Insets(17, 3, 0, 0);
        jPanel4.add(txt_Printers_device_id, gridBagConstraints);

        txt_Printers_device_name.setAlignmentX(0.1F);
        txt_Printers_device_name.setAlignmentY(0.1F);
        txt_Printers_device_name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Printers_device_nameActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        jPanel4.add(txt_Printers_device_name, gridBagConstraints);

        txt_Printers_dealer.setAlignmentX(0.1F);
        txt_Printers_dealer.setAlignmentY(0.1F);
        txt_Printers_dealer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Printers_dealerActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        jPanel4.add(txt_Printers_dealer, gridBagConstraints);

        txt_Printers_location.setAlignmentX(0.1F);
        txt_Printers_location.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        jPanel4.add(txt_Printers_location, gridBagConstraints);

        txt_Printers_state.setAlignmentX(0.1F);
        txt_Printers_state.setAlignmentY(0.1F);
        txt_Printers_state.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_Printers_stateActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        jPanel4.add(txt_Printers_state, gridBagConstraints);

        txt_Printers_drum_cartridge.setAlignmentX(0.1F);
        txt_Printers_drum_cartridge.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel4.add(txt_Printers_drum_cartridge, gridBagConstraints);

        txt_Printers_roller.setAlignmentX(0.1F);
        txt_Printers_roller.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel4.add(txt_Printers_roller, gridBagConstraints);

        txt_Printers_toner_cartridge.setAlignmentX(0.1F);
        txt_Printers_toner_cartridge.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.insets = new java.awt.Insets(17, 0, 0, 0);
        jPanel4.add(txt_Printers_toner_cartridge, gridBagConstraints);

        txt_Printers_notice.setAlignmentX(0.1F);
        txt_Printers_notice.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 0, 0);
        jPanel4.add(txt_Printers_notice, gridBagConstraints);

        txt_Printers_waste_toner.setAlignmentX(0.1F);
        txt_Printers_waste_toner.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 302;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        jPanel4.add(txt_Printers_waste_toner, gridBagConstraints);

        txt_Printers_date.setAlignmentX(0.1F);
        txt_Printers_date.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 109;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        jPanel4.add(txt_Printers_date, gridBagConstraints);

        btn_Printers_insert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/icons/check.png"))); // NOI18N
        btn_Printers_insert.setText("Вставить");
        btn_Printers_insert.setAlignmentY(0.1F);
        btn_Printers_insert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Printers_insertActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 14, 0, 13);
        jPanel4.add(btn_Printers_insert, gridBagConstraints);

        btn_Printers_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/icons/refresh_22.png"))); // NOI18N
        btn_Printers_refresh.setText("Обновить");
        btn_Printers_refresh.setAlignmentY(0.1F);
        btn_Printers_refresh.setName(""); // NOI18N
        btn_Printers_refresh.setPreferredSize(new java.awt.Dimension(109, 29));
        btn_Printers_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Printers_refreshActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 0, 0);
        jPanel4.add(btn_Printers_refresh, gridBagConstraints);

        btn_Printers_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/icons/delete_18.png"))); // NOI18N
        btn_Printers_delete.setText("Удалить");
        btn_Printers_delete.setAlignmentY(0.1F);
        btn_Printers_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Printers_deleteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.ipadx = 10;
        jPanel4.add(btn_Printers_delete, gridBagConstraints);

        btn_Printers_next.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/icons/next_20.png"))); // NOI18N
        btn_Printers_next.setText("Следующий");
        btn_Printers_next.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 6;
        jPanel4.add(btn_Printers_next, gridBagConstraints);

        btn_Printers_first.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/icons/first_20.png"))); // NOI18N
        btn_Printers_first.setText("Вначало");
        btn_Printers_first.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 7, 3, 7);
        jPanel4.add(btn_Printers_first, gridBagConstraints);

        btn_Printers_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/icons/back_20.png"))); // NOI18N
        btn_Printers_back.setText("Предыдущий");
        btn_Printers_back.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 11;
        jPanel4.add(btn_Printers_back, gridBagConstraints);

        btn_Printers_last.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pict/icons/last_20.png"))); // NOI18N
        btn_Printers_last.setText("Вконец");
        btn_Printers_last.setAlignmentY(0.1F);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 0, 0);
        jPanel4.add(btn_Printers_last, gridBagConstraints);

        jTabbedPane1.addTab("MFP", jPanel4);

        jPanel5.setAlignmentX(0.1F);
        jPanel5.setAlignmentY(0.1F);
        jPanel5.setName("mfp_parts"); // NOI18N

        jScrollPane2.setAlignmentX(0.1F);
        jScrollPane2.setAlignmentY(0.1F);

        jTable_mf_spare_parts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "device_id", "device_name", "dealer", "location", "date", "condition", "type", "notice"
            }
        ));
        jTable_mf_spare_parts.setAlignmentX(0.1F);
        jTable_mf_spare_parts.setAlignmentY(0.1F);
        jTable_mf_spare_parts.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_mf_spare_partsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_mf_spare_parts);

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Наименование:");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Дилер:");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("Локация:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Дата:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Кондиция:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Тип:");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setText("Комментарий:");

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setText("ID детали:");

        jTextField1.setText("jTextField1");

        jTextField2.setText("jTextField1");

        jTextField3.setText("jTextField1");

        jTextField4.setText("jTextField1");

        jTextField6.setText("jTextField1");

        jTextField7.setText("jTextField1");

        jTextField8.setText("jTextField1");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1265, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel30)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addComponent(jTextField4))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 329, Short.MAX_VALUE)
                            .addComponent(jTextField8)))
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jDateChooser2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Запчасти", jPanel5);

        jPanel6.setAlignmentX(0.1F);
        jPanel6.setAlignmentY(0.1F);
        jPanel6.setName("mfp_log"); // NOI18N

        jScrollPane3.setAlignmentX(0.1F);
        jScrollPane3.setAlignmentY(0.1F);

        jTable_mfprinters_log.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "action_id", "action", "dealer", "date", "toner_cartridge", "drum_cartridge", "roller", "waste_toner_container", "count", "notice"
            }
        ));
        jTable_mfprinters_log.setAlignmentX(0.1F);
        jTable_mfprinters_log.setAlignmentY(0.1F);
        jScrollPane3.setViewportView(jTable_mfprinters_log);

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel19.setText("ID операции:");
        jLabel19.setAlignmentY(0.1F);

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel20.setText("Операция:");
        jLabel20.setAlignmentY(0.1F);

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel21.setText("Поставщик:");
        jLabel21.setAlignmentY(0.1F);

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel22.setText("Дата:");
        jLabel22.setAlignmentY(0.1F);

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel23.setText("Тонер:");
        jLabel23.setAlignmentY(0.1F);

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel24.setText("Барабан:");
        jLabel24.setAlignmentY(0.1F);

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setText("Ролик");
        jLabel25.setAlignmentY(0.1F);

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel26.setText("Бункер:");

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setText("Количество:");

        jTextField20.setText("jTextField1");
        jTextField20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField20ActionPerformed(evt);
            }
        });

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setText("Комментарий:");

        jTextField21.setText("jTextField1");

        jTextField22.setText("jTextField1");

        jTextField24.setText("jTextField1");

        jTextField25.setText("jTextField1");

        jTextField26.setText("jTextField1");

        jTextField27.setText("jTextField1");

        jTextField28.setText("jTextField1");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(489, 489, 489)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22)
                            .addComponent(jLabel21)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField20, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField21, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField24)
                            .addComponent(jTextField22, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jDateChooser3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(51, 51, 51)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel24)
                                    .addComponent(jLabel27)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addGap(41, 41, 41)
                                .addComponent(jLabel28)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                            .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
                .addGap(616, 616, 616))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(jTextField25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextField21, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26)
                        .addComponent(jTextField26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(jTextField27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel22)
                        .addComponent(jLabel28)
                        .addComponent(jTextField28, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Протокол обслуживания", jPanel6);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1270, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 97, Short.MAX_VALUE))
        );

        canvas1.setBackground(new java.awt.Color(255, 51, 51));
        canvas1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        canvas1.setName("DB"); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 714, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable_mf_spare_partsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_mf_spare_partsMouseClicked
        // TODO add your handling code here:
        
        
    }//GEN-LAST:event_jTable_mf_spare_partsMouseClicked

    private void jTextField20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField20ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField20ActionPerformed

    private void btn_Printers_insertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Printers_insertActionPerformed
        // обработчик кнопки Вставить
        if(checkInputsPrinters() && txt_Printers_device_id.getText() != null)
        {
            try {
                Connection con = getConnection();
                //PreparedStatement ps = con.prepareStatement("INSERT INTO mfprinters(device_name, dealer, location, date, state, toner_cartridge, drum_cartridge, roller, waste_toner_container, notice)"
                //+ "values(?,?,?,?,?,?,?,?,?,?) ");
                PreparedStatement ps = con.prepareStatement("INSERT INTO mfprinters(device_name, dealer, location, date, state, toner_cartridge, drum_cartridge, roller, waste_toner_container, notice)"
                        + "VALUES('Kyocera','Onlinetrade','Koridor','26.04.2018','turn off','108R457345','108D457345',"108ROL457345","108BUN457345","In my otpusk period")");
                ps.setString(1, txt_Printers_device_name.getText());
                ps.setString(2, txt_Printers_dealer.getText());
                ps.setString(3, txt_Printers_location.getText());
                
                 // преобразуем формат даты из формы               
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String addDate = dateFormat.format(txt_Printers_date.getDate());
                ps.setString(4, addDate);
                
                ps.setString(5, txt_Printers_state.getText());
                ps.setString(6, txt_Printers_toner_cartridge.getText());
                ps.setString(7, txt_Printers_drum_cartridge.getText());
                ps.setString(8, txt_Printers_roller.getText());
                ps.setString(9, txt_Printers_waste_toner.getText());
                ps.setString(10, txt_Printers_notice.getText());
                
                /*
                InputStream img = new FileInputStream(new File(ImgPath));
                ps.setBlob(4, img);
                ps.executeUpdate();
                */
                System.out.println("Connection => "+con);
                System.out.println("Prepared statements => "+ps);
        
                Show_Data_In_JTable_mfprinters();                
                
                JOptionPane.showMessageDialog(null, "Данные добавлены");
                //System.out.println(device_id + device_name + dealer + location + date + condition + toner_cartridge + drum_cartridge + roller + waste_toner_container + notice);
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }else{
            JOptionPane.showMessageDialog(null, "Некоторые поля пустые");
        }
        
        System.out.println("Название => "+txt_Printers_device_name.getText());
        System.out.println("Поставщик => "+txt_Printers_dealer.getText());
        System.out.println("Локация => "+txt_Printers_location.getText());
        System.out.println("Дата => "+txt_Printers_date.getDate());
        System.out.println("Состояние => "+txt_Printers_state.getText());
        System.out.println("Тонер => "+txt_Printers_toner_cartridge.getText());
        System.out.println("Барабан => "+txt_Printers_drum_cartridge.getText());
        System.out.println("Ролик => "+txt_Printers_roller.getText());
        System.out.println("Бункер => "+txt_Printers_roller.getText());
        System.out.println("Комментарий => "+txt_Printers_notice.getText());
        
        System.out.println("checkInputsPrinters => "+checkInputsPrinters());
        
    }//GEN-LAST:event_btn_Printers_insertActionPerformed

    private void btn_Printers_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Printers_refreshActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_Printers_refreshActionPerformed

    private void btn_Printers_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Printers_deleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_Printers_deleteActionPerformed

    private void txt_Printers_stateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Printers_stateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Printers_stateActionPerformed

    private void txt_Printers_device_nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Printers_device_nameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Printers_device_nameActionPerformed

    private void txt_Printers_device_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Printers_device_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Printers_device_idActionPerformed

    private void txt_Printers_dealerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_Printers_dealerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_Printers_dealerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AppWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AppWindow().setVisible(true);
                //сообщение в консоли.
                System.out.println("Отображается форма");
            }
        });
        // выведем в консоль календарик. мне удобно
        CalendarTest cal = new CalendarTest();
        cal.calendarTest();
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_Printers_back;
    private javax.swing.JButton btn_Printers_delete;
    private javax.swing.JButton btn_Printers_first;
    private javax.swing.JButton btn_Printers_insert;
    private javax.swing.JButton btn_Printers_last;
    private javax.swing.JButton btn_Printers_next;
    private javax.swing.JButton btn_Printers_refresh;
    private java.awt.Button button1;
    private java.awt.Button button2;
    private java.awt.Canvas canvas1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_mf_spare_parts;
    private javax.swing.JTable jTable_mfprinters;
    private javax.swing.JTable jTable_mfprinters_log;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField21;
    private javax.swing.JTextField jTextField22;
    private javax.swing.JTextField jTextField24;
    private javax.swing.JTextField jTextField25;
    private javax.swing.JTextField jTextField26;
    private javax.swing.JTextField jTextField27;
    private javax.swing.JTextField jTextField28;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JToolBar jToolBar1;
    private com.toedter.calendar.JDateChooser txt_Printers_date;
    private javax.swing.JTextField txt_Printers_dealer;
    private javax.swing.JTextField txt_Printers_device_id;
    private javax.swing.JTextField txt_Printers_device_name;
    private javax.swing.JTextField txt_Printers_drum_cartridge;
    private javax.swing.JTextField txt_Printers_location;
    private javax.swing.JTextField txt_Printers_notice;
    private javax.swing.JTextField txt_Printers_roller;
    private javax.swing.JTextField txt_Printers_state;
    private javax.swing.JTextField txt_Printers_toner_cartridge;
    private javax.swing.JTextField txt_Printers_waste_toner;
    // End of variables declaration//GEN-END:variables
}
