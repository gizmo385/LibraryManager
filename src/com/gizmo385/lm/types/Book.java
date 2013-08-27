package com.gizmo385.lm.types;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a book
 * @author Christopher
 *
 */
public class Book extends Item {
	private static final long serialVersionUID = -120108029461760655L;
	
	private int yearPublished;
	private String author, publisher;
	
	/**
	 * Instantiates this book object passing the tag "#book" to the superconstructor
	 * @param name The name of this book
	 * @param genre The genre of this book
	 * @param id This book's ID
	 * @param copies The number of copies of this book in inventory
	 * @param tags Words associated with this book for searching
	 * @param author The author of this book
	 * @param publisher The book's publisher
	 * @param yearPublished The year this book was published
	 */
	public Book( String name, String genre, String id, int copies, ArrayList<String> tags, String author, String publisher, int yearPublished ) {
		super( "book", name, genre, id, copies, tags );
		this.author = author;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
		
		//Add necessary tags
		this.addTags( this.author, this.publisher );
	}
	
	/**
	 * @return The year this book was published
	 */
	public int getYearPublished() {
		return yearPublished;
	}

	/**
	 * @return The author of this book
	 */
	public String getAuthor() {
		return author;
	}


	/**
	 * @return The publisher of this book
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param yearPublished The year this book was published
	 */
	public void setYearPublished(int yearPublished) {
		this.yearPublished = yearPublished;
	}

	/**
	 * @param author The author of this book
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @param publisher The publisher of this book
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean saveToFile( BufferedWriter fileOut ) {
		String lineSep = System.lineSeparator();
		
		try {
			fileOut.write( "[book]" );
			fileOut.write( lineSep );
			fileOut.write( this.getName() );
			fileOut.write( lineSep );
			fileOut.write( this.getGenre() );
			fileOut.write( lineSep );
			fileOut.write( this.getId() );
			fileOut.write( lineSep );
			fileOut.write( this.getCopies() );
			fileOut.write( lineSep );
			for( String s : this.getTags() ) //save tags and delimit each tag with a semicolon
				fileOut.write( s + ";" );
			fileOut.write( lineSep );
			fileOut.write( this.getAuthor() );
			fileOut.write( lineSep );
			fileOut.write( this.getPublisher() );
			fileOut.write( lineSep );
			fileOut.write( this.getYearPublished() );
			fileOut.write( lineSep );
		}
		catch( IOException ioe ) {
			return false;
		}
		return true;
	}

	/**
	 * Loads this item from the file associated with fileScan
	 * @param fileScan The Scanner that is reading from the load file
	 * @return The Item loaded from the Scanner
	 */
	public static Item loadFromFile( Scanner fileScan ) {
		try {
			//Load lines from the file
			String name = fileScan.nextLine();
			String genre = fileScan.nextLine();
			String id = fileScan.nextLine();
			int copies = Integer.parseInt( fileScan.nextLine() );
			String tagLine = fileScan.nextLine();
			String author = fileScan.nextLine();
			String publisher = fileScan.nextLine();
			int yearPublished = Integer.parseInt( fileScan.nextLine() );
			
			//Parse tags
			ArrayList<String> tags = new ArrayList<String>();
			Scanner tagParser = new Scanner( tagLine );
			tagParser.useDelimiter( ";" );
			while( tagParser.hasNext() )
				tags.add( tagParser.next() );
			tagParser.close();
			
			return new Book( name, genre, id, copies,tags, author, publisher, yearPublished );
		} catch (NumberFormatException e) {
			System.err.println( "Error loading book." + e.toString() );
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
        StringBuilder sb = new StringBuilder();
        String lineSep = System.lineSeparator(); //line separator
        
        sb.append( "Name: ").append( this.getName() ).append( lineSep );
        sb.append( "Author: " ).append( this.getAuthor() ).append( lineSep );
        sb.append( "Genre: " ).append( this.getGenre() ).append( lineSep );
        sb.append( "Published in " ).append( this.getYearPublished() ).append( " by " ).append( this.getPublisher() ).append( lineSep );
        sb.append( "Copies owned: " ).append( this.getCopies() ).append( lineSep );
        sb.append( "ISBN: " ).append( this.getId() ).append( lineSep );
        return sb.toString();
	}
}
