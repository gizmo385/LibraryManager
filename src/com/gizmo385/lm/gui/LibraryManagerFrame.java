package com.gizmo385.lm.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.gizmo385.lm.ItemLibrary;
import com.gizmo385.lm.gui.dialogs.AddItemDialog;
import com.gizmo385.lm.types.Item;

/**
 * Primary window for the Library Manager
 * 
 * <p>This window allows the user to interact with their library. Selecting items from the JList will display their full contents
 * within the JTextArea. This allows for many items to be displayed at the same time without crowding the screen. Search
 * capabilities are also provided so that the user can search for items based on items they're tagged with.</p>
 * 
 * @author Christopher
 *
 */
public class LibraryManagerFrame extends JFrame implements ActionListener, ListSelectionListener, ItemListener, WindowListener {

	//Objects
	private ItemLibrary library;
	private HashMap<String, Item> titlesToItems;
	private DefaultListModel<String> defaultListModel, currentListModel;
	
	//Dialogs
	private AddItemDialog aid;

	//Components
	private JList<String> itemsDisplaying;
	private JTextArea detailedItemDisplay;
	private JTextField searchQuery;
	private JButton search, cancel, deleteSelectedItems;
	private JScrollPane listScrollPane, textAreaScrollPane;
	private JComboBox<String> itemSelection;

	//Final Variables
	private static final long serialVersionUID = -8264093334345119816L;
	private static final int WIDTH = 535;
	private static final int HEIGHT = 430;

	/**
	 * Calls initialization method and manages JFrame settings.
	 */
	public LibraryManagerFrame() {
		super( "Library Manager" );

		init();

		super.setSize( WIDTH, HEIGHT );
		super.setLayout( new FlowLayout() );
		super.setVisible( true );
		super.setResizable( false );
		super.setLocationRelativeTo( null );
		super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	/**
	 * Loads library entires into the DefaultListModel and displays it.
	 */
	private final void reloadLibraryEntries() {
		this.library = new ItemLibrary();
		this.titlesToItems = new HashMap<String, Item>();
		this.defaultListModel = new DefaultListModel<String>();

		for( Item i : this.library.getLibrary() ) {
			this.defaultListModel.addElement( i.getName() );
			this.titlesToItems.put( i.getName(), i );
		}
		
		this.currentListModel = defaultListModel;
	}

	private final void init() {
		//Objects
		reloadLibraryEntries();

		//JList
		this.itemsDisplaying = new JList<String>( this.defaultListModel );
		this.itemsDisplaying.setSelectionMode( ListSelectionModel.MULTIPLE_INTERVAL_SELECTION );
		this.itemsDisplaying.setToolTipText( "Select items here for detailed representations to the right." );
		this.itemsDisplaying.addListSelectionListener( this ); 
		
		//Text area
		this.detailedItemDisplay = new JTextArea( 20, 22 );
		this.detailedItemDisplay.setEditable( false );
		this.detailedItemDisplay.setToolTipText( "Items selected to the left will display here." );

		//Scroll panes
		this.listScrollPane = new JScrollPane( this.itemsDisplaying );
		this.listScrollPane.setBorder( BorderFactory.createTitledBorder( "Items in the library: " ) );
		this.listScrollPane.setPreferredSize( new Dimension( 250, 345 ) );

		this.textAreaScrollPane = new JScrollPane( this.detailedItemDisplay );
		this.textAreaScrollPane.setBorder( BorderFactory.createTitledBorder( "Items currently selected: " ) );

		//Text field
		this.searchQuery = new JTextField( 15 );
		this.searchQuery.addActionListener( this );

		//Buttons
		this.search = new JButton( "Search" );
		this.search.addActionListener( this );
		
		this.deleteSelectedItems = new JButton( "Delete" );
		this.deleteSelectedItems.addActionListener( this );
		
		this.cancel = new JButton( "Cancel" );
		this.cancel.addActionListener( this );
		
		//Combo Box
		this.itemSelection = new JComboBox<String>();
		this.itemSelection.addItem( "---Add Item---" );
		this.itemSelection.addItem( "Book" );
		this.itemSelection.addItem( "Video Game" );
		this.itemSelection.addItem( "Album" );
		this.itemSelection.addItem( "Movie" );
		this.itemSelection.addItemListener( this );
		
		//Add components
		super.add( this.listScrollPane );
		super.add( this.textAreaScrollPane );
		super.add( this.searchQuery );
		super.add( this.search );
		super.add( this.cancel );
		super.add( this.itemSelection );
		super.add( this.deleteSelectedItems );
	}

	/**
	 * Manage button actions
	 */
	public void actionPerformed(ActionEvent ae) {
		if( ae.getSource() == this.searchQuery || ae.getSource() == this.search ) {
			//displays results matching the query typed into the search box
			ArrayList<Item> results = this.library.searchByTag( this.searchQuery.getText() );
			
			this.currentListModel = new DefaultListModel<String>();
			for( Item i : results ) {
				this.currentListModel.addElement( i.getName() );
			}
			
			this.itemsDisplaying.setModel( this.currentListModel );
		}
		
		else if( ae.getSource() == this.cancel ) {
			//clears out all searches, restores default list model
			this.searchQuery.setText("");
			this.detailedItemDisplay.setText("");
			this.itemsDisplaying.setModel( this.defaultListModel );
			this.currentListModel = this.defaultListModel;
		}
		
		else if( ae.getSource() == this.deleteSelectedItems ) {
			int[] selected = this.itemsDisplaying.getSelectedIndices();
			
			if( selected.length == 0 ) {
				JOptionPane.showMessageDialog( this, "No items have been selected.", "No items selected.", JOptionPane.ERROR_MESSAGE );
			}
			else {
				int confirmation = JOptionPane.showConfirmDialog( this, "Are you sure you wish to delete" + selected.length + " item(s) from the library?", "Confirm deletion.", JOptionPane.YES_NO_OPTION );
				
				if( confirmation == JOptionPane.YES_OPTION ) {
					for( int i : selected ) {
						this.library.deleteItem( this.titlesToItems.get( this.currentListModel.get( i ) ) );
					}
					
					this.library.save();
					this.library.load();
					reloadLibraryEntries();
					this.itemsDisplaying.setModel( this.defaultListModel );
				}
			}
		}
	}

	/**
	 * Manage item selections in the JList
	 */
	public void valueChanged(ListSelectionEvent ise) {
		if( ise.getSource() == this.itemsDisplaying ) {
			//displays toString() method of the selected items
			this.detailedItemDisplay.setText("");
			int[] selected = this.itemsDisplaying.getSelectedIndices();
			
			for( int i : selected ) {
				this.detailedItemDisplay.append( this.titlesToItems.get( this.currentListModel.get( i ) ).toString() + System.lineSeparator() );
			}
		}
	}
	
	/**
	 * Manage combo box selection
	 */
	public void itemStateChanged(ItemEvent ie) {
		if( ie.getSource() == this.itemSelection && ie.getStateChange() == ItemEvent.SELECTED ) {
			String s = (String)this.itemSelection.getSelectedItem();
			if( s.equals( "Book" ) ) {
				this.aid = new AddItemDialog( this, true, this.library, "book" );
				this.aid.addWindowListener( this );
			}
			else if( s.equals("Album" ) ) {
				this.aid = new AddItemDialog( this, true, this.library, "album" );
				this.aid.addWindowListener( this );
			}
			else if( s.equals( "Movie" ) ) {
				this.aid = new AddItemDialog( this, true, this.library, "movie" );
				this.aid.addWindowListener( this );
			}
			else if(s.equals( "Video Game" ) ) {
				this.aid = new AddItemDialog( this, true, this.library, "videogame" );
				this.aid.addWindowListener( this );
			}
		}
	}

	/**
	 * Create instance of LibraryManagerFrame
	 */
	public static void main( String[] args ) {
		@SuppressWarnings("unused")
		LibraryManagerFrame lmf = new LibraryManagerFrame();
	}

	/** Unused */
	public void windowActivated(WindowEvent e) { }

	/** Manages window closing */
	public void windowClosed(WindowEvent e) { 
		reloadLibraryEntries();
		this.itemsDisplaying.setModel( this.defaultListModel );
	}

	/** Manages window closing */
	public void windowClosing(WindowEvent e) { 
		reloadLibraryEntries();
		this.itemsDisplaying.setModel( this.defaultListModel );
	}

	/** Unused */
	public void windowDeactivated(WindowEvent e) { }

	/** Unused */
	public void windowDeiconified(WindowEvent e) { }

	/** Unused */
	public void windowIconified(WindowEvent e) { }

	/** Unused */
	public void windowOpened(WindowEvent e) { }
}
