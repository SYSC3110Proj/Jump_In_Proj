package tree;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	
	private List<Node<T>> children = new ArrayList<Node<T>>();
	private Node<T> parent = null;
	private T data = null;
	
	/**
	 * Constructor for a new node
	 * @param data The data to be stored in this node
	 */
	public Node(T data) {
		this.children = new ArrayList<Node<T>>();
		this.data = data;
	}
	
	/**
	 * Constructor for a new node
	 * @param data The data to be stored in this node
	 * @param parent The parent node for this node
	 */
	public Node(T data, Node<T> parent) {
		this.children = new ArrayList<Node<T>>();
		this.data = data;
		this.parent = parent;
	}
	
	/**
	 * Set the parent for this node
	 * @param parent The parent node for this node
	 */
	public void setParent(Node<T> parent) {
		parent.addChild(this);
		this.parent = parent;
	}
	
	/**
	 * Add a child node to this node
	 * @param data the data to be stored in the child node
	 */
	public void addChild(T data) {
		Node<T> child = new Node<T>(data);
		child.setParent(this);
		this.children.add(child);
	}
	
	/**
	 * Add a child node to this node
	 * @param child the already existing child node to be added
	 */
	public void addChild(Node<T> child) {
		child.setParent(this);
		this.children.add(child);
	}
	
	/**
	 * Get the data in this node
	 * @return the data in this node
	 */
	public T getData() {
		return this.data;
	}
	
	/**
	 * Set the data in this node 
	 * @param data the data to be set
	 */
	public void setData(T data) {
		this.data = data;
	}
	
	/**
	 * Check if this node is the root
	 * @return True if the node is the root, false otherwise
	 */
	public boolean isRoot() {
		if (this.parent == null) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Check if this node is a leaf
	 * @return True if this node is a leaf, false otherwise
	 */
	public boolean isLeaf() {
		if (this.children.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Remove the parent from this node
	 */
	public void removeParent() {
		this.parent = null;
	}

}
