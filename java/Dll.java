import java.util.*;
    


public class Dll{
    Node current;
    public Dll(){
	current = null;
	Node n = new Node(0);
	Node n2 = new Node(1);
	Node n3 = new Node(2);
	current = n3;
	n3.setNext(n2);n3.setPrev(n);
	n2.setNext(n);n2.setPrev(n3);
	n.setNext(n3);n.setPrev(n2);
    }

    public String toString(){
	String s = "";
	Node t = current.getNext();
	while (t!=current){
	    s=s+t.getData()+" ";
	    t=t.getNext();
	}
	s=s+t.getData();
	return s;
    }

    public void inc(){current = current.getNext();}
    public void dec(){current = current.getPrev();}
    public int getCurrent(){return current.getData();}

    public void insert(int d){
	Node n = new Node(d);
	n.setNext(current.getNext());
	n.setPrev(current);
	n.getNext().setPrev(n);
	n.getPrev().setNext(n);
	current = n;
    }
    public void delete(){
	Node tmp=current;
	current=current.getNext();
	tmp.getNext().setPrev(tmp.getPrev());
	tmp.getPrev().setNext(tmp.getNext());
        
    }

}
