

public class LinkedList<T> {
	
	Node<T> head;
	Node<T> tail;
	int size;
	
	LinkedList() {
		head=tail=null;
		size=0;
	}
	
	public Node<T> getHead() {
		return head;
	}
	
	public Node<T> getTail() {
		return tail;
	}
	
	public int getSize() {
		return size;
	}
	
	public boolean add(Triangle value) {
		Node<T> node=new Node<T>((T) value);
		if (head==null) {
			head=tail=node;
			size++;
			return true;
		}
		else {
			if (this.isPresent(value)==false) {
				tail.setNext(node);
				tail=tail.getNext();
				size++;
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public boolean add(Edge value) {
		Node<T> node=new Node<T>((T) value);
		if (head==null) {
			head=tail=node;
			size++;
			return true;
		}
		else {
			if (this.isPresent(value)==false) {
				tail.setNext(node);
				tail=tail.getNext();
				size++;
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public boolean add(Point value) {
		Node<T> node=new Node<T>((T) value);
		if (head==null) {
			head=tail=node;
			size++;
			return true;
		}
		else {
			if (this.isPresent(value)==false) {
				tail.setNext(node);
				tail=tail.getNext();
				size++;
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public boolean forced_add(T value) {
		Node<T> node=new Node<T>((T) value);
		if (head==null) {
			head=tail=node;
			size++;
			return true;
		}
		else {
				tail.setNext(node);
				tail=tail.getNext();
				size++;
				return true;
		}
	}
	
	public boolean isPresent(Edge value) {
		Node<T> curr=head;
		if (curr==null) {
			return false;
		}
		if (((Edge) curr.getValue()).equals(value)) {
			return true;
		}
		while(curr!=null) {
			if (((Edge) curr.getValue()).equals(value)) {
				return true;
			}
			curr=curr.getNext();
		}
		return false;
	}
	
	public boolean isPresent(Point value) {
		Node<T> curr=head;
		if (curr==null) {
			return false;
		}
		if (((Point) curr.getValue()).equals(value)) {
			return true;
		}
		while(curr!=null) {
			if (((Point) curr.getValue()).equals(value)) {
				return true;
			}
			curr=curr.getNext();
		}
		return false;
	}
	
	public boolean isPresent(Triangle value) {
		Node<T> curr=head;
		if (curr==null) {
			return false;
		}
		if (((Triangle) curr.getValue()).equals(value)) {
			return true;
		}
		while(curr!=null) {
			if (((Triangle) curr.getValue()).equals(value)) {
				return true;
			}
			curr=curr.getNext();
		}
		return false;
	}
}
