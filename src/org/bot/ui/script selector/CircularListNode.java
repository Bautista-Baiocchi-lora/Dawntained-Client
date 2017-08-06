
public class CircularListNode<T>
{
	private T item;
	private CircularListNode<T> right;
	private CircularListNode<T> left;
	
	public CircularListNode(T item)
	{
		this.item=item;
	}
	
	public void setRight(CircularListNode<T> right)
	{
		this.right=right;
	}
	
	public void setLeft(CircularListNode<T> left)
	{
		this.left=left;
	}
	
	public T item()
	{
		return item;
	}
	
	public CircularListNode<T> right()
	{
		return right;
	}
	
	public CircularListNode<T> left()
	{
		return left;
	}
}

