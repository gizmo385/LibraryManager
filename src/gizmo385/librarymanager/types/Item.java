package gizmo385.librarymanager.types;

import gizmo385.datastructures.sll.SingleLinkedList;

/**
 * Representation of an Item that implements the Comparable interface (comparing the Item titles)
 * @author cachapline8
 *
 */
public class Item implements Comparable<Item>
{
	private String name, genre, id;
	private int copies;
	private SingleLinkedList<String> tags = new SingleLinkedList<String>();

	/**
	 * @param name The Item's name and target of compareTo method
	 * @param copies Number of Items that are owned
	 * @param tags A single linked list of tags (things to search by)
	 */
	public Item( String name, int copies, SingleLinkedList<String> tags, String genre, String id )
	{
		this.setCopies( copies );
		this.setName( name );
		this.setGenre( genre );
		this.setId(id);
		this.setTags( tags );
	}

	/** Returns the name */
	public String getName() { return this.name; }
	
	/** Returns the genre */
	public String getGenre() { return this.genre; }
	
	/** Returns the ID (ISBN, Barcode, etc) */
	public String getId() { return this.id; }

	/** Returns the number of copies */
	public int getCopies() { return this.copies; }

	/** Returns the tags */
	public SingleLinkedList<String> getTags()
	{
		SingleLinkedList<String> tagsCopy = new SingleLinkedList<String>();
		
		for( String s : this.tags )
			tagsCopy.insertInOrder( s.toLowerCase() );
		
		return tagsCopy;
	}

	/** Sets the name */
	public void setName( String name ) { this.name = name; }
	
	/** Sets the genre */
	public void setGenre( String genre ) { this.genre = genre; }
	
	/** Sets the genre (ISBN, barcode, etc) */
	public void setId( String id ) { this.id = id; }

	/** Sets the tags */
	public void setTags( SingleLinkedList<String> tags )
	{
		for( String s : tags )
			this.tags.insertInOrder( s );
		
		//adds the title as a tag by default	
		this.tags.insertInOrder( this.name ); 
		this.tags.insertInOrder( this.genre );
		this.tags.insertInOrder( this.id );
	}

	/** Validates and sets copies */
	public void setCopies( int copies )
	{
		if( copies >= 0 )
			this.copies = copies;
		else
			this.copies = 0;
	}
	
	public void addTag( String tag )
	{
		this.tags.insertInOrder( tag );
	}

	/** Returns the String.compareTo() for the name */
	public int compareTo( Item other ) { return this.getName().compareTo( other.getName() ); }

	/** String representation of this Item */
	public String toString() { return "Name: " + this.getName() + "\nCopies: " + this.getCopies() + "\n"; }
} // end Item superclass