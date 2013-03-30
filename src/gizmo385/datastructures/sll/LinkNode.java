package gizmo385.datastructures.sll;

/**
 * A LinkNode, the building block of a linked list
 * @author cachapline8
 *
 * @param <E> The type of data in this Node
 */
public class LinkNode<E extends Comparable<E> >
{
	private E data;
	private LinkNode<E> next;
	
	/** Creates a null node */
	public LinkNode()
	{
		this.data = null;
		this.next = null;
	}
	
	/** Creates a node with data and a null reference */
	public LinkNode( E data )
	{
		this.data = data;
		this.next = null;
	}
	
	/** Creates a node with a reference and null data */
	public LinkNode( LinkNode<E> next )
	{
		this.next = next;
		this.data = null;
	}
	
	/** Creates a node with data and a reference */
	public LinkNode( E data, LinkNode<E> next )
	{
		this.next = next;
		this.data = data;
	}
	
	/** Return the next field of this LinkNode instance */
	public LinkNode<E> getNext()
	{
		return this.next;
	}
	
	/** Return the data field of this LinkNode instance */
	public E getData()
	{
		return this.data;
	}
	
	/** Change the next field of this LinkNode instance */
	public void setNext( LinkNode<E> next )
	{
		this.next = next;
	}
	
	/** Change the data field of this LinkNode instance */
	public void setData( E data )
	{
		this.data = data;
	}
}
