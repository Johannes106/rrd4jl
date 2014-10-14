package org.rrd4j.jl;

import java.awt.Frame;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Cdialog builds the different dialogs which are used in Rrdtoolgui and DualListBox1 to open or save files
 * @author j.launer
 *
 */
public class Cdialog {
	// Variable, um in der Gui den Text anzuzeigen
	protected String statusMessage = "Statusmeldung";



	public String path;// = "C:/";
	
	public String getOPath() {
		return path;
	}

	public void setOPath(String path) {
		this.path = path;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	
	

	Vector<String> vPath = new Vector<String>();

		public Vector<String> getvPath() {
		return vPath;
	}

	public void setvPath(Vector<String> vPath) {
		this.vPath = vPath;
	}

	/** 
	 * Generate a window to open a file
	 * @return
	 */
		protected String openDia(String fileTyp) {
			JFileChooser fc = new JFileChooser();
//			
			File defaultLocation = new File("//EMGPERFMESS01/Messdaten");
			// Filefilter(extension, nameToDisplay)
			fc.setFileFilter(new FileNameExtensionFilter(fileTyp, fileTyp));  // Dieser Filter ist nur in jre1.7 vorhanden
			fc.setDialogTitle("Öffnen einer " + fileTyp + "-Datei");
			fc.setToolTipText("Öffnen einer Datei, dessen Inhalt durch ein Komma getrennt ist.");
			if(defaultLocation.exists()==true)
			{
				fc.setCurrentDirectory(defaultLocation);
			}
			int state = fc.showOpenDialog(null);
			File file=null;
			
			if (state == JFileChooser.APPROVE_OPTION)
			{
				file = fc.getSelectedFile().getAbsoluteFile();									
				System.out.println(file);
			} 
			else
			{
				System.out.println("Auswahl abgebrochen");
			}
			
			return file.getAbsolutePath();
		}
		/**
		 * It lists all files in the choosen Folder (of a file)
		 */
		protected String openFolderDia(String fileTyp) {
			JFileChooser fc = new JFileChooser();
//			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//			File defaultLocation = new File("C:/rrd/e");
			File defaultLocation = new File("//EMGPERFMESS01/Messdaten/rrd");
			fc.setFileFilter(new FileNameExtensionFilter(fileTyp, fileTyp)); 
			fc.setDialogTitle("Öffnen einer " + fileTyp + "Datei im Verzeichnis um alle " + fileTyp +"-Dateien darin zu öffnen."
					+ "");
			fc.setToolTipText("Öffnen einer Datei im entsprechenden Ordner. Es werden Dateien mit der Endung .rrd erwartet");
			if(defaultLocation.exists()==true)
			{
				fc.setCurrentDirectory(defaultLocation);
			}
			int state = fc.showOpenDialog(null);
			File file=new File("C:/");
			if (state == JFileChooser.APPROVE_OPTION) 
			{
				file = fc.getCurrentDirectory();
			} 
			else
			{
				System.out.println("Auswahl abgebrochen");
				this.setStatusMessage("Auswahl abgebrochen");
//				fc.setVisible(false);
//				file = fc.getCurrentDirectory();
				
			}		
			return file.getPath();
		}
		
		/** 
		 * it lists all files of the typ rrd of the chosen folder
		 * @param path
		 * @return
		 */
			public String[] listFileRrd(String path){
				String files;
				int j=0;
			  File folders = new File(path);
//			  setOPath(path);			
			  setOPath(path);
//				System.out.println(getOPath());
			  File[] listOfFiles = folders.listFiles();
			  vPath.clear();
			  for (int i = 0; i < listOfFiles.length; i++) 
			  {
				   if (listOfFiles[i].isFile()) 
				   {
				   files = listOfFiles[i].getName();
				       if (files.endsWith(".rrd") || files.endsWith(".RRD") || files.endsWith(".rrd4j"))
				       {
//				          System.out.println(files);
//				    	   vPath.add(files);
				          vPath.add(files);
				          ++j;
				       }
				   }
			  }
//			  System.out.println("Pfad: " + getPath());
//	          System.out.println("In diesem Verzeichnis gibt es "+listOfFiles.length+" Dateien.");
			  System.out.println("In dem Verzeichnis existieren "+j+" RRDs.");
//			  System.out.println("Path: " + getOPath());
//			  setStatusMessage("Es gibt "+j+" RRDs.");
			  String []arrFiles = new String[j];
			  int anzahlRrd = j;
			  for(int ij=0;ij<anzahlRrd;ij++){
				  arrFiles[ij]=vPath.elementAt(ij);
//				  System.out.println("ArrFiles:"+arrFiles[ij]);
			  }
			  if (anzahlRrd == 0)
			  {
				  setStatusMessage("Es sind keine .Rrd enthalten.");
			  }
			  else
			  {
				  setStatusMessage("Es gibt " + anzahlRrd + " RRDs.");
			  }
			  return arrFiles;
			}
		/**
		 * it builds a opendialog and give back a string of the targetfile
		 * @param String description
		 * @param String extension
		 * @return String pathOfFile
		 */
		protected String saveDia(String description, String extension) {
			JFrame parentFrame = new JFrame();

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Speicherort wählen");
//			fileChooser.setFileFilter(new FileNameExtensionFilter(description, extension));
			File fileToSave = null;
			int userSelection;
			//do{
			userSelection = fileChooser.showSaveDialog(parentFrame);
			if (userSelection == JFileChooser.APPROVE_OPTION) 
			{
				fileToSave = fileChooser.getSelectedFile();
				System.out.println("Speicherort: " + fileToSave.getAbsolutePath()+extension );
				setStatusMessage("Speicherort ist gewählt");
			}
			if(userSelection!=0)
			{
				System.out.println("Bitte Speicherdatei angeben");
				setStatusMessage("Bitte Speicherdatei angeben");
//				setStatusMessage("Bitte Speicherdatei angeben");
//				DualListBox1.setStatus("Bitte Speicherdatei angeben");
				
			}
			//}while(userSelection!=0);
			return fileToSave.getAbsolutePath() + extension;
		}

//		public static void main(String []args)
//		{
//			Cdialog d = new Cdialog();
//			System.out.println(d.listFileRrd(d.openFolderDia()));
//			d.listFileRrd(d.openFolderDia());
//			System.out.println(d.openDia());
//			System.out.println(d.saveDia("Datenbank", "rrd"));
//		}
}
