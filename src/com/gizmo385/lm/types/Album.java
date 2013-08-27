package com.gizmo385.lm.types;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a music album
 * @author Christopher
 *
 */
public class Album extends Item {
	private static final long serialVersionUID = -6654556798874693337L;
	
	private String artist, label;
	private ArrayList<String> songs;
	private int yearReleased;

	public Album( String name, String genre, String id, int copies, ArrayList<String> tags, String artist, String label, ArrayList<String> songs, int yearReleased ) {
		super( "album", name, genre, id, copies, tags );
		this.artist = artist;
		this.label = label;
		this.songs = new ArrayList<String>( songs );
		this.yearReleased = yearReleased;
	}

	/**
	 * @return This album's artist
	 */
	public String getArtist() {
		return artist;
	}

	/**
	 * @return This album's label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return This album's song list
	 */
	public ArrayList<String> getSongs() {
		return new ArrayList<String>( this.songs );
	}

	/**
	 * @return The year this album was released
	 */
	public int getYearReleased() {
		return yearReleased;
	}

	/**
	 * @param artist This album's artist
	 */
	public void setArtist(String artist) {
		this.artist = artist;
	}

	/**
	 * @param label This album's label
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param songs This album's song list
	 */
	public void setSongs(ArrayList<String> songs) {
		this.songs = new ArrayList<String>( songs );
	}

	/**
	 * @param yearReleased The year this album was released
	 */
	public void setYearReleased(int yearReleased) {
		this.yearReleased = yearReleased;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String lineSep = System.lineSeparator();

		sb.append( "Title: ").append( this.getName() ).append( lineSep );
		sb.append( "Artist: " ).append( this.getArtist() ).append( lineSep );
		sb.append( "Music label: " ).append( this.getLabel() ).append( lineSep );
		sb.append( "Music genre: " ).append( this.getGenre() ).append( lineSep );
		sb.append( "Song list: " );
		for( int i = 0; i < this.songs.size(); i++ )
			sb.append( this.songs.get(i) ).append( ", " );
		sb.append( lineSep );
		sb.append( "Released in: " ).append( this.getYearReleased() ).append( lineSep );

		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean saveToFile(BufferedWriter fileOut) {
		String lineSep = System.lineSeparator();

		try {
			fileOut.write( "[album]" );
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
			fileOut.write( this.getArtist() );
			fileOut.write( lineSep );
			fileOut.write( this.getLabel() );
			fileOut.write( lineSep );
			fileOut.write( this.getYearReleased() );
			fileOut.write( lineSep );
			for( String s : this.getSongs() )
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
		String artist = fileScan.nextLine();
		String label = fileScan.nextLine();
		int yearReleased = Integer.parseInt( fileScan.nextLine() );
		String songLine = fileScan.nextLine();

		//Parse tags
		ArrayList<String> tags = new ArrayList<String>();
		Scanner tagParser = new Scanner( tagLine );
		tagParser.useDelimiter( ";" );
		while( tagParser.hasNext() )
			tags.add( tagParser.next() );
		tagParser.close();

		//Parse song list
		ArrayList<String> songs = new ArrayList<String>();
		Scanner songLineParser = new Scanner( songLine );
		songLineParser.useDelimiter(";" );
		while( songLineParser.hasNext() )
			songs.add( songLineParser.next() );
		songLineParser.close();

		return new Album( name, genre, id, copies, tags, artist, label, songs, yearReleased );
	}
}
