/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package core.views;

import core.controllers.MainController;
import core.controllers.UserController;
import core.controllers.utils.Response;
import core.controllers.utils.Status;
import core.models.Observers.FlightTableObserver;
import core.models.Observers.LocationTableObserver;
import core.models.Observers.MyFlightsObserver;
import core.models.Observers.PassengerTableObserver;
import core.models.Observers.PlaneTableObserver;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author edangulo
 */
public class AirportFrame extends javax.swing.JFrame {

    /**
     * Creates new form AirportFrame
     */
    private int x, y;
    private final MainController controller;
    private final LoadComboBox loadComboBox;
    public AirportFrame() {
        initComponents();
        //read jsons:
        controller = new MainController();
        controller.initializeData();
        loadComboBox = new LoadComboBox();
        loadComboBoxes();
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);
        this.generateMonths();
        this.generateDays();
        this.generateHours();
        this.generateMinutes();
        this.blockPanels();
        setupObservers();
    }

    private void blockPanels() {
        for (int i = 1; i < pestañas.getTabCount(); i++) {
            if (i != 9 && i != 11) {
                pestañas.setEnabledAt(i, false);
            }
        }
    }

    private void generateMonths() {
        for (int i = 1; i < 13; i++) {
            MONTH.addItem("" + i);
            MONTH1.addItem("" + i);
            MONTH5.addItem("" + i);
        }
    }

    private void generateDays() {
        for (int i = 1; i < 32; i++) {
            DAY.addItem("" + i);
            DAY1.addItem("" + i);
            DAY5.addItem("" + i);
        }
    }

    private void generateHours() {
        for (int i = 0; i < 24; i++) {
            DepartureHour.addItem("" + i);
            HourDurationArrival.addItem("" + i);
            HourDurationScale.addItem("" + i);
            SelectHour.addItem("" + i);
        }
    }

    private void generateMinutes() {
        for (int i = 0; i < 60; i++) {
            DepaetureMinutes.addItem("" + i);
            MinuteDurationArrival.addItem("" + i);
            MinuteDurationScale.addItem("" + i);
            SelectMinute.addItem("" + i);
        }
    }

    private void loadComboBoxes() {
        List<String> passengerIds = (List<String>) controller.getPassengerController().getAllPassengerIds().getObject();
        for (String id : passengerIds) {
            userSelect.addItem(id);
        }
        userSelect.setEnabled(false);
        List<String> flightIds = (List<String>) controller.getFlightController().getAllFlightsIds().getObject();
        for (String id : flightIds) {
            FlightSelector.addItem(id);
            SelectID.addItem(id);
        }
        List<String> planeIds = (List<String>) controller.getPlaneController().getAllPlaneIds().getObject();
        for (String id : planeIds) {
            PlaneSelector.addItem(id);
        }
        List<String> locationIds = (List<String>) controller.getLocationController().getAllLocationIds().getObject();
        for (String id : locationIds) {
            DepartureLocationSelector.addItem(id);
            ArrivalLocationSelector.addItem(id);
            ScaleLocationSelector.addItem(id);
        }

    }

    private void clearPassengerRegister() {
        IDpassenger.setText("");
        IDPassengerUpdate.setText("");
        firstName.setText("");
        FirstNameUpdate.setText("");
        LastName.setText("");
        LastNameUpdate.setText("");
        yearBirthdate.setText("");
        BDayUpdate.setText("");
        phoneCodeCountry.setText("");
        CountryCodeUpdate.setText("");
        phoneNumber.setText("");
        PhoneNumberUpdate.setText("");
        Country.setText("");
        CountryUserUpdate.setText("");
        MONTH.setSelectedIndex(0);
        MONTH5.setSelectedIndex(0);
        DAY.setSelectedIndex(0);
        DAY5.setSelectedIndex(0);
    }

    private void clearFlightRegister() {
        System.out.println("se supone que se limpia");
        IDFlight.setText("");
        PlaneSelector.setSelectedIndex(0);
        DepartureLocationSelector.setSelectedIndex(0);
        ArrivalLocationSelector.setSelectedIndex(0);
        ScaleLocationSelector.setSelectedIndex(0);
        DepartureDateYear.setText("");
        MONTH1.setSelectedIndex(0);
        DAY1.setSelectedIndex(0);
        DepartureHour.setSelectedIndex(0);
        DepaetureMinutes.setSelectedIndex(0);
        HourDurationArrival.setSelectedIndex(0);
        MinuteDurationArrival.setSelectedIndex(0);
        HourDurationScale.setSelectedIndex(0);
        MinuteDurationScale.setSelectedIndex(0);
    }

    private void clearLocationRegister() {
        AirPortID_LocationRegistration.setText("");
        AirportName.setText("");
        AirportCity.setText("");
        AirportCountry.setText("");
        AirportLatitude.setText("");
        AirportLongitude.setText("");
    }

    private void clearAirplaneRegister() {
        IdAirplane.setText("");
        Brand.setText("");
        Model.setText("");
        MaxCapacity.setText("");
        Airline.setText("");
    }

    private void setupObservers() {
        // Observador de pasajeros
        PassengerTableObserver passengerObserver = new PassengerTableObserver(AllPassengersTable);
        controller.getPassengerController().registerObserver(passengerObserver);
        // Observador de vuelos
        FlightTableObserver flightObserver = new FlightTableObserver(AllFlightsTable);

        controller.getFlightController().registerObserver(flightObserver);

        // Observador de ubicaciones
        LocationTableObserver locationObserver = new LocationTableObserver(AllLocationTable);
        controller.getLocationController().registerObserver(locationObserver);

        // Observador de aviones
        PlaneTableObserver planeObserver = new PlaneTableObserver(AllPlanesTable);
        controller.getPlaneController().registerObserver(planeObserver);
        
        for(String id: (List<String>) controller.getPassengerController().getAllPassengerIds().getObject()){
            passengerFlightsObserver(id);
        }
    }
    private void passengerFlightsObserver(String passengerId){ 
        
        MyFlightsObserver passengerFlightsObserver = new MyFlightsObserver(ownflightsTable,passengerId);
        controller.getPassengerController().registerObserver(passengerId, passengerFlightsObserver);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new core.views.PanelRound();
        panelSuperior = new core.views.PanelRound();
        jButton13 = new javax.swing.JButton();
        pestañas = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        userRadioButton = new javax.swing.JRadioButton();
        adminRadioButton = new javax.swing.JRadioButton();
        userSelect = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        phoneCodeCountry = new javax.swing.JTextField();
        IDpassenger = new javax.swing.JTextField();
        yearBirthdate = new javax.swing.JTextField();
        Country = new javax.swing.JTextField();
        phoneNumber = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        LastName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        MONTH = new javax.swing.JComboBox<>();
        firstName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        DAY = new javax.swing.JComboBox<>();
        RegisterPassengerButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        IdAirplane = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        Brand = new javax.swing.JTextField();
        Model = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        MaxCapacity = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        Airline = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        CreateAirplaneButton = new javax.swing.JButton();
        jPanel13 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        AirPortID_LocationRegistration = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        AirportName = new javax.swing.JTextField();
        AirportCity = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        AirportCountry = new javax.swing.JTextField();
        AirportLatitude = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        AirportLongitude = new javax.swing.JTextField();
        CreateLocationButton = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        IDFlight = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        PlaneSelector = new javax.swing.JComboBox<>();
        DepartureLocationSelector = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        ArrivalLocationSelector = new javax.swing.JComboBox<>();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        ScaleLocationSelector = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        DepartureDateYear = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        MONTH1 = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        DAY1 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        DepartureHour = new javax.swing.JComboBox<>();
        jLabel33 = new javax.swing.JLabel();
        DepaetureMinutes = new javax.swing.JComboBox<>();
        HourDurationArrival = new javax.swing.JComboBox<>();
        jLabel34 = new javax.swing.JLabel();
        MinuteDurationArrival = new javax.swing.JComboBox<>();
        jLabel35 = new javax.swing.JLabel();
        HourDurationScale = new javax.swing.JComboBox<>();
        MinuteDurationScale = new javax.swing.JComboBox<>();
        createFlightButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        IDPassengerUpdate = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        FirstNameUpdate = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        LastNameUpdate = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        BDayUpdate = new javax.swing.JTextField();
        MONTH5 = new javax.swing.JComboBox<>();
        DAY5 = new javax.swing.JComboBox<>();
        PhoneNumberUpdate = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        CountryCodeUpdate = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        CountryUserUpdate = new javax.swing.JTextField();
        UpdateButton = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        PassengerID = new javax.swing.JTextField();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        FlightSelector = new javax.swing.JComboBox<>();
        AddFlightButton = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ownflightsTable = new javax.swing.JTable();
        RefreshMyFlightsButton = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        AllPassengersTable = new javax.swing.JTable();
        RefreshPasengersTable = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        AllFlightsTable = new javax.swing.JTable();
        RefreshAllFlightsTable = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        RefreshPlanesTable = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        AllPlanesTable = new javax.swing.JTable();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        AllLocationTable = new javax.swing.JTable();
        RefreshLocationsButton = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        SelectHour = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        SelectID = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        SelectMinute = new javax.swing.JComboBox<>();
        DelayButton = new javax.swing.JButton();
        panelRound3 = new core.views.PanelRound();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelRound1.setRadius(40);
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelSuperior.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelSuperiorMouseDragged(evt);
            }
        });
        panelSuperior.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelSuperiorMousePressed(evt);
            }
        });

        jButton13.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jButton13.setText("X");
        jButton13.setBorderPainted(false);
        jButton13.setContentAreaFilled(false);
        jButton13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButton(evt);
            }
        });

        javax.swing.GroupLayout panelSuperiorLayout = new javax.swing.GroupLayout(panelSuperior);
        panelSuperior.setLayout(panelSuperiorLayout);
        panelSuperiorLayout.setHorizontalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelSuperiorLayout.createSequentialGroup()
                .addContainerGap(1083, Short.MAX_VALUE)
                .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        panelSuperiorLayout.setVerticalGroup(
            panelSuperiorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelSuperiorLayout.createSequentialGroup()
                .addComponent(jButton13)
                .addGap(0, 12, Short.MAX_VALUE))
        );

        panelRound1.add(panelSuperior, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, -1));

        pestañas.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        userRadioButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        userRadioButton.setText("User");
        userRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userRadioButtonRadioButtonActionPerformed(evt);
            }
        });
        jPanel1.add(userRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 230, -1, -1));

        adminRadioButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        adminRadioButton.setText("Administrator");
        adminRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminRadioButtonActionPerformed(evt);
            }
        });
        jPanel1.add(adminRadioButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 164, -1, -1));

        userSelect.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        userSelect.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select User" }));
        userSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userSelectActionPerformed(evt);
            }
        });
        jPanel1.add(userSelect, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 130, -1));

        pestañas.addTab("Administration", jPanel1);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel1.setText("Country:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, -1, -1));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel2.setText("ID:");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel3.setText("First Name:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel4.setText("Last Name:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, -1, -1));

        jLabel5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel5.setText("Birthdate:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        jLabel6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel6.setText("+");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, 20, -1));

        phoneCodeCountry.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(phoneCodeCountry, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 50, -1));

        IDpassenger.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(IDpassenger, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 90, 130, -1));

        yearBirthdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(yearBirthdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 280, 90, -1));

        Country.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(Country, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, 130, -1));

        phoneNumber.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(phoneNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, 130, -1));

        jLabel7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel7.setText("Phone:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 340, -1, -1));

        jLabel8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel8.setText("-");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, 30, -1));

        LastName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(LastName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 220, 130, -1));

        jLabel9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel9.setText("-");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 340, 30, -1));

        MONTH.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MONTH.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));
        jPanel2.add(MONTH, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 280, -1, -1));

        firstName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel2.add(firstName, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 160, 130, -1));

        jLabel10.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel10.setText("-");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 30, -1));

        DAY.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DAY.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));
        jPanel2.add(DAY, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 280, -1, -1));

        RegisterPassengerButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RegisterPassengerButton.setText("Register");
        RegisterPassengerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterPassengerButtonActionPerformed(evt);
            }
        });
        jPanel2.add(RegisterPassengerButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 480, -1, -1));

        pestañas.addTab("Passenger registration", jPanel2);

        jPanel3.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel11.setText("ID:");
        jPanel3.add(jLabel11);
        jLabel11.setBounds(53, 96, 22, 25);

        IdAirplane.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(IdAirplane);
        IdAirplane.setBounds(180, 93, 130, 31);

        jLabel12.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel12.setText("Brand:");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(53, 157, 70, 25);

        Brand.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(Brand);
        Brand.setBounds(180, 154, 130, 31);

        Model.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(Model);
        Model.setBounds(180, 213, 130, 31);

        jLabel13.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel13.setText("Model:");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(53, 216, 70, 25);

        MaxCapacity.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(MaxCapacity);
        MaxCapacity.setBounds(180, 273, 130, 31);

        jLabel14.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel14.setText("Max Capacity:");
        jPanel3.add(jLabel14);
        jLabel14.setBounds(53, 276, 130, 25);

        Airline.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jPanel3.add(Airline);
        Airline.setBounds(180, 333, 130, 31);

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel15.setText("Airline:");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(53, 336, 70, 25);

        CreateAirplaneButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        CreateAirplaneButton.setText("Create");
        CreateAirplaneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateAirplaneButtonActionPerformed(evt);
            }
        });
        jPanel3.add(CreateAirplaneButton);
        CreateAirplaneButton.setBounds(490, 480, 120, 40);

        pestañas.addTab("Airplane registration", jPanel3);

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel16.setText("Airport ID:");

        AirPortID_LocationRegistration.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel17.setText("Airport name:");

        AirportName.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        AirportCity.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel18.setText("Airport city:");

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel19.setText("Airport country:");

        AirportCountry.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        AirportLatitude.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel20.setText("Airport latitude:");

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel21.setText("Airport longitude:");

        AirportLongitude.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        CreateLocationButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        CreateLocationButton.setText("Create");
        CreateLocationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CreateLocationButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jLabel21))
                        .addGap(80, 80, 80)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AirportLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AirPortID_LocationRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AirportName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AirportCity, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AirportCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AirportLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(515, 515, 515)
                        .addComponent(CreateLocationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(515, 515, 515))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(36, 36, 36)
                        .addComponent(jLabel17)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel18)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel19)
                        .addGap(35, 35, 35)
                        .addComponent(jLabel20))
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(AirPortID_LocationRegistration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(AirportName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(AirportCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(AirportCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(AirportLatitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(AirportLongitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(CreateLocationButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        pestañas.addTab("Location registration", jPanel13);

        jLabel22.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel22.setText("ID:");

        IDFlight.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel23.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel23.setText("Plane:");

        PlaneSelector.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PlaneSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Plane" }));

        DepartureLocationSelector.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DepartureLocationSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        jLabel24.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel24.setText("Departure location:");

        ArrivalLocationSelector.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        ArrivalLocationSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel25.setText("Arrival location:");

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel26.setText("Scale location:");

        ScaleLocationSelector.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        ScaleLocationSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Location" }));

        jLabel27.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel27.setText("Duration:");

        jLabel28.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel28.setText("Duration:");

        jLabel29.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel29.setText("Departure date:");

        DepartureDateYear.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel30.setText("-");

        MONTH1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MONTH1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel31.setText("-");

        DAY1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DAY1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));

        jLabel32.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel32.setText("-");

        DepartureHour.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DepartureHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel33.setText("-");

        DepaetureMinutes.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DepaetureMinutes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        HourDurationArrival.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        HourDurationArrival.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel34.setText("-");

        MinuteDurationArrival.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MinuteDurationArrival.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel35.setText("-");

        HourDurationScale.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        HourDurationScale.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        MinuteDurationScale.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MinuteDurationScale.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        createFlightButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        createFlightButton.setText("Create");
        createFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createFlightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ScaleLocationSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ArrivalLocationSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(46, 46, 46)
                        .addComponent(DepartureLocationSelector, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(IDFlight)
                            .addComponent(PlaneSelector, 0, 130, Short.MAX_VALUE))))
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(DepartureDateYear, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(MONTH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(DAY1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(DepartureHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(DepaetureMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(30, 30, 30))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(HourDurationArrival, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(MinuteDurationArrival, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(HourDurationScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(20, 20, 20)
                                        .addComponent(MinuteDurationScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(createFlightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(530, 530, 530))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel22))
                    .addComponent(IDFlight, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(PlaneSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(DepartureHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jLabel33)
                    .addComponent(DepaetureMinutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel24)
                                .addComponent(DepartureLocationSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel29))
                            .addComponent(DepartureDateYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MONTH1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(jLabel31)
                            .addComponent(DAY1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel25)
                                .addComponent(ArrivalLocationSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel28))
                            .addComponent(HourDurationArrival, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel34)
                            .addComponent(MinuteDurationArrival, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(HourDurationScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel35)
                            .addComponent(MinuteDurationScale, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel26)
                                .addComponent(ScaleLocationSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel27)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 112, Short.MAX_VALUE)
                .addComponent(createFlightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        pestañas.addTab("Flight registration", jPanel4);

        jLabel36.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel36.setText("ID:");

        IDPassengerUpdate.setEditable(false);
        IDPassengerUpdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        IDPassengerUpdate.setEnabled(false);

        jLabel37.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel37.setText("First Name:");

        FirstNameUpdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel38.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel38.setText("Last Name:");

        LastNameUpdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel39.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel39.setText("Birthdate:");

        BDayUpdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        MONTH5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        MONTH5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Month" }));

        DAY5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DAY5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Day" }));

        PhoneNumberUpdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel40.setText("-");

        CountryCodeUpdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel41.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel41.setText("+");

        jLabel42.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel42.setText("Phone:");

        jLabel43.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel43.setText("Country:");

        CountryUserUpdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        UpdateButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        UpdateButton.setText("Update");
        UpdateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UpdateButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addGap(108, 108, 108)
                                .addComponent(IDPassengerUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel37)
                                .addGap(41, 41, 41)
                                .addComponent(FirstNameUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addGap(43, 43, 43)
                                .addComponent(LastNameUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addGap(55, 55, 55)
                                .addComponent(BDayUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(MONTH5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(34, 34, 34)
                                .addComponent(DAY5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addGap(56, 56, 56)
                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(CountryCodeUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(PhoneNumberUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel43)
                                .addGap(63, 63, 63)
                                .addComponent(CountryUserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(507, 507, 507)
                        .addComponent(UpdateButton)))
                .addContainerGap(547, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(IDPassengerUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addComponent(FirstNameUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(LastNameUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel39)
                    .addComponent(BDayUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MONTH5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(DAY5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42)
                    .addComponent(jLabel41)
                    .addComponent(CountryCodeUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40)
                    .addComponent(PhoneNumberUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addComponent(CountryUserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(UpdateButton)
                .addGap(113, 113, 113))
        );

        pestañas.addTab("Update info", jPanel5);

        PassengerID.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        PassengerID.setEnabled(false);

        jLabel44.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel44.setText("ID:");

        jLabel45.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel45.setText("Flight:");

        FlightSelector.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        FlightSelector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Flight" }));

        AddFlightButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        AddFlightButton.setText("Add");
        AddFlightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddFlightButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel44)
                    .addComponent(jLabel45))
                .addGap(79, 79, 79)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(FlightSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PassengerID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(829, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AddFlightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(509, 509, 509))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel44))
                    .addComponent(PassengerID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(FlightSelector, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 272, Short.MAX_VALUE)
                .addComponent(AddFlightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );

        pestañas.addTab("Add to flight", jPanel6);

        ownflightsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        ownflightsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Departure Date", "Arrival Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(ownflightsTable);

        RefreshMyFlightsButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RefreshMyFlightsButton.setText("Refresh");
        RefreshMyFlightsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshMyFlightsButtonButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 590, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(291, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(RefreshMyFlightsButton)
                .addGap(527, 527, 527))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(RefreshMyFlightsButton)
                .addContainerGap())
        );

        pestañas.addTab("Show my flights", jPanel7);

        AllPassengersTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        AllPassengersTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Birthdate", "Age", "Phone", "Country", "Num Flight"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Long.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(AllPassengersTable);

        RefreshPasengersTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RefreshPasengersTable.setText("Refresh");
        RefreshPasengersTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshPasengersTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(489, 489, 489)
                        .addComponent(RefreshPasengersTable))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1078, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(53, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RefreshPasengersTable)
                .addContainerGap())
        );

        pestañas.addTab("Show all passengers", jPanel8);

        AllFlightsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        AllFlightsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Departure Airport ID", "Arrival Airport ID", "Scale Airport ID", "Departure Date", "Arrival Date", "Plane ID", "Number Passengers"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(AllFlightsTable);

        RefreshAllFlightsTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RefreshAllFlightsTable.setText("Refresh");
        RefreshAllFlightsTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshAllFlightsTableActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(521, 521, 521)
                        .addComponent(RefreshAllFlightsTable)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(RefreshAllFlightsTable)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pestañas.addTab("Show all flights", jPanel9);

        RefreshPlanesTable.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RefreshPlanesTable.setText("Refresh");
        RefreshPlanesTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshPlanesTableActionPerformed(evt);
            }
        });

        AllPlanesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Brand", "Model", "Max Capacity", "Airline", "Number Flights"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(AllPlanesTable);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(RefreshPlanesTable))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 816, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(189, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(RefreshPlanesTable)
                .addGap(17, 17, 17))
        );

        pestañas.addTab("Show all planes", jPanel10);

        AllLocationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Airport ID", "Airport Name", "City", "Country"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(AllLocationTable);

        RefreshLocationsButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        RefreshLocationsButton.setText("Refresh");
        RefreshLocationsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshLocationsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(508, 508, 508)
                        .addComponent(RefreshLocationsButton))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(226, 226, 226)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 652, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(272, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(29, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(RefreshLocationsButton)
                .addGap(17, 17, 17))
        );

        pestañas.addTab("Show all locations", jPanel11);

        SelectHour.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        SelectHour.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hour" }));

        jLabel46.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel46.setText("Hours:");

        jLabel47.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel47.setText("ID:");

        SelectID.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        SelectID.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ID" }));

        jLabel48.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel48.setText("Minutes:");

        SelectMinute.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        SelectMinute.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Minute" }));

        DelayButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        DelayButton.setText("Delay");
        DelayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DelayButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SelectMinute, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel47)
                            .addComponent(jLabel46))
                        .addGap(79, 79, 79)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(SelectHour, 0, 105, Short.MAX_VALUE)
                            .addComponent(SelectID, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(820, 820, 820))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(DelayButton)
                .addGap(531, 531, 531))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(SelectID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(SelectHour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(32, 32, 32)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel48)
                    .addComponent(SelectMinute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 288, Short.MAX_VALUE)
                .addComponent(DelayButton)
                .addGap(33, 33, 33))
        );

        pestañas.addTab("Delay flight", jPanel12);

        panelRound1.add(pestañas, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 41, 1150, 620));

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1150, Short.MAX_VALUE)
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 36, Short.MAX_VALUE)
        );

        panelRound1.add(panelRound3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-2, 660, 1150, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void panelSuperiorMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSuperiorMousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_panelSuperiorMousePressed

    private void panelSuperiorMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelSuperiorMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_panelSuperiorMouseDragged

    private void adminRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminRadioButtonActionPerformed
        if (userRadioButton.isSelected()) {
            userRadioButton.setSelected(false);
            userSelect.setSelectedIndex(0);
            userSelect.setEnabled(false);
        }
        // Llamar al controlador para obtener los estados de las pestañas
        Response response = controller.getUserController().setUserRole(UserController.UserRole.ADMINISTRATOR, pestañas.getTabCount());
        if (response.getStatus() == Status.OK) { // Si la respuesta es exitosa
            List<Boolean> tabStates = (List<Boolean>) response.getObject();
            for (int i = 0; i < tabStates.size(); i++) {
                pestañas.setEnabledAt(i, tabStates.get(i));
            }
        }
        JOptionPane.showMessageDialog(this, response.getMessage(), "Rol Cambiado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_adminRadioButtonActionPerformed

    private void userRadioButtonRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userRadioButtonRadioButtonActionPerformed
        userSelect.setEnabled(true);
        if (adminRadioButton.isSelected()) {
            adminRadioButton.setSelected(false);
        }
        Response response = controller.getUserController().setUserRole(UserController.UserRole.USER, pestañas.getTabCount());
        if (response.getStatus() == Status.OK) {
            List<Boolean> tabStates = (List<Boolean>) response.getObject();
            for (int i = 0; i < tabStates.size(); i++) {
                pestañas.setEnabledAt(i, tabStates.get(i));
            }
        }
        JOptionPane.showMessageDialog(this, response.getMessage(), "Rol Cambiado", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_userRadioButtonRadioButtonActionPerformed

    private void RegisterPassengerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterPassengerButtonActionPerformed
        // Este es para crear pasajero:
        String id = IDpassenger.getText();
        String firstname = firstName.getText();
        String lastname = LastName.getText();
        String year = yearBirthdate.getText();
        String month = (String) MONTH.getSelectedItem();
        String day = (String) DAY.getSelectedItem();
        String phoneCode = phoneCodeCountry.getText();
        String phone = phoneNumber.getText();
        String country = Country.getText();
        // Llama al controlador para registrar el pasajero
        Response response = controller.getPassengerController().registerPassenger(id, firstname, lastname, year, month, day, phoneCode, phone, country);
        JOptionPane.showMessageDialog(this, response.getMessage(), "Passenger Register", JOptionPane.INFORMATION_MESSAGE);
        if (response.getStatus() == Status.CREATED) {    
            List<String> passengerIds = (List<String>) controller.getPassengerController().getAllPassengerIds().getObject();
            loadComboBox.load(userSelect, passengerIds);
            clearPassengerRegister();
        }
    }//GEN-LAST:event_RegisterPassengerButtonActionPerformed

    private void CreateAirplaneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateAirplaneButtonActionPerformed
        // Este es para crear Avión:
        String id = IdAirplane.getText();
        String brand = Brand.getText();
        String model = Model.getText();
        String maxCapacity = MaxCapacity.getText();
        String airline = Airline.getText();
        // Llama al controlador para registrar el avión
        Response response = controller.getPlaneController().createPlane(id, brand, model, maxCapacity, airline);
        JOptionPane.showMessageDialog(this, response.getMessage(), "Passenger Register", JOptionPane.INFORMATION_MESSAGE);
        if (response.getStatus() == Status.CREATED) {
            List<String> planeIds = (List<String>) controller.getPlaneController().getAllPlaneIds().getObject();
            loadComboBox.load(PlaneSelector, planeIds);
        clearAirplaneRegister();
        } 
    }//GEN-LAST:event_CreateAirplaneButtonActionPerformed

    private void CreateLocationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CreateLocationButtonActionPerformed
        // Para crear Aeropuerto:
        String id = AirPortID_LocationRegistration.getText();
        String name = AirportName.getText();
        String city = AirportCity.getText();
        String country = AirportCountry.getText();
        String latitude = AirportLatitude.getText();
        String longitude = AirportLongitude.getText();
        // Llama al controlador para registrar el aeropuerto
        Response response = controller.getLocationController().createAirport(id, name, city, country, latitude, longitude);
        JOptionPane.showMessageDialog(this, response.getMessage(), "Airport Register", JOptionPane.INFORMATION_MESSAGE);
        if (response.getStatus() == Status.CREATED) {
            List<String> lcn = (List<String>) controller.getLocationController().getAllLocationIds().getObject();
            loadComboBox.load(DepartureLocationSelector, lcn);       
            loadComboBox.load(ArrivalLocationSelector, lcn);                   
            loadComboBox.load(ScaleLocationSelector, lcn);             
            clearLocationRegister();
        }
    }//GEN-LAST:event_CreateLocationButtonActionPerformed

    private void createFlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createFlightButtonActionPerformed

        // Para crear un vuelo:
        String id = IDFlight.getText();
        String planeId = (String) PlaneSelector.getSelectedItem();
        String departureLocationId = (String) DepartureLocationSelector.getSelectedItem();
        String arrivalLocationId = (String) ArrivalLocationSelector.getSelectedItem();
        String scaleLocationId = (String) ScaleLocationSelector.getSelectedItem();
        String year = DepartureDateYear.getText();
        String month = (String) MONTH1.getSelectedItem();
        String day = (String) DAY1.getSelectedItem();
        String hour = (String) DepartureHour.getSelectedItem();
        String minutes = (String) DepaetureMinutes.getSelectedItem();

        String hoursDurationArrival = (String) HourDurationArrival.getSelectedItem();
        String minutesDurationArrival = (String) MinuteDurationArrival.getSelectedItem();
        String hoursDurationScale = (String) HourDurationScale.getSelectedItem();
        String minutesDurationScale = (String) MinuteDurationScale.getSelectedItem();

        // Llama al controlador para registrar el vuelo:
        Response response = controller.getFlightController().registerFlight(
                id,
                planeId,
                departureLocationId,
                arrivalLocationId,
                scaleLocationId,
                year,
                month,
                day,
                hour,
                minutes,
                hoursDurationArrival,
                minutesDurationArrival,
                hoursDurationScale,
                minutesDurationScale
        );

        JOptionPane.showMessageDialog(this, response.getMessage(), "Flight Register", JOptionPane.INFORMATION_MESSAGE);

        if (response.getStatus() == Status.CREATED) {
              List<String> fltId = (List<String>) controller.getFlightController().getAllFlightsIds().getObject();
            loadComboBox.load(SelectID, fltId);       
            loadComboBox.load(FlightSelector, fltId); 
            clearFlightRegister();
        }
    }//GEN-LAST:event_createFlightButtonActionPerformed

    private void UpdateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UpdateButtonActionPerformed
        // Para actualizar la información de un usuario:

        String id = IDPassengerUpdate.getText();
        String firstname = FirstNameUpdate.getText();
        String lastname = LastNameUpdate.getText();
        String year = BDayUpdate.getText();
        String month = MONTH.getItemAt(MONTH5.getSelectedIndex());
        String day = DAY.getItemAt(DAY5.getSelectedIndex());
        String phoneCode = CountryCodeUpdate.getText();
        String phone = PhoneNumberUpdate.getText();
        String country = CountryUserUpdate.getText();
        // Llama al controlador para actualizar el usuario:
        Response response = controller.getPassengerController().updatePassenger(id, firstname, lastname, year, month, day, phoneCode, phone, country);
        JOptionPane.showMessageDialog(this, response.getMessage(), "Actualizar datos", JOptionPane.INFORMATION_MESSAGE);
        if (response.getStatus() == Status.OK) {
            clearPassengerRegister();
        }
    }//GEN-LAST:event_UpdateButtonActionPerformed

    private void AddFlightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFlightButtonActionPerformed
        //Para añadir un pasajero a un vuelo necesitamos el pasajero y el vuelo:
        String passengerId = PassengerID.getText();
        String flightId = FlightSelector.getItemAt(FlightSelector.getSelectedIndex());

        //Llamamos al controlador para añadir el pasajero al vuelo:
        Response response = controller.getFlightController().addPassengertoFlight(flightId, passengerId);
        JOptionPane.showMessageDialog(this, response.getMessage(), "Agregar vuelo", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_AddFlightButtonActionPerformed

    private void DelayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DelayButtonActionPerformed
        // Para retrasar un vuelo:
        String flightId = SelectID.getItemAt(SelectID.getSelectedIndex());
        String hours = SelectHour.getItemAt(SelectHour.getSelectedIndex());
        String minutes = SelectMinute.getItemAt(SelectMinute.getSelectedIndex());

        Response response = controller.getFlightController().delayFlight(flightId, hours, minutes);
        JOptionPane.showMessageDialog(this, response.getMessage(), "Delay flight", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_DelayButtonActionPerformed

    private void RefreshMyFlightsButtonButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshMyFlightsButtonButtonActionPerformed
        //LA TABLA ES EL OBSERVADOR --> EL CREAR Y ACTUALIZAR ES LO OBSERVADO.
        // Ver los vuelos propios de un Usuario:
        //1. leer el id del usuario que quiere ver sus vuelos:
        String passengerId = userSelect.getItemAt(userSelect.getSelectedIndex());
        //2. Hacer que el controlador busque los vuelos de ese usuario:
        Response response = controller.getPassengerController().getFlightRowsOfPassenger(passengerId);

        if (response.getStatus() == Status.OK) {
            List<Object[]> rows = (List<Object[]>) response.getObject();
            DefaultTableModel model = (DefaultTableModel) ownflightsTable.getModel();
            model.setRowCount(0);
            for (Object[] row : rows) {
                model.addRow(row);
            }
            JOptionPane.showMessageDialog(this, response.getMessage(), "Ok", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_RefreshMyFlightsButtonButtonActionPerformed

    private void RefreshPasengersTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshPasengersTableActionPerformed
        // TODO add your handling code here:
        Response response = controller.getPassengerController().getPassengers();
        if (response.getStatus() == Status.OK) {
            List<Object[]> rows = (List<Object[]>) response.getObject();
            DefaultTableModel model = (DefaultTableModel) AllPassengersTable.getModel();
            model.setRowCount(0);
            for (Object[] row : rows) {
                model.addRow(row);
            }
            JOptionPane.showMessageDialog(this, response.getMessage(), "Ok", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_RefreshPasengersTableActionPerformed

    private void RefreshAllFlightsTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshAllFlightsTableActionPerformed
        // TODO add your handling code here:
        Response response = controller.getFlightController().getFlights();
        if (response.getStatus() == Status.OK) {
            List<Object[]> rows = (List<Object[]>) response.getObject();
            DefaultTableModel model = (DefaultTableModel) AllFlightsTable.getModel();
            model.setRowCount(0);
            for (Object[] row : rows) {
                model.addRow(row);
            }
            JOptionPane.showMessageDialog(this, response.getMessage(), "Ok", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_RefreshAllFlightsTableActionPerformed

    private void RefreshPlanesTableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshPlanesTableActionPerformed
        // TODO add your handling code here:
        Response response = controller.getPlaneController().getPlanes();
        if (response.getStatus() == Status.OK) {
            List<Object[]> rows = (List<Object[]>) response.getObject();
            DefaultTableModel model = (DefaultTableModel) AllPlanesTable.getModel();
            model.setRowCount(0);
            for (Object[] row : rows) {
                model.addRow(row);
            }
            JOptionPane.showMessageDialog(this, response.getMessage(), "Ok", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_RefreshPlanesTableActionPerformed

    private void RefreshLocationsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshLocationsButtonActionPerformed
        // TODO add your handling code here:
        Response response = controller.getLocationController().getLocations();
        if (response.getStatus() == Status.OK) {
            List<Object[]> rows = (List<Object[]>) response.getObject();
            DefaultTableModel model = (DefaultTableModel) AllLocationTable.getModel();
            model.setRowCount(0);
            for (Object[] row : rows) {
                model.addRow(row);
            }
            JOptionPane.showMessageDialog(this, response.getMessage(), "Ok", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, response.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_RefreshLocationsButtonActionPerformed

    private void ExitButton(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitButton
        System.exit(0);
    }//GEN-LAST:event_ExitButton

    private void userSelectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userSelectActionPerformed
        try {
            String id = userSelect.getSelectedItem().toString();
            if (!id.equals(userSelect.getItemAt(0))) {
                IDPassengerUpdate.setText(id);
                PassengerID.setText(id);  
                controller.getPassengerController().notify(id);
                Response data = controller.getPassengerController().getPassengerData(id);
                if (data.getStatus() == Status.OK) {
                    Map<String, String> pdata = (Map<String, String>) data.getObject();

                    FirstNameUpdate.setText(pdata.get("firstName"));
                    LastNameUpdate.setText(pdata.get("lastName"));
                    BDayUpdate.setText(pdata.get("birthYear"));
                    CountryCodeUpdate.setText(pdata.get("phoneCode"));
                    PhoneNumberUpdate.setText(pdata.get("phone"));
                    CountryUserUpdate.setText(pdata.get("country"));
                } else {
                    JOptionPane.showMessageDialog(this, data.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                IDPassengerUpdate.setText("");
                PassengerID.setText("");
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_userSelectActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddFlightButton;
    private javax.swing.JTextField AirPortID_LocationRegistration;
    private javax.swing.JTextField Airline;
    private javax.swing.JTextField AirportCity;
    private javax.swing.JTextField AirportCountry;
    private javax.swing.JTextField AirportLatitude;
    private javax.swing.JTextField AirportLongitude;
    private javax.swing.JTextField AirportName;
    private javax.swing.JTable AllFlightsTable;
    private javax.swing.JTable AllLocationTable;
    private javax.swing.JTable AllPassengersTable;
    private javax.swing.JTable AllPlanesTable;
    private javax.swing.JComboBox<String> ArrivalLocationSelector;
    private javax.swing.JTextField BDayUpdate;
    private javax.swing.JTextField Brand;
    private javax.swing.JTextField Country;
    private javax.swing.JTextField CountryCodeUpdate;
    private javax.swing.JTextField CountryUserUpdate;
    private javax.swing.JButton CreateAirplaneButton;
    private javax.swing.JButton CreateLocationButton;
    private javax.swing.JComboBox<String> DAY;
    private javax.swing.JComboBox<String> DAY1;
    private javax.swing.JComboBox<String> DAY5;
    private javax.swing.JButton DelayButton;
    private javax.swing.JComboBox<String> DepaetureMinutes;
    private javax.swing.JTextField DepartureDateYear;
    private javax.swing.JComboBox<String> DepartureHour;
    private javax.swing.JComboBox<String> DepartureLocationSelector;
    private javax.swing.JTextField FirstNameUpdate;
    private javax.swing.JComboBox<String> FlightSelector;
    private javax.swing.JComboBox<String> HourDurationArrival;
    private javax.swing.JComboBox<String> HourDurationScale;
    private javax.swing.JTextField IDFlight;
    private javax.swing.JTextField IDPassengerUpdate;
    private javax.swing.JTextField IDpassenger;
    private javax.swing.JTextField IdAirplane;
    private javax.swing.JTextField LastName;
    private javax.swing.JTextField LastNameUpdate;
    private javax.swing.JComboBox<String> MONTH;
    private javax.swing.JComboBox<String> MONTH1;
    private javax.swing.JComboBox<String> MONTH5;
    private javax.swing.JTextField MaxCapacity;
    private javax.swing.JComboBox<String> MinuteDurationArrival;
    private javax.swing.JComboBox<String> MinuteDurationScale;
    private javax.swing.JTextField Model;
    private javax.swing.JTextField PassengerID;
    private javax.swing.JTextField PhoneNumberUpdate;
    private javax.swing.JComboBox<String> PlaneSelector;
    private javax.swing.JButton RefreshAllFlightsTable;
    private javax.swing.JButton RefreshLocationsButton;
    private javax.swing.JButton RefreshMyFlightsButton;
    private javax.swing.JButton RefreshPasengersTable;
    private javax.swing.JButton RefreshPlanesTable;
    private javax.swing.JButton RegisterPassengerButton;
    private javax.swing.JComboBox<String> ScaleLocationSelector;
    private javax.swing.JComboBox<String> SelectHour;
    private javax.swing.JComboBox<String> SelectID;
    private javax.swing.JComboBox<String> SelectMinute;
    private javax.swing.JButton UpdateButton;
    private javax.swing.JRadioButton adminRadioButton;
    private javax.swing.JButton createFlightButton;
    private javax.swing.JTextField firstName;
    private javax.swing.JButton jButton13;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable ownflightsTable;
    private core.views.PanelRound panelRound1;
    private core.views.PanelRound panelRound3;
    private core.views.PanelRound panelSuperior;
    private javax.swing.JTabbedPane pestañas;
    private javax.swing.JTextField phoneCodeCountry;
    private javax.swing.JTextField phoneNumber;
    private javax.swing.JRadioButton userRadioButton;
    private javax.swing.JComboBox<String> userSelect;
    private javax.swing.JTextField yearBirthdate;
    // End of variables declaration//GEN-END:variables
}
