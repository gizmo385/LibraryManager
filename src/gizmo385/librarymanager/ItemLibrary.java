package gizmo385.librarymanager;

import gizmo385.datastructures.sll.SingleLinkedList;
import gizmo385.librarymanager.types.Album;
import gizmo385.librarymanager.types.Book;
import gizmo385.librarymanager.types.Item;
import gizmo385.librarymanager.types.Movie;
import gizmo385.librarymanager.types.VideoGame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class to manage a library of Items
 * @author cachapline8
 * Included search methods: tags, id, publisher, genre, author, actor, artist, song, director, developer, name label
 */
public class ItemLibrary
{
	private SingleLinkedList<Item> userLibrary = new SingleLinkedList<Item>();

	/** Default Constructor - does nothing */
	public ItemLibrary() { };

	/**
	 * Add multiple items to the library
	 * @param items An array of Items to add to the list
	 */
	public ItemLibrary( Item[] items )
	{
		for( Item i : items  )
			this.userLibrary.insertInOrder( i );
	}

	/** Adds an Item to the ArrayList */
	public void addItem( Item item ) { this.userLibrary.insertInOrder( item ); }

	/** Removes an Item by reference */
	public boolean removeItem( Item item ) { return this.userLibrary.remove( item ); }

	/** Returns to ArrayList as an Item[] array */
	public SingleLinkedList<Item> getLibrary()
	{
		SingleLinkedList<Item> result = new SingleLinkedList<Item>();
		for( Item i : this.userLibrary )
			result.add( i );

		return result;
	}

	/**
	 * Searches based on the tags of every item
	 * @param searchQuery The query to search for
	 * @return A SingleLinkedList instance that contains Item instances that matched the search query
	 */
	public SingleLinkedList<Item> search( String searchQuery )
	{
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
		try {
			Scanner fileScan = new Scanner( new File("library.dat") );
			this.userLibrary = new SingleLinkedList<Item>();

			//Parse library
			while( fileScan.hasNextLine() )
			{
				String castTag = fileScan.nextLine();
				Item item;

				if( castTag.equalsIgnoreCase( "[book]" ) )
				{
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

					item = new Book( name, copies, tags,  id, yearPublished, publisher, author, genre);
					this.addItem( item );
				}

				else if( castTag.equalsIgnoreCase( "[movie]" ) )
				{
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

					item = new Movie( name, copies, tags, genre, director, rating, starring, yearDirected, id);
					this.addItem( item );
				}

				else if( castTag.equalsIgnoreCase( "[videogame]" ) )
				{
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

					item = new VideoGame( name, copies, tags, developer, publisher, genre, console, contentRating, id);
					this.addItem( item );
				}

				else if( castTag.equalsIgnoreCase( "[album]" ) )
				{
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

					item = new Album( name, copies, tags, artist, genre, label, songs, yearReleased, barcodeNumber );
					this.addItem( item );
				}

				else
				{
                    castTag = fileScan.nextLine();
				}
			}

			fileScan.close();
		} 
		catch ( NumberFormatException nfe )
		{
			System.err.println( "Invalid integer value passed to Integer.parseInt()" );
			nfe.printStackTrace();
			return false;
		}
		catch( FileNotFoundException fnfe )
		{
			System.err.println( "Library file could not be found!" );
			fnfe.printStackTrace();
			return false;
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}

		return true;
	} // end LoadLibrary()

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
		File libFile = new File( "library.dat" );

		try
		{
			if( libFile.exists() )
				libFile.delete();

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
			ioe.printStackTrace();
			return false;
		}

		return true;
	} // end saveLibrary
} // end ItemLibrary
