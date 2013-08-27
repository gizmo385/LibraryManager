package com.gizmo385.lm.types;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a Movie
 * @author Christopher
 *
 */
public class Movie extends Item {
	private static final long serialVersionUID = -4801301178032374860L;
	
	private String director, rating;
	private int yearReleased;
	private ArrayList<String> stars;

	public Movie( String name, String genre, String id, int copies, ArrayList<String> tags, String director, String rating, int yearReleased, ArrayList<String> stars ) {
		super( "movie", name, genre, id, copies, tags );
		this.director = director;
		this.rating = rating;
		this.yearReleased = yearReleased;
		this.stars = new ArrayList<String>( stars );
	}

	/**
	 * @return The director of this movie
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * @return The rating of this movie (E, R, NC-17, etc)
	 */
	public String getRating() {
		return rating;
	}

	/**
	 * @return The year this movie was released
	 */
	public int getYearReleased() {
		return yearReleased;
	}

	/**
	 * @return The stars of this movie
	 */
	public ArrayList<String> getStars() {
		return new ArrayList<String>( this.stars );
	}

	/**
	 * @param director The director of this movie
	 */
	public void setDirector(String director) {
		this.director = director;
	}

	/**
	 * @param rating The rating of this movie (E, R, NC-17, etc)
	 */
	public void setRating(String rating) {
		this.rating = rating;
	}

	/**
	 * @param yearReleased The year this movie was released
	 */
	public void setYearReleased(int yearReleased) {
		this.yearReleased = yearReleased;
	}

	/**
	 * @param stars The stars of this movie
	 */
	public void setStars(ArrayList<String> stars) {
		this.stars = new ArrayList<String>( stars );
	}


	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String lineSep = System.lineSeparator();

		sb.append( "Title: ").append( this.getName() ).append( lineSep );
		sb.append( "Director: " ).append( this.getDirector() ).append( lineSep );
		sb.append( "Starring: " );
		for( String s : this.stars ) 
			sb.append( s + ", " );
		sb.append( lineSep ).append( "Genre: " ).append( this.getGenre() ).append( lineSep );
		sb.append( "Rated: " ).append( this.getRating() );
		sb.append( "Released in " ).append( this.getYearReleased () ).append( lineSep );
		
		
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean saveToFile(BufferedWriter fileOut) {
		String lineSep = System.lineSeparator();

		try {
			fileOut.write( "[movie]" );
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
			fileOut.write( this.getDirector() );
			fileOut.write( lineSep );
			fileOut.write( this.getRating() );
			fileOut.write( lineSep );
			fileOut.write( this.getYearReleased() );
			fileOut.write( lineSep );
			for( String s : this.stars )
				fileOut.write( s + ";" );
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
		//Load lines from the file
		String name = fileScan.nextLine();
		String genre = fileScan.nextLine();
		String id = fileScan.nextLine();
		int copies = Integer.parseInt( fileScan.nextLine() );
		String tagLine = fileScan.nextLine();
		String director = fileScan.nextLine();
		String rating = fileScan.nextLine();
		int yearReleased = Integer.parseInt( fileScan.nextLine() );
		String starsLine = fileScan.nextLine();

		//Parse tags
		ArrayList<String> tags = new ArrayList<String>();
		Scanner tagParser = new Scanner( tagLine );
		tagParser.useDelimiter( ";" );
		while( tagParser.hasNext() )
			tags.add( tagParser.next() );
		tagParser.close();

		//Parse songs
		ArrayList<String> stars = new ArrayList<String>();
		Scanner starsLineParser = new Scanner( starsLine );
		starsLineParser.useDelimiter( ";" );
		while( starsLineParser.hasNext() )
			stars.add( starsLineParser.next() );
		starsLineParser.close();
		
		return new Movie( name, genre, id, copies, tags, director, rating, yearReleased, stars );
	}
}
