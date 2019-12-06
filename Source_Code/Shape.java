

public class Shape implements ShapeInterface {
	
	LinkedList<Point> all_Points=new LinkedList<Point>();
	LinkedList<Edge> all_Edges=new LinkedList<Edge>();
	LinkedList<Triangle> all_Triangles=new LinkedList<Triangle>();
	
	public boolean ADD_TRIANGLE(float [] triangle_coord) {
		float[] side1=new float[3];
		float[] side2=new float[3];
		side1[0]=triangle_coord[0]-triangle_coord[3];
		side1[1]=triangle_coord[1]-triangle_coord[4];
		side1[2]=triangle_coord[2]-triangle_coord[5];
		side2[0]=triangle_coord[0]-triangle_coord[6];
		side2[1]=triangle_coord[1]-triangle_coord[7];
		side2[2]=triangle_coord[2]-triangle_coord[8];
		float[] side3= {triangle_coord[3]-triangle_coord[6], triangle_coord[4]-triangle_coord[7], triangle_coord[5]-triangle_coord[8]};
		float dot12=side1[0]*side2[0]+side1[1]*side2[1]+side1[2]*side2[2];
		float dot23=side2[0]*side3[0]+side2[1]*side3[1]+side2[2]*side3[2];
		float dot31=side3[0]*side1[0]+side3[1]*side1[1]+side3[2]*side1[2];
		float l1=(float) Math.sqrt(side1[0]*side1[0]+side1[1]*side1[1]+side1[2]*side1[2]);
		float l2=(float) Math.sqrt(side2[0]*side2[0]+side2[1]*side2[1]+side2[2]*side2[2]);
		float l3=(float) Math.sqrt(side3[0]*side3[0]+side3[1]*side3[1]+side3[2]*side3[2]);
		float tan12=Math.abs((float) Math.tan(Math.acos(dot12/(l1*l2))));
		float tan23=Math.abs((float) Math.tan(Math.acos(dot23/(l2*l3))));
		float tan31=Math.abs((float) Math.tan(Math.acos(dot31/(l3*l1))));	
		if (tan12 < 0.001 || tan23 < 0.001 || tan31 < 0.001) {
			return false; // collinear points.
		}
		if (l1+l2==l3 || 12+l3==l1 || l3+l1==l2) {
			return false; // collinear points.
		}
		Point a=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point b=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point c=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Triangle t =new Triangle(a,b,c);
		Edge p=new Edge(a,b);
		Edge q=new Edge(b,c);
		Edge r=new Edge(c,a);
		boolean bool=all_Triangles.add(t);
		if (bool) {
			Node<Triangle> curr=all_Triangles.getHead();
			while(curr!=null) {
				if (curr.getValue().is_edge_neighbour(t)) {
					curr.getValue().add_neighbour(t);
					t.add_neighbour(curr.getValue());
				}
				curr=curr.getNext();
			}
			all_Points.add(a);
			all_Points.add(b);
			all_Points.add(c);
			all_Edges.add(p);
			all_Edges.add(q);
			all_Edges.add(r);
		}
		return bool;
	}

	public int TYPE_MESH() {
		int state=1; // assuming mesh to be proper in the beginning.
		Node<Edge> curr=all_Edges.getHead();
		if (curr==null) {
			return -1; // if no triangle have been inserted till now.
		}
		while(curr!=null) {
			Edge e=curr.getValue();
			float[] array=new float[6];
			array[0]=e.edgeEndPoints()[0].getX();
			array[1]=e.edgeEndPoints()[0].getY();
			array[2]=e.edgeEndPoints()[0].getZ();
			array[3]=e.edgeEndPoints()[1].getX();
			array[4]=e.edgeEndPoints()[1].getY();
			array[5]=e.edgeEndPoints()[1].getZ();		
			TriangleInterface [] list=this.TRIANGLE_NEIGHBOR_OF_EDGE(array);
			if (list==null) {
				
			}
			else {
				if (list.length>2) {
					state=3; // improper mesh.
					break;
				}
				if (list.length==1) {
					state=2; // semi_proper mesh.
				}
			}
			curr=curr.getNext();
		}
		return state;
	}	
	
	public EdgeInterface [] BOUNDARY_EDGES() {
		Node<Edge> curr=all_Edges.getHead();
		if (curr==null) {
			return null; // if no triangle have been inserted till now.
		}
		LinkedList<Edge> list=new LinkedList<Edge>();
		while(curr!=null) {
			Edge e=curr.getValue();
			float[] array=new float[6];
			array[0]=e.edgeEndPoints()[0].getX();
			array[1]=e.edgeEndPoints()[0].getY();
			array[2]=e.edgeEndPoints()[0].getZ();
			array[3]=e.edgeEndPoints()[1].getX();
			array[4]=e.edgeEndPoints()[1].getY();
			array[5]=e.edgeEndPoints()[1].getZ();		
			TriangleInterface [] temp=this.TRIANGLE_NEIGHBOR_OF_EDGE(array);
			if (temp==null) {
				
			}
			else {
				if (temp.length==1) {
					list.add(e);
				}
			}
			curr=curr.getNext();
		}
		if (list.getSize()==0) {
			return null;
		}
		EdgeInterface [] BOUNDARY_EDGES=new Edge[list.getSize()];
		Node<Edge> iter=list.getHead();
		for (int i=0; i<list.getSize(); i++) {
			BOUNDARY_EDGES[i]=iter.getValue();
			iter=iter.getNext();
		}
		// using quick_sort.
		quicksortA(BOUNDARY_EDGES,0,BOUNDARY_EDGES.length-1);
		return BOUNDARY_EDGES;
	}
	
	private static void quicksortA(EdgeInterface[] A,int low,int high) {
		if (low<high) {// ensures that length A>=2.
			if (A.length>2) {
				float[] f=new float[3];
				f[0]=((Edge) A[low]).getLength();
				f[1]=((Edge) A[high]).getLength();
				f[2]=((Edge) A[(low+high)/2]).getLength();
	//			System.out.println(f[0]+" "+f[1]+" "+f[2]);
				if (f[0]>f[1]) {
					float temp=f[0];
					f[0]=f[1];
					f[1]=temp;
				}
				if (f[1]>f[2]) {
					float temp=f[1];
					f[1]=f[2];
					f[2]=temp;
				}
				if (f[0]>f[1]) {
					float temp=f[0];
					f[0]=f[1];
					f[1]=temp;
				}
		//		System.out.println(f[0]+" "+f[1]+" "+f[2]);
	            // after doing sorting we are finding median of 3 values and choosing it
				//as pivot.	
				int[] array=partitionA(A,low,high,f[1]);//  f[1]==median will be the pivot.
				// middle elements are at their right position after partition.
				quicksortA(A,low,array[0]-1);// array[0]=start index after partition.
				quicksortA(A,array[1]+1,high);// array[1]=end index after partition.
			}
			else {// length of A == 2.
				if (((Edge) A[0]).getLength() > ((Edge) A[1]).getLength()) {
					swapA(A,0,1);
					return;
				}
				else
					return;
			}
		}
	}

	private static int[] partitionA(EdgeInterface[] A,int start,int end,float pivot) {
	//	 System.out.println(start+" "+end+" "+pivot);
		 int middle = start;
		 while (middle <= end) {
			 if (((Edge) A[middle]).getLength() < pivot) {
				 swapA (A,start,middle);
				 start++;
				 middle++;
			 }
			 else if(((Edge) A[middle]).getLength() > pivot) {
				 swapA (A,middle,end);
				 end--;
			 }
			 else { // A[middle].getLength() == pivot
				 middle++;
			 }
		 }
		 int [] a=new int[2];
		 a[0]=start;
		 a[1]=end;
		 return a;
	}

	private static void swapA(EdgeInterface[] A,int i,int j) {
		Edge l=(Edge) A[i];
		Edge m=(Edge) A[j];
		A[i]=m;
		A[j]=l;
	}
	
	public int COUNT_CONNECTED_COMPONENTS() {
		Node<Triangle> curr=all_Triangles.getHead();
		if (curr==null) {
			return 0;
		}
		int count=0;
		while(curr!=null) {
			if (curr.getValue().isMarker()==false) {// if unmarked node do bfs.
				LinkedList<Triangle> queue=new LinkedList<Triangle>();
				queue.add(curr.getValue());
				Node<Triangle> iter=queue.getHead();
				while(iter!=null) {
					Node<Triangle> node=iter.getValue().getAdjacency_list().getHead();
					// add all elements of adjacency list in queue.
					for(int i=0; i<iter.getValue().getAdjacency_list().getSize(); i++) {
						queue.add(node.getValue());
						node.getValue().setMarker(true);
						node=node.getNext();
					}
					iter=iter.getNext();// we will operate on next element of queue in next operation.
				}
				count++;
			}
			curr=curr.getNext();
		}
		curr=all_Triangles.getHead();
		for(int i=0; i<all_Triangles.getSize(); i++) {
			curr.getValue().setMarker(false);
			curr=curr.getNext();
		}
		return count;
	}
	

	public boolean IS_CONNECTED(float [] triangle_coord_1, float [] triangle_coord_2) {
		Node<Triangle> curr=all_Triangles.getHead();
		if (curr==null) {
			return false;
		}
		Triangle t1=new Triangle(new Point(triangle_coord_1[0],triangle_coord_1[1],triangle_coord_1[2]), new Point(triangle_coord_1[3],triangle_coord_1[4],triangle_coord_1[5]), new Point(triangle_coord_1[6],triangle_coord_1[7],triangle_coord_1[8]));
		Triangle t2=new Triangle(new Point(triangle_coord_2[0],triangle_coord_2[1],triangle_coord_2[2]), new Point(triangle_coord_2[3],triangle_coord_2[4],triangle_coord_2[5]), new Point(triangle_coord_2[6],triangle_coord_2[7],triangle_coord_2[8]));
		
		while(curr!=null) {
			if (curr.getValue().equals(t1)) {
				LinkedList<Triangle> queue=new LinkedList<Triangle>();
				queue.add(curr.getValue());
				Node<Triangle> iter=queue.getHead();
				while(iter!=null) {
					if (iter.getValue().equals(t2)) {// even if t1==t2 then also we will say two triangles are connected as they share a common edge.
						return true;
					}
					Node<Triangle> node=iter.getValue().getAdjacency_list().getHead();
					// add all elements of adjacency list in queue.
					for(int i=0; i<iter.getValue().getAdjacency_list().getSize(); i++) {
						queue.add(node.getValue());
						node=node.getNext();
					}
					iter=iter.getNext();// we will operate on next element of queue in next operation.
				}
				return false;
			}
			else if (curr.getValue().equals(t2)) {
				LinkedList<Triangle> queue=new LinkedList<Triangle>();
				queue.add(curr.getValue());
				Node<Triangle> iter=queue.getHead();
				while(iter!=null) {
					if (iter.getValue().equals(t1)) {// even if t1==t2 then also we will say two triangles are connected as they share a common edge.
						return true;
					}
					Node<Triangle> node=iter.getValue().getAdjacency_list().getHead();
					// add all elements of adjacency list in queue.
					for(int i=0; i<iter.getValue().getAdjacency_list().getSize(); i++) {
						queue.add(node.getValue());
						node=node.getNext();
					}
					iter=iter.getNext();// we will operate on next element of queue in next operation.
				}
				return false;
			}
			else {
				curr=curr.getNext();
			}
		}
		return false;// query triangle do not exists.
	}

	
	public PointInterface [] CENTROID () {
		Node<Triangle> curr=all_Triangles.getHead();
		if (curr==null) {
			return null;
		}
		LinkedList<Point> centroids=new LinkedList<Point>();
		while(curr!=null) {
			if (curr.getValue().isMarker()==false) {// if unmarked node do bfs.
				LinkedList<Triangle> queue=new LinkedList<Triangle>();
				LinkedList<Point> points_in_a_component=new LinkedList<Point>();
				queue.add(curr.getValue());
				Node<Triangle> iter=queue.getHead();
				float sumX=0;
				float sumY=0;
				float sumZ=0;
				while(iter!=null) {
					boolean l=points_in_a_component.add((Point) iter.getValue().triangle_coord()[0]);
					boolean m=points_in_a_component.add((Point) iter.getValue().triangle_coord()[1]);
					boolean n=points_in_a_component.add((Point) iter.getValue().triangle_coord()[2]);
					if (l) {
						sumX=sumX+((Point) iter.getValue().triangle_coord()[0]).getX();
						sumY=sumY+((Point) iter.getValue().triangle_coord()[0]).getY();
						sumZ=sumZ+((Point) iter.getValue().triangle_coord()[0]).getZ();
					}
					if (m) {
						sumX=sumX+((Point) iter.getValue().triangle_coord()[1]).getX();
						sumY=sumY+((Point) iter.getValue().triangle_coord()[1]).getY();
						sumZ=sumZ+((Point) iter.getValue().triangle_coord()[1]).getZ();
					}
					if (n) {
						sumX=sumX+((Point) iter.getValue().triangle_coord()[2]).getX();
						sumY=sumY+((Point) iter.getValue().triangle_coord()[2]).getY();
						sumZ=sumZ+((Point) iter.getValue().triangle_coord()[2]).getZ();
					}
					Node<Triangle> node=iter.getValue().getAdjacency_list().getHead();
					// add all elements of adjacency list in queue.
					for(int i=0; i<iter.getValue().getAdjacency_list().getSize(); i++) {
						queue.add(node.getValue());
						node.getValue().setMarker(true);
						node=node.getNext();
					}
					iter=iter.getNext();// we will operate on next element of queue in next operation.
				}
				// calculate centroid of component and add it to list.
				Point centroid_of_component=new Point(sumX/points_in_a_component.getSize(),sumY/points_in_a_component.getSize(),sumZ/points_in_a_component.getSize());
				centroids.forced_add(centroid_of_component);
			}
			curr=curr.getNext();
		}
		curr=all_Triangles.getHead();
		for(int i=0; i<all_Triangles.getSize(); i++) {
			curr.getValue().setMarker(false);
			curr=curr.getNext();
		}
		if (centroids.getSize()==0) {
			return null;
		}
		Point[] CENTROID=new Point[centroids.getSize()];
		Node<Point> random=centroids.getHead();
		for (int i=0; i<centroids.getSize(); i++) {
			CENTROID[i]=random.getValue();
			random=random.getNext();
		}
		mergesort(CENTROID);
		return CENTROID;
	}
	
	private static void mergesort(PointInterface[] A) {
		if(A==null) {return;} 
	    if(A.length>1) { 
	            int mid=A.length/2; 
	            // Split left part 
	            PointInterface[] left=new PointInterface[mid]; 
	            for(int i=0;i<mid;i++) { 
	                left[i]=A[i]; 
	            }  
	            // Split right part 
	            PointInterface[] right=new PointInterface[A.length-mid]; 
	            for(int i=mid; i<A.length; i++) {
	                right[i-mid]=A[i]; 
	            } 
	            mergesort(left); 
	            mergesort(right); 
	            int i=0;
	            int j=0;
	            int k=0;
	            // Merge sorted left and sorted right arrays into single sorted bigger array.
	            while(i<left.length && j<right.length) { 
	                if(((Point) left[i]).compare((Point) right[j])<0) {
	                    A[k]=left[i]; 
	                    i++; 
	                } 
	                else { 
	                    A[k]=right[j]; 
	                    j++; 
	                }
	                k++; 
	            }
	            // Collect remaining elements if any one of the two arrays gets out of elements. 
	            while(j<right.length) { 
	                A[k]=right[j]; 
	                j++;
	                k++;
	            }
	            while(i<left.length) { 
	                A[k]=left[i]; 
	                i++;
	                k++;
	            }
	        }
	}

	public PointInterface CENTROID_OF_COMPONENT (float [] point_coordinates) {
		Point p=new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		Node<Triangle> curr=all_Triangles.getHead();
		if (curr==null) {
			return null;
		}
		while(curr!=null) {
			if (((Point) curr.getValue().triangle_coord()[0]).equals(p) || ((Point) curr.getValue().triangle_coord()[1]).equals(p) || ((Point) curr.getValue().triangle_coord()[2]).equals(p)) {
				LinkedList<Triangle> queue=new LinkedList<Triangle>();
				LinkedList<Point> points_in_a_component=new LinkedList<Point>();
				queue.add(curr.getValue());
				Node<Triangle> iter=queue.getHead();
				float sumX=0;
				float sumY=0;
				float sumZ=0;
				while(iter!=null) {
					boolean l=points_in_a_component.add((Point) iter.getValue().triangle_coord()[0]);
					boolean m=points_in_a_component.add((Point) iter.getValue().triangle_coord()[1]);
					boolean n=points_in_a_component.add((Point) iter.getValue().triangle_coord()[2]);
					if (l) {
						sumX=sumX+((Point) iter.getValue().triangle_coord()[0]).getX();
						sumY=sumY+((Point) iter.getValue().triangle_coord()[0]).getY();
						sumZ=sumZ+((Point) iter.getValue().triangle_coord()[0]).getZ();
					}
					if (m) {
						sumX=sumX+((Point) iter.getValue().triangle_coord()[1]).getX();
						sumY=sumY+((Point) iter.getValue().triangle_coord()[1]).getY();
						sumZ=sumZ+((Point) iter.getValue().triangle_coord()[1]).getZ();
					}
					if (n) {
						sumX=sumX+((Point) iter.getValue().triangle_coord()[2]).getX();
						sumY=sumY+((Point) iter.getValue().triangle_coord()[2]).getY();
						sumZ=sumZ+((Point) iter.getValue().triangle_coord()[2]).getZ();
					}
					Node<Triangle> node=iter.getValue().getAdjacency_list().getHead();
					// add all elements of adjacency list in queue.
					for(int i=0; i<iter.getValue().getAdjacency_list().getSize(); i++) {
						queue.add(node.getValue());
	// 					node.getValue().setMarker(true);  removed as we are only via 1 component.
						node=node.getNext();
					}
					iter=iter.getNext();// we will operate on next element of queue in next operation.
				}
				// calculate centroid of component and return it.
				Point centroid_of_component=new Point(sumX/points_in_a_component.getSize(),sumY/points_in_a_component.getSize(),sumZ/points_in_a_component.getSize());
				return centroid_of_component;
			}
			curr=curr.getNext();
		}
		return null;
	}



	public 	PointInterface [] CLOSEST_COMPONENTS() {
		Node<Triangle> curr=all_Triangles.getHead();
		if (curr==null) {
			return null;
		}
		LinkedList<LinkedList<Point>> list=new LinkedList<LinkedList<Point>>();
		int count=0;
		while(curr!=null) {
			if (curr.getValue().isMarker()==false) {// if unmarked node do bfs.
				LinkedList<Triangle> queue=new LinkedList<Triangle>();
				LinkedList<Point> points_in_a_component=new LinkedList<Point>();
				queue.add(curr.getValue());
				Node<Triangle> iter=queue.getHead();
				while(iter!=null) {
					points_in_a_component.add((Point) iter.getValue().triangle_coord()[0]);
					points_in_a_component.add((Point) iter.getValue().triangle_coord()[1]);
					points_in_a_component.add((Point) iter.getValue().triangle_coord()[2]);
					Node<Triangle> node=iter.getValue().getAdjacency_list().getHead();
					// add all elements of adjacency list in queue.
					for(int i=0; i<iter.getValue().getAdjacency_list().getSize(); i++) {
						queue.add(node.getValue());
						node.getValue().setMarker(true);
						node=node.getNext();
					}
					iter=iter.getNext();// we will operate on next element of queue in next operation.
				}
				list.forced_add(points_in_a_component);
				count++;
			}
			curr=curr.getNext();
		}
		curr=all_Triangles.getHead();
		for(int i=0; i<all_Triangles.getSize(); i++) {
			curr.getValue().setMarker(false);
			curr=curr.getNext();
		}
		@SuppressWarnings("unchecked")
		LinkedList<Point>[] array=new LinkedList[list.getSize()];
		Node<LinkedList<Point>> t=list.getHead();
		for(int j=0; j<count; j++) {
			array[j]=t.getValue();
			t=t.getNext();
		}
		Point [] CLOSEST_COMPONENTS=new Point[2];
		float min=Float.MAX_VALUE;
		for(int p=0; p<count; p++) {
			for(int q=p+1; q<count; q++) {
				LinkedList<Point> l1=array[p];
				LinkedList<Point> l2=array[q];
				Node<Point> n1=l1.getHead();
				while(n1!=null) {
					Node<Point> n2=l2.getHead();
					while(n2!=null) {
						Point p1=n1.getValue();
						Point p2=n2.getValue();
						float distance=(float) Math.sqrt((p1.getX()-p2.getX())*(p1.getX()-p2.getX())+(p1.getY()-p2.getY())*(p1.getY()-p2.getY())+(p1.getZ()-p2.getZ())*(p1.getZ()-p2.getZ()));
						if (distance<min) {
							min=distance;
							CLOSEST_COMPONENTS[0]=p1;
							CLOSEST_COMPONENTS[1]=p2;
						}
						n2=n2.getNext();
					}
					n1=n1.getNext();
				}
			}
		}
		return CLOSEST_COMPONENTS;
	}
	
	
	public int MAXIMUM_DIAMETER() {
		Node<Triangle> curr=all_Triangles.getHead();
		if (curr==null) {
			return 0;
		}
		LinkedList<LinkedList<Triangle>> list=new LinkedList<LinkedList<Triangle>>();
		while(curr!=null) {
			if (curr.getValue().isMarker()==false) {// if unmarked node do bfs.
				LinkedList<Triangle> queue=new LinkedList<Triangle>();
				queue.add(curr.getValue());
				Node<Triangle> iter=queue.getHead();
				while(iter!=null) {
					Node<Triangle> node=iter.getValue().getAdjacency_list().getHead();
					// add all elements of adjacency list in queue.
					for(int i=0; i<iter.getValue().getAdjacency_list().getSize(); i++) {
						queue.add(node.getValue());
						node.getValue().setMarker(true);
						node=node.getNext();
					}
					iter=iter.getNext();// we will operate on next element of queue in next operation.
				}
				list.forced_add(queue);
			}
			curr=curr.getNext();
		}
		curr=all_Triangles.getHead();
		for(int i=0; i<all_Triangles.getSize(); i++) {
			curr.getValue().setMarker(false);
			curr=curr.getNext();
		}
		int max_size=0;
		LinkedList<Triangle> largest_component=null;
		Node<LinkedList<Triangle>> temp=list.getHead();
		while(temp!=null) {
			if (temp.getValue().getSize()>max_size) {
				max_size=temp.getValue().getSize();
				largest_component=temp.getValue();
			}
			temp=temp.getNext();
		}
		int diameter=0;	
		
		Node<Triangle> node=largest_component.getHead();
		while(node!=null) {
			Node<Triangle> n=largest_component.getHead();
			while(n!=null) {
				if (node.getValue()!=n.getValue()) {
					int r=node.getValue().shortest_path(n.getValue(),largest_component);
					if (r>diameter) {
						diameter=r;
					}
				}
				n=n.getNext();
			}
			node=node.getNext();
		}
		return diameter;
	}	
	

	
	public TriangleInterface [] NEIGHBORS_OF_TRIANGLE(float [] triangle_coord) {
		Point a=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point b=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point c=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Node<Triangle> curr=all_Triangles.getHead();
		LinkedList<Triangle> list=new LinkedList<Triangle>();
		while(curr!=null) {
			Triangle t=curr.getValue();
			int state=0;
			Point l=(Point) t.triangle_coord()[0];
			Point m=(Point) t.triangle_coord()[1];
			Point n=(Point) t.triangle_coord()[2];
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
				list.add(t);
			}
			curr=curr.getNext();
		}
		if (list.getSize()==0) {
			return null;// return null if triangle is not a neighbor of any triangle.
		}
		Triangle[] NEIGHBORS_OF_TRIANGLE=new Triangle[list.getSize()];
		Node<Triangle> iter=list.getHead();
		for (int i=0; i<list.getSize(); i++) {
			NEIGHBORS_OF_TRIANGLE[i]=iter.getValue();
			iter=iter.getNext();
		}
		return NEIGHBORS_OF_TRIANGLE;
	}


	
	public EdgeInterface [] EDGE_NEIGHBOR_TRIANGLE(float [] triangle_coord) {
		Point a=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point b=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point c=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Node<Edge> curr=all_Edges.getHead();
		LinkedList<Edge> list=new LinkedList<Edge>();
		while(curr!=null) {
			Edge e=curr.getValue();
			Point u=(Point) e.edgeEndPoints()[0];
			Point v=(Point) e.edgeEndPoints()[1];
			if (u.equals(a) || u.equals(b) || u.equals(c)) {
				if (v.equals(a) || v.equals(b) || v.equals(c)) {
					list.add(e);
				}
			}
			curr=curr.getNext();
		}
		if (list.getSize()==0) {
			return null;// return null if triangle is not a neighbor of any edge.
		}
		Edge[] EDGE_NEIGHBOR_TRIANGLE=new Edge[list.getSize()];
		Node<Edge> iter=list.getHead();
		for (int i=0; i<list.getSize(); i++) {
			EDGE_NEIGHBOR_TRIANGLE[i]=iter.getValue();
			iter=iter.getNext();
		}
		return EDGE_NEIGHBOR_TRIANGLE;
	}

	
	
	public PointInterface [] VERTEX_NEIGHBOR_TRIANGLE(float [] triangle_coord) {
		Point a=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point b=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point c=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Node<Point> curr=all_Points.getHead();
		LinkedList<Point> list=new LinkedList<Point>();
		while(curr!=null) {
			Point vertex=curr.getValue();
			if (vertex.equals(a) || vertex.equals(b) || vertex.equals(c)) {
				list.add(vertex);
			}
			curr=curr.getNext();
		}
		if (list.getSize()==0) {
			return null;// return null if triangle is not a neighbor of any point.
		}
		Point[] VERTEX_NEIGHBOR_TRIANGLE=new Point[list.getSize()];
		Node<Point> iter=list.getHead();
		for (int i=0; i<list.getSize(); i++) {
			VERTEX_NEIGHBOR_TRIANGLE[i]=iter.getValue();
			iter=iter.getNext();
		}
		return VERTEX_NEIGHBOR_TRIANGLE;
	}


	
	public TriangleInterface [] EXTENDED_NEIGHBOR_TRIANGLE(float [] triangle_coord) {
		Point a=new Point(triangle_coord[0],triangle_coord[1],triangle_coord[2]);
		Point b=new Point(triangle_coord[3],triangle_coord[4],triangle_coord[5]);
		Point c=new Point(triangle_coord[6],triangle_coord[7],triangle_coord[8]);
		Node<Triangle> curr=all_Triangles.getHead();
		LinkedList<Triangle> list=new LinkedList<Triangle>();
		while(curr!=null) {
			Triangle t=curr.getValue();
			int state=0;
			Point l=(Point) t.triangle_coord()[0];
			Point m=(Point) t.triangle_coord()[1];
			Point n=(Point) t.triangle_coord()[2];
			if (a.equals(l) || a.equals(m) || a.equals(n)) {
				state++;
			}
			if (b.equals(l) || b.equals(m) || b.equals(n)) {
				state++;
			}
			if (c.equals(l) || c.equals(m) || c.equals(n)) {
				state++;
			}
			if (state==2 || state==1) {
				list.add(t);
			}
			curr=curr.getNext();
		}
		if (list.getSize()==0) {
			return null;// return null if triangle is not having extended neighbor of any triangle.
		}
		Triangle[] EXTENDED_NEIGHBOR_TRIANGLE=new Triangle[list.getSize()];
		Node<Triangle> iter=list.getHead();
		for (int i=0; i<list.getSize(); i++) {
			EXTENDED_NEIGHBOR_TRIANGLE[i]=iter.getValue();
			iter=iter.getNext();
		}
		return EXTENDED_NEIGHBOR_TRIANGLE;
	}

	
	public TriangleInterface [] INCIDENT_TRIANGLES(float [] point_coordinates) {
		Point p=new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		Node<Triangle> curr=all_Triangles.getHead();
		LinkedList<Triangle> list=new LinkedList<Triangle>();
		while(curr!=null) {
			Triangle t=curr.getValue();
			Point l=(Point) t.triangle_coord()[0];
			Point m=(Point) t.triangle_coord()[1];
			Point n=(Point) t.triangle_coord()[2];
			if (p.equals(l) || p.equals(m) || p.equals(n)) {
				list.add(t);
			}
			curr=curr.getNext();
		}
		if (list.getSize()==0) {
			return null;// return null if point is not a part of any triangle or face.
		}
		Triangle[] INCIDENT_TRIANGLES=new Triangle[list.getSize()];
		Node<Triangle> iter=list.getHead();
		for (int i=0; i<list.getSize(); i++) {
			INCIDENT_TRIANGLES[i]=iter.getValue();
			iter=iter.getNext();
		}
		return INCIDENT_TRIANGLES;
	}

	
	public PointInterface [] NEIGHBORS_OF_POINT(float [] point_coordinates) {
		Point p=new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		Node<Edge> curr=all_Edges.getHead();
		LinkedList<Point> list=new LinkedList<Point>();
		while(curr!=null) {
			Edge e=curr.getValue();
			Point u=(Point) e.edgeEndPoints()[0];
			Point v=(Point) e.edgeEndPoints()[1];
			if (p.equals(u)) {
				list.add(v);	
			}
			if (p.equals(v)) {
				list.add(u);
			}
			curr=curr.getNext();
		}
		if (list.getSize()==0) {
			return null;// return null if point is not a part of any edge. 
		}
		Point[] NEIGHBORS_OF_POINT=new Point[list.getSize()];
		Node<Point> iter=list.getHead();
		for (int i=0; i<list.getSize(); i++) {
			NEIGHBORS_OF_POINT[i]=iter.getValue();
			iter=iter.getNext();
		}
		return NEIGHBORS_OF_POINT;
	}

	
	public EdgeInterface [] EDGE_NEIGHBORS_OF_POINT(float [] point_coordinates) {
		Point p=new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		Node<Edge> curr=all_Edges.getHead();
		LinkedList<Edge> list=new LinkedList<Edge>();
		while(curr!=null) {
			Edge e=curr.getValue();
			Point u=(Point) e.edgeEndPoints()[0];
			Point v=(Point) e.edgeEndPoints()[1];
			if (p.equals(u) || p.equals(v)) {
				list.add(e);
			}
			curr=curr.getNext();
		}
		if (list.getSize()==0) {
			return null;// return null if point is not a part of any edge. 
		}
		Edge[] EDGE_NEIGHBORS_OF_POINT=new Edge[list.getSize()];
		Node<Edge> iter=list.getHead();
		for (int i=0; i<list.getSize(); i++) {
			EDGE_NEIGHBORS_OF_POINT[i]=iter.getValue();
			iter=iter.getNext();
		}
		return EDGE_NEIGHBORS_OF_POINT;
	}


	
	public TriangleInterface [] FACE_NEIGHBORS_OF_POINT(float [] point_coordinates) {
		Point p=new Point(point_coordinates[0],point_coordinates[1],point_coordinates[2]);
		Node<Triangle> curr=all_Triangles.getHead();
		LinkedList<Triangle> list=new LinkedList<Triangle>();
		while(curr!=null) {
			Triangle t=curr.getValue();
			Point l=(Point) t.triangle_coord()[0];
			Point m=(Point) t.triangle_coord()[1];
			Point n=(Point) t.triangle_coord()[2];
			if (p.equals(l) || p.equals(m) || p.equals(n)) {
				list.add(t);
			}
			curr=curr.getNext();
		}
		if (list.getSize()==0) {
			return null;// return null if point is not a part of any triangle or face.
		}
		Triangle[] FACE_NEIGHBORS_OF_POINT=new Triangle[list.getSize()];
		Node<Triangle> iter=list.getHead();
		for (int i=0; i<list.getSize(); i++) {
			FACE_NEIGHBORS_OF_POINT[i]=iter.getValue();
			iter=iter.getNext();
		}
		return FACE_NEIGHBORS_OF_POINT;
	}

	
	public TriangleInterface [] TRIANGLE_NEIGHBOR_OF_EDGE(float [] edge_coordinates) {
		Point a=new Point(edge_coordinates[0],edge_coordinates[1],edge_coordinates[2]);
		Point b=new Point(edge_coordinates[3],edge_coordinates[4],edge_coordinates[5]);
		if (a.equals(b)) {
			return null;
		}
		Node<Triangle> curr=all_Triangles.getHead();
		LinkedList<Triangle> list=new LinkedList<Triangle>();
		while(curr!=null) {
			Triangle t=curr.getValue();
			Point l=(Point) t.triangle_coord()[0];
			Point m=(Point) t.triangle_coord()[1];
			Point n=(Point) t.triangle_coord()[2];
			if (a.equals(l) || a.equals(m) || a.equals(n)) {
				if (b.equals(l) || b.equals(m) || b.equals(n)) {
					list.add(t);
				}
			}
			curr=curr.getNext();
		}
		if (list.getSize()==0) {
			return null;
		}
		Triangle[] TRIANGLE_NEIGHBOR_OF_EDGE=new Triangle[list.getSize()];
		Node<Triangle> iter=list.getHead();
		for (int i=0; i<list.getSize(); i++) {
			TRIANGLE_NEIGHBOR_OF_EDGE[i]=iter.getValue();
			iter=iter.getNext();
		}
		return TRIANGLE_NEIGHBOR_OF_EDGE;
	}
	
}
