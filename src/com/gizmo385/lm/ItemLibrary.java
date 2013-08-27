package com.gizmo385.lm;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import com.gizmo385.lm.types.Item;

/**
 * Manage a collection of Items and interface with them
 * @author Christopher
 *
 */
public class ItemLibrary {

	private ArrayList<Item> library = new ArrayList<Item>();
	private String saveLocation;
	private String libFileName;

	/**
	 * Initializes the ItemLibrary
	 */
	public ItemLibrary() {
		//create path to save location
		String userHome = System.getProperty( "user.home" );
		String sep = File.separator;
		this.saveLocation = userHome + sep + "gizmo385" + sep + "Library Manager";
		this.libFileName = "library.dat";
		
		verifySaveFileLoc();
		
		//Load and print success
		System.out.println( "Load status: " + load() );
	}

	/**
	 * Searches the tags of each item and returns matchings Items
	 * @param tagToMatch The tag to match
	 * @return An ArrayList containing items that match the tag
	 */
	public final ArrayList<Item> searchByTag( String tagToMatch ) {
		ArrayList<Item> results = new ArrayList<Item>();

		for( Item i : this.library )
			if( i.tagPresent( tagToMatch ) )
				results.add( i );

		return results;
	}

	/**
	 * Clears out the library
	 */
	public final boolean truncateLibrary() {
		this.library = new ArrayList<Item>();
		return save();
	}
	
	/**
	 * Verifies the location of the save file.
	 * 
	 * <p>Will create parent directories and the file itself if they're not present in the file system</p>
	 */
	private final void verifySaveFileLoc() {
		try {
			File saveFile = new File( saveLocation, libFileName );
			if( ! saveFile.getParentFile().exists() )
				saveFile.getParentFile().mkdirs();
			
			if( ! saveFile.exists() )
				saveFile.createNewFile();
		} 
		catch ( IOException ioe ) {
			System.err.println( ioe );
		}
	}
	
	/**
	 * Saves the library to the designated save location
	 * @return The success or failure of the save operation.
	 */
	public final boolean save() {
		verifySaveFileLoc();
		
		try {
			File file = new File( this.saveLocation, this.libFileName );
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream( file );
			ObjectOutputStream oos = new ObjectOutputStream( fos );
			
			//Write each Item to the file
			for( Item i : this.library ) {
				oos.writeObject( i );
			}
			
			//Close stream
			oos.close();
			return true;
		}
		catch( IOException ioe ) {
			return false;
		}
	}
	
	/**
	 * Loads the library from the file, located in the designated save location
	 * @return The ArrayList of items
	 */
	public final boolean load() {
		verifySaveFileLoc();
		
		try {
			FileInputStream fis = new FileInputStream( new File( this.saveLocation, this.libFileName ) );
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream( fis );
			
			this.library = new ArrayList<Item>();
			
			//Read until EOFException (end of file) is thrown 
			while( true ) {
				this.library.add( (Item) ois.readObject() );
			}
		}
		catch( EOFException eof ) {
			return true;
		}
		catch( IOException ioe ) {
			//An I/O exception of some kind as occured.
			System.err.println( ioe );
			return false;
		}
		catch( ClassNotFoundException cnfe ) {
			return false;
		}
	}

	/**
	 * Removes the specified item from the library
	 * @param item The item to remove
	 */
	public void deleteItem( Item item ) {
		this.library.remove( item );
	}

	/**
	 * Remove an item from the specified index
	 * @param index The index of the item to remove
	 */
	public void deleteItem( int index ) {
		this.library.remove( index );
	}

	/**
	 * Returns an item at a specified index
	 * @param index The index of the item to return
	 * @return The item at the specified index
	 */
	public Item getItem( int index ) {
		return this.library.get( index );
	}
	
	/**
	 * Adds an item to the library
	 * @param i The item to add
	 */
	public void add( Item i ) {
		this.library.add( i );
	}

	/**
	 * Returns a copy of the array list
	 * @return A copy of the instance ArrayList
	 */
	public ArrayList<Item> getLibrary () {
		return new ArrayList<Item>( this.library );
	}
}
