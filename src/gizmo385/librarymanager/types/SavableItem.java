package gizmo385.librarymanager.types;

/**
 * Interface that is will be implemented by all Item subtypes and makes saving Items easier by providing a method from where their save data can be read
 * @author cachapline8
 *
 */
public interface SavableItem
{
	/** toString() method specifically designed for saving into a file */
	public String toSaveString();
	
	/** Generic toString() method for printing into console/text area */
	public String toString();
}
