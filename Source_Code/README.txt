                            README 
			ASSIGNMENT 6
In this, assignment I have used Shape class in which I will explain all the
functions inside it. Also, I will report time complexities of various operations.

In Shape class:
ADD_TRIANGLE:
	In this query, I have added triangle to the list of all triangles if
it is valid and not present before. Similarly, I have done it for edges and points if
the triangle is sucessfully added. These addition took O(n) time each as inserting is O(1) in linkedlist but I have to search whether it is previously present in the linkedlist or not.

TYPE_MESH:
	In this query, I have calculated the mesh_type of the shape. I have iterated through the list of all edges and then, found the no. of triangle neighbours of each edge. With the help, of that I was able to conclude the mesh_type of the shape. It took O(mn) time where m=no. of edges and n=no. of triangles.

BOUNDARY_EDGES:
	In this query, time complexity required was O(mn) where m=no. of edges and n=no. of triangles because I am iterating through list of edges
and for each edge I am iterating through list of triangles.Also, I sorted using quicksort which took O(klogk) where k is the no. of boundary edges.

COUNT_CONNECTED_COMPONENTS:
	In this query, I have done bsf over list of all points. It took O(m+n) for doing bsf over each component of graph where m=no. of edges and n=no. of vertices. So, on adding time for all components, time complexity once again came O(m+n).

IS_CONNECTED:
	In this query, I have done bsf over list of all points. It took O(m+n) for doing bsf over each component of graph where m=no. of edges and n=no. of vertices. So, on adding time for all components, time complexity once again came O(m+n).

CENTROID:
	In this query, I have done bsf over list of all points. It took O(m+n) for doing bsf over each component of graph where m=no. of edges and n=no. of vertices. So, on adding time for all components, time complexity once again came O(m+n). Also, I have kept variables which calculated centroid of each component as I stored all points of a component in a list
which is O(1) in each storage operation. Also, I stored centroids of each component in array. Also, I sorted array of centroids using mergesort algorithm having average complexity O(klogk) where k is the no. of components in the graph or the no. of centroids in array.

CENTROID_OF_COMPONENT:
	In this query, I have searched the triangle which has given point as a part by iterating through the triangle list in O(N) time and then done bsf over that component to find its centroid which as mentioned earlier took O(a+b) where a is the no. of edges and b is the no. of vertices in the component whose part that point given in the argument is.

CLOSEST_COMPONENTS:
	In this query, I have done bsf over each triangle to create a list containing list of points in a specific component. This took O((b+c)) time where  b is the no. of edges and c is the no. of vertices in the graph. Then after, I have iterated through basically all point pairs in different components, calculated their distance and found the minimum among them. It took O(p^2) where p is the no. of points of graph.

MAXIMUM_DIAMETER:
	In this query, I have done bsf over all components to first find the list of components. It took O(m+n) where m is no. of edges and n is the no. of vertices in graph.After that, I found largest component in O(k) time where k is the no. of components. Then after I have found diameter by again doing bsf in the largest component which took O(a+b) where a is the no. of edges and b is the no. of vertices in the largest component.

NEIGHBORS_OF_TRIANGLE:
	It took O(n) time as for getting neighbours I had to iterate through the list of all triangles.

EDGE_NEIGHBOR_TRIANGLE:
	It took O(n) time as for getting edge neighbours I had to iterate through the list of all edges.

VERTEX_NEIGHBOR_TRIANGLE:
	It took O(n) time as for getting vertex neighbours I had to iterate through the list of all vertices.

EXTENDED_NEIGHBOR_TRIANGLE:
	It took O(n) time as for getting extended neighbours I had to iterate through the list of all triangles.

INCIDENT_TRIANGLES:
	It took O(n) time as for getting triangle neighbours I had to iterate through the list of all triangles.

NEIGHBORS_OF_POINT:
	It took O(n) time as for getting point neighbours I had to iterate through the list of all vertices.

EDGE_NEIGHBORS_OF_POINT:
	It took O(n) time as for getting edge neighbours I had to iterate through the list of all edges.

FACE_NEIGHBORS_OF_POINT:
	It took O(n) time as for getting triangle neighbours I had to iterate through the list of all triangles.

TRIANGLE_NEIGHBORS_OF_EDGE:
	It took O(n) time as for getting triangle neighbours I had to iterate through the list of all triangles.
