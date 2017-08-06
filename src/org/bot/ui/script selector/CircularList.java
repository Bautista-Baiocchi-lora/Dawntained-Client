import javax.swing.*;
import java.util.ArrayList;

public class CircularList <T>
{
	private int windowSize;
	private CircularListNode<T> windowRight;
	private CircularListNode<T> windowLeft;
	
	public CircularList(ArrayList<T> array, int windowSize)
	{
		this.windowSize=windowSize;
		makeList(array);
	}
	
	private void makeList(ArrayList<T> array)
	{
		int size=array.size();
		if(size==1)
		{
			makeSingularList(array.get(0));
		}
		else
		{
			CircularListNode<T> startNode=new CircularListNode<T>(array.get(0));
			CircularListNode<T> lastNode=startNode;
			for(int i=1;i<size;++i)
			{
				CircularListNode<T> node=new CircularListNode<T>(array.get(i));
				node.setLeft(lastNode);
				lastNode.setRight(node);

				if(i==size-1)
				{
					node.setRight(startNode);
					startNode.setLeft(node);
				}
				else
				{
					lastNode=node;
				}
			}

			windowLeft=startNode;

			windowRight=windowLeft;
			for(int i=0;i<windowSize-1;++i)
			{
				windowRight=windowRight.right();
			}
		}
	}

	private void makeSingularList(T data)
	{
		CircularListNode<T> node=new CircularListNode<T>(data);
		node.setLeft(node);
		node.setRight(node);
		windowLeft=node;
		windowRight=node;
	}
	
	public ArrayList<T> getWindow()
	{
		ArrayList<T> window=new ArrayList<T>(windowSize);
		
		window.add(windowLeft.item());
		CircularListNode<T> node=windowLeft.right();
		while(node!=windowRight)
		{
			window.add(node.item());
			node=node.right();
		}
		window.add(windowRight.item());
		
		return window;
	}
	
	public void spinRight(int distance)
	{
		for(int i=0;i<distance;++i)
		{
			windowLeft=windowLeft.right();
			windowRight=windowRight.right();
		}
	}
	
	public void spinLeft(int distance)
	{
		for(int i=0;i<distance;++i)
		{
			windowLeft=windowLeft.left();
			windowRight=windowRight.left();
		}
	}

	public void addRight(T data)
	{
		CircularListNode<T> node=new CircularListNode<T>(data);
		CircularListNode<T> rightNode=windowRight.right();

		node.setRight(rightNode);
		rightNode.setLeft(node);
		node.setLeft(windowRight);
		windowRight.setRight(node);
	}

	public void addLeft(T data)
	{
		CircularListNode<T> node=new CircularListNode<T>(data);
		CircularListNode<T> leftNode=windowLeft.left();

		node.setLeft(leftNode);
		leftNode.setRight(node);
		node.setRight(windowLeft);
		windowLeft.setLeft(node);
	}
}
