package gizmo385.librarymanager.types;

import gizmo385.datastructures.sll.SingleLinkedList;

/** 
 * Represents a video game
 * @author cachapline8
 */
public class VideoGame extends Item implements SavableItem
{
	private String developer, publisher, console, contentRating;
	/**
	 * @param name Passed to super constructor
	 * @param copies Passed to super constructor
	 * @param tags Passed to super constructor
	 * @param developer The game's developer
	 * @param publisher The game's publisher
	 * @param genre The game's genre (i.e: fps, rpg, mmo, mmorpg, etc.)
	 * @param console The game's console (i.e: Xbox 360, Wii U, PC, etc.)
	 * @param contentRating The content rating as given by a content rating agency (PEGI, ESRB, etc)
	 * @param barcodeNumber The number on the barcode for the game or it's individual ID number. Unique for this game.
	 */
	public VideoGame(String name, int copies, SingleLinkedList<String> tags, String developer, String publisher, String genre, String console, String contentRating, String barcodeNumber)
	{
		super( name, copies, tags, genre, barcodeNumber);
		this.setDeveloper( developer );
		this.setPublisher( publisher );
		this.setConsole( console );
		this.setContentRating( contentRating );
		
		this.addTag( developer );
		this.addTag( publisher );
		this.addTag( console );
		this.addTag( contentRating );
	}

	/** Returns the developer */
	public String getDeveloper() { return this.developer; }
	
	/** Returns the publisher */
	public String getPublisher() { return this.publisher;}
	
	/** Returns the console */
	public String getConsole() { return this.console; }
	
	/** Returns the content rating */
	public String getContentRating() { return this.contentRating; }
	
	/** Sets the developer */
	public void setDeveloper(String developer) { this.developer = developer; }
	
	/** Sets the publisher */
	public void setPublisher(String publisher) { this.publisher = publisher; }
	
	/** Sets the console */
	public void setConsole(String console) { this.console = console; }
	
	/** Sets the content rating */
	public void setContentRating(String contentRating) { this.contentRating = contentRating; }

	/** Returns a string representation of this VideoGame */
	public String toString()
	{
		return super.toString() + "Genre: " + this.getGenre() + "\nConsole: " + this.getConsole() + 
				"\nDeveloper: " + this.getDeveloper() + "\nPublisher: " + this.getPublisher() + "\nConsole Rating: " + 
				this.getContentRating() + "\nBarcode Number: " + super.getId();
	}
	
	public String toSaveString()
	{
		StringBuilder sb = new StringBuilder( "[videogame]" + System.lineSeparator() );
		sb.append( this.getName() + System.lineSeparator() );
		sb.append( this.getCopies() + System.lineSeparator() );
		sb.append( this.getGenre() + System.lineSeparator() );
		
		for( String s : this.getTags() )
			sb.append( s.trim() + ";" );
		
		sb.append( System.lineSeparator() + this.getDeveloper() + System.lineSeparator() );
		sb.append( this.getPublisher() + System.lineSeparator() );
		sb.append( this.getConsole() + System.lineSeparator() );
		sb.append( this.getContentRating() + System.lineSeparator() );
		sb.append( this.getId() + System.lineSeparator() );
		
		return sb.toString();
	}
} // end VideoGame subclass
