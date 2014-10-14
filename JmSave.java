package org.rrd4j.jl;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.*;

import javax.swing.colorchooser.ColorSelectionModel;

import org.omg.CORBA.Request;
import org.rrd4j.ConsolFun;
import org.rrd4j.DsType;
import org.rrd4j.core.ArcDef;
import org.rrd4j.core.ArcState;
import org.rrd4j.core.Archive;
import org.rrd4j.core.DsDef;
import org.rrd4j.core.FetchData;
import org.rrd4j.core.FetchRequest;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdDef;
import org.rrd4j.core.RrdToolReader;
import org.rrd4j.core.RrdToolkit;
import org.rrd4j.core.Sample;
import org.rrd4j.data.Aggregates;
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphDef;
import org.rrd4j.inspector.ArchiveTableModel;
import org.rrd4j.inspector.DataTableModel;
import org.rrd4j.inspector.DatasourceTableModel;
import org.rrd4j.inspector.InspectorModel;
import org.rrd4j.inspector.Util;

import com.sun.java_cup.internal.runtime.virtual_parse_stack;
import com.sun.xml.internal.fastinfoset.stax.events.StartDocumentEvent;
import com.sun.xml.internal.messaging.saaj.packaging.mime.Header;

public class JmSave {
	// Diese Klasse funktioniert, aber es muss auf die passende end- und
	// startzeit geachtet werden
	// Variablen zum Erstellen einer RRD
	protected String pathFile = "C:/rrd/c1.rrd";
	protected String dsType = "GAUGE";
	// protected String pathCSV = "C:/rrd/jm.csv";
	protected String pathCSV = "C:/rrd/csv.csv";
	protected String pathFolder = "C:/rrd/";
	protected String pathGraph = "C:/rrd/graph/graph.png";
	protected String dbName = "time";
	protected String csvSplitter = ",";
	// Startet immer den halben heartbeat früher
	protected long sTime = 1396331100L;
	protected long eTime = 1396333800L;
	// create rrd
	protected int step = 1;
	protected int stepReal = 1;
	protected int min = 0;
	protected int max = 1000;
	// addArchive
	protected String arStep = "1";
	// protected int arRow = 100;
	protected String arRow = "100";

	// Statements der Befehle
	protected String statementC = " ";
	protected String statementU = " ";
	protected String statementG = " ";

	// Var for createGraphGraph
	protected int endCounter = 0;

	private int counterColor = 0;
	// Var for start- and endtime to get Time for Textfields
	protected String startTime = " ";
	protected String endTime = " ";
	// Var for start- and endtime to calculate with them
	protected long longStartTime = 140000000L;
	protected long longEndTime =   140000000L;
	// Array for colors
	ArrayList<Color> colors = new ArrayList<Color>();
   	
	
//	colors[0]=(10, 10, 10);
	
	
	
	protected long getLongStartTime() {
		return longStartTime;
	}

	protected void setLongStartTime(long longStartTime) {
		this.longStartTime = longStartTime;
	}

	protected long getLongEndTime() {
		return longEndTime;
	}

	protected void setLongEndTime(long longEndTime) {
		this.longEndTime = longEndTime;
	}

	protected String getStartTime() {
		return startTime;
	}

	protected void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	protected String getEndTime() {
		return endTime;
	}

	protected void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	// Variables to store time in array for comparing
	Vector<Long> storeStartTime = new Vector<Long>();
	Vector<Long> storeEndTime = new Vector<Long>();

	// Create Object of DateCalc to createGraphCalc
	//

	// Vector definieren
	// Vector v = new Vector();



	protected String getStatementC() {
		return statementC;
	}

	protected void setStatementC(String statement) {
		this.statementC = statement;
	}

	//
	protected String getStatementU() {
		return statementU;
	}

	protected void setStatementU(String statementU) {
		this.statementU = statementU;
	}

	protected String getStatementG() {
		return statementG;
	}

	protected void setStatementG(String statementG) {
		this.statementG = statementG;
	}

	// protected int getArStep() {
	// String stringStep = String.valueOf(arStep);
	// return stringStep;
	// }
	protected String getArStep() {
		return arStep;
	}

	// protected void setArStep(int arStep) {
	// this.arStep = arStep;
	// }
	protected void setArStep(String arrStep) {
		this.arStep = arrStep;
	}

	// protected String getArRow() {
	// String stringRow = String.valueOf(arRow);
	// return stringRow;
	// }
	protected String getArRow() {
		return arRow;
	}

	// protected void setArRow(int arRow) {
	// this.arRow = arRow;
	// }
	protected void setArRow(String arRow) {
		this.arRow = arRow;
	}

	protected long startTime() {
		long secondsSince1970 = new Date().getTime() / 1000;
		return secondsSince1970;
	}

	protected String getPathFile() {
		return pathFile;
	}

	protected void setPathFile(String pathFile) {
		this.pathFile = pathFile;
	}

	protected String getPathCSV() {
		return pathCSV;
	}

	protected void setPathCSV(String pathCSV) {
		this.pathCSV = pathCSV;
	}

	protected String getPathFolder() {
		return pathFolder;
	}

	protected void setPathFolder(String pathFolder) {
		this.pathFolder = pathFolder;
	}

	protected String getPathGraph() {
		return pathGraph;
	}

	protected void setPathGraph(String pathGraph) {
		this.pathGraph = pathGraph;
	}

	protected String getDbName() {
		return dbName;
	}

	protected void setDbName(String dbName) {
		this.dbName = dbName;
	}

	protected String getCsvSplitter() {
		return csvSplitter;
	}

	protected void setCsvSplitter(String csvSplitter) {
		this.csvSplitter = csvSplitter;
	}

	protected long getsTime() {
		return sTime;
	}

	protected void setsTime(long sTime) {
		this.sTime = sTime;
	}

	protected long geteTime() {
		return eTime;
	}

	protected void seteTime(long eTime) {
		this.eTime = eTime;
	}

	protected String getDsType() {
		return dsType;
	}

	protected void setDsType(String dsType) {
		this.dsType = dsType;
	}

	protected int getStep() {
		return step;
	}

	protected void setStep(int step) {
		this.step = step;
	}

	protected int getStepReal() {
		return stepReal;
	}

	protected void setStepReal(int stepReal) {
		this.stepReal = stepReal;
	}

	protected int getMin() {
		return min;
	}

	protected void setMin(int min) {
		this.min = min;
	}

	protected int getMax() {
		return max;
	}

	protected void setMax(int max) {
		this.max = max;
	}

	/**
	 * createRrd creates a .rrd and set its Definition
	 * 
	 * @param pathFile
	 * @param dbName
	 * @param stepReal
	 * @param step
	 * @param startTime
	 * @param min
	 * @param max
	 * @param arStep
	 * @param arRow
	 * @throws Exception
	 */
	protected void createRrd(String pathFile, String dbName, int stepReal,
			int step, long startTime, int min, int max, String arStep,
			String arRow/* , int archives */) throws Exception {
		// Define the RRD
		// ------------------------------------------------------------------------------------------------------------------------------------------------------------
		System.out.println("rrdtool create " + pathFile + " --start "
				+ startTime + " --step " + stepReal + " DS:" + dbName
				+ ":GAUGE:" + step + ":" + min + ":" + max);

		RrdDef rrdDef = new RrdDef(pathFile);
		rrdDef.setStartTime(startTime); // timE of first value (unixtime
		// DS:ds-name:GAUGE | COUNTER | DERIVE | ABSOLUTE:heartbeat:min:max
		// -> Es wird eine Datenbank definiert, die in einem bestimmten
		// Zeitintervall Werte erwartet,
		// falls innerhalb dieser Zeit kein Wert kommt, dann wird ein leerer
		// Wert eingetragen, dies geschieht auch falls der Wert auï¿½erhalb des
		// min/max liegt.
		// -> mit stepReal werden die tatsaechlichen einzelnen Schritte
		// festgelegt:
		rrdDef.setStep(stepReal);
		// rrdDef.addDatasource("speed", DsType.COUNTER, 600, Double.NaN,
		// Double.NaN); //DB-Name, DB-Type, step(interval in seconds, Value,
		// Value)
		rrdDef.addDatasource(dbName, DsType.GAUGE, step, min, max); // DB-Name: speed, Type:Gauge, step(interval) in seconds, Value, Value)
		// Ein Archive stellt sozusagen eine eigene Sammlung innerhalb der DB
		// dar, Im nachfolgenden wird nach im 1. Archive alle 24Stunden ein
		// Durchschnitt berechnet
		// und im 2. Archive wird alle (6*300s) 30 Minuten fï¿½r 10 stunden der
		// Durchschnitt berechnet
		// http://oss.oetiker.ch/rrdtool/doc/rrdcreate.en.html
		// https://calomel.org/rrdtool.html
		// rrdDef.addDatasource(dbName, DsType.GAUGE, step, min, max);
		// GAUGE is a RRDtool directive to mean the data entered is absolute and
		// should be entered as is without any manipulation or calculations done
		// to it.
		// rrdDef.addArchive(ConsolFun.TOTAL, 0.5, 1, 2);//1 Step, 2 Rows

		// Var for count of archives
		int commas = 1;
		String[] stringArStep = null;
		String[] stringArRow = null;
		if (arStep.length() > 1) {
			// Get number of commas
			for (int i = 0; i < arStep.length(); i++) {
				if (arStep.charAt(i) == ',') {
					commas++;
					stringArStep = arStep.split(",");
				}
			}
			// Get number of commas
			for (int i = 0; i < arRow.length(); i++) {
				if (arRow.charAt(i) == ',') {
					commas++;
					stringArRow = arRow.split(",");
				}
			}
			// get each value of archive without comma
			for (int j = 0; j < Array.getLength(stringArStep); ++j) {
				System.out.println(stringArStep[j]);
			}
			for (int j = 0; j < Array.getLength(stringArRow); ++j) {
				System.out.println(stringArRow[j]);
			}
			System.out.println("Archiveanzahl: "
					+ Array.getLength(stringArStep));

			// Create Archive
			for (int i = 0; i < Array.getLength(stringArStep); ++i) {
				int helpArStep = Integer.parseInt(stringArStep[i]);
				int helpArRow = Integer.parseInt(stringArRow[i]);
				rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, helpArStep, helpArRow);
			}
			RrdDb rrdDb = new RrdDb(rrdDef);
		} else {

			// convert arStep + arRow to int
			int intArStep = Integer.parseInt(arStep);
			int intArRow = Integer.parseInt(arRow);
			// Loop for Count of archives (,)
			for (int i = 0; i < commas; ++i) {
				rrdDef.addArchive(ConsolFun.AVERAGE, 0.5, intArStep, intArRow);// 1
																				// Step,
																				// 24
																				// Rows
				System.out.println("RRA:AVERAGE:0.5:" + intArStep + ":"
						+ intArRow);
			}

			RrdDb rrdDb = new RrdDb(rrdDef);
		}
	}

	/**
	 * updateRrd pull new data into the given rrd-File
	 * @param pathFile
	 * @param csvPath
	 * @param csvSplitter
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	protected void updateRrd(String pathFile, String csvPath,
			String csvSplitter, long startTime, long endTime, Logging l) throws Exception {
		Logging log = l;
		// Update DB
		// Create DB and add Data
		// ---------------------------------------------------------------------------------------------------------------------------------------------------
		RrdDb rrdDb = new RrdDb(pathFile);
		ReadCsv rc = new ReadCsv();
		Logging dbData = new Logging();
		dbData.logFile("rrd4j.data.log");


		// Create new File, it's necessary to delete file
		File csvFile = new File(csvPath);

		// Create new Sample
		Sample sample = rrdDb.createSample();
		String updateCommand=("rrdtool update: " + pathFile +" " + "data");
		log.logger.info(updateCommand);		
		// System.out.println("Zeilen: " + rc.countL(csvFile));
		for (int i = 0; i < rc.countL(csvFile); i++) {
			sample.setAndUpdate(rc.readCSV(csvFile, csvSplitter, i,
					rc.countL((csvFile))));
			String uCommand=("rrdtool update: "
					+ pathFile
					+ " "
					+ rc.readCSV(csvFile, csvSplitter, i,
							rc.countL((csvFile))));
			System.out
					.println(uCommand);
			log.logger.info(uCommand);
		}
		// show Data in DB
		FetchRequest fetchRequest = rrdDb.createFetchRequest(ConsolFun.AVERAGE,
				startTime, endTime);
		System.out.println("rrdtool fetch " + pathFile + " --start "
				+ startTime + " --end " + endTime);
		System.out.println("Starttime: " + startTime);
		FetchData fetchData = fetchRequest.fetchData();
//		log.logFile("arrd4j.log");
		 dbData.logger.info(fetchData.dump());
//		System.out.println(fetchData.dump());
//		System.out.println(rrdDb.dump());
		 rrdDb.close();
//		 Delete CSV-File
		 csvFile.delete();
		rrdDb.close();
	}

	/**
	 * createGraph generates a new JPG-file of given data
	 * 
	 * @param pathFile
	 * @param dbName
	 * @param pathGraphFile
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	protected void createGraph(String pathFile, String dbName,
			String pathGraphFile, long startTime, long endTime)
			throws Exception {
		// Generate graph
		// -------------------------------------------------------------------------------------------------------------------------------------------------------------
		System.out.println("rrdtool graph " + pathGraphFile + " DEF:t="
				+ dbName + " start " + startTime + " end " + endTime);

		RrdGraphDef graphDef = new RrdGraphDef();
		// Size
		graphDef.setWidth(800);
		graphDef.setHeight(600);
		graphDef.setTitle(dbName);
		graphDef.setVerticalLabel("Antwortzeit");
		graphDef.setImageInfo("<img src='%s' width='%d' height = '%d'>");
		graphDef.setTimeSpan(startTime, endTime);
		graphDef.datasource("time", pathFile, dbName, ConsolFun.AVERAGE);

		graphDef.line("time", new Color(0xFF, 0, 0), null, 2);
		graphDef.setFilename(pathGraphFile);
		RrdGraph graph = new RrdGraph(graphDef);
		System.out.println(graph.getRrdGraphInfo().dump());
		BufferedImage bi = new BufferedImage(100, 100,
				BufferedImage.TYPE_INT_RGB);
		graph.render(bi.getGraphics());
	}

//	/**
//	 * Orginal method createGraphes
//	 * 
//	 * @throws Exception
//	 */
//	protected void createGraphesOrg() throws Exception {
//
//		// Generate graph
//		// -------------------------------------------------------------------------------------------------------------------------------------------------------------
//		// String[] arrayPathFile = new String[anzahlRrd];
//		// String[] arrayDbName = new String[anzahlRrd];
//		// String[] arrayStartTime = new String[anzahlRrd];
//		// String[] arrayEndTime = new String[anzahlRrd];
//
//		String pathGraphFile = "C:/rrd/e/ab_graph.png";
//		dbName = "time";
//		pathFile = "C:/rrd/e/a.rrd";
//		String pathFileB = "C:/rrd/e/b.rrd";
//		ReadCsv csv = new ReadCsv();
//		long startTimeA = csv.longFirstTime("C:/rrd/e/s80_1.csv", ",", 900);
//		long endTimeA = csv.longLastTime("C:/rrd/e/s80_1.csv", ",", 900);
//		long startTimeB = csv.longFirstTime("C:/rrd/e/s80_1.csv", ",", 900);
//		long endTimeB = csv.longLastTime("C:/rrd/e/s80_1.csv", ",", 900);
//		System.out.println("rrdtool graph " + pathGraphFile + " DEF:t="
//				+ dbName + " start " + startTimeA + " end " + endTimeA);
//		System.out.println("rrdtool graph " + pathGraphFile + " DEF:t="
//				+ dbName + " start " + startTimeB + " end " + endTimeB);
//		RrdGraphDef graphDef = new RrdGraphDef();
//		// Size
//		graphDef.setWidth(800);
//		graphDef.setHeight(600);
//		graphDef.setTitle(dbName);
//		graphDef.setVerticalLabel("Antwortzeit");
//		graphDef.setImageInfo("<img src='%s' width='%d' height = '%d'>");
//		graphDef.setTimeSpan(startTimeA, endTimeA);
//		// Loop for several rrd-Files:
//		graphDef.datasource("timea", pathFile, dbName, ConsolFun.AVERAGE);
//		graphDef.line("timea", new Color(245, 22, 22), null, 2);
//		// sceond line
//		graphDef.datasource("timeb", pathFileB, dbName, ConsolFun.AVERAGE);
//		graphDef.line("timeb", new Color(35, 230, 100), null, 2);
//
//		graphDef.setFilename(pathGraphFile);
//		RrdGraph graph = new RrdGraph(graphDef);
//		System.out.println(graph.getRrdGraphInfo().dump());
//		BufferedImage bi = new BufferedImage(100, 100,
//				BufferedImage.TYPE_INT_RGB);
//		graph.render(bi.getGraphics());
//
//		// rrdDb.close();
//	}
	
	protected void createGraphes(Vector<String> vPathFile, String dbName,
			String pathGraphFile, long startTime, long endTime) /*the value of the startTime is the last value of our database*/
			throws Exception {
		// Generate graph
		// -------------------------------------------------------------------------------------------------------------------------------------------------------------
		setColors();
		DateCalc dateCalc = new DateCalc();
		System.out.println("StartTime: "+ dateCalc.convertToDate(startTime));
		System.out.println("EndTime: "+ dateCalc.convertToDate(endTime));
		// create array it is necessay to store different times (1day,1week,1month,1year)
		long[] startArray = new long[4];
		// store different times in array
		startArray[0] = dateCalc.calcOneDay(startTime);;
		System.out.println(startArray[0]);
		startArray[1] = dateCalc.calcOneWeek(startTime);
		System.out.println(startArray[1]);
		startArray[2] = dateCalc.calcOneMonth(startTime);
		System.out.println(startArray[2]);
		startArray[3] = dateCalc.calcOneYear(startTime);
		System.out.println(startArray[3]);
		//for titlename
		String time[] = new String[4];
		time[0]=" täglich";
		time[1]=" wöchentlich";
		time[2]=" monatlich";
		time[3]=" jährlich";
		int j=0;
		// for each graph generate 4 different time periods depend on starttime and endtime
		for(int i=0;i<=3;++i)
		{
			this.counterColor=0;
			RrdGraphDef graphDef = new RrdGraphDef();
			// set size of png
			graphDef.setWidth(800);
			graphDef.setHeight(600);
			// title of graph
			graphDef.setTitle(dbName+time[j]);
			j++;
			// vertical label
			graphDef.setVerticalLabel("Antwortzeit");
			graphDef.setImageInfo("<img src='%s' width='%d' height = '%d'>");
			// real timeperiod
			graphDef.setTimeSpan(startArray[i], endTime);
			// turn of the grid in the graphic
//			graphDef.setOnlyGraph(true);
//			graphDef.setDYGrid(false);
//			graphDef.setDrawXGrid(false);
			graphDef.setAltYMrtg(true);
//			graphDef.setAntiAliasing(true);
			
			
			// Loop for several rrd-Files:
			String stringVectorPathFile = null;
			String stringVectorFile = null;
			// show different graphes: add .datasource(Linienname, PfadVonRrd, DBName, DsType)
			// Go through vector till it's empty. So you can generate different
			// graphes in one imageddddd
			for (Enumeration<String> el = vPathFile.elements(); el
					.hasMoreElements();) 
			{
				{
					stringVectorPathFile = (String) el.nextElement();
					stringVectorFile = stringVectorPathFile.substring(stringVectorPathFile.lastIndexOf("\\") + 1);
					System.out.println("rrdtool graph " + stringVectorFile
							+ " DEF:t=" + dbName + " start " + startArray[i] + " end "
							+ endTime);
	//				i++;
					graphDef.datasource(stringVectorFile, stringVectorPathFile,
							dbName, ConsolFun.AVERAGE);
					// Use diffferent colors for each line
					counterColor++;
					graphDef.line(stringVectorFile, getColor(counterColor), stringVectorFile, 2);
//					graphDef.line(stringVectorFile, randomColor(), stringVectorFile, 2);
				}
			}
//			graphDef.l
			//edit name of graphfile
			String pathGraphFileName=null;
			String stringPathGrapFile=pathGraphFile;
			pathGraphFileName=pathGraphFile.substring(0, pathGraphFile.lastIndexOf("."));
			graphDef.setFilename(pathGraphFileName+i+".png");

			RrdGraph graph = new RrdGraph(graphDef);
			System.out.println(graph.getRrdGraphInfo().dump());
			BufferedImage bi = new BufferedImage(100, 100,
					BufferedImage.TYPE_INT_RGB);
			graph.render(bi.getGraphics());
		}
	}

	/** 
	 * define and store different colors into an arrayList (used in createGraphes 
	 */
	protected void setColors()
	{
		Color cBlack = new Color(0,0,0);
//		Color cWhite = new Color(255,255,255);
		Color cRed = new Color(255,0,0);
		Color cGreen = new Color(0,255,0);
		Color cBlue = new Color(0,0,255);
		Color cYellow = new Color(255,255,0);
		Color cMagenta = new Color(255,0,255);
		Color cSilver = new Color(192,192,192);
		Color cGray = new Color(128,128,128);
		Color cMaroon = new Color(128,0,0);
		Color cOlive = new Color(128,128,0);
		Color cDarkGreen = new Color(0,128,0);
		Color cTeal= new Color(0,128,128);
		Color cNavy = new Color(0,0,128);
		colors.add(cBlack);
		colors.add(cRed);
		colors.add(cGreen);
		colors.add(cBlue);
		colors.add(cYellow);
		colors.add(cMagenta);
		colors.add(cSilver);
		colors.add(cGray);
		colors.add(cMaroon);
		colors.add(cOlive);
		colors.add(cDarkGreen);
		colors.add(cTeal);
		colors.add(cNavy);	
	}
	
    /**
     *  give back the color of appendant index
     * @param int i
     * @return color
     */
	protected Color getColor(int i)
	{

    	Color col = colors.get(i);
    	return col;
	}
	
	/**
	 * randomColor generates different colors which will used in createGraphes for each line
	 * @return new Color
	 */
	public Color randomColor()
	{
		
		
//		this.counterColor=this.counterColor+50;
//		return new Color(this.counterColor,this.counterColor,this.counterColor);
//		
	  Random random=new Random(); // Probably really put this somewhere where it gets executed only once
	  //to control the mount of generated colors
	  int red=random.nextInt(256);
	  int green=random.nextInt(128);
	  int blue=random.nextInt(200);
	  return new Color(red, green, blue);
	}
	
 	/**
	 * getFileTimes looks for the start- and endtime of the given RRD and set
	 * the Fields
	 * 
	 * @param String pathFile
	 * @throws Exception
	 */
	protected void getFileTimes(String pathFile) throws Exception {
		DateCalc dateCalc = new DateCalc();
		// Generate graph
		long graphStartTime = 140000000L;
		long graphEndTime = 140000000L;
		// Define new classes it's necessary to get start- and endtime
		// Define new RRdFile
		RrdDef rrdDef = new RrdDef(pathFile);
		RrdDb rDb = new RrdDb(pathFile, true);
		DataTableModel dataTableModel = new DataTableModel();
		File rrdFile = new File(pathFile);
		dataTableModel.setFile(rrdFile);
		ArchiveTableModel archiveTableModel = new ArchiveTableModel();
		archiveTableModel.setFile(rrdFile);
		int arcIndex = rrdDef.getDsCount();
		int dsIndex = rrdDef.getArcCount();
		archiveTableModel.setIndex(dsIndex, arcIndex);
		Archive arc = rDb.getArchive(arcIndex);
				
		// store starttime and endtime into a variable
		graphStartTime = (arc.getStartTime());
		graphEndTime = (arc.getEndTime());
		// store starttime and endtime into a vector
		storeStartTime.add(graphStartTime);
		storeEndTime.add(graphEndTime);
		// Variables for createGraphGraph
		// Variable for min
		long minStart = 0L;
		long minEnd = 0L;
		// get min of starttime and endtime
		minStart = dateCalc.findMinDate(storeStartTime);
		minEnd = dateCalc.findMinDate(storeEndTime);
		// set Long(Start/End)Time
		setStartTime(dateCalc.convertToDate(minStart));
		setLongStartTime(minStart);
		setEndTime(dateCalc.convertToDate(minEnd));
		setLongEndTime(minEnd);
	}

	/**
	 * Get lasttime from a archive
	 * -define archive with path of rrdfile
	 * -store all endtimes in vektors
	 * -determine minimum endtime of vektors
	 * @param vector<String> storedFiles
	 * @return long minEndTime
	 * @throws Exception
	 */
	protected long getFileEndTime(Vector<String> storedFiles) throws Exception {
		DateCalc dateCalc = new DateCalc();
		// Generate graph
		String stringFilePath="";
		long graphEndTime = 140000000L;
		// Save in a loop each value of vector into a string
		for (Enumeration<String> el=storedFiles.elements(); el.hasMoreElements(); ) {
			stringFilePath=(String)el.nextElement();
		          System.out.println("In getFileEndTime:"+stringFilePath);
				// Define new classes it's necessary to get start- and endtime
		// Define with stringFilePath a new RrdDef to getEndtime of Archive
		RrdDef rrdDef = new RrdDef(stringFilePath);
		RrdDb rDb = new RrdDb(stringFilePath, true);
		DataTableModel dataTableModel = new DataTableModel();
		File rrdFile = new File(stringFilePath);
		dataTableModel.setFile(rrdFile);
		ArchiveTableModel archiveTableModel = new ArchiveTableModel();
		archiveTableModel.setFile(rrdFile);
		int arcIndex = rrdDef.getDsCount();
		int dsIndex = rrdDef.getArcCount();
		archiveTableModel.setIndex(dsIndex, arcIndex);
		Archive arc = rDb.getArchive(arcIndex);
		// store endtime of archive into a variable
		graphEndTime = (arc.getEndTime());
		// store and endtime into a vector
		storeEndTime.add(graphEndTime);
		}
		// Variables for createGraphGraph
		// Variable for min
		long minEnd = 0L;
		// get min of endtime
		minEnd = dateCalc.findMinDate(storeEndTime);
		setLongEndTime(minEnd);
		return minEnd;
	}
	
	/**
	 * vectorOutput serves as testtoolkit and print all string-elements of a vector
	 * @param vec
	 */
	public void vectorOutput (Vector<String> vec)
	{
		for (Enumeration<?> el=vec.elements(); el.hasMoreElements(); ) {
			System.out.println("vector Output: "+(String)el.nextElement());
		}}

	/** it is used in logging-file
	 * vectorIntoStringFileName builds a String for filePath with ',' as seperator
	 * @param vector 
	 * @return String 
	 */
	public String vectorIntoStringFileName (Vector<String> ve)
	{
		String vectorString="";
		String wholeString="";
		String commaString=",";
	      for (Enumeration<?> el=ve.elements(); el.hasMoreElements(); ) {
	    	  vectorString = (String)el.nextElement();
	          System.out.println(vectorString);
	          wholeString=wholeString.concat(commaString+vectorString);
	}
	      return wholeString;
	}
	
//	public static void main(String[] args) throws Exception {
//		JmSave jm = new JmSave();
//		DateCalc dateCalc = new DateCalc();
//		jm.getFileTimes("C:/rrd/e/a.rrd");
		//		DateCalc dateCalc = new DateCalc();
//		jm.getFileTimes("C:/rrd/e/a.rrd");
		// ReadCsv csv = new ReadCsv();
		// // System.out.println("Unixtime: " + jm.startTime());
//		 jm.createRrd("C:/rrd/e/c.rrd", "time", 900, 900, 1396338817L, 0,
		// 1500,
		// "1,2,3", "100,200,300");
		// jm.createRrd("C:/rrd/e/jmsave.rrd", "time", 900, 1800, 1396338817L,
		// 0,
		// 1500, "1,96", "700,3000");
		// jm.createRrd("C:/rrd/e/jmsave.rrd", "time", 900, 1800, 1396338817L,
		// 0,
		// 1500, "1,96", "3600,3000");
		// jm.updateRrd("C:/rrd/e/jmsave.rrd",
		// "C:/rrd/e/scriptresults80mitTimes.csv", ",", 1396338817L,
		// 1403083854L);
		// jm.createGraph("C:/rrd/e/jmsave.rrd", "time", "C:/rrd/e/jmsave.png",
		// 1396338817L, 1403083854L);
		// jm.createGraph("C:/rrd/e/b.rrd", "time", "C:/rrd/e/ab.png",
		// csv.longFirstTime("C:/rrd/e/s80_11.csv", ",", 900),
		// csv.longLastTime("C:/rrd/e/s80_11.csv", ",", 900));
//		Vector pfad = new Vector<String>();
//		pfad.add("C:/rrd/e/a.rrd");
//		pfad.add("C:/rrd/e/c.rrd");
//		pfad.add("C:/rrd/e/c1.rrd");
//		pfad.add("C:/rrd/e/s80.rrd");
//		jm.createGraphes(pfad, "time", "C:/rrd/datetest.png", dateCalc.convertToTimestamp("01-04-2014 10:00"), dateCalc.convertToTimestamp("01-04-2014 12:00"));
//	}
}
