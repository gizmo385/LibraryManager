package gizmo385.datastructures.sll;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of the Iterator interface designed to traverse a SingleLinkedList instance
 * @author cachapline8
 *
 * @param <E> The element iterated over by this Iterator
 */
public class SingleLinkedListIterator<E extends Comparable<E> > implements Iterator<E>
{

	private LinkNode<E> nextElement;
	
	/** Initializes the iterator and sets the first element */
	public SingleLinkedListIterator( LinkNode<E> listHead )
	{
		this.nextElement = listHead;
	}
	
	/** Determines if the Iterator has reached the end of the list */
	public boolean hasNext()
	{
		return nextElement != null && nextElement.getNext() != null && nextElement.getData() != null;
	}

	/** Retrieves the next element within the list */
	public E next()
	{
		if( !hasNext() )
			throw new NoSuchElementException();
		
		E dataToReturn = nextElement.getData();
		nextElement = nextElement.getNext();
		
		return dataToReturn;

	}

	/** Not implemented in this Iterator */
	public void remove()
	{
		throw new UnsupportedOperationException();
		
	}

}
