package gizmo385.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * A class to write messages to log a file
 * 
 * Each message published will include the time and date the message was sent and the message.
 * @author cachapline8
 *
 */
public class Logger {
	
	/** The filename that the log is under */
	private String filename;
	
	/** The File object to manipulate the log */
	private File logFile;
	
	/** Checks that the logFile exists */
	public Logger( String filename ) {
		this.filename = filename;
		
		this.logFile = new File( this.filename );
		
		//Create the file if it doesn't exist
		try {
			if( !this.logFile.exists() ) { 
				if( !this.logFile.createNewFile() ) {
					System.err.println( "Error creating logfile!" );
				}
			}
		}
		catch ( IOException ioe ) {
			System.err.println( ioe.toString() );
		}
	} // end constructor
	
	/** Deletes and recreates the file */
	public boolean truncateLog() {
		//Delete the file if it exists and then return the result of createNewFile()
		try {
			if( this.logFile.exists() ) {
				this.logFile.delete();
			}
			
			return this.logFile.createNewFile();
		}
		catch ( IOException ioe ) {
			System.err.println( ioe );
			return false;
		}
	}
	
	/** Publishes a message to the logFile */
	public boolean publishToLog( String message ) {
		try {
			BufferedWriter fileOut = new BufferedWriter(new FileWriter( this.logFile, true ));
			
			fileOut.write( new Date().toString() );
			fileOut.write(": ");
			fileOut.write( message );
			fileOut.write( System.lineSeparator() );
			
			fileOut.close();
			
			return true;
		}
		catch( FileNotFoundException fnfe ) {
			
			System.err.println( fnfe );
			return false;
		}
		catch( IOException ioe ) {
			System.err.println( ioe );
			return false;
		}
	}
	
	/** Publishes an Exception to the logFile */
	public boolean publishToLog( Exception e ) {
		try {
			BufferedWriter fileOut = new BufferedWriter( new FileWriter( this.logFile, true ) );
			
			fileOut.write( new Date().toString() );
			fileOut.write(": ");
			fileOut.write( e.toString() );
			fileOut.write( System.lineSeparator() );
			
			fileOut.close();
			
			return true;
		}
		catch( FileNotFoundException fnfe ) {
			
			System.err.println( fnfe );
			return false;
		}
		catch( IOException ioe ) {
			System.err.println( ioe );
			return false;
		}
	}
	
	/** Creates a new log, but first renames the old log with the filename, the date, .old, and then the old extension */
	public boolean toNewLog() {
		try {
			if( this.logFile.exists() ) {
				StringBuilder rename = new StringBuilder();
				rename.append( this.filename.substring(0, filename.indexOf('.') ) ).append( new Date().toString() ).append(".old").append( this.filename.substring( filename.indexOf('.')));
				
				logFile.renameTo( new File( rename.toString() ) );
			}
			
			logFile = new File( filename );
			logFile.createNewFile();
			return true;
		}
		catch( IOException ioe ) {
			System.err.println( ioe );
			return false;
		}
	}
}
