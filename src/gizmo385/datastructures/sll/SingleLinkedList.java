package gizmo385.datastructures.sll;

import java.util.Iterator;

/**
 * A single linked list where each node only knows what is ahead of it. The linked list is kept in such a way that the last element in the list will always be a null node.
 * 
 * This linked list implements Iterable through the SingleLinkedListIterator class and each element within the list must implement the Comparable interface
 * @author cachapline8
 *
 * @param <E> The element kept within this linked list
 */
public class SingleLinkedList<E extends Comparable<E> > implements Iterable<E>
{
	private LinkNode<E> head;
	
	/** Returns the head of this list */
	public LinkNode<E> getHead()
	{
		return this.head;
	}
	
	/** Method implemented under Iterable - Returns a SingleLinkedListIterator starting at the list head*/
	public Iterator<E> iterator()
	{
		return new SingleLinkedListIterator<E>( this.head );
	}
	
	/** Creates the null node that caps the list */
	public SingleLinkedList()
	{
		this.head = new LinkNode<E>();
	}

    public void addAll( SingleLinkedList<E> listToAddFrom )
    {
        for( E element : listToAddFrom )
        {
            this.insertInOrder( element );
        }
    }
	
	/**
	 * Walks the list and attempts to locate the element specified
	 * @param elementToFind The element that is being searched for
	 * @return Return a reference to the element if it was found, null if not
	 */
	public E find( E elementToFind )
	{
		for( E element : this )
        {
			if( element.equals( elementToFind ) )
            {
				return element;
            }
        }
		
		return null;
	}
	
	/**
	 * Adds the element in it's natural position using the compareTo() method specified in the Comparable interface
	 * @param elementToAdd The element being added into its natural position
	 */
	public void insertInOrder( E elementToAdd )
	{
		LinkNode<E> current = this.head;
		
		while( current.getData() != null && current.getNext() != null && current.getData().compareTo( elementToAdd ) < 0 )
        {
			current = current.getNext();
        }
		
		LinkNode<E> temp = new LinkNode<E>( current.getData(), current.getNext() );
		current.setData( elementToAdd );
		current.setNext( temp );
	}
	/**
	 * Adds an element to the end of the list (behind the null node)
	 * @param elementToAdd The element being added to the end of this
	 */
	public void add( E elementToAdd )
	{
		LinkNode<E> current = this.head;
		
		while( current.getData() != null && current.getNext() != null )
        {
			current = current.getNext();
        }
		
		current.setData( elementToAdd );
		current.setNext( new LinkNode<E>() );
	}
	
	/**
	 * Attempts to remove an element from the list
	 * @param elementToRemove The element to be removed
	 * @return If the element is not within the list, return false. Otherwise, remove and then return true
	 */
	public boolean remove( E elementToRemove )
	{
        if( find( elementToRemove) == null )
            return false;
        else
        {
            LinkNode<E> current = this.head;

            while( current.getNext() != null && !current.getData().equals( elementToRemove ) )
                current = current.getNext();

            current.setData( current.getNext().getData() );
            current.setNext( current.getNext().getNext() );

            return true;
        }
	}// end remove
}
