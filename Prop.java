package org.rrd4j.jl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Pattern;

public class Prop {
	/**
	 * @param args
	 * This class creates a popertie-file and check if it already exists
	 */
	Vector<String> pfadGlobal = new Vector<String>();
	Vector<String> pfadConfig = new Vector<String>();
	
	private int fileCounter = 0; //Diese Variable in checkGraphBase verwenden und bei jedem Aufruf von checkGraphBase muss die Variable hochgezählt werden und zur vorhandenen graph.properties ein Configpfad+fileCounter hinzugefügt werden.
	
	/**
	 * createP generates a propertieFile with lots of string as arguments
	 * @param pathFile
	 * @param dbName
	 * @param pathCSV
	 * @param pathGraph
	 * @param csvSplitter
	 * @param sTime
	 * @param step
	 * @param stepReal
	 * @param min
	 * @param max
	 */
	protected void createP(String pathFile, String dbName, String pathCSV, String pathGraph, String csvSplitter, String sTime, String step, String stepReal, String min, String max) 
	{
		//Check if file already exists
		if (new File(pathFile + ".properties").exists() == false)
		{
			File f = new File(pathFile + ".properties");
			Properties p = new Properties();
		
			p.setProperty("RRDpfad", pathFile);
			p.setProperty("Datenbankname", dbName);
			p.setProperty("Csvpfad", pathCSV);
			p.setProperty("Graphpfad", pathGraph);
			p.setProperty("Trennzeichen", csvSplitter);
			p.setProperty("Startzeit", sTime);
			p.setProperty("Zaehlschritt", stepReal);
			p.setProperty("Updatefrequenz", step);
			p.setProperty("Minimum", min);
			p.setProperty("Maximum", max);
//			System.out.println("Hinweis: " + dbName + ".properties wurde erstellt.");
		
			try {
				//save
				p.store(new FileOutputStream(f), new Date(System.currentTimeMillis()).toString());
				//normal
			} catch (FileNotFoundException e) {
				System.out.println("ERROR createP!!! Kann die angegebene Datei nicht finden!");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR createP!!! IO0Exception");
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Hinweis: " + dbName + ".properties existiert schon.");
			readP(pathFile, dbName);
		}
	}
		
	/**
	 * createBase generates a file with a link to the wholepropertie-files
	 * @param pathFileProp
	 */
	protected void createBase(String pathFileProp) 
	{
//		Check if file already exists
		if (new File(/*"C:/rrd/*/"rrd.properties").exists() == false)
		{
			File f1 = new File(/*"C:/rrd/*/"rrd.properties");
			Properties p = new Properties();
			
			System.out.println("Create Base " + pathFileProp);
		
			p.setProperty("Configpfad", pathFileProp);
			try {
				//save
				p.store(new FileOutputStream(f1), new Date(System.currentTimeMillis()).toString());
				//normal
				// props.store(new FileOutputStream("C:/props.properties"), "comment");
			} catch (FileNotFoundException e) {
				System.out.println("ERROR createBase!!! Kann die angegebene Datei nicht finden!");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR createBase!!! IOException");
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("Hinweis: rrd.properties existiert schon.");
//			readBase();
		}
	}
	
	/**
	 * readBase reads configpfad of the basepropertie
	 * @return
	 */
	protected String readBase()
	{
		String s = " ";
		try
		{
		File f = new File(/*"C:/rrd*/"rrd.properties");
		Properties p3 = new Properties();
		p3.load(new FileInputStream(f));

		s = p3.getProperty("Configpfad");//.replace('\\', ' ');
//		System.out.println("Pfad:" + s);
		}
		catch (FileNotFoundException e)
		{
		System.out.println("ERROR readBase!!! No file found!");
		}	
		catch (IOException e)
		{
		System.out.println("ERROR readBase!!! IOException!");
		}
		catch (Exception e)
		{
		System.out.println("ERROR readBase!!! Exception!");
		}	
		return s;
	}
	
	/**
	 * readBase() reads one value of one properties
	 * @param String pathFile
	 * @param String propName
	 * @return String value
	 */
	protected String readP(String pathFile, String propName)
	{
		String s = " ";
		try
		{
//			File f = new File(pathFile + ".properties");
		File f = new File(pathFile);
		Properties p = new Properties();
		p.load(new FileInputStream(f));

//		int x = Integer.valueOf(p.getProperty("X"));
//		int y = Integer.valueOf(p.getProperty("Y"));
//		String x = p.getProperty("X");
//		String y = p.getProperty("Y");
//		System.out.println("RRDpfad:" + p.getProperty("RRDpfad"));
//		System.out.println("Datenbankname:" + p.getProperty("Datenbankname"));
//		System.out.println("Csvpfad:" + p.getProperty("Csvpfad"));
//		System.out.println("Graphpfad:" + p.getProperty("Graphpfad"));
//		System.out.println("Trennzeichen:" + p.getProperty("Trennzeichen"));
//		System.out.println("Startzeit:" + p.getProperty("Startzeit"));
//		System.out.println("Zaehlschritt:" + p.getProperty("Zaehlschritt"));
//		System.out.println("Updatefrequenz:" + p.getProperty("Updatefrequenz"));
//		System.out.println("Minimum:" + p.getProperty("Minimum"));
//		System.out.println("Maximum:" + p.getProperty("Maximum"));
//		System.out.println("Pathfile: " + pathFile);
//		System.out.println("propName: " + propName);
		s = p.getProperty(propName);
//		System.out.println("Wert:" + s);
		}
		catch (Exception e)
		{
		System.out.println("ERROR readP!!! No options found, using default!");
		}	
		return s;
	}

	/**
	 * check if base for graph already exists and manage the call
	 */
//	protected void checkBaseGraph() 
//	{
////		Check if file already exists
//		if (new File("graph.properties").exists() == false)
//		{
//			File f1 = new File(/*"C:/rrd/*/"rrd.properties");
//			Properties p = new Properties();
//			
//			System.out.println("Create Base " + pathFileProp);
//		
//			p.setProperty("Configpfad", pathFileProp);
//			try {
//				//save
//				p.store(new FileOutputStream(f1), new Date(System.currentTimeMillis()).toString());
//				//normal
//				// props.store(new FileOutputStream("C:/props.properties"), "comment");
//			} catch (FileNotFoundException e) {
//				System.out.println("ERROR createBase!!! Kann die angegebene Datei nicht finden!");
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				System.out.println("ERROR createBase!!! IOException");
//				e.printStackTrace();
//			}
//		}
//		else
//		{
//			System.out.println("Hinweis: rrd.properties existiert schon.");
////			readBase();
//		}
//	}
	
	/**
	 * createGraphProp generates the base of a propertie-file of graph
	 * @param pathGraphProp
	 */
	protected void createBaseGraph(String pathGraphProp) 
	{
//			Check if file already exists
			if (new File("graph.properties").exists() == false)
			{
				File f1 = new File("graph.properties");
				Properties p = new Properties();
				
				System.out.println("Create Base " + pathGraphProp);
			
				p.setProperty("Configpfad"+fileCounter, pathGraphProp);
				++fileCounter;
				try {
					//save
//					p.store(new FileOutputStream(f1), new Date(System.currentTimeMillis()).toString());
					p.store(new FileWriter(("graph.properties"), true), "Konfigurationspfad");
					//normal
					// props.store(new FileOutputStream("C:/props.properties"), "comment");
				} catch (FileNotFoundException e) {
					System.out.println("ERROR createBase!!! Kann die angegebene Datei nicht finden!");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR createBase!!! IOException");
					e.printStackTrace();
				}
			}
			else
			{
				File f1 = new File("graph.properties");
//				f1.delete();
				System.out.println("ELSE");
				
				Properties p = new Properties();
				
				System.out.println("Create Base " + pathGraphProp);
			
				p.setProperty("Configpfad"+fileCounter, pathGraphProp);
				++fileCounter;
				try {
					//save
//					p.store(new FileOutputStream(f1), new Date(System.currentTimeMillis()).toString());
					p.store(new FileWriter(("graph.properties"), true), "Konfigurationspfad");
					//normal
					// props.store(new FileOutputStream("C:/props.properties"), "comment");
				} catch (FileNotFoundException e) {
					System.out.println("ERROR createBase!!! Kann die angegebene Datei nicht finden!");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("ERROR createBase!!! IOException");
					e.printStackTrace();
				}
//					readBaseGraph();
			}
	}

	/**
	 * readBaseGraph reads value of 'graph.properties'
	 * @return String Propertiepath
	 */
	protected String readBaseGraph()
	{
		String s = " ";
		try
		{
		File f = new File("graph.properties");
		Properties p3 = new Properties();
		p3.load(new FileInputStream(f));

		s = p3.getProperty("Configpfad"+fileCounter);//.replace('\\', ' ');
//		System.out.println("Pfad:" + s);
		}
		catch (FileNotFoundException e)
		{
		System.out.println("ERROR readBase!!! No file found!");
		}	
		catch (IOException e)
		{
		System.out.println("ERROR readBase!!! IOException!");
		}
		catch (Exception e)
		{
		System.out.println("ERROR readBase!!! Exception!");
		}	
		return s;
	}

	/**
	 * readBaseGraph reads value of 'graph.properties'
	 * @return String Propertiepath
	 */
	protected Vector<String> readBaseGraphes()
	{
		if(readElementGraphProp()>1)
		{
		String s = " ";
		Vector<String> vectorConfigPath = new Vector<String>();
		for(int i=0; i<readElementGraphProp(); ++i)
		{
			try
			{
				File f = new File("graph.properties");
				Properties p3 = new Properties();
				p3.load(new FileInputStream(f));
				System.out.println("Configpath:"+i);
				s = p3.getProperty("Configpath"+i);//.replace('\\', ' ');
				vectorConfigPath.add(p3.getProperty("Configpath"+i));
		//		System.out.println("Pfad:" + s);
				}
				catch (FileNotFoundException e)
				{
				System.out.println("ERROR readBase!!! No file found!");
				}	
				catch (IOException e)
				{
				System.out.println("ERROR readBase!!! IOException!");
				}
				catch (Exception e)
				{
				System.out.println("ERROR readBase!!! Exception!");
				}
			}
//		return pfadConfig;
//		return s;
		}
		return pfadConfig;
	}
	
	protected int readElementGraphProp()
	{
		int anzahlConfigpfad=0;
		try {
			BufferedReader in = new BufferedReader(new FileReader("graph.properties"));
			String zeile = null;
			boolean bool = false;
			while ((zeile = in.readLine()) != null) {
//				System.out.println("Gelesene Zeile: " + zeile);
				bool=Pattern.matches( "Configpfad.*", zeile );
				if(bool==true)
				{
					String configConcat=zeile.substring(11); //concat("=") ab 11Zeichen 
//					System.out.println("Configpfad"+fileCounter+"=" + configConcat);
					//Control elements of pfadConfig
					if(pfadConfig.size()<=anzahlConfigpfad)
					{
					   pfadConfig.add(configConcat);
					}
//					++fileCounter;
					++anzahlConfigpfad;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return anzahlConfigpfad;
	}
	
	protected void writeElementGraphProp()
	{
		Writer writer = null;

		try {
		    writer = new BufferedWriter(new OutputStreamWriter(
		          new FileOutputStream("filename.txt"), "utf-8"));
		    writer.write("Something");
		} catch (IOException ex) {
		  // report
		} finally {
		   try {writer.close();} catch (Exception ex) {}
		}
	}
	
//	Important: Die Endzeit ließ ich immer aus jmsave.getTimes und die Startzeit einmal aus Textfeld
	//jm.createGraphes(pathFiles, "time", "C:/rrd/e/graph.png", jm.getLongStartTime(), jm.getLongEndTime());
	/**
	 * create properties-file for values of graph
	 * @param Vektor pathFile of Typ String
	 * @param String dbName
	 * @param String pathGraph
	 * @param String sTime
	 * @param String eTime
	 * @param String permanent
	 */
	protected void createGraphP(Vector<String> pathFile, String dbName, String pathGraph, String sTime, String eTime, String permanent) 
	{
		//Check if file already exists
//		if (new File("graph.properties").exists() == false)
//		{
			//Write size of vector into properties
			int intVectorSize=pathFile.size();
			String stringVectorSize=String.valueOf(intVectorSize);
			//Write vector into propertiesfile
			File f = new File(pathGraph+".properties");
			Properties p = new Properties();
			int i=0;
		      for (Enumeration el=pathFile.elements(); el.hasMoreElements(); ) {
		    	  String path=((String)el.nextElement());
		      	p.setProperty("RRDpfad"+i, path);
		      	i++;
//		}
			p.setProperty("Dateianzahl", stringVectorSize);
			p.setProperty("Datenbankname", dbName);
			p.setProperty("Permanent", permanent);
			p.setProperty("Graphpfad", pathGraph);
			p.setProperty("Startzeit", sTime);
			p.setProperty("Endzeit", eTime);
			try {
				//save
				p.store(new FileOutputStream(f), new Date(System.currentTimeMillis()).toString());
				//normal
				// props.store(new FileOutputStream("C:/props.properties"), "comment");
			} catch (FileNotFoundException e) {
				System.out.println("ERROR createP!!! Kann die angegebene Datei nicht finden!");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("ERROR createP!!! IO0Exception");
				e.printStackTrace();
			}
		}
//		else
//		{
//			System.out.println("Hinweis: " + dbName + ".properties existiert schon.");
//			readGraphP("graph.properties", "RRDpfad");
//		}
	}
	
	/**
	 * Read RRDpfad and save the value into a vector
	 * @param pathFile
	 */
	protected Vector<String> readAndAddGraphP(String pathFile, String propName)
	{
		String s = " ";
		try
		{
		File f = new File(pathFile);
		Properties p = new Properties();
		p.load(new FileInputStream(f));
		// Due to vector is a loop necessary
		// ask for property Dateianzahl and save the value as integer to have a variable for makeing a loop 
		if(propName.equals("RRDpfad"))
		{
			int intSize=0; 
			String stringSize=p.getProperty("Dateianzahl");
//			System.out.println("Dateianzahl: "+stringSize);
			//convert int to string
			intSize=Integer.parseInt(stringSize);
			System.out.println("Dateianzahl: "+intSize);
			String stringProp="";
			for (int i=0;i<intSize;i++)
			{
//				System.out.println("RRDpfad"+i+": "+p.getProperty("RRDpfad"+i));
				stringProp=p.getProperty("RRDpfad"+i);
//				System.out.println("stringProp:"+stringProp);
				pfadGlobal.add(stringProp);
			}
		}
		s = p.getProperty(propName);
//		System.out.println("s: "+s);
		}
		catch (Exception e)
		{
		System.out.println("ERROR readP!!! No options found, using default!");
		}	
		return pfadGlobal;
	}
	
	/**
	 * read properties of graph.properties to create a graph 
	 * @param String pathFile
	 * @param String propName
	 * @return String value
	 */
	protected String readGraphP(String pathFile, String propName)
	{
		String s = " ";
		try
		{
		File f = new File(pathFile+".properties");
		Properties p = new Properties();
		p.load(new FileInputStream(f));

		// Due to vector is a loop necessary
		// ask for property Dateianzahl and save the value as integer to have a variable for makeing a loop 
		if(propName.equals("RRDpfad"))
		{
			int intSize=0; 
			String stringSize=p.getProperty("Dateianzahl");
//			System.out.println("Dateianzahl: "+stringSize);
			//convert int to string
			intSize=Integer.parseInt(stringSize);
			System.out.println("Dateianzahl: "+intSize);
			for (int i=0;i<intSize;i++)
			{
				System.out.println("RRDpfad"+i+": "+p.getProperty("RRDpfad"+i));
			}
		}
		s = p.getProperty(propName);
		System.out.println("s: "+s);
		}
		catch (Exception e)
		{
		System.out.println("ERROR readP!!! No options found, using default!");
		}	
		return s;
	}

	
//	public static void main(String[] args) {
//		Prop pro = new Prop();
//		pro.createBaseGraph("C:/rrd/a");
//		pro.createBaseGraph("C:/rrd/b");
//		pro.createBaseGraph("C:/rrd/c");
//		pro.readBaseGraphes();
//		System.out.println("ProB"+pro.readBaseGraphes());
//		pro.readElementGraphProp();
//		System.out.println("readElement:"+pro.readElementGraphProp());
//		Vector pfad = new Vector<String>();
//		pfad.add("C:/rrd/vectorgraph.rrd");
//		pfad.add("C:/rrd/graphvector.rrd");
//		pro.readGraphP("graph.properties", "RRDpfad");
//		pro.readAndAddGraphP("graph.properties", "RRDpfad");
//		Vector pfad = new Vector<String>();
//		pro.readGraphVec(pfad);
//	      for (Enumeration el=pfad.elements(); el.hasMoreElements(); ) {
//	          System.out.println("Vec:"+(String)el.nextElement());
//	}
//		System.out.println("Vec:"+pfad);
//		pro.createGraphP(pfad, "time", "C:/graphpfad.png", "212", "222", "ja");
//		pro.readGraphP("graph.properties", "Startzeit"); pro.readGraphP("graph.properties", "Endzeit");	pro.readGraphP("graph.properties", "Graphpfad");
//		pro.readGraphP("graph.properties", "Datenbankname"); pro.readGraphP("graph.properties", "Permanent"); pro.readAndAddGraphP("graph.properties", "RRDpfad");
	
//		Prop baseProp = new Prop();
//		System.out.println("Go!");
//		pro.createP("C:/rrd/e/", "was", "C:/rrd/e/s80_1.csv", "C:/rrd/e/graph.png", ",", "1396339267", "900", "900", "0", "1300");
//		p.readP("/home/johannes/Sonstiges/Programmieren/java/WorkspaceJo/rrd4j/e/eclipsehome.rrd", "Zaehlschritt");
//		pro.createBase("C:/rrd/proper.rrd");
//		System.out.println(pro.readBase());
//		pro.readP(pro.readBase(), "Updatefrequenz");
//		p.readP(p.readBase(), "Zaehlschritt");
//		pro.createBase("C:/rrd/e/c.rrd.properties");
//		System.out.println("readBase:" + pro.readBase());
//		System.out.println("pro.readBaseGraph:"+pro.readBaseGraph());
//		System.out.println(pro.readP(pro.readBaseGraph(), "Permanent"));
//		System.out.println(pro.readAndAddGraphP(pro.readBaseGraph(), "RRDpfad"));
//	}
}