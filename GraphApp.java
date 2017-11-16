/** Rial Johnson & Connor Overcast
 * CSCI 232 Lab #2
 * Lab 2 is about algorithms for undirected, unweighted graphs.
 *
 */

// driver
public class GraphApp {
	public static void main(String[] args) {
		Graph theGraph = new Graph(); // new instance of graph
		
		// depth first search
		System.out.println("Depth First Search Visits: ");
		theGraph.depthFirstSearch();

		// breadth first search
		System.out.println("\nBreadth First Search Visits: ");
		theGraph.breadthFirstSearch();

		// minimum spanning tree
		System.out.println("\nMininum Spanning Tree: ");
		theGraph.minimum_spanning_tree();
	}
}
