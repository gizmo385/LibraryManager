package gizmo385.librarymanager.ui;

import gizmo385.util.Logger;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class AdminDialog extends JDialog {
	
	private JButton migrateLogs, truncateLogs, printLogs, close;
	private JTextArea ilLog, lmLog;
	private Logger actionLog;
	
	public AdminDialog( JFrame frame ) {
		
		//dialog settings
		super( frame, "Log Management" );
		setLayout( new FlowLayout() );
		setSize( 500, 370 );
		setResizable( false );
		setLocationRelativeTo( frame );
		setDefaultCloseOperation( JDialog.DISPOSE_ON_CLOSE );
		setModalityType( ModalityType.APPLICATION_MODAL );
		setVisible( false );
		
		//Set up components
		setUpButtons();
		setUpTA();
		
		add( new JScrollPane( ilLog ) );
		add( new JScrollPane( lmLog ) );
		add( printLogs );
		add( migrateLogs );
		add( truncateLogs );
		add( close );
	}
	
	/** Sets up the button components */
	public void setUpButtons() {
		//Set up migrateLogs button
		migrateLogs = new JButton( "Refactor Logs" );
		migrateLogs.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// migrate lmLog
				actionLog = new Logger( "lmLog.txt" );
				actionLog.toNewLog();
				
				//migrate ilLog
				actionLog = new Logger( "ilLog.txt" );
				actionLog.toNewLog();
			}
		});
		
		//Set up truncateLogs button
		truncateLogs = new JButton( "Truncate Logs" );
		truncateLogs.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// truncate lmLog
				actionLog = new Logger( "lmLog.txt" );
				actionLog.truncateLog();
				
				//truncate ilLog
				actionLog = new Logger( "ilLog.txt" );
				actionLog.truncateLog();
			}
		});
		
		//Set up print logs
		printLogs = new JButton( "Display log contents" );
		printLogs.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Read from the first log (Item Library log)
				File logBeingRead = new File( "ilLog.txt" );
				if( logBeingRead.exists() ) {
					ilLog.setText( "Item Library log file: " + System.lineSeparator() );
					try {
						Scanner fileScan = new Scanner( logBeingRead );
						
						while( fileScan.hasNextLine() ) {
							ilLog.append( fileScan.nextLine() );
							ilLog.append( System.lineSeparator() );
						}
						fileScan.close();
					} catch (FileNotFoundException fnfe) {
						System.err.println( fnfe );
					}
				}
				ilLog.setCaretPosition(0);
				
				//Read from the second log (Library Manager log)
				logBeingRead = new File( "lmLog.txt" );
				if( logBeingRead.exists() ) {
					lmLog.setText( " Library manager log file: " + System.lineSeparator() );
					try {
						Scanner fileScan = new Scanner( logBeingRead );
						
						while( fileScan.hasNextLine() ) {
							lmLog.append( fileScan.nextLine() );
							lmLog.append( System.lineSeparator() );
						}
						fileScan.close();
					} catch (FileNotFoundException fnfe) {
						System.err.println( fnfe );
					}
				}
				lmLog.setCaretPosition(0);
			}
		});
		
		close = new JButton( "Close" );
		close.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}
	
	/** Sets up the text area components */
	public void setUpTA() {
		ilLog = new JTextArea( 20, 20 );
		ilLog.setEditable( false );
		ilLog.setToolTipText( "Information from the Item Library log is printed here" );
		
		lmLog = new JTextArea( 20, 20 );
		lmLog.setEditable( false );
		lmLog.setToolTipText( "Information from the Library Manager log is printed here" );
	}
	
	/** Inverts the visibility of the JDialog */
	public void flipVisibility() {
		setVisible( ! isVisible() );
	}

}
