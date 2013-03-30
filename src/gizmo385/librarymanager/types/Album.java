package gizmo385.librarymanager.types;

import gizmo385.datastructures.sll.SingleLinkedList;

import java.util.Arrays;

/**
 * Represents a music album
 * @author cachapline8
 *
 */
public class Album extends Item implements SavableItem
{
	private String artist, label;
	private String[] songList;
	private int yearReleased;

	/**
	 * 
	 * @param name Passed to super constructor
	 * @param copies Passed to super constructor
	 * @param tags Passed to super constructors
	 * @param artist The artist or band name
	 * @param genre The musical genre (i.e: Rock, jazz, pop, rap, etc.)
	 * @param label The music label that this was released under
	 * @param songList The songs present on the album
	 * @param yearReleased The year this album was released
	 */
	public Album( String name, int copies, SingleLinkedList<String> tags, String artist, String genre, String label, String[] songList, int yearReleased, String barcodeNumber )
	{
		super( name, copies, tags, genre, barcodeNumber );
		this.setArtist( artist );
		this.setLabel( label );
		this.setSongList( songList );
		this.setYearReleased( yearReleased );
		
		this.addTag( artist );
		this.addTag( label );
		for( String s : songList )
			this.addTag( s );
	}

	/** Returns the artist */
	public String getArtist() { return this.artist; }

	/** Returns the label */
	public String getLabel() { return this.label; }

	/** Returns the year released */
	public int getYearReleased() { return this.yearReleased; }

	/** Returns the number of songs */
	public int getNumberOfSongs() { return this.songList.length; }

	/** Returns a copy of the song list */
	public String[] getSongList() { return Arrays.copyOf( this.songList, this.songList.length ); }

	/** Sets the artist */
	public void setArtist( String artist ) { this.artist = artist; }

	/** Sets the label */
	public void setLabel( String label ) { this.label = label; }

	/** Sets the year released */
	public void setYearReleased( int yearReleased ) { this.yearReleased = yearReleased; }

	/** Copies the passed array */
	public void setSongList( String[] songList ) { this.songList = Arrays.copyOf( songList, songList.length ); }

	/** Returns a string representation of this album */
	public String toString()
	{
		StringBuilder temp = new StringBuilder();

		temp.append( super.toString() );
		temp.append( "Artist: " + this.getArtist() + "\nSong List: " );
		//Add songList
		if( this.songList.length == 1 )
			temp.append( this.songList[0] + "\n" );
		else if( this.songList.length == 2 )
			temp.append( this.songList[0] + " and " + this.songList[1] + "\n" );
		else
		{
			for( int i = 0; i < this.songList.length - 1; i++ )
				temp.append( this.songList[i] + ", " );

			temp.append( " and " + this.songList[ this.songList.length - 1 ] + "\n" );
		}

		temp.append( "Genre: " + this.getGenre() + "\nLabel: " + this.getLabel() + 
				"\nYear Released: " + this.getYearReleased() + "\nBarcode Number: " + super.getId() );


		return temp.toString();
	}
	
	public String toSaveString()
	{
		StringBuilder sb = new StringBuilder( "[movie]" + System.lineSeparator() );
		sb.append( this.getName() + System.lineSeparator() );
		sb.append( this.getCopies() + System.lineSeparator() );
		sb.append( this.getGenre() + System.lineSeparator() );
		
		for( String s : this.getTags() )
			sb.append( s.trim() + ";" );
		
		sb.append( System.lineSeparator() );
		
		for( String s : this.songList )
			sb.append( s.trim() + "," );
		
		sb.append( System.lineSeparator()+ this.getArtist() + System.lineSeparator() );
		sb.append( this.getLabel() + System.lineSeparator() );
		sb.append( this.getId() + System.lineSeparator() );
		sb.append( this.getYearReleased() + System.lineSeparator() );
		
		return sb.toString();
	}
}
