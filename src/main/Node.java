package main;

public class Node{
	int x,y;
	double g,h,f;
	Node parent;
	public Node(int x, int y, double g, double h, Node parent) {
		this.x=x;
		this.y=y;
		this.g=g;
		this.h=h;
		this.f=g+h;
		this.parent=parent;
	}
}