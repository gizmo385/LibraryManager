package gizmo385.librarymanager.types;

import gizmo385.datastructures.sll.SingleLinkedList;

import java.util.Arrays;

/**
 * Represents a movie
 * @author cachapline8
 *
 */
public class Movie extends Item implements SavableItem
{
	private String director, rating;
	private String[] starring;
	private int yearDirected;

	/**
	 * 
	 * @param name Passed to super constructor
	 * @param copies Passed to super constructor
	 * @param tags Passed to super constructor
	 * @param genre The movie's genre
	 * @param director The person(s) who directed this movie
	 * @param rating The movie's content rating (i.e: PG-13, R, NC-17, etc.)
	 * @param starring The list of people starring in this movie
	 * @param yearDirected The year the movie was directed
	 * @param barcodeNumber The barcode on the back of the case or the movie's individual ID number
	 */
	public Movie( String name, int copies, SingleLinkedList<String> tags, String genre, String director, String rating, String[] starring, int yearDirected, String barcodeNumber )
	{
		super( name, copies, tags, genre, barcodeNumber );
		this.setDirector( director );
		this.setRating( rating );
		this.setStarring( starring );
		this.setYearDirected( yearDirected );
		
		this.addTag( director );
		this.addTag( rating );
		for( String s : starring )
			this.addTag( s );
		this.addTag( yearDirected + "" );
	}

	/**
	 * @return the director
	 */
	public String getDirector()
	{
		return director;
	}
	/**
	 * @return the rating
	 */
	public String getRating()
	{
		return rating;
	}
	/**
	 * @return A copy of starring
	 */
	public String[] getStarring()
	{
		return Arrays.copyOf( this.starring, this.starring.length );
	}
	/**
	 * @return the yearDirected
	 */
	public int getYearDirected()
	{
		return yearDirected;
	}
	/**
	 * @param director the director to set
	 */
	public void setDirector(String director)
	{
		this.director = director;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(String rating)
	{
		this.rating = rating;
	}
	/**
	 * @param starring the starring to set
	 */
	public void setStarring(String[] starring)
	{
		this.starring = Arrays.copyOf( starring, starring.length );
	}
	/**
	 * @param yearDirected the yearDirected to set
	 */
	public void setYearDirected(int yearDirected)
	{
		this.yearDirected = yearDirected;
	}

	/**
	 * Returns a string representation of this Movie
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append( super.toString() );
		sb.append( "Starring: " );

		//Add starring
		if( this.starring.length == 1 )
        {
			sb.append( this.starring[0] );
            sb.append( System.lineSeparator() );
        }
		else if( this.starring.length == 2 )
        {
            sb.append( this.starring[0] );
            sb.append( " and " );
            sb.append( this.starring[1] );
            sb.append( System.lineSeparator() );
        }
		else
		{
			for( int i = 0; i < this.starring.length - 1; i++ )
            {
				sb.append( this.starring[i] );
                sb.append( ", ");
            }
            sb.append( " and " );
            sb.append( this.starring[ this.starring.length - 1 ] );
            sb.append( System.lineSeparator() );
		}

        sb.append( "Genre: " );
        sb.append( this.getGenre() );
        sb.append( System.lineSeparator() );
        sb.append( "Director: " );
        sb.append( this.getDirector() );
        sb.append( System.lineSeparator() );
        sb.append( "Rating: " );
        sb.append( this.getRating() );
        sb.append( System.lineSeparator() );
        sb.append( "Directed in: " );
        sb.append( this.getYearDirected() );
        sb.append( System.lineSeparator() );
        sb.append( "Barcode Number: " );
        sb.append( super.getId() );

		return sb.toString();
	}

    /** String that gets saved to the library file */
	public String toSaveString()
	{
		StringBuilder sb = new StringBuilder( "[movie]" );
        sb.append( System.lineSeparator() );
		sb.append( this.getName() );
        sb.append( System.lineSeparator() );
		sb.append( this.getCopies() );
        sb.append( System.lineSeparator() );
		sb.append( this.getGenre() );
        sb.append( System.lineSeparator() );
		
		for( String s : this.getTags() )
        {
			sb.append( s.trim() );
            sb.append( ";" );
        }
		
		sb.append( System.lineSeparator() );
		
		for( String s : this.starring )
        {
			sb.append( s.trim() );
            sb.append( "," );
        }

        sb.append( System.lineSeparator() );
		sb.append( this.getDirector() );
        sb.append( System.lineSeparator() );
		sb.append( this.getRating() );
        sb.append( System.lineSeparator() );
		sb.append( this.getId() );
        sb.append( System.lineSeparator() );
		sb.append( this.getYearDirected() );
        sb.append( System.lineSeparator() );
		
		return sb.toString();
	}
} // end Movie subclass