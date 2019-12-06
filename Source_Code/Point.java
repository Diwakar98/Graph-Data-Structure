
public class Point implements PointInterface {
	
	private float[] xyz=new float[3];
	
	Point(float a,float b, float c) {
		xyz[0]=a;xyz[1]=b;xyz[2]=c;
	}

	@Override
	public float getX() {
		// TODO Auto-generated method stub
		return xyz[0];
	}

	@Override
	public float getY() {
		// TODO Auto-generated method stub
		return xyz[1];
	}

	@Override
	public float getZ() {
		// TODO Auto-generated method stub
		return xyz[2];
	}

	@Override
	public float[] getXYZcoordinate() {
		// TODO Auto-generated method stub
		return xyz;
	}
	
	public boolean equals(Point r) {
		if (this.getX()==r.getX() && this.getY()==r.getY() && this.getZ()==r.getZ()) {
			return true;
		}
		else
			return false;
	}
	
	public String toString() {
		return "("+xyz[0]+","+xyz[1]+","+xyz[2]+")";
	}
	
	public int compare(Point p) {
		if (this.getX()<p.getX()) {
			return -1;
		}
		else if (this.getX()>p.getX()) {
			return 1;
		}
		else {// this.getX()==p.getX();
			if (this.getY()<p.getY()) {
				return -2;
			}
			else if (this.getY()>p.getY()) {
				return 2;
			}
			else {// this.getY()==p.getY();
				if (this.getZ()<p.getZ()) {
					return -3;
				}
				else if (this.getZ()>p.getZ()) {
					return 3;
				}
				else {
					return 0;
				}
			}
		}
	}
}
