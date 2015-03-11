package algo;

public class GraphTest {
	
	
	
	public static void main(String [] args) {
		EdgeWeightedGraph roads = new EdgeWeightedGraph(8);
		roads.addEdge(new Edge(5, 0, 15));
		roads.addEdge(new Edge(5, 6, 7));
		roads.addEdge(new Edge(0, 1, 7));
		roads.addEdge(new Edge(1, 7, 10));
		roads.addEdge(new Edge(1, 2, 6));
		roads.addEdge(new Edge(2, 3, 8));
		roads.addEdge(new Edge(3, 4, 3));
		roads.addEdge(new Edge(7, 4, 11));
		
		for (Edge e: roads.adj(0)) {
			//System.out.println(e);
			int other = e.other(0);
			//System.out.println(other);
		}
		
		BreadthFirstPaths bfs = new BreadthFirstPaths(roads, 0);
		System.out.println("To vinnitsa: " + bfs.distTo(4));
		
		//System.out.println(roads);
	}

}
