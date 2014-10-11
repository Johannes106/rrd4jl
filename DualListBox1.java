package org.rrd4j.jl;

/** 
 * Description of DualListBox1: The class builds a window in which you can chooose rrd-Files. It manage the gui of a duallistbox.
 * 
 *  
 * Description of Rrdgrapher: 
 * Rrdgrapher.jar: Rrdgrapher starts with a file-open-dialog in this box the user has to choose an any old file in the folder of the required RRD-Files. With aid of these rrd-datas four graph with different times will be build. The timeperiods for the charts are: (last) one day, (last) one week, (last) one month, (last) one year. 
 */

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.JTextField;

import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.jrrd.RRDatabase;

import com.sleepycat.je.rep.impl.node.Feeder.ExitException;

import sun.rmi.log.ReliableLog.LogFile;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

@SuppressWarnings("serial")
public class DualListBox1 extends JPanel {
	@SuppressWarnings("rawtypes")
	private JList sourceList;
	private SortedListModel sourceListModel;
	@SuppressWarnings("rawtypes")
	private JList destList;

	protected SortedListModel destListModel;
	Vector<String> pathFilesVector = new Vector<String>();

	// For gui (open-, close-window)
	Cdialog c = new Cdialog();
	// for functionality
	JmSave jm = new JmSave();

	Prop pro = new Prop();
	// for calculation with date
	static DateCalc dateCalc = new DateCalc();
	// for logging of errors
	static Logging lo = new Logging();

	private JButton addButton;

	private JButton removeButton;
	private JPanel centerPanel;
	private JButton btnVerzeichnisWhlen;
	private JButton btnGraphErzeugen;
	private JTextField txtZielverzeichnis;
	private JTextField txtStartzeit;
	private JTextField txtEndzeit;
	private JPanel centerPanelTop;
	private JPanel centerPanelBottom;
	private JTextField txtStatus;
	protected JTextField txtEndzeitpunkt;
	protected JTextField txtStartzeitpunkt;
	protected String stringStartzeit = "Startzeit";
	protected String stringEndzeit = "Endezeit";
	protected String filePath = "xyz";
	protected String graphPath = "C:/rrd/graph.png";


	String stringWholeInt = "";
	private JRadioButton rdbtnPermanent;
	// private JComboBox<Serializable> comboBox;
	private JTextField txtAnzahlGrafiken;
	private JComboBox<Integer> comboBox_1;
	private JButton btnNeuenGraph;
	private JButton btnNeuenGraph_1;

	protected String getGraphPath() {
		return graphPath;
	}

	protected void setGraphPath(String graphPath) {
		this.graphPath = graphPath;
	}

	protected String getStringStartzeit() {
		return stringStartzeit;
	}

	protected void setStringStartzeit(String stringStartzeit) {
		this.stringStartzeit = stringStartzeit;
	}

	protected String getStringEndzeit() {
		return stringEndzeit;
	}

	protected void setStringEndzeit(String stringEndzeit) {
		this.stringEndzeit = stringEndzeit;
	}

	public DualListBox1() {
		initScreen();
	}

	public void clearSourceListModel() {
		sourceListModel.clear();
	}

	public void clearDestinationListModel() {
		destListModel.clear();
	}

	public void addSourceElements(ListModel<String> newValue) {
		fillListModel(sourceListModel, newValue);
	}

	private void fillListModel(SortedListModel model,
			ListModel<String> newValues) {
		int size = newValues.getSize();
		for (int i = 0; i < size; i++) {
			model.add(newValues.getElementAt(i));
		}
	}

	public void addSourceElements() {
		Object newValue[] = c.listFileRrd(c.openFolderDia());
		fillListModel(sourceListModel, newValue);

	}

	public void addDestinationElements(Object newValue[]) throws Exception {
		fillListModel(destListModel, newValue);
		// to pull starttime into fields
		getTimeOf();
	}

	private void fillListModel(SortedListModel model, Object newValues[]) {
		// clearSourceListModel();
		model.addAll(newValues);
	}

	// Print all values of sourcelistmodel
	@SuppressWarnings("unused")
	private void printListSourceModel() {
		// Output of Source Elements
		@SuppressWarnings("rawtypes")
		DefaultListModel listenModell = new DefaultListModel();
		int size = sourceListModel.getSize();
		for (int i = 0; i < size; i++) {
			System.out
					.println("GET SOURCE: " + sourceListModel.getElementAt(i));
		}
		System.out.println("Anzahl der verfügbaren Elemente: " + size);
		// System.out.println("GET Path: " + c.getOPath());
	}

	/**
	 * printListDestinationModel returns all whole paths of the selected Files
	 * 
	 * @param countArray
	 * @return String
	 */
	protected String printListDestinationModel(int countArray) {
		// Output of Destination Elements
		String[] wholePaths = new String[destListModel.getSize()];
		int size = destListModel.getSize();
		for (int i = 0; i < size; i++) {
			// Output of JList 'destListModel' (Vektor)
			// save elements of 'destListModel' with added path in stringarray
			wholePaths[i] = c.getOPath() + "\\"
					+ (String) destListModel.getElementAt(i);
			pathFilesVector.add(wholePaths[i]);
		}
		return wholePaths[countArray];
	}

	private void clearSourceSelected() {
		@SuppressWarnings("deprecation")
		Object selected[] = sourceList.getSelectedValues();
		for (int i = selected.length - 1; i >= 0; --i) {
			sourceListModel.removeElement(selected[i]);
		}
		sourceList.getSelectionModel().clearSelection();
	}

	private void clearDestinationSelected() {
		@SuppressWarnings("deprecation")
		Object selected[] = destList.getSelectedValues();
		for (int i = selected.length - 1; i >= 0; --i) {
			destListModel.removeElement(selected[i]);
		}
		destList.getSelectionModel().clearSelection();
	}

	/**
	 * setFiles() adds the selected files to destinationlist
	 */
	protected void setFiles() {
		clearSourceListModel();
		clearDestinationListModel();
		this.addSourceElements();
	}

	/**
	 * setVectorPath add a string to a vector
	 */
	protected void setVectorPath() {
		Vector<String> pathFilesVector = new Vector<String>();
		for (int i = 0; i < destListModel.getSize(); i++) {
			String filePath = printListDestinationModel(i);
			pathFilesVector.add(filePath);
		}
	}

	/**
	 * getVectorPath gives the content of a string vector back
	 * 
	 * @return
	 */
	protected Vector<String> getVectorPath() {
		Vector<String> pathFilesVector = new Vector<String>();
		for (Enumeration<String> el = pathFilesVector.elements(); el
				.hasMoreElements();) {
			System.out.println((String) el.nextElement());
		}
		return pathFilesVector;
	}

	/**
	 * getTimeOf determines start- and endtime of destination-files (it's method
	 * of JmSave) and set txtStartzeitpunkt and txtEndzeitpunkt
	 * 
	 * @throws Exception
	 */
	protected void getTimeOf() throws Exception {
		// Save each value of destListModel in one String (filePath
		for (int i = 0; i < destListModel.getSize(); i++) {
			filePath = printListDestinationModel(i);
			System.out.println("filePath:" + i + " " + filePath);
			System.out.println("getStringStartzeit:" + i + " "
					+ getStringStartzeit());
			System.out.println("getStringEndzeit:" + i + " "
					+ getStringEndzeit());
			System.out
					.println("---------------------------------------------------");
			try {
				// set Long(Start/End)Time
				jm.getFileTimes(filePath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.err.println("Fehler kann Datei nicht finden");

				txtStatus.setText("Fehler keine Datei vorhanden");
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("Diese Datei ist keine RRD4J");
				txtStatus.setText("Keine rrd4J Datei");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.err
						.println("Fehler beim Ermitteln der gesuchten Zeiten");
				txtStatus.setText("Fehler bei gesuchten Zeiten");
			}
		}
		setStringStartzeit(jm.getStartTime());
		setStringEndzeit(jm.getEndTime());
		if (txtStartzeit.getText().equals("Startzeit")
				&& txtEndzeit.getText().equals("Endzeit")) {
			txtStartzeitpunkt.setText(getStringStartzeit());
			txtEndzeitpunkt.setText(getStringEndzeit());
		}
		jm.setLongStartTime(dateCalc.convertToTimestamp(getStringStartzeit()));
		jm.setLongEndTime(dateCalc.convertToTimestamp(getStringEndzeit()));
	}

	/**
	 * compareTimeOf checks the possibility of start- and endtime
	 */
	protected void compareTimeOf() {
		// Var for times of rrd
		long firstStartTime = 0L;
		long firstEndTime = 0L;
		// Var for times in txtfields
		long userStartTime = 0L;
		long userEndTime = 0L;
		// check if value is in correct format
		// if(txtStartzeit.getText().equals("([0-9]{2})-([0-9]{2})-([0-9]{4})\\s([0-2][0-9]):([0-5][0-9])")
		// &&
		// txtEndzeit.getText().equals("([0-9]{2})-([0-9]{2})-([0-9]{4})\\s([0-2][0-9]):([0-5][0-9])")){
		// Convert start and endtime to long
		firstStartTime = jm.getLongStartTime();
		firstEndTime = jm.getLongEndTime();
		// Get time of txtfields and convert it into long
		try {
			userStartTime = dateCalc.convertToTimestamp(txtStartzeit.getText());
			userEndTime = dateCalc.convertToTimestamp(txtEndzeit.getText());
			// compare userStartTime and userEndTime with the value of
			// txtStartTime and txtEndTime
			if (firstStartTime > userStartTime || firstEndTime < userEndTime) {
				System.out
						.println("userstartTime liegt außerhalb der Datenbank (compareTimeOf)");
				// print ERROR-message
				txtStatus.setForeground(Color.red);
				txtStatus
						.setText("Zeit liegt außerhalb der RRD (compareTimeOf)");
			} else {
				System.out
						.println("userstartTime liegt im gültigen Zeitraum (compareTimeOf)");
				// txtStatus.setForeground(Color.gray);
				// txtStatus.setText("Zeiten ok");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("Fehler beim Vergleichen (compareTimeOf)");
		}

	}

	/**
	 * initSceen builds the elements to gui
	 */
	private void initScreen() {
		setLayout(new GridLayout(0, 3));
		sourceListModel = new SortedListModel();
		removeButton = new JButton("X");
		removeButton.addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object selected[] = destList.getSelectedValues();
				// addSourceElements(selected);
				clearDestinationSelected();
			}
		}));

		destListModel = new SortedListModel();
		destList = new JList(destListModel);

		JPanel rightPanel = new JPanel(new BorderLayout());

		rightPanel.add(new JLabel("Ausgew\u00E4hlte Elemente:"),
				BorderLayout.NORTH);
		rightPanel.add(new JScrollPane(destList), BorderLayout.CENTER);
		rightPanel.add(removeButton, BorderLayout.SOUTH);
		sourceList = new JList(sourceListModel);

		addButton = new JButton(">>");
		addButton.addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object selected[] = sourceList.getSelectedValues();
				try {
					addDestinationElements(selected);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				clearSourceSelected();
			}
		}));

		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(new JLabel("Verf\u00FCgbare Elemente:"),
				BorderLayout.NORTH);
		leftPanel.add(new JScrollPane(sourceList), BorderLayout.CENTER);
		// System.out.println(sourceList);
		leftPanel.add(addButton, BorderLayout.SOUTH);
		// System.out.println(leftPanel);

		add(leftPanel);

		centerPanel = new JPanel();
		add(centerPanel);

		centerPanelTop = new JPanel();
		centerPanel.add(centerPanelTop);
		centerPanelTop.setLayout(new GridLayout(5, 1, 0, 0));

		btnVerzeichnisWhlen = new JButton("Quellverzeichnis w\u00E4hlen");
		btnVerzeichnisWhlen.setToolTipText("RRD Folder");
		btnVerzeichnisWhlen.addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setFiles();
				txtStatus.setText(c.getStatusMessage());
			}
		}));
		centerPanelTop.add(btnVerzeichnisWhlen);

		txtZielverzeichnis = new JTextField();
		txtZielverzeichnis.setToolTipText("Zielverzeichnis f\u00FCr Speicherort w\u00E4hlen");
		centerPanelTop.add(txtZielverzeichnis);
		txtZielverzeichnis.setText("\\\\EMGPERFMESS01\\Messdaten\\png");
		txtZielverzeichnis.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				// setGraphPath(c.saveDia("PNG", ".png"));
				String graphPath = c.saveDia("PNG", ".png");
				txtStatus.setText(c.getStatusMessage());
				txtZielverzeichnis.setText(graphPath);
				setGraphPath(txtZielverzeichnis.getText());
				// txtZielverzeichnis.setText(getGraphPath());
				System.out.println("Graphverzeichnis: " + getGraphPath());
				txtStatus.setForeground(Color.GRAY);
				txtStatus.setText("Speicherort ist gewählt");
			}
		});
		txtZielverzeichnis.setColumns(10);

		btnGraphErzeugen = new JButton("Graph erzeugen");
		btnGraphErzeugen.setToolTipText("Graph f\u00FCr letzen Tag, letzte Woche, letzten Monat und letztes Jahr");
		btnGraphErzeugen.addActionListener((new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Graph wird erzeugt");
				c.setStatusMessage("Graph wird erzeugt");
				txtStartzeit.setForeground(Color.GRAY);
				// String s = txtStartzeit.getText();
				txtStatus.setText(c.getStatusMessage());
				// setStartZeit
				setStringStartzeit(txtStartzeit.getText());
				setStringEndzeit(txtEndzeit.getText());
				try {
					boxCreateGraph();
				} catch (Exception e1) {
					e1.printStackTrace();
					System.out.println("Fehler bei create");
					// txtStatus.setForeground(Color.RED);
					// txtStatus.setText("Fehler beim Erzeugen");
				}
				// System.out.println("btnGr: " + c.getOPath());
				// System.out.println("Size" + destList.getSelectionModel());
			}
		}));
		centerPanelTop.add(btnGraphErzeugen);

		txtStartzeit = new JTextField();
		txtStartzeit.setForeground(Color.LIGHT_GRAY);
		txtStartzeit.setEnabled(false);
		txtStartzeit.setEditable(false);
		centerPanelTop.add(txtStartzeit);
		txtStartzeit.setText("Startzeit");
		txtStartzeit.setColumns(10);

		txtEndzeit = new JTextField();
		txtEndzeit.setEditable(false);
		txtEndzeit.setEnabled(false);
		centerPanelTop.add(txtEndzeit);
		txtEndzeit.setText("Endzeit");
		txtEndzeit.setColumns(10);
		// btnGraphErzeugen.addActionListener((new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		//
		// }
		// }));
		centerPanelBottom = new JPanel();
		centerPanel.add(centerPanelBottom);
		centerPanelBottom.setLayout(new GridLayout(3, 1, 1, 1));

		txtStatus = new JTextField();
		txtStatus.setToolTipText("Info");
		// Cdialog cd = new Cdialog();
		txtStatus.setText(c.getStatusMessage());
		txtStatus.setEditable(false);
		txtStatus.setForeground(Color.GRAY);

		centerPanelBottom.add(txtStatus);
		txtStatus.setColumns(15);

		txtStartzeitpunkt = new JTextField();
		txtStartzeitpunkt.setEnabled(false);
		txtStartzeitpunkt.setEditable(false);
		txtStartzeitpunkt.setForeground(Color.GRAY);
		txtStartzeitpunkt.setText("Erster-Startzeitpunkt");
		centerPanelBottom.add(txtStartzeitpunkt);
		txtStartzeitpunkt.setColumns(10);

		txtEndzeitpunkt = new JTextField();
		txtEndzeitpunkt.setEnabled(false);
		txtEndzeitpunkt.setEditable(false);
		txtEndzeitpunkt.setForeground(Color.GRAY);
		txtEndzeitpunkt.setText("Letzter-Endzeitpunkt");
		centerPanelBottom.add(txtEndzeitpunkt);
		txtEndzeitpunkt.setColumns(10);

		rdbtnPermanent = new JRadioButton("permanent");
		rdbtnPermanent.setSelected(true);
		rdbtnPermanent.setToolTipText("Soll der Graph konstant erzeugt werden?");
		centerPanel.add(rdbtnPermanent);

		btnNeuenGraph_1 = new JButton("neuen Graph");
		btnNeuenGraph_1.setToolTipText("neue Properties erstellen");
		btnNeuenGraph_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setGraphPath(txtZielverzeichnis.getText());
				// txtStartzeit.setText(t);
				c.setStatusMessage("zusätzlich Graph erzeugt");
				txtStartzeit.setForeground(Color.GRAY);
				// String s = txtStartzeit.getText();
				txtStatus.setText(c.getStatusMessage());
				// setStartZeit
				setStringStartzeit(txtStartzeit.getText());
				setStringEndzeit(txtEndzeit.getText());

				System.out.println("Create new Graphes");
				try {
					setVectorPath();
					writeIntoPropAndCreate();
				} catch (Exception e) {
					System.out.println("writeIntoProp does not work");
					e.printStackTrace();
				}
				// clearFieldsForNew();
			}
		});
		centerPanel.add(btnNeuenGraph_1);

		// combobox for new values
		// comboBox_1 = new JComboBox();
		// if(comboBox_1.getItemCount()==0)
		// comboBox_1.addItem(counter);
		//
		// btnNeuenGraph = new JButton("neuen Graph");
		// btnNeuenGraph.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// counter++;
		// ArrayList<Integer> scripts = new ArrayList<Integer>();
		// System.out.println("Graph "+counter);
		// comboBox_1.addItem(counter);
		// scripts.add(counter);
		// try {
		// writeIntoPropAndCreate();
		// } catch (Exception e1) {
		// System.out.println("Fehler bei writeIntoP");
		// e1.printStackTrace();
		// }
		// clearFieldsForNew();
		//
		// }
		// });
		// centerPanel.add(btnNeuenGraph);
		// centerPanel.add(comboBox_1);

		add(rightPanel);

	}

	protected void clearFieldsForNew() {
		txtEndzeit.setText("");
		txtStartzeit.setText("");
		txtZielverzeichnis.setText("");
	}

	/**
	 * writeIntoProp write values of GUI into properties-file and generate a
	 * graph with its values
	 * 
	 * @throws Exception
	 */
	protected void writeIntoPropAndCreate() throws Exception {
		/*
		 * TOdo: 1. check if file exist done 2. save endtime from archive into
		 * long done 3. save values from properties into other variables done
		 */
		// for radiobutton
		boolean checkPermanent = rdbtnPermanent.isSelected();
		String checkPer = "";// String.valueOf(checkPermanent);
		if (checkPermanent == true) {
			checkPer = "ja";
		} else {
			checkPer = "nein";
		}
		// set path of propertiesfile
		// guarantee unique values in vector and so remove duplicates In vector
		Vector<String> uniqueVectorBc = new Vector<String>(
				new LinkedHashSet<String>(pathFilesVector));
		jm.vectorOutput(uniqueVectorBc);
		pathFilesVector.clear();
		// create Base Propertie
		// pro.createBaseGraph(getGraphPath()+".properties");
		if(getGraphPath()!="C:/rrd/graph.png")
		{
		pro.createBaseGraph(getGraphPath() + ".properties");
		// // write values into propertie-file
		pro.createGraphP(uniqueVectorBc, "time", getGraphPath(),
				txtStartzeit.getText(), getStringEndzeit(), checkPer);
		}
		else
		{
			pro.createBaseGraph(txtZielverzeichnis.getText() + ".properties");
			// // write values into propertie-file
			pro.createGraphP(uniqueVectorBc, "time", txtZielverzeichnis.getText(),
					txtStartzeit.getText(), getStringEndzeit(), checkPer);			
		}
		String strStartTime = txtStartzeit.getText();
//		long lonStartTime = dateCalc.convertToTimestamp(strStartTime);
		long lonStartTime = jm.getLongEndTime();
		long lonEndTime = jm.getLongEndTime();
//		System.out.println("lonStart:" + lonStartTime);
//		System.out.println("lonEnd:" + lonEndTime);
		jm.createGraphes(uniqueVectorBc, "time", getGraphPath(), lonStartTime,
				lonEndTime);
		// clearFieldsForNew();
		// ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
		// als naechstes muß ich mit der Klasse Prop.java arbeiten um die Werte
		// der Variablen zu speichern und anschließend automatisiert aufzurufen.
		// Da muss ich dann wieder mit einer if-Abfrage arbeiten.
		// jm.createGraph(pathFile, dbName, pathGraphFile, startTime, endTime);
	}
	
	/**
	 * boxCreate is used in initScreen of button btnGraphErzeugen to generate
	 * call generatesGraphesGraph()
	 * 
	 * @throws Exception
	 */
	protected void boxCreateGraph() throws Exception {
		/*
		 * Todo: 1. get pathFiles in a vector 2. remove duplicates in
		 * pathFilesVector 3. save values of TextField startTime, endtime,
		 * graphpath, and Datenbankname into properties Vector<String> pathFile,
		 * String dbName, String pathGraph, String sTime, String eTime, String
		 * permanent 4. execute createGraphes
		 */
		System.out.println("Create Graph");
		// Var for Date-format
		String dateFormat = "\\d\\d-\\d\\d-\\d\\d\\d\\d \\d\\d:\\d\\d";
		if (destListModel.getSize() == 0) {
			System.out.println("Keine Datei gewählt!");
			txtStatus.setForeground(Color.RED);
			txtStatus.setText("Keine Datei gewählt!");
			// txtStatus.setForeground(Color.BLACK);
		}
		// get values of textfields
		String startTime = txtStartzeit.getText();
		String endTime = txtEndzeit.getText();
		// check if value of field is in correct format 12-02-2010 12:12
		if (startTime.matches(dateFormat) && endTime.matches(dateFormat)) {
			System.out.println("StartTime wird gesetzt:" + startTime);
			setStringStartzeit(txtStartzeit.getText());
			System.out.println("EndTime wird gesetzt:" + endTime);
			setStringEndzeit(txtEndzeit.getText());
		} else {
			// get Times of start and endtime
			if (txtEndzeit.getText().equals("Endzeit")
					&& txtStartzeit.getText().equals("Startzeit")
					|| txtEndzeit.getText().equals("")
					&& txtStartzeit.getText().equals("")/*
														 * ||
														 * (txtEndzeit.getText
														 * ().matches( ".*\\w")
														 * &&
														 * txtStartzeit.getText
														 * ().matches(".*\\w"))
														 */) {
				System.out.println("No time");
				getTimeOf();
				txtStatus.setForeground(Color.red);
//				txtStatus.setText("Start- und Endzeit fehlt");
				txtStartzeit.setForeground(Color.GRAY);
				txtStartzeit.setText(jm.getStartTime());
				// txtStartzeit.setForeground(Color.BLACK);
				txtEndzeit.setForeground(Color.GRAY);
				txtEndzeit.setText(jm.getEndTime());
				// txtEndzeit.setForeground(Color.BLACK);
			} else if (txtStartzeit.getText().equals("Startzeit")) {
				getTimeOf();
				System.out.println("No time");
				txtStatus.setForeground(Color.RED);
				txtStatus.setText("Startzeit fehlt");
				txtStartzeit.setForeground(Color.GRAY);
				txtStartzeit.setText(jm.getStartTime());
				txtStartzeit.setForeground(Color.BLACK);
				// getTimeOf();#
			} else if (txtEndzeit.getText().equals("Endzeit")) {
				getTimeOf();
				System.out.println("No time");
				txtStatus.setForeground(Color.RED);
				txtStatus.setText("Endzeit fehlt");
				txtEndzeit.setForeground(Color.GRAY);
				txtEndzeit.setText(jm.getEndTime());
				txtStartzeit.setForeground(Color.BLACK);
				// compareTimeOf();
			} else if (txtEndzeit.getText().matches(".*\\d")
					&& txtStartzeit.getText().matches(".*\\d")
					&& (!startTime.matches(dateFormat) && !endTime
							.matches(dateFormat))) {
				System.out.println("Zahlen sind eingegeben");
				txtStatus.setForeground(Color.RED);
				txtStatus.setText("Falsches Eingabeformat!");
			}

			else {
				getTimeOf();
				System.out.println("Keine Zeiten vorhanden");
				// txtStartzeit.setText(txtStartzeitpunkt.getText());
				// txtEndzeit.setText(txtEndzeitpunkt.getText());
			}

			compareTimeOf();
			System.out.println("GetFilePath:" + filePath);
			for (int i = 0; i < destListModel.getSize(); ++i) {
				System.out.println("PathFiles:" + printListDestinationModel(i));
				jm.getFileTimes(printListDestinationModel(i));
			}
			System.out.println("getGraphPath:" + getGraphPath());
			System.out.println("jm.getLongStartTime:" + jm.getLongStartTime());
			System.out.println("jm.getLongEndTime  :" + jm.getLongEndTime());

			// jm.createGraphesGraph(filePath, txtStartzeit.getText(),
			// txtEndzeit.getText());

			System.out
					.println("boxCreateGraph------------------------------------------------------------------------------");
		}
		setVectorPath(); 
		writeIntoPropAndCreate();
	}

	/**
	 * createListBox builds the frame
	 * 
	 * @throws Exception
	 */
	protected void createListBox() throws Exception {
		JFrame frame = new JFrame("Graphgenerator");
		DualListBox1 dual = new DualListBox1();
		dual.addSourceElements();
		// setFiles();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(dual, BorderLayout.CENTER);
		frame.setSize(600, 300);
		frame.setVisible(true);
	}

	protected void updateAidOfProp() throws Exception {
		// Check how many pathes exist
		int pathCount = 0;
		pathCount = pro.readElementGraphProp();
		if (pathCount > 1) {
			System.out
					.println("mehrere Pfade --------------------------------------------------------------------"
							+ pathCount);
			// Remove duplicates in vector
			// Vector<String> uniqueVectorGraph = new Vector<String>(new
			// LinkedHashSet<String>(pro.readBaseGraphes()));

			// for (Enumeration<String> el=pro.readBaseGraphes().elements();
			// el.hasMoreElements(); ) {
			// System.out.println("SSS"+(String)el.nextElement());
			for (int ij = 0; ij < pathCount; ij++) {
				System.out.println("pro.readB"
						+ pro.readBaseGraphes().elementAt(ij));
				String propertiePath = pro.readBaseGraphes().elementAt(ij);
				System.out.println("PropertiePath: " + propertiePath);
				File file = new File(propertiePath);

				System.out.println(propertiePath + " ist vorhanden");
				System.out.println("Permanent ist aktiviert");
				// guarantee unique values in vector and so remove duplicates In
				// vector
				Vector<String> rrdPath = pro.readAndAddGraphP(
						pro.readBaseGraph(), "RRDpfad");
				Vector<String> uniqueVectorRrd = new Vector<String>(
						new LinkedHashSet<String>(rrdPath));
				// jm.vectorOutput(pro.readAndAddGraphP("graph.properties",
				// "RRDpfad"));
				// get EndTime
				System.out.println("MinTime:"
						+ jm.getFileEndTime(uniqueVectorRrd));
				long longEndTime = jm.getLongEndTime();
				System.out.println("longEndTime:" + longEndTime);
				// get starttime
				String stringStartTime = "";
				// stringStartTime=pro.readP("graph.properties", "Startzeit");
				stringStartTime = pro.readP(propertiePath, "Startzeit");
				long longStartTime = dateCalc
						.convertToTimestamp((stringStartTime));
				System.out.println("Start:" + longStartTime);
				// Save values from properties into variables
				String graphPath = pro.readP(propertiePath, "Graphpfad");
				String dbName = pro.readP(propertiePath, "Datenbankname");
				// build statement for output
				String graphStatement = "rrdtool graph " + graphPath
						+ " --start " + longStartTime + " --end " + longEndTime
						+ " DEF:time="
						+ jm.vectorIntoStringFileName(uniqueVectorRrd) + dbName
						+ ":AVERAGE";
				System.out.println(graphStatement);
				// write into logfile
				lo.logFile("graphgenerator.log");
				lo.logger.info(graphStatement);
				lo.logger
						.warning("Grafik wird aus Properties-Datei erstellt und permanent ist aktiviert. Um einen neuen Graph zu konfigurieren, muss die 'graph.properties' gelöscht werden.");
				// Generate Graph with execution of createGraphes
				jm.createGraphes(uniqueVectorRrd, dbName, graphPath,
						longEndTime, longEndTime);
				
			}
		} else {
			String propertiePath = pro.readBaseGraph();
			System.out.println("PropertiePath: " + propertiePath);
			File file = new File(propertiePath);

			System.out.println(propertiePath + " ist vorhanden");
			System.out.println("Permanent ist aktiviert");
			// guarantee unique values in vector and so remove duplicates In
			// vector
			Vector<String> rrdPath = pro.readAndAddGraphP(pro.readBaseGraph(),
					"RRDpfad");
			Vector<String> uniqueVector = new Vector<String>(
					new LinkedHashSet<String>(rrdPath));
			// jm.vectorOutput(pro.readAndAddGraphP("graph.properties",
			// "RRDpfad"));
			// get EndTime
			System.out.println("MinTime:" + jm.getFileEndTime(uniqueVector));
			long longEndTime = jm.getLongEndTime();
			System.out.println("longEndTime:" + longEndTime);
			// get starttime
			String stringStartTime = "";
			// stringStartTime=pro.readP("graph.properties", "Startzeit");
			stringStartTime = pro.readP(propertiePath, "Startzeit");
			long longStartTime = dateCalc.convertToTimestamp((stringStartTime));
			System.out.println("Start:" + longStartTime);
			// Save values from properties into variables
			String graphPath = pro.readP(propertiePath, "Graphpfad");
			String dbName = pro.readP(propertiePath, "Datenbankname");
			// build statement for output
			String graphStatement = "rrdtool graph " + graphPath + " --start "
					+ longStartTime + " --end " + longEndTime + " DEF:time="
					+ jm.vectorIntoStringFileName(uniqueVector) + dbName
					+ ":AVERAGE";
			System.out.println(graphStatement);
			// write into logfile
			lo.logFile("graphgenerator.log");
			lo.logger.info(graphStatement);
			lo.logger
					.warning("Grafik wird aus Properties-Datei erstellt und permanent ist aktiviert. Um einen neuen Graph zu konfigurieren, muss die 'graph.properties' gelöscht werden.");
			// Generate Graph with execution of createGraphes and use 'longEndTime' as startTime to calculate last day , week, month, year 
			jm.createGraphes(uniqueVector, dbName, graphPath, longEndTime,
					longEndTime);
		}
	}

	protected void createInstance() throws Exception {
		String propertiePath = "";
		propertiePath = pro.readBaseGraph();
		System.out.println("PropertiePath: " + propertiePath);

		File file = new File(propertiePath);
		/**
		 * start the gui if permanent is choosen and properties exists
		 */
		// chec 'graph.properties' exists and btnPermanent is selected (only
		// 'Congipath0')
		if ((file.exists() == true)
				&& (pro.readP(pro.readBaseGraph(), "Permanent").equals("ja"))) {
			System.out.println("Schreibe mithilfe der Properties");
			updateAidOfProp();
		} else {
			System.out.println("Erzeuge Gui, da Prop nicht vorhanden");
			createListBox();

		}
	}

	public static void main(String[] args) throws Exception {

		// Create objects for classes
		DualListBox1 d = new DualListBox1();
		d.createInstance();

	}
}