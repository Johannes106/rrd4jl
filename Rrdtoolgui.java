package org.rrd4j.jl;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.AbstractAction;

import java.awt.event.ActionEvent;

import javax.swing.Action;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.awt.SystemColor;

/**
 * this class builds the gui for define and update a rrd-file or if a rrd-file is already built it only makes an update of the rrd.
 * @author j.launer
 * @param CsvFile (ReadCsv.java)
 * @return rrdFile (JmSave.java)
 */
public class Rrdtoolgui {
//  TextFields
	private JFrame frame;
	private JTextField txtRrdFile;
	private JTextField txtCsvFile;
	private JTextField txtDbName;
	private JTextField txtSplitter;
	private JTextField txtStart;
	private JTextField txtEnd;
//	objects of classes
	Prop pro = new Prop();
	Logging log = new Logging();
	Logging logInfo = new Logging();
	Logging logError = new Logging();
	 
	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public Rrdtoolgui() throws Exception {
		initialize();
	}
	
	JmSave jm0 = new JmSave();
	ReadCsv c = new ReadCsv();
	private final Action action_1 = new SwingAction();
	private JTextField txtDstype;
	private JTextField txtMin;
	private JTextField txtStep;

	private JTextField txtMax;
	private JPanel panel_4;
	private JPanel panel_5;
	private JTextField txtStepReal;
	private JPanel panel_6;
	private JLabel lblArchivestepsRows;
	private JTextField txtAverage;
	private JPanel panel_7;
	private JTextField txtArRow;
	private JTextField txtArStep;
	private JLabel lblConfig;
	
	
	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 1200, 302);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(0, 2, 0, 0));
				
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		panel.setLayout(new GridLayout(9, 1, 0, 2));
		
		lblConfig = new JLabel("Ds-Type, Step, Frequenz, Mnimum,Maximum");
		panel.add(lblConfig);
		
		lblArchivestepsRows = new JLabel("Archive: (Steps, Rows)");
		lblArchivestepsRows.setToolTipText("In Archive werden die einzelnen Speicherschritte definier, nach welchen ein Durchschnitt gebildet werden soll");
		panel.add(lblArchivestepsRows);
		
		JLabel lblDatenbankdatei = new JLabel("Datenbankdatei: ");
		lblDatenbankdatei.setToolTipText("In dieser Datei werden die verschiedenen Dateien gespeichert");
		panel.add(lblDatenbankdatei);
		
		JLabel lblDatenbankname = new JLabel("Datenbankname:");
		lblDatenbankname.setToolTipText("Wie soll die Datenbank heissen? (muss nicht zwingend geaendert werden)");
		panel.add(lblDatenbankname);
		
		panel_4 = new JPanel();
		panel_4.setToolTipText("Wo befindet sich die Import-Datei (muss csv-Format sein)");
		panel.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblCsvdatei = new JLabel("CSV-Datei:");
		lblCsvdatei.setToolTipText("Wo befindet sich die Import-Datei (muss csv-Format sein)");
		panel_4.add(lblCsvdatei);
		
		JLabel lblTrennzeichenInCsv = new JLabel("Trennzeichen in CSV:");
		lblTrennzeichenInCsv.setToolTipText("Durch welches Zeichen sind die Werte getrennt? (Standard: ,)");
		panel.add(lblTrennzeichenInCsv);
		
		JLabel lblStartzeit = new JLabel("Startzeit:");
		lblStartzeit.setToolTipText("Was ist der erste Wert, der eingetragen werden soll? Zusaetzlich wird die Haelfte des Herzschlags abgezogen. (keine Aenderung notwendig)");
		panel.add(lblStartzeit);
		
		JLabel lblEndzeit = new JLabel("Endzeit:");
		lblEndzeit.setToolTipText("Was ist der letzte Wert, der eingetragen werden soll? Zusaetzlich wird die Haelfte des Herzschlags hinzugefuegt. (keine Aenderung notwendig)");
		panel.add(lblEndzeit);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(9, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{72, 0, 0};
		gbl_panel_2.rowHeights = new int[]{32, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		System.out.println(pro.readBase());
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 1;
		gbc_panel_3.gridy = 0;
		panel_2.add(panel_3, gbc_panel_3);
		panel_3.setLayout(new GridLayout(2, 2, 0, 0));
		
		txtDstype = new JTextField();
		txtDstype.setToolTipText("Datenbanktyp");
		txtDstype.setBackground(Color.LIGHT_GRAY);
		txtDstype.setEditable(false);
		txtDstype.setText("GAUGE");
		panel_3.add(txtDstype);
		txtDstype.setColumns(10);
		
		panel_5 = new JPanel();
		panel_3.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		txtStepReal = new JTextField();
		txtStepReal.setToolTipText("Zaehlschritt");
		//If propertie exist use text of it else take standarttext
//				if (f2.exists() == true)
//				{
//					txtStepReal.setText(pro.readP(pro.readBase(), "Zaehlschritt"));
//					System.out.println("Zaehlschritt aus Properties!");
//				}
//				else
//				{
//					txtStepReal.setText("1");
//				}
		txtStepReal.setText("900");
		panel_5.add(txtStepReal);
		txtStepReal.setColumns(10);
		
		txtStep = new JTextField();
		txtStep.setToolTipText("Herzschlag");
		panel_5.add(txtStep);
		txtStep.setText("2700");
		txtStep.setColumns(10);
		
		txtMin = new JTextField();
		txtMin.setToolTipText("Minimum");
		txtMin.setText("1");
		panel_3.add(txtMin);
		txtMin.setColumns(10);
		
		txtMax = new JTextField();
		txtMax.setToolTipText("Maximum");
		txtMax.setText("15000");
		panel_3.add(txtMax);
		txtMax.setColumns(10);
		
		panel_6 = new JPanel();
		panel_1.add(panel_6);
		panel_6.setLayout(new GridLayout(0, 2, 0, 0));
		
		txtAverage = new JTextField();
		txtAverage.setToolTipText("Um mehrere Archive zu erstellen: 1,96 und 96,3000 (durch Komma trennen)");
		txtAverage.setEditable(false);
		txtAverage.setBackground(Color.LIGHT_GRAY);
		txtAverage.setText("Average");
		panel_6.add(txtAverage);
		txtAverage.setColumns(10);
		
		panel_7 = new JPanel();
		panel_6.add(panel_7);
		panel_7.setLayout(new GridLayout(2, 0, 0, 0));
		
		txtArStep = new JTextField();
		txtArStep.setToolTipText("Aus wieviel Daten soll der Durchschnitt gebildet werden?");
		txtArStep.setBackground(SystemColor.inactiveCaption);
		txtArStep.setText("1,96,35040");
		panel_7.add(txtArStep);
		txtArStep.setColumns(10);
		
		txtArRow = new JTextField();
		txtArRow.setToolTipText("Wieoft soll der Durchschnitt gebildet werden?");
		txtArRow.setBackground(SystemColor.inactiveCaption);
		//fuer 1Monat: 15min in 1 Stunde= 4 4*24Stunden=96mal 96malproTag*30Tage =2880malproMonat 2880*12Monate=34560Jahr
		txtArRow.setText("96,7,10");
		panel_7.add(txtArRow);
		txtArRow.setColumns(10);
		
		txtRrdFile = new JTextField();
		panel_1.add(txtRrdFile);
		txtRrdFile.setToolTipText("Pfadangabe f\u00FCr .rrd");
		txtRrdFile.setText("//EMGPERFMESS01/Messdaten/rrd");
		txtRrdFile.addMouseListener(new java.awt.event.MouseAdapter() {
		        @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
		            Cdialog c = new Cdialog();
		            String s;
		            s = c.saveDia("Datenbank", ".rrd");
		            txtRrdFile.setText(s);
		        }
		    });
		//		}
				txtRrdFile.setForeground(Color.BLACK);
				txtRrdFile.setColumns(10);
		
		txtDbName = new JTextField();
		txtDbName.setToolTipText("Wie soll die Datenbank heissen? (muss nicht zwingend geaendert werden)");
		txtDbName.setForeground(Color.GRAY);
		txtDbName.setText("time");
		panel_1.add(txtDbName);
		txtDbName.setColumns(10);
		
		txtCsvFile = new JTextField();
		txtCsvFile.setToolTipText("Wo befindet sich die Import-Datei (muss csv-Format sein)");
		txtCsvFile.setForeground(Color.GRAY);
//		txtCsvFile.setText("C:/rrd/e/s80_1.csv");
//		txtCsvFile.setText("//EMGPERFMESS01/Messdaten/csv");
//		jm0.setPathCSV(txtCsvFile.getText());
		txtCsvFile.addMouseListener(new java.awt.event.MouseAdapter() {
	        @Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
	            Cdialog c = new Cdialog();
	            String s;
	            s = c.openDia("csv");//("Datenbank", ".rrd");
	            txtCsvFile.setText(s);
	            jm0.setPathCSV(txtCsvFile.getText());
	            System.out.println("s:"+s);
//	            String[] splittedFilePath = s.split("\\");
//	            System.out.println(splittedFilePath[0]);
//	            int partsOfSplit = 0;
//	            		partsOfSplit=Array.getLength(splittedFilePath);
//	            System.out.println("Der Pfad wurde in " + partsOfSplit + "aufgeteilt");
	        }
	    });
		//If propertie exist use text of it else take standarttext
//		if (f2.exists() == true)
//		{
//			txtCsvFile.setText(pro.readP(pro.readBase(), "Csvpfad"));
//			System.out.println("Csv aus Properties!");
//		}
//		else
//		{
//			txtMax.setText("1000");
//		}
		panel_1.add(txtCsvFile);
		txtCsvFile.setColumns(10);
		
		txtSplitter = new JTextField();
		txtSplitter.setForeground(Color.GRAY);
		txtSplitter.setText(",");
		panel_1.add(txtSplitter);
		txtSplitter.setColumns(10);
		
		txtStart = new JTextField();
		txtStart.setToolTipText("Was ist der letzte Wert, der eingetragen werden soll? Zusaetzlich wird die Haelfte des Herzschlags abgezogen. (keine Aenderung notwendig)");
		txtStart.setForeground(Color.GRAY);
		panel_1.add(txtStart);
		txtStart.setColumns(10);
		
		txtEnd = new JTextField();
		txtEnd.setToolTipText("Was ist der letzte Wert, der eingetragen werden soll? Zusaetzlich wird die Haelfte des Herzschlags hinzugefuegt. (keine Aenderung notwendig)");
		txtEnd.setForeground(Color.GRAY);
//		txtEnd.setText("1396339280200");
		int heartbeat = Integer.parseInt(txtStepReal.getText());
//		System.out.println("Heartbeat: " + heartbeat);
//		txtEnd.setText(c.lastTime(txtCsvFile.getText(), txtSplitter.getText(), heartbeat));		
		panel_1.add(txtEnd);
		txtEnd.setColumns(10);
		JButton btnGo = new JButton("GO");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					long lsTime;
					if (txtStart.getText().length()==0)
					{
						int heartbeat = Integer.parseInt(txtStepReal.getText());
//						System.out.println("Heartbeat: " + heartbeat);
					String sTime = c.firstTime(txtCsvFile.getText(), txtSplitter.getText(), heartbeat);
					lsTime = Long.parseLong(sTime); 
					System.out.println("setTimeausCSV:" + sTime);
					txtStart.setText(sTime);
					jm0.setsTime(lsTime);
					}
					else
					{
						String sTime = txtStart.getText();
						lsTime = Long.parseLong(sTime); 
//						System.out.println("schon vorhanden:" + sTime);
						jm0.setsTime(lsTime);
					}
				
					long leTime;
					int heartbeat = Integer.parseInt(txtStepReal.getText());
//					System.out.println("Heartbeat: " + heartbeat);
					String eTime = c.lastTime(txtCsvFile.getText(), txtSplitter.getText(), heartbeat);
					leTime = Long.parseLong(eTime);
//					System.out.println("Endtime:" + eTime);
					txtEnd.setText(eTime);
//					System.out.println(txtStep.getText());
					jm0.seteTime(leTime);;
				} catch (Exception e1) {
					System.out.println("Refresh gescheitert!");
					e1.printStackTrace();
				}

				// Fill in the Data of the gui-fields
				System.out.println("GO");
//				System.out.println("RrdFile: " + txtRrdFile.getText());
				jm0.setPathFile(txtRrdFile.getText());
//				pro.createBase(txtRrdFile.getText() + ".properties");
				pro.createBase(jm0.getPathFile() + ".properties");
//				System.out.println("GraphFile: " + txtGraphFile.getText());
				jm0.setPathGraph("//EMGPERFMESS01/Messdaten/graph.png");
//				System.out.println("DbName: " + txtDbName.getText());
				jm0.setDbName(txtDbName.getText());
				jm0.setPathCSV(txtCsvFile.getText());
//				System.out.println("CsvFile: " + txtCsvFile.getText());
				jm0.setCsvSplitter(txtSplitter.getText());
//				System.out.println("Splitter: " + txtSplitter.getText());
				
				long startTime = Long.parseLong(txtStart.getText());
//				System.out.println("Starttime: " + startTime);
				jm0.setsTime(startTime);
				long endTime = Long.parseLong(txtEnd.getText());
//				System.out.println("Endtime: " + endTime);
				jm0.seteTime(endTime);
				long lastTime = Long.parseLong(txtEnd.getText());
//				System.out.println("lasttime: " + lastTime);
				jm0.seteTime(lastTime);
				
				int stepReal = Integer.parseInt(txtStepReal.getText());
//				System.out.println("StepReal: " + stepReal);
				jm0.setStepReal(stepReal);
				int step = Integer.parseInt(txtStep.getText());
//				System.out.println("Heartbeat: " + step);
				jm0.setStep(step);
				int min = Integer.parseInt(txtMin.getText());
//				System.out.println("Mintime: " + min);
				jm0.setMin(min);
				int max = Integer.parseInt(txtMax.getText());
//				System.out.println("lasttime: " + max);
				jm0.setMax(max);
				
				jm0.setArRow(txtArRow.getText());
				jm0.setArStep(txtArStep.getText());
//				int arStep = Integer.parseInt(txtArStep.getText());
//				System.out.println("arStep: " + arStep);
//				jm0.setArStep(arStep);
//				System.out.println("TextArsStep: " + txtArStep.getText());
//				int arRow = Integer.parseInt(txtArRow.getText());
//				System.out.println("arRow: " + arRow);
//				jm0.setArRow(arRow);
				
				pro.createP(txtRrdFile.getText(), txtDbName.getText(), txtCsvFile.getText(), "//EMGPERFMESS01/Messdaten/rrd/graph.png", txtSplitter.getText(), txtStart.getText(), txtStep.getText(), txtStepReal.getText(), txtMin.getText(), txtMax.getText());
				
//				String wtf = c.lastTime(txtCsvFile.getText(), txtSplitter.getText());
//				System.out.println("WTF:" + wtf);
//				txtEnd.setText(wtf);
				
				/**
				 * If it does not exist create and define a rrd, update rrd and generate graph of it, else update rrd and generate an graph 
				 */
				// Create new File, it's necessary to control if a file exists
				File file = new File(txtRrdFile.getText());
				if (file.exists()==false)
				{
//					//Create RRD, Update DB and generate graph
					cug();
					File rrdP = new File (txtRrdFile.getText());
					if (rrdP.exists() == true)
					{
					File f = new File(txtCsvFile.getText());
					f.delete();
					}
				}
				else
				{
					//Update DB and generate graph
					ug();
					File f = new File(pro.readP(pro.readBase(), "Csvpfad"));
					f.delete();

				}
			}
		});
		btnGo.setAction(action_1);
		panel_1.add(btnGo);
	}
	
	/**
	 * cug generate and define the database, update the csv into the database and generate a graph
	 */
	protected void cug(){
		log.logFile("rrd4j.error.log");
		try {
			//Define RRD jm.createRrd("C:/rrd/e/jmsave.rrd", "time", 900, 1800, 1396338817L, 0, 1500, "1,96", "3600,3000");
			jm0.createRrd(jm0.getPathFile(), jm0.getDbName(), jm0.getStepReal(), jm0.getStep(), jm0.getsTime(), jm0.getMin(), jm0.getMax(), jm0.getArStep(), jm0.getArRow());
//			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.println("rrdtool create " + jm0.getDbName() + " --start " + jm0.getsTime() + " --step " + jm0.getStepReal() + "DS:" + jm0.getDbName() + ":GAUGE:" + jm0.getStepReal() + ":" + jm0.getMin() + ":" + jm0.getMax() + " RRA:Average:0.5:" + jm0.getArStep() + jm0.getArRow());
			jm0.setStatementC("rrdtool create " + jm0.getDbName() + " --start " + jm0.getsTime() + " --step " + jm0.getStepReal() + "DS:" + jm0.getDbName() + ":GAUGE:" + jm0.getStepReal() + ":" + jm0.getMin() + ":" + jm0.getMax() + " RRA:Average:0.5:" + jm0.getArStep() + jm0.getArRow());
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("ERROR: Kann RRD (in Define-part) nicht definieren!");
			log.logger.warning("Fehler: Kann RRD nicht definieren!");
			log.logger.warning(" txtRrdFile:" + jm0.getPathFile() + " | startTime:"+jm0.getsTime()+" | txtDbName:" + jm0.getDbName()+" | txtStep:" + jm0.getStep()+" | txtMin:"+jm0.getMin()+" | txtMax:"+jm0.getMax());
		}
		try {
			jm0.updateRrd(txtRrdFile.getText(), txtCsvFile.getText(), txtSplitter.getText(), jm0.getsTime(), jm0.geteTime(), log);
		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("ERROR: Kann DB (in update-Part) nicht updaten!" + txtRrdFile.getText()+"|startTime: "+jm0.getsTime()+"|txtDbName: " + txtDbName.getText()+"|txtStep"+jm0.getStep()+"|txtMin: "+jm0.getMin()+"|txtMax: "+jm0.getMax());
			log.logger.warning("Fehler: Kann DB nicht updaten! ");
			System.out.println("txtRrdFile: " + txtRrdFile.getText()+"|startTime: "+jm0.getsTime()+"|txtDbName: " + txtDbName.getText()+"|txtStep"+jm0.getStep()+"|txtMin: "+jm0.getMin()+"|txtMax: "+jm0.getMax());
		}
//		try {
//			jm0.createGraph(txtRrdFile.getText(), txtDbName.getText(), txtGraphFile.getText(), jm0.getsTime(), jm0.geteTime());
//
//		} catch (Exception e1) {
//			e1.printStackTrace();
//			System.err.println("ERROR: Kann Grafik (in Create-Part) nicht erstellen!");
//			l.logger.warning("Fehler: Kann Grafik nicht erzeugen!");
//		}

	}
	
	/**
	 * update the csv into the database and generate a graph
	 */
	protected void ug(){
		// necessary for logging
//		log.logFile("rrd4j.log");
		
		File fileCsv = new File(pro.readP(pro.readBase(), "Csvpfad"));
		if(fileCsv.exists()==false)	
		{
			System.out.println("CSV nicht existieren");
			log.logger.warning("CSV-Datei existiert nicht");
		}
		try {
			// Read heartbeat of Properties-File and parse it into int for using in longFirstTime()
			System.out.println("rrdtool update ");
			String stringHb = pro.readP(pro.readBase(), "Updatefrequenz");
			int intHb = Integer.parseInt(stringHb);
			String stringStartTime = pro.readP(pro.readBase(), "Startzeit");
			int intStartTime = Integer.parseInt(stringStartTime);
//			File fileCsv = new File(pro.readP(pro.readBase(), "Csvpfad"));
			
			long longEndTime = c.longLastTime(pro.readP(pro.readBase(), "Csvpfad"), pro.readP(pro.readBase(), "Trennzeichen"), intHb);
			System.out.println("Startzeit:" + pro.readP(pro.readBase(), "Startzeit"));
			System.out.println("Endzeit:" + longEndTime);
			jm0.updateRrd(pro.readP(pro.readBase(), "RRDpfad"), pro.readP(pro.readBase(), "Csvpfad"), pro.readP(pro.readBase(), "Trennzeichen"), intStartTime, longEndTime, log);					
//			jm0.createGraph(pro.readP(pro.readBase(), "RRDpfad"), pro.readP(pro.readBase(), "Datenbankname"), pro.readP(pro.readBase(), "Graphpfad"), c.longFirstTime(pro.readP(pro.readBase(), "Csvpfad"), pro.readP(pro.readBase(), "Trennzeichen"), intHb), c.longLastTime(pro.readP(pro.readBase(), "Csvpfad"), pro.readP(pro.readBase(), "Trennzeichen"), intHb));

		} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("ERROR: Kann DB nicht updaten! vorletztes try");
			log.logger.warning("Fehler: Kann DB nicht updaten!");
			log.logger.info("Moegliche Fehler: Der timestamp in der Quelldatei paﬂt nicht oder die Quelldatei ist nicht im richtigen Format");
		}
		
		fileCsv.delete();
	}

	protected void createGui()
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Cdialog cd = new Cdialog();
				try {
					Rrdtoolgui window = new Rrdtoolgui();
					String filePathCsv = "";
					window.frame.setTitle("RRD Manager");
					filePathCsv = cd.openDia("csv");
					window.txtCsvFile.setText(filePathCsv);

					// build rrdPath with aid of CsvPath - cut, cut, merge = doing handicraft work 
					System.out.println("filePathCsv:"+filePathCsv);
					String filePathWithoutNameCsv = filePathCsv.substring(0, filePathCsv.lastIndexOf("\\"));
					System.out.println("filename:"+filePathWithoutNameCsv);
					String filePathWihtoutPathAndName = filePathWithoutNameCsv.substring(0, filePathWithoutNameCsv.lastIndexOf("\\"));
					System.out.println("file Without all:" + filePathWihtoutPathAndName);
					String lastPathAndName = filePathWithoutNameCsv.substring(filePathWithoutNameCsv.lastIndexOf("\\"), filePathWithoutNameCsv.length())  + filePathCsv.substring(filePathCsv.lastIndexOf("\\"), filePathCsv.lastIndexOf(".")) + ".rrd";
					System.out.println("Needed Part of Path:" + lastPathAndName);
					window.txtRrdFile.setText("\\\\MGPERFMESS01\\Messdaten\\rrd" + lastPathAndName);
					
					
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private class SwingAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public SwingAction() {
			putValue(NAME, "GO");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}

	
	public static void main(String []args) throws Exception{
		Prop p = new Prop();
		Rrdtoolgui rGui = new Rrdtoolgui();
		File f2 = new File("rrd.properties");
		File fLog = new File("rrd4j.log");
		File fErr = new File("rrd4j.error.log");
//		File fLog = new File("rrd4j_Config.log");
		Cdialog cDia = new Cdialog();
		//If propertie exist don't start gui 
		if (f2.exists() == true)
		{
			// Update
			rGui.logInfo.logFile("rrd4j.log");
			rGui.logInfo.logger.info("Die Kofiguration fuer ein Projekt existiert bereits. Um die grafische Oberflaeche zu starten, muessen Sie die 'rrd.properties'-Datei im Quellverzeichnis loeschen");
//			rGui.log.logFile("rrd4j.error.log");
			rGui.ug();
		}
		else
		{
			// Start GUI
						if (fLog.exists() == true)
						{
							fLog.delete();
							System.out.println("Delete fLog");
						}
			rGui.createGui();
			rGui.logInfo.logFile("rrd4j.log");
			rGui.logInfo.logger.info("Neue Konfiguration fuer neue Datenbank wurde angelegt und geupdated, falls das Programm nicht geschlossen wurde. ");
			rGui.logInfo.logger.info("rrdtool create " + rGui.jm0.getStatementC());
//			if (l.checkForEmpty("rrd4j.error.log") == true)
//			{
//				System.out.println("Fehler vorhanden");
			File f = new File(p.readP(p.readBase(), "Csvpfad"));
			f.delete();
//			}
//			else
//			{
//				System.out.println("Keine Fehler");
//			}
		}
	}
}
