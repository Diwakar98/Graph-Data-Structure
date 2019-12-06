

public class Triangle implements TriangleInterface {
	
	private Point[] tri_points=new Point[3];
	private boolean marker;
	private LinkedList<Triangle> adjacency_list;
	private int distance;
	 
	Triangle(Point a, Point b, Point c) {
		tri_points[0]=a;tri_points[1]=b;tri_points[2]=c;
		setAdjacency_list(new LinkedList<Triangle> ());
		this.setMarker(false);
		this.setDistance(Integer.MAX_VALUE);
	}
	@Override
	public PointInterface[] triangle_coord() {
		// TODO Auto-generated method stub
		return tri_points;
	}
	
	public boolean equals(Triangle t) {
		Point a=tri_points[0];
		Point b=tri_points[1];
		Point c=tri_points[2];
		Point l=(Point) t.triangle_coord()[0];
		Point m=(Point) t.triangle_coord()[1];
		Point n=(Point) t.triangle_coord()[2];
		int state=0;
		if (a.equals(l) || a.equals(m) || a.equals(n)) {
			state++;
		}
		if (b.equals(l) || b.equals(m) || b.equals(n)) {
			state++;
		}
		if (c.equals(l) || c.equals(m) || c.equals(n)) {
			state++;
		}
		if (state==3) {
			return true;
		}
		return false;
	}
	
	public boolean is_edge_neighbour(Triangle t) {
		Point a=tri_points[0];
		Point b=tri_points[1];
		Point c=tri_points[2];
		Point l=(Point) t.triangle_coord()[0];
		Point m=(Point) t.triangle_coord()[1];
		Point n=(Point) t.triangle_coord()[2];
		int state=0;
		if (a.equals(l) || a.equals(m) || a.equals(n)) {
			state++;
		}
		if (b.equals(l) || b.equals(m) || b.equals(n)) {
			state++;
		}
		if (c.equals(l) || c.equals(m) || c.equals(n)) {
			state++;
		}
		if (state==2) {
			return true;
		}
		return false;
	}
	public LinkedList<Triangle> getAdjacency_list() {
		return adjacency_list;
	}
	public void setAdjacency_list(LinkedList<Triangle> adjacency_list) {
		this.adjacency_list = adjacency_list;
	}
	public void add_neighbour(Triangle value) {
		this.adjacency_list.add(value);
	}
	public String toString() {
		return tri_points[0].toString()+";"+tri_points[1].toString()+";"+tri_points[2].toString();
	}
	public boolean isMarker() {
		return marker;
	}
	public void setMarker(boolean marker) {
		this.marker = marker;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	// shortest distance between two triangle nodes in same component.
	public int shortest_path(Triangle value,LinkedList<Triangle> comp_list) {
		if(this.equals(value)) {
			return 0;
		}
		else if(this.is_edge_neighbour(value)) {
			return 1;
		}
		else {
			int i= path(this,value,comp_list);
			return i;
		}
	}
	

	
	public int path(Triangle source, Triangle destination, LinkedList<Triangle> comp_list) {
		LinkedList<Triangle> queue=new LinkedList<Triangle>();
		queue.add(source);
		source.setDistance(0);
		Node<Triangle> iter=queue.getHead();
		while(iter!=null) {	
			Node<Triangle> tempnode=iter.getValue().getAdjacency_list().getHead();
			// add all elements of adjacency list in queue.
			for(int i=0; i<iter.getValue().getAdjacency_list().getSize(); i++) {
				boolean b=queue.add(tempnode.getValue());
				if (b) {
					tempnode.getValue().setDistance(iter.getValue().getDistance()+1);
				}
				if (tempnode.getValue().equals(destination)) {
					return tempnode.getValue().getDistance();
				}
				tempnode=tempnode.getNext();
			}
			iter=iter.getNext();// we will operate on next element of queue in next operation.
		}
		// calculate centroid of component and return it.
		return 0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	@Override
	public int compareTo(Object obj) {
		if (this.getDistance()==((Triangle) obj).getDistance()) {
			return 0;
		}
		else if (this.getDistance()<((Triangle) obj).getDistance()) {
			return -1;
		}
		else {
			return 1;
		}
	}*/
	
	
	/*	public int dijkstra(Triangle source, Triangle destination, LinkedList<Triangle> comp_list) {
	source.setDistance(0);
	MinHeap<Triangle> priority_queue=new MinHeap<Triangle>(comp_list.getSize());
	Node<Triangle> head=comp_list.getHead();
	while(head!=null) {
		priority_queue.insert(head.getValue());
		head=head.getNext();
	}
	while(priority_queue.getSize()>0) {
		Triangle temp=priority_queue.extractMin();
		if (temp.equals(destination)) {
			// we are done.
			return temp.getDistance();
		}
		Node<Triangle> itr=temp.getAdjacency_list().getHead();
		while(itr!=null) {
			if (itr.getValue().getDistance()>temp.getDistance()+1) {
				itr.getValue().setDistance(temp.getDistance()+1);
				priority_queue.insert(temp);
			}
			itr=itr.getNext();
		}
	}
	ADD_TRIANGLE 1 2 2 3 1 0 8 1 5
ADD_TRIANGLE 1 3 2 3 1 0 8 1 5
	return 0;
	



}*/
}
