package gizmo385.librarymanager.types;

import gizmo385.datastructures.sll.SingleLinkedList;

/**
 * Represents a book item
 * @author cachapline8
 *
 */
public class Book extends Item implements SavableItem
{
	private int yearPublished;
	private String author, publisher;
	/**
	 * Creates a book object and passes name, copies, and tags to the super constructor (Item)
	 * @param name Passed to super constructor
	 * @param copies Passed to super constructor
	 * @param tags Passed to super constructor
	 * @param genre Passed to super constructor
	 * @param isbn A book's ISBN (International Standard Book Number)
	 * @param yearPublished The year the book was published
	 * @param author The book's author
	 * @param publisher The book's publisher
	 */
	public Book( String name, int copies, SingleLinkedList<String> tags , String isbn, int yearPublished, String publisher, String author, String genre )
	{
		super( name, copies, tags, genre, isbn ); //call to super constructor (Item)
		this.setAuthor( author );
		this.setPublisher( publisher );
		this.setYearPublished( yearPublished );
		
		this.addTag( author );
		this.addTag( publisher );
	}
	
	/** Returns the year published */
	public int getYearPublished() { return this.yearPublished;}
	
	/** Returns the author */
	public String getAuthor() { return this.author; }
	
	/** Returns the publisher */
	public String getPublisher() { return this.publisher; }
	
	/** Sets the year published */
	public void setYearPublished( int yearPublished ) { this.yearPublished = yearPublished; }
	
	/** Sets the author */
	public void setAuthor( String author ) { this.author = author; }
	
	/** Sets the publisher */
	public void setPublisher( String publisher ) { this.publisher = publisher; }

	/** Returns a string representation of this item */
	public String toString()
	{
		return super.toString() + "Author: " + this.getAuthor() + "\nGenre: " + 
				this.getGenre() + "\nPublisher: " + this.getPublisher() + "\nYear Published: " 
				+ this.getYearPublished() + "\nISBN: " + super.getId();
	}
	
	public String toSaveString()
	{
		StringBuilder sb = new StringBuilder( "[book]" + System.lineSeparator() );
		sb.append( this.getName() + System.lineSeparator() );
		sb.append( this.getCopies() + System.lineSeparator() );
		sb.append( this.getGenre() + System.lineSeparator() );
		
		for( String s : this.getTags() )
			sb.append( s.trim() + ";" );
		
		sb.append( System.lineSeparator() + this.getAuthor() + System.lineSeparator() );
		sb.append( this.getPublisher() + System.lineSeparator() );
		sb.append( this.getId() + System.lineSeparator() );
		sb.append( this.getYearPublished() + System.lineSeparator() );
		
		return sb.toString();
	}


} // end Book
