package dfs;

import java.util.ArrayList;

public class DFSNode {
	private int name;
	private int direc;
	
	private DFSNode prevNode;
	private ArrayList<DFSNode> nextNodes;
	
	public DFSNode(int name, int direc, DFSNode prevNode) {
		this.name = name;
		this.direc = direc;
		nextNodes = new ArrayList<DFSNode>();
		this.prevNode = prevNode;
	}
	
	public int getName() {
		return this.name;
	}
	
	public int getDirec() {
		return this.direc;
	}
	
	public void removeNext() {
		if(hasNext()) {
			nextNodes.remove(0);
		}
	}
	
	public boolean hasNext() {
		return !nextNodes.isEmpty();
	}
	
	public boolean hasPrev() {
		return this.prevNode!=null;
	}
	
	public void addNext(DFSNode node) {
		nextNodes.add(node);
		node.prevNode = this;
	}
	
	public DFSNode getPrev() {
		return this.prevNode;
	}
	
	public DFSNode getNext() {
		if(hasNext()) {
			return nextNodes.get(0);
		}
		return this;
	}
	
	public ArrayList<DFSNode> getNextList(){
		return nextNodes;
	}
}
