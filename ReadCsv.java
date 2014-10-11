package org.rrd4j.jl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
 
public class ReadCsv {
	
	public int countL(File csvFile) {
		// reads characters
		  BufferedReader br1 = null;
			int lines = 0; 
			String line = "";
			String f="";
//			String cvsSplitBy = ",";
			try{
				//convert input of fFileReader into Characters
				br1 = new BufferedReader(new FileReader(csvFile));
//				System.out.println("Line:"+line);
				while ((line=br1.readLine()) != null && line.length()!=0)
				{
//					System.out.println(line);
					lines= lines+1;
				}		 
				br1.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.err.println("ReadCSV: Fehler beim Ermitteln der ersten Zeit");
			}
			return lines;
		   }

	public String firstTime(String csvFile, String splitter, int heartbeat) {
		BufferedReader br1 = null;
		String line = "text";
		long lineCal = 0;
//		int lineCal = 0;
		try{
		br1 = new BufferedReader(new FileReader(csvFile));
		String arrLine[] = br1.readLine().split(splitter);
		line = arrLine[0];
		lineCal = Long.parseLong(line);
		lineCal = (lineCal/1000) - (heartbeat/2);
		line = String.valueOf(lineCal);
		br1.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ReadCSV: Fehler beim Ermitteln der ersten Zeile");
		}
		System.out.println("CSV erste Zeit: " + line);
		return line;
	}
	
	public long longFirstTime(String csvFile, String splitter, int heartbeat){
		BufferedReader br1 = null;
		String line = "text";
		long lineCal = 0;
//		int lineCal = 0;
		try{
		br1 = new BufferedReader(new FileReader(csvFile));

			String arrLine[] = br1.readLine().split(splitter);
			line = arrLine[0];
			lineCal = Long.parseLong(line);
			lineCal = (lineCal/1000) - (heartbeat/2);
//			line = String.valueOf(lineCal);
		br1.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ReadCSV: Fehler beim Ermitteln der ersten Zeit");
		}
//				line = line.substring(0, line.indexOf(splitter));
		System.out.println("CSV erste Zeit: " + lineCal);
		return lineCal;
	}
	
	@SuppressWarnings({ "resource"})
	protected String lastTime(String csvFile, String csvSplitBy, int heartbeat){
		BufferedReader br = null;
		long arrLong = 14444444444L;
		String[] arrline = null;
		String strLastL = "";
		String tmp = "";
		String strT = "";
		try{
		br = new BufferedReader(new FileReader(csvFile));
		while ((tmp = br.readLine()) != null)
		{
			arrline = tmp.split(csvSplitBy);
			strLastL = arrline[0];
			arrLong = Long.parseLong(strLastL);
			arrLong = arrLong/1000 + heartbeat;
			strT = String.valueOf(arrLong);
		}
		br.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("ReadCSV: Fehler beim Ermitteln der letzen Zeile");
		}
		System.out.println("CSV letzte Zeit: " + strLastL);
		return strT;
	}
	
	  @SuppressWarnings({ "resource"})
	protected long longLastTime(String csvFile, String csvSplitBy, int heartbeat) {
		BufferedReader br = null;
		long arrLong0 = 14444444444L;
		long arrLong = 14444444444L;
		String[] arrline = null;
		  String strLastL = "";
		  String tmp = "";
		  String strT = "";
		  try{
			br = new BufferedReader(new FileReader(csvFile));
			  while ((tmp = br.readLine()) != null)
			  {
			     arrline = tmp.split(csvSplitBy);
			     strLastL = arrline[0];
			     arrLong0 = Long.parseLong(strLastL);
			     arrLong0 = arrLong0/1000; 
			     arrLong = arrLong0 + heartbeat/2;
			  }
		  } catch (Exception e) {
				e.printStackTrace();
				System.err.println("ReadCSV: Fehler beim Ermitteln der letzen Zeit");
			}
				System.out.println("LongLastTime: " + arrLong);
			  return arrLong;
		}

	public int countRow(File csvFile, String string) {
		  BufferedReader br1 = null;
			String lineString = "Hallo";
			int rowCount = 0;
//			String cvsSplitBy = ",";
			try {
				br1 = new BufferedReader(new FileReader(csvFile));
				while ((br1.readLine()) != null)
				{
					lineString = br1.readLine();
					String[] st = lineString.split(",");
					rowCount = st.length;
				}		 
					br1.close();
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println("ReadCSV: Fehler beim Ermitteln der Zeilenanzahl");
				}
			return rowCount;
		   }

	public String readCSV(File csvFile, String csvSplitBy, int lineRow, int size) {		  
		  String txtSplitted = "";
		  String[] txtArr = new String[size];
		  String[] text = null;
		  BufferedReader br = null;
			String line = "";
			int lines=0;
			int j = 0;
			String strArr = null;
//			String cvsSplitBy = ",";
			try {
				br = new BufferedReader(new FileReader(csvFile));
				while ((line = br.readLine()) != null)
				{
			        // use csvSplitBy as separator
					text = line.split(csvSplitBy);
					// Alles ausgeben:	text[0] Muss ich zuerste in long umwandeln dann 1000 abziehen und dann wieder in String umwandeln
					long arrText = Long.parseLong(text[0]);
					arrText = arrText/1000;
					strArr = String.valueOf(arrText);
					txtArr[j] = strArr + ":" + text[1];
					j++;
				}	br.close();	 
			} catch (FileNotFoundException e) {
				System.out.println("Datei wurde nicht gefunden");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Falsches Trennzeichen oder falsche Reihenanzahl");
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						System.out.println("Falsches Trennzeichen angegeben!");
						e.printStackTrace();
					}
				}
			}
			return txtArr[lineRow];
		   }
 
  public static void main(String[] args) throws Exception {
	ReadCsv r1 = new ReadCsv();
	File f = new File("C:/rrd/s100.csv");
	System.out.println("Zeilen: " + r1.countL(f));
	System.out.println("Firsttime: " + r1.firstTime("C:/rrd/s100.csv", ",", 900));
	System.out.println("Lasttime:  " + r1.lastTime("C:/rrd/s100.csv", ",", 900));
//	System.out.println("Firsttime: " + r1.longFirstTime("C:/rrd/e/s80_1.csv", ",", 900));
//	System.out.println("Lasttime:  " + r1.longLastTime("C:/rrd/e/s80_1.csv", ",", 900));
//	System.out.println("Reihen: " + r1.countRow(f));
	System.out.println(r1.readCSV(f, ",", 1,/* 1,*/ r1.countL(f)));
  }
}