

public class Edge implements EdgeInterface{
	
	private Point[] edge_end=new Point[2];
	private float length;
	
	Edge(Point p, Point q) {
		edge_end[0]=p;edge_end[1]=q;
		setLength(calculate_distance(p,q));
	}
	private float calculate_distance(Point p, Point q) {
		// TODO Auto-generated method stub
		float x1=p.getX();
		float y1=p.getY();
		float z1=p.getZ();
		float x2=q.getX();
		float y2=q.getY();
		float z2=q.getZ();
		return (float) Math.sqrt((z2-z1)*(z2-z1)+(y2-y1)*(y2-y1)+(x2-x1)*(x2-x1));
	}
	@Override
	public PointInterface[] edgeEndPoints() {
		// TODO Auto-generated method stub
		return edge_end;
	}
	
	public boolean equals(Edge e) {
		Point a=this.edge_end[0];
		Point b=this.edge_end[1];
		Point l=e.edge_end[0];
		Point m=e.edge_end[1];
		if (l.equals(a) || l.equals(b)) {
			if (m.equals(a) || m.equals(b)) {
				return true;
			}
		}
		return false;
	}
	public float getLength() {
		return length;
	}
	public void setLength(float length) {
		this.length = length;
	}
	public String toString() {
		return edge_end[0].toString()+";"+edge_end[1].toString();
	}
}
