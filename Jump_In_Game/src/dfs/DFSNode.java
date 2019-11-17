package dfs;

import java.util.ArrayList;

public class DFSNode {
	private int name;
	private int direc;
	
	private DFSNode prevNode;
	private DFSNode nextNode;
	
	private ArrayList<Choice> choices;
	private int choiceNum;
	private int maxChoiceNum;
	
	
	public DFSNode(int name, int direc, DFSNode prevNode, ArrayList<Choice> choices) {
		this.name = name;
		this.direc = direc;
		this.choices = choices;
		this.prevNode = prevNode;
		this.choiceNum = 0;
		this.maxChoiceNum = choices.size();
	}
	
	public int getName() {
		return this.name;
	}
	
	public int getDirec() {
		return this.direc;
	}
	
	public void setNextNode() {
		if(hasOtherWayToGo()) {
			this.nextNode = new DFSNode(choices.get(choiceNum).getName(), choices.get(choiceNum).getDirection(), this, this.choices);
			this.choiceNum++;
		}
	}
	
	public boolean hasOtherWayToGo() {
		return choiceNum < maxChoiceNum;
	}
	
	public boolean hasPrev() {
		return this.prevNode!=null;
	}
	
	public boolean hasNext() {
		return this.nextNode != null;
	}
	
	public void setPrev(DFSNode node) {
		this.prevNode = node;
	}
	
	public DFSNode getPrev() {
		if(prevNode != null) {
			return this.prevNode;
		}
		return this;
	}
	
	public DFSNode getNext() {
		return nextNode;
	}
}
