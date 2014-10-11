package org.rrd4j.jl;

import static org.rrd4j.ConsolFun.AVERAGE;
import static org.rrd4j.ConsolFun.MAX;

import java.awt.Color;
import java.io.File;
import java.util.Locale;

import org.rrd4j.ConsolFun;
import org.rrd4j.core.FetchData;
import org.rrd4j.core.FetchRequest;
import org.rrd4j.core.RrdDb;
import org.rrd4j.core.RrdSafeFileBackend;
import org.rrd4j.core.Sample;
import org.rrd4j.core.Util;
import org.rrd4j.graph.RrdGraph;
import org.rrd4j.graph.RrdGraphConstants;
import org.rrd4j.graph.RrdGraphDef;

public class Updater {
//	Rrdtoolgui rGui = new Rrdtoolgui();
	Prop pro = new Prop();
	JmSave jm = new JmSave();
	ReadCsv c = new ReadCsv();
	File f2 = new File("rrd.properties");
	File fLog = new File("rrd4j.log");
	File fErr = new File("rrd4j.error.log");
	Logging l = new Logging();
	File csvFile;

	protected void updateRrd() throws Exception {
		// Update DB
		// Create DB and add Data
		// ---------------------------------------------------------------------------------------------------------------------------------------------------
		// Get Param of Prop
	System.out.println("rrdtool update ");	
	
	String pathFile = pro.readP(pro.readBase(), "RRDpfad");
	String csvPath = pro.readP(pro.readBase(), "Csvpfad"); 
	String stringHb = pro.readP(pro.readBase(), "Updatefrequenz");
	String csvSplitter = pro.readP(pro.readBase(), "Trennzeichen");
	int intHb = Integer.parseInt(stringHb);
	String stringStartTime = pro.readP(pro.readBase(), "Startzeit");
	Long startTime = Long.parseLong(stringStartTime);
//	int intStartTime = Integer.parseInt(stringStartTime);
	long endTime = c.longLastTime(pro.readP(pro.readBase(), "Csvpfad"), pro.readP(pro.readBase(), "Trennzeichen"), intHb);
	System.out.println("Startzeit:" + pro.readP(pro.readBase(), "Startzeit"));
//	System.out.println("Endzeit:" + longEndTime);
		RrdDb rrdDb = new RrdDb(pathFile);
		ReadCsv rc = new ReadCsv();

		// Create new File, it's necessary to delete file
		File csvFile = new File(csvPath);

		// Create new Sample
		Sample sample = rrdDb.createSample();
		// System.out.println("Zeilen: " + rc.countL(csvFile));
		for (int i = 0; i < rc.countL(csvFile); i++) {
			sample.setAndUpdate(rc.readCSV(csvFile, csvSplitter, i,
					rc.countL((csvFile))));
			System.out
					.println("rrdtool update: "
							+ pathFile
							+ " "
							+ rc.readCSV(csvFile, csvSplitter, i,
									rc.countL((csvFile))));
		}
		// show Data in DB
		FetchRequest fetchRequest = rrdDb.createFetchRequest(ConsolFun.AVERAGE,
				startTime, endTime);
		System.out.println("rrdtool fetch " + pathFile + " --start "
				+ startTime + " --end " + endTime);
		System.out.println("Starttime: " + startTime);
		FetchData fetchData = fetchRequest.fetchData();
		// log.logger.info(fetchRequest.dump());
		System.out.println(fetchData.dump());
		System.out.println(rrdDb.dump());
		 rrdDb.close();
//		 Delete CSV-File
		 csvFile.delete();
		rrdDb.close();
	}
	
	protected void updateFromJM()
	{
		l.logFile("rrd4j.error.log");
		// Create new File, it's necessary to delete file
		csvFile = new File(pro.readP(pro.readBase(), "Csvpfad"));
		long t=140000000L;
		int n=0;
		try {
			// Read heartbeat of Properties-File and parse it into int for using in longFirstTime()
			System.out.println("rrdtool update ");
			String stringHb = pro.readP(pro.readBase(), "Updatefrequenz");
			int intHb = Integer.parseInt(stringHb);
			String stringStartTime = pro.readP(pro.readBase(), "Startzeit");
			int intStartTime = Integer.parseInt(stringStartTime);
			long longEndTime = c.longLastTime(pro.readP(pro.readBase(), "Csvpfad"), pro.readP(pro.readBase(), "Trennzeichen"), intHb);
			System.out.println("Startzeit:" + pro.readP(pro.readBase(), "Startzeit"));
			System.out.println("Endzeit:" + longEndTime);
			jm.updateRrd(pro.readP(pro.readBase(), "RRDpfad"), pro.readP(pro.readBase(), "Csvpfad"), pro.readP(pro.readBase(), "Trennzeichen"), intStartTime, longEndTime);					
//			TRY:
//			RrdDb rrdDb = new RrdDb(pro.readP(pro.readBase(), "RRDpfad"));
//			Sample sample = rrdDb.createSample();
//			System.out.println(pro.readP(pro.readBase(), "Csvpfad"));
//			while (t >= longEndTime) {
//			    sample.setTime(t);
//			    sample.setValue(pro.readP(pro.readBase(), "Csvpfad"), longEndTime);
//			    sample.update();
//			    n++;
//			}
//			rrdDb.close();
			csvFile.delete();

	} catch (Exception e1) {
			e1.printStackTrace();
			System.out.println("ERROR: Kann DB nicht updaten! vorletztes try");
			l.logger.warning("Fehler: Kann DB nicht updaten!");
			l.logger.info("Moegliche Fehler: Der timestamp in der Quelldatei paﬂt nicht oder die Quelldatei ist nicht im richtigen Format");
		}
	}

	protected void checkLogs()
	{
			// Update
			System.out.println("Prop already exist!!!!!!!!!!!!!!");
			if (fLog.exists() == true)
			{
				fLog.delete();
				System.out.println("fLog deleted");
			}
			if(fErr.exists() == true)
			{
				fErr.delete();
				System.out.println("fErr deleted");
			}
	}
	


	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Updater updater = new Updater();
		updater.checkLogs();
		updater.updateRrd();
				


	}

}
