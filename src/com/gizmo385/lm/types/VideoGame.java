package com.gizmo385.lm.types;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a video game
 * @author Christopher
 *
 */
public class VideoGame extends Item {
	private static final long serialVersionUID = -6305473299083489814L;
	
	private String developer, publisher, console, contentRating;
	
	public VideoGame( String name, String genre, String id, int copies, ArrayList<String> tags, String developer, String publisher, String console, String contentRating ) {
		super( "video game", name, genre, id, copies, tags );
		this.developer = developer;
		this.publisher = publisher;
		this.console = console;
		this.contentRating = contentRating;
		
		//Add tags necessary tags
		this.addTags( this.developer, this.publisher, this.console, this.contentRating );
	}
	
	/**
	 * @return The game developer for this VideoGame
	 */
	public String getDeveloper() {
		return developer;
	}

	/**
	 * @return The publisher for this video game
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @return The console this video game is owned for (PC, Xbox360, SNES, etc)
	 */
	public String getConsole() {
		return console;
	}

	/**
	 * @return This video game's content rating (i.e: PEGI 16, M, E10+, etc)
	 */
	public String getContentRating() {
		return contentRating;
	}

	/**
	 * @param developer The game developer for this VideoGame
	 */
	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	/**
	 * @param publisher The publisher for this video game
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	/**
	 * @param console The console this game is owned for (PC, Xbox360, SNES, etc)
	 */
	public void setConsole(String console) {
		this.console = console;
	}


	/**
	 * @param contentRating This video game's content rating (i.e: PEGI 16, M, E10+, etc)
	 */
	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean saveToFile(BufferedWriter fileOut) {
		String lineSep = System.lineSeparator();
		
		try {
			fileOut.write( "[videogame]" );
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
			fileOut.write( this.getDeveloper() );
			fileOut.write( lineSep );
			fileOut.write( this.getPublisher() );
			fileOut.write( lineSep );
			fileOut.write( this.getConsole() );
			fileOut.write( lineSep );
			fileOut.write( this.getContentRating() );
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
	public static Item loadFromFile(Scanner fileScan) {
		//Load lines from the file
		String name = fileScan.nextLine();
		String genre = fileScan.nextLine();
		String id = fileScan.nextLine();
		int copies = Integer.parseInt( fileScan.nextLine() );
		String tagLine = fileScan.nextLine();
		String developer = fileScan.nextLine();
		String publisher = fileScan.nextLine();
		String console = fileScan.nextLine();
		String contentRating = fileScan.nextLine();
		
		//Parse tags
		ArrayList<String> tags = new ArrayList<String>();
		Scanner tagParser = new Scanner( tagLine );
		tagParser.useDelimiter( ";" );
		while( tagParser.hasNext() )
			tags.add( tagParser.next() );
		tagParser.close();
		
		return new VideoGame( name, genre, id, copies, tags, developer, publisher, console, contentRating );
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String lineSep = System.lineSeparator(); //line separator
		
		sb.append( "Title: " ).append( this.getName() ).append( lineSep );
		sb.append( "Genre: " ).append( this.getGenre() ).append( lineSep );
		sb.append( "Developer: " ).append( this.getDeveloper() ).append( lineSep );
		sb.append( "Publisher: " ).append( this.getPublisher() ).append( lineSep );
		sb.append( "Console: " ).append( this.getConsole() ).append( lineSep );
		sb.append( "Rating: " ).append( this.getContentRating() ).append( lineSep );
		sb.append( "Copies owned: " ).append( this.getCopies() ).append( lineSep );
		
		return sb.toString();
	}
}
