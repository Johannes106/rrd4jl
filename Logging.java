package org.rrd4j.jl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Logging {
	  private static final Logger log = Logger.getLogger( Logging.class.getName() );

	   Logger logger = Logger.getLogger("MyLog");  
	    FileHandler fh;  
	    
	    /**
	     * logFile generates a File that is the base for logging
	     */
	    protected void logFile(String logPath)
	    {
	    	try {  
		        // This block configure the logger with handler and formatter  
		        fh = new FileHandler(/*"C:/rrd/rrd4j.log"*/logPath);  
		        logger.addHandler(fh);
		        SimpleFormatter formatter = new SimpleFormatter();  
		        fh.setFormatter(formatter);  
		        // the following statement is used to log any messages  
//		        logger.info();  
		    } catch (SecurityException e) {  
		        e.printStackTrace();  
		    } catch (IOException e) {  
		        e.printStackTrace();  
		    }  
	    }

	    protected boolean checkForEmpty(String fileName) throws IOException
	    {
	    	FileInputStream fis = new FileInputStream(new File(fileName));  
	    	int b = fis.read();
	    	boolean check = false;
	    	if (b == -1)  
	    	{  
	    	  System.out.println(fileName + " is empty!!");
	    	  check = true;
	    	}  
	    	return check;
	    }
	    
//	  public static void main(String[] args) throws IOException {  
//		  Logging l = new Logging();
//		  l.logFile("C:/rrd/e/l.log");
////		  l.logger.info("Tschüss");
////		  l.logger.warning("Hilfe");
//		  System.out.println(l.checkForEmpty("C:/rrd/e/l.log"));
//		    }
}