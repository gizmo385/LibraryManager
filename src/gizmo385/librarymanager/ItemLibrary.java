package gizmo385.librarymanager;

import gizmo385.datastructures.sll.SingleLinkedList;
import gizmo385.librarymanager.types.Album;
import gizmo385.librarymanager.types.Book;
import gizmo385.librarymanager.types.Item;
import gizmo385.librarymanager.types.Movie;
import gizmo385.librarymanager.types.VideoGame;
import gizmo385.util.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

/**
 * A class to manage a library of Items
 * @author cachapline8
 * Included search methods: tags, id, publisher, genre, author, actor, artist, song, director, developer, name label
 */
public class ItemLibrary
{
	private SingleLinkedList<Item> userLibrary = new SingleLinkedList<Item>();
	private Logger actionLog = new Logger( "ilLog.txt" );

	/** Default Constructor - only informs log*/
	public ItemLibrary() 
	{ 
		actionLog.publishToLog( "Creating ItemLibrary - empty list");
	}

	/**
	 * Add multiple items to the library
	 * @param items An array of Items to add to the list
	 */
	public ItemLibrary( Item[] items )
	{
		actionLog.publishToLog( "Creating ItemLibrary - adding " + items.length + " items" );
		
		for( Item i : items  )
			this.userLibrary.insertInOrder( i );
	}

	/** Adds an Item to the ArrayList */
	public void addItem( Item item )
	{ 
		this.userLibrary.insertInOrder( item ); 
	}

	/** Removes an Item by reference */
	public boolean removeItem( Item item ) 
	{ 
		return this.userLibrary.remove( item ); 
	}

	/** Returns to ArrayList as an Item[] array */
	public SingleLinkedList<Item> getLibrary()
	{
		SingleLinkedList<Item> result = new SingleLinkedList<Item>();
		for( Item i : this.userLibrary )
			result.add( i );

		return result;
	}
	
	/** Truncates an array */
	public void truncateLibrary() {
		this.userLibrary = new SingleLinkedList<Item>();
	}

	/**
	 * Searches based on the tags of every item
	 * @param searchQuery The query to search for
	 * @return A SingleLinkedList instance that contains Item instances that matched the search query
	 */
	public SingleLinkedList<Item> search( String searchQuery )
	{
		actionLog.publishToLog( "Performing search for " + searchQuery );
		
		SingleLinkedList<Item> results = new SingleLinkedList<Item>();
		String trimmedQuery = searchQuery.trim(); //remove whitespace
		
		for( Item i : this.userLibrary )
			if( i.getTags().find( trimmedQuery ) != null )
				results.insertInOrder( i );
		
		return results;
	}

	/**
	 * Loads the library from a file
	 * 
	 * The library entries will be organized in the file as such:
	 * 
	 * [castTag (movie, videogame, book, album)]
	 * [name]
	 * [copies]
	 * [genre]
	 * [tags] (tag 1;tag2;tag3)
	 * 
	 * These will be followed by information respective to the Item specified in the castTag (i.e: Developer in VideoGame, director in Movie)
	 * @return Whether the load was successful or not
	 */
	public boolean loadLibrary()
	{
		actionLog.publishToLog( "Loading library from file" );
		try {
			Scanner fileScan = new Scanner( new File("library.dat") );
			this.userLibrary = new SingleLinkedList<Item>();

			//Parse library
			while( fileScan.hasNextLine() )
			{
				String castTag = fileScan.nextLine();

				if( castTag.equalsIgnoreCase( "[book]" ) ) {					
					this.addItem( loadBook( fileScan ) );
				}

				else if( castTag.equalsIgnoreCase( "[movie]" ) ) {
					this.addItem( loadMovie( fileScan ) );
				}

				else if( castTag.equalsIgnoreCase( "[videogame]" ) ) {					
					this.addItem( loadVideoGame( fileScan ) );
				}

				else if( castTag.equalsIgnoreCase( "[album]" ) ) { 					
					this.addItem( loadAlbum( fileScan ) );
				}

				else {
                    castTag = fileScan.nextLine();
				}
			}

			fileScan.close();
		} 
		catch ( NumberFormatException nfe )
		{
			System.err.println( "Invalid integer value passed to Integer.parseInt()" );
			actionLog.publishToLog( nfe );
			nfe.printStackTrace();
			return false;
		}
		catch( FileNotFoundException fnfe )
		{
			actionLog.publishToLog( fnfe );
			
			int create = JOptionPane.showConfirmDialog(null, "Library file could not be found!" + System.lineSeparator() + System.lineSeparator() + "Would you like to create one?" );
			
			if( create == JOptionPane.YES_OPTION ) {
				createNewLibraryFile();
			}
			else {
				actionLog.publishToLog( "Not creating new library file!" );
			}
		}
		
		return true;
	} // end LoadLibrary()
	
	public void createNewLibraryFile()
	{
		actionLog.publishToLog( "Attempting to create new library file..." );
		File libraryFile = new File("library.dat");
		try {
			if( libraryFile.createNewFile() ) {
				actionLog.publishToLog( "Successfully created new library file!" );
				this.loadLibrary();
			}
			else {
				
				actionLog.publishToLog( "Failed to create new library file!" );
			}
			
		} 
		catch ( IOException ioe ) {
			System.err.println( "Failed to create new file!" );
			actionLog.publishToLog( ioe );
		}
	}

	/**
	 * Saves the library to a file
	 * 
	 * <p>The library entries will be organized in the file as such:
	 * 
	 * <ul>
	 * <li>[castTag (movie, videogame, book, album)]</li>
	 * <li>[name] </li>
	 * <li>[copies] </li>
	 * <li>[genre] </li>
	 * <li>[tags] (tag 1;tag2;tag3) </li>
	 * </ul></p>
	 * <p>These will be followed by information respective to the Item specified in the castTag (i.e: Developer in VideoGame, director in Movie)
	 * @return If the save was successful or not
	 */
	public boolean saveLibrary()
	{
		actionLog.publishToLog( "Saving library to file" );
		File libFile = new File( "library.dat" );

		try
		{
			if( libFile.exists() ) {
				libFile.delete();
			}

			libFile.createNewFile();
			BufferedWriter fileOut = new BufferedWriter(new FileWriter( libFile ));

			for( Item i : this.userLibrary )
			{
				fileOut.write( i.toSaveString() );
			}
			fileOut.close();
		}
		catch( IOException ioe )
		{
			actionLog.publishToLog( ioe );
			ioe.printStackTrace();
			return false;
		}

		return true;
	} // end saveLibrary
	
	/** Loads a book from the Scanner */
	public Book loadBook( Scanner fileScan ) {
		String name, genre, author, publisher, id;
		SingleLinkedList<String> tags = new SingleLinkedList<String>();
		int copies, yearPublished;

		name = fileScan.nextLine();
		copies = Integer.parseInt( fileScan.nextLine() );
		genre = fileScan.nextLine();

		Scanner tagsParser = new Scanner( fileScan.nextLine() );
		tagsParser.useDelimiter( ";" );

		while( tagsParser.hasNext() )
			tags.add( tagsParser.next() );
		tagsParser.close();

		author = fileScan.nextLine();
		publisher = fileScan.nextLine();
		id = fileScan.nextLine();
		yearPublished = Integer.parseInt( fileScan.nextLine() );
		
		return new Book( name, copies, tags,  id, yearPublished, publisher, author, genre);
	}
	
	/** Loads a VideoGame from the Scanner */
	public VideoGame loadVideoGame( Scanner fileScan ) {
		String name, genre, developer, publisher, console, contentRating, id;
		SingleLinkedList<String> tags = new SingleLinkedList<String>();
		int copies;

		name = fileScan.nextLine();
		copies = Integer.parseInt( fileScan.nextLine() );
		genre = fileScan.nextLine();

		//Read the tags
		Scanner tagsParser = new Scanner( fileScan.nextLine() );
		tagsParser.useDelimiter( ";" );
		while( tagsParser.hasNext() )
			tags.add( tagsParser.next() );
		tagsParser.close();

		developer = fileScan.nextLine();
		publisher = fileScan.nextLine();
		console = fileScan.nextLine();
		contentRating = fileScan.nextLine();
		id = fileScan.nextLine();

		return new VideoGame( name, copies, tags, developer, publisher, genre, console, contentRating, id);
	}
	
	/** Loads an Album from the Scanner */
	public Album loadAlbum( Scanner fileScan ) {
		String name, genre, artist, label, barcodeNumber;
		SingleLinkedList<String> tags = new SingleLinkedList<String>();
		String[] songs;
		int copies, yearReleased;

		name = fileScan.nextLine();
		copies = Integer.parseInt( fileScan.nextLine() );
		genre = fileScan.nextLine();

		//Read the tags
		Scanner tagsParser = new Scanner( fileScan.nextLine() );
		tagsParser.useDelimiter( ";" );
		while( tagsParser.hasNext() )
			tags.add( tagsParser.next() );
		tagsParser.close();

		//Read the song list
		ArrayList<String> songList = new ArrayList<String>();
		tagsParser = new Scanner( fileScan.nextLine() );
		tagsParser.useDelimiter( "," );
		while( tagsParser.hasNext() )
			songList.add( tagsParser.next() );
		tagsParser.close();
		songs = new String[ songList.size () ];
		for( int i = 0; i < songList.size(); i++ )
			songs[i] = songList.get(i);

		artist = fileScan.nextLine();
		label = fileScan.nextLine();
		barcodeNumber = fileScan.nextLine();
		yearReleased = Integer.parseInt( fileScan.nextLine() );

		return new Album( name, copies, tags, artist, genre, label, songs, yearReleased, barcodeNumber );
	}
	
	/** Loads a movie from the Scanner */
	public Movie loadMovie( Scanner fileScan ) {
		String name, genre, director, rating, id;
		String[] starring;
		SingleLinkedList<String> tags = new SingleLinkedList<String>();
		int yearDirected, copies;

		name = fileScan.nextLine();
		copies = Integer.parseInt( fileScan.nextLine() );
		genre = fileScan.nextLine();

		//Read the tags
		Scanner tagsParser = new Scanner( fileScan.nextLine() );
		tagsParser.useDelimiter( ";" );
		while( tagsParser.hasNext() )
			tags.add( tagsParser.next() );
		tagsParser.close();

		//Read the star list
		ArrayList<String> starList = new ArrayList<String>();
		tagsParser = new Scanner( fileScan.nextLine() );
		tagsParser.useDelimiter( "," );
		while( tagsParser.hasNext() )
			starList.add( tagsParser.next() );
		tagsParser.close();
		starring = new String[ starList.size() ];
		for( int i = 0; i < starList.size(); i++ )
			starring[i] = starList.get(i);

		director = fileScan.nextLine();
		rating = fileScan.nextLine();
		id = fileScan.nextLine();
		yearDirected = Integer.parseInt( fileScan.nextLine() );

		return new Movie( name, copies, tags, genre, director, rating, starring, yearDirected, id);
	}
} // end ItemLibrary
