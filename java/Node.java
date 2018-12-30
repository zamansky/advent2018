public class Node{
    public int data;
    public Node next,prev;
    public Node(int d){
	data = d;next=null;prev=null;
    }
    public void setNext(Node n){next = n;}
    public void setPrev(Node n){prev = n;}
    public int getData(){return data;}
    public Node getNext(){return next;}
    public Node getPrev(){return prev;}
}
