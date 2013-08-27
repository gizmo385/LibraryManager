package com.gizmo385.lm.types;

import java.io.BufferedWriter;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Abstract class representing an Item. Superclass to all other items
 * @author Christopher
 *
 */
public abstract class Item implements Comparable<Item>, Serializable {
	private static final long serialVersionUID = -2518470675811167937L;
	
	private final String ITEM_TYPE;
	private String name, genre, id;
	private int copies;
	private ArrayList<String> tags;

	/**
	 * Constructor for the abstract Item class
	 * @param itemType The type of item extending this abstract class (book, movie, etc)
	 * @param name The name of this item
	 * @param genre This genre this item falls under
	 * @param id This item's ID (isbn, barcode, etc)
	 * @param copies The number of copies of this item in inventory
	 * @param tags Word associated with this item used for searching
	 */
	public Item( String itemType, String name, String genre, String id, int copies, ArrayList<String> tags ) {
		this.ITEM_TYPE = itemType.toLowerCase();
		this.name = name;
		this.genre = genre;
		this.id = id;
		this.copies = copies > 0 ? copies : 0;

		//Instatiate the ArrayList
		this.tags = new ArrayList<String>();

		//Add passed ArrayList contents to the tags
		for( String s : tags )
			if( ! this.tags.contains( s.toLowerCase() ) )
				this.tags.add( s.toLowerCase() );

		//Add additional tag information if not already present
		if( ! this.tags.contains( this.name.toLowerCase() ) )
			this.tags.add( this.name.toLowerCase() );
		if( ! this.tags.contains( this.genre.toLowerCase() ) )
			this.tags.add( this.genre.toLowerCase() );
		if( ! this.tags.contains( this.id.toLowerCase() ) )
			this.tags.add( this.id.toLowerCase() );
		if( ! this.tags.contains( this.ITEM_TYPE.toLowerCase() ) )
			this.tags.add( this.ITEM_TYPE.toLowerCase() );
	}
	
	/**
	 * @return The name of the item
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @return The ITEM_TYPE associated with this item (Book, movie, etc)
	 */
	public String getItemType() {
		return this.ITEM_TYPE;
	}

	/**
	 * @return The genre of the item
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @return The ID string (barcode, isbn, id number, etc) of the item
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return The number of copies of this item in inventory
	 */
	public int getCopies() {
		return copies;
	}

	/**
	 * @return The "tags" - words associated with this item used for searching
	 */
	public ArrayList<String> getTags() {
		return new ArrayList<String>( this.tags );
	}

	/**
	 * @param name The name of this item
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param genre The genre of this item
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @param id The id of this item
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param copies The number of copies of this item in inventroy
	 */
	public void setCopies(int copies) {
		this.copies = copies;
	}

	/**
	 * @param tags The "tags" - words associated with this item used for searching
	 */
	public void setTags(ArrayList<String> tags) {
		//Re instatiate the ArrayList
		this.tags = new ArrayList<String>();

		//Add passed ArrayList contents to the tags
		for( String s : tags )
			if( ! this.tags.contains( s.toLowerCase() ) )
				this.tags.add( s.toLowerCase() );

		//Add additional tag information if not already present
		if( ! this.tags.contains( this.name.toLowerCase() ) )
			this.tags.add( this.name.toLowerCase() );
		if( ! this.tags.contains( this.genre.toLowerCase() ) )
			this.tags.add( this.genre.toLowerCase() );
		if( ! this.tags.contains( this.id.toLowerCase() ) )
			this.tags.add( this.id.toLowerCase() );
	}
	
	/**
	 * Adds a tag to this Item's tag list
	 * @param tag The tag to add
	 * @return If the tag was added or not
	 * 
	 * <p>Use {@link #tagPresent( String tag )} to add multiple tags in one method call </p>
	 */
	public boolean addTag( String tag ) {
		return this.tagPresent( tag ) ? this.tags.add( tag.toLowerCase() ) : false;
	}
	
	/**
	 * Adds an unspecified amount of tags to the Item's tag list
	 * @param tags A collection of Strings of unspecified size
	 * 
	 * <p>Use {@link #addTag( String tag )} to add individual tags </p>
	 */
	public void addTags( String... tags ) {
		for( int i = 0; i < tags.length; i++ )
			this.addTag( tags[i] );
		
	}
	
	/**
	 * Removes a tag from this Item's tag list
	 * @param tag The tag to be removed
	 * @return If the tag was removed or not
	 */
	public boolean removeTag( String tag ) {
		return this.tagPresent( tag ) ? this.tags.remove( tag.toLowerCase() ) : false;
	}
	
	/**
	 * Checks if a tag is present in this Item's tags
	 * @param tag The tag to check for
	 * @return Whether or not the tag was present
	 */
	public boolean tagPresent( String tag ) {
		return this.tags.contains( tag.toLowerCase() );
	}
	
	/**
	 * Compares the names of these two items
	 */
	public int compareTo( Item i ) {
		return this.name.compareTo( i.getName() );
	}
	
	/**
	 * The equals method for this Item
	 */
	public boolean equals( Object o ) {
		if( o instanceof Item ) {
			Item i = (Item)o;
			return this.name.equals( i.getName() ) && this.genre.equals( i.getGenre() ) && this.id.equals( i.getId() );
		}
		return false;
	}
	
	/**
	 * The string representation of this Item
	 */
	public abstract String toString();
	
	/**
	 * Saves this item to the file associated with fileOut
	 * @param fileOut The BufferedWriter that is writing to the save file
	 * @return The success/failure of the save operation for this Item
	 */
	public abstract boolean saveToFile( BufferedWriter fileOut );
}
