/*   CODE STARTS  */



import java.util.Arrays;

public class Graph {

	int V,E;
	Edge edge[];
	
	class Edge implements Comparable<Edge>{    //   implementing Comparable .. 
		int src,dest;
		int weight;
		@Override
		public int compareTo(Edge o) {     //   sorting of edges based on weight
			return this.weight - o.weight;
		}
	}
	
	class subset{			//   a class representing parent and rank of a vertex
		int parent,rank;
	}
	
	Graph(int v,int e)
	{
		V = v;
		E = e;
		
		edge = new Edge[E];
		
		for(int i=0;i<e;i++)
		{
			edge[i] = new Edge();
		}
	}
	
	//    main function to find the MST in the graph   
	
	void KruskalMST()     
	{
		Edge[] result = new Edge[V];    //   our result set which will contain all the edges included in MST
		int e = 0;
		int i = 0;
		
		for(i=0;i<V;i++) {
			result[i] = new Edge();
		}
		//	STEP-1
		Arrays.sort(edge);				//    sorting the edge list based on weights
		
		subset subsets[] = new subset[V];		//  creating a subset array of objects.
		
		for(i=0;i<V;i++)
			subsets[i] = new subset();		//   initializing it
		
		for(i=0;i<V;i++) {
			subsets[i].parent = i;      //   initially , the parent will be the node values themselves. ..
			subsets[i].rank = 0;		// and rank will be 0 ...
		}
		
		i=0;
		
		while(e<V-1) {           		//    when V-1 edges are included in result set, we stop
			
			Edge next_edge = new Edge();
			next_edge = edge[i++];
			
			int x = find(subsets, next_edge.src);    //   checking  x and y belong to which subset
			int y = find(subsets, next_edge.dest);
			
			if(x!=y)		//  if they both belong to different subset, means this edge wont create a cycle
			{
				result[e++] = next_edge;		//     so add it to result
				Union(subsets, x, y);			//   and do a union of x's and y's subsets
			}
		}
		
		 System.out.println("Following are the edges in " +  
                 "the constructed MST"); 
		 for (i = 0; i < e; ++i) 
			 System.out.println(result[i].src+" -- " +  
					 result[i].dest+" == " + result[i].weight); 
		
	}

	private void Union(subset[] subsets, int x, int y) {
		
		int xroot = find(subsets, x);    //   will give the ancestor of x vertex
		int yroot = find(subsets, y);	 //   will give the ancestor of y vertex
		
		if(subsets[xroot].rank > subsets[yroot].rank)			//    checking ranks
			subsets[yroot].parent = xroot;		
		
		else if(subsets[xroot].rank < subsets[yroot].rank)
			subsets[xroot].parent = yroot;
		
		else			//   if ranks are same make one of them as root and increase its rank ...
		{
			subsets[xroot].parent = yroot;
			subsets[yroot].rank ++;
		}
	}

	private int find(subset[] subsets, int i) {		//   to find  i vertex belong to which subset
		
		if(subsets[i].parent != i)
			find(subsets, subsets[i].parent);		//   if its parent is not itself, then its root must be diff
		
		return subsets[i].parent;		//   we found its parent(or itself), return it ..
	}
	
	// Driver Program 
    public static void main (String[] args) 
    { 
  
        /* Let us create following weighted graph 
                 10 
            0--------1 
            |  \     | 
           6|   5\   |15 
            |      \ | 
            2--------3 
                4       */
        int V = 4;  // Number of vertices in graph 
        int E = 5;  // Number of edges in graph 
        Graph graph = new Graph(V, E); 
  
        // add edge 0-1 
        graph.edge[0].src = 0; 
        graph.edge[0].dest = 1; 
        graph.edge[0].weight = 10; 
  
        // add edge 0-2 
        graph.edge[1].src = 0; 
        graph.edge[1].dest = 2; 
        graph.edge[1].weight = 6; 
  
        // add edge 0-3 
        graph.edge[2].src = 0; 
        graph.edge[2].dest = 3; 
        graph.edge[2].weight = 5; 
  
        // add edge 1-3 
        graph.edge[3].src = 1; 
        graph.edge[3].dest = 3; 
        graph.edge[3].weight = 15; 
  
        // add edge 2-3 
        graph.edge[4].src = 2; 
        graph.edge[4].dest = 3; 
        graph.edge[4].weight = 4; 
  
        graph.KruskalMST(); 
    } 
} 
