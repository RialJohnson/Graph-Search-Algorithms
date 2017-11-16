import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.util.Queue;
import java.util.LinkedList;

public class Graph {
	// fields
	private Vertex vertexList[];
	private String minSpanList[];
	private int adjacencyMatrix[][];
	private String labelString = "";
	private int labelLength;

	// constructor calls methods that read the input files
	public Graph() {
		readLabels("labels.txt"); // reads in labels for matrix
		readAdjacencyMatrix("adjacency_matrix.txt"); // reads in matrix values
	}

	// reads the adjacency matrix input file and saves it into the
	// adjacencyMatrix field
	private void readAdjacencyMatrix(String filePath) {

		// creates 1d array of size label length square (guranteed length is
		// labelLength squared)
		int[] temp1dArray = new int[labelLength * labelLength];

		FileReader inputStream = null; // declare FileReader

		try {
			inputStream = new FileReader(filePath);

			int c = 0; // use for input stream
			int z = 0; // use for array index
			while ((c = inputStream.read()) != -1) {
				if (c == 48 || c == 49) {
					// gives 0 or 1 based on ascii value
					temp1dArray[z] = c - 48;
					z++;
				}

			}
		}

		// catches if a file isn't found
		catch (FileNotFoundException exception) {

			System.out.print("File not found!");
		}

		// catches everything else
		catch (IOException exception) {
			System.out.println("Something went terribly wrong!");
		}

		// length will be label x label
		adjacencyMatrix = new int[labelLength][labelLength];

		// make adjacency matrix.
		int p = 0; // use for temp array index
		for (int i = 0; i < labelLength; i++) {
			for (int j = 0; j < labelLength; j++) {
				adjacencyMatrix[i][j] = temp1dArray[p];
				p++;
			}
		}
	}

	// reads the labels input file, creates a new vertex for each label, and
	// saves each new vertex into the vertexList field
	private void readLabels(String filePath) {

		try {
			labelString = new Scanner(new File(filePath)).useDelimiter("//A").next();
		} catch (FileNotFoundException exception) { // catches if a file isn't
													// found
			System.out.print("File not found!");
		} 

		// gets length for using in creating adjacency matrix
		labelLength = labelString.length();

		// initialize with length of label string
		vertexList = new Vertex[labelLength];

		// adds labels to vertex and stores them in array
		for (int i = 0; i < labelLength; i++) {
			Vertex v = new Vertex(labelString.charAt(i));
			vertexList[i] = v;
		}
	}

	// implements the depth first search algorithm calling
	// getAdjacentUnvisitedVertex() and displayVertex() as needed
	public void depthFirstSearch() {

		// array and counter for storing and printing
		char[] depthFirstArray = new char[labelLength];
		
		// creates array for Min Span strings with (n-1) as size
		minSpanList = new String[depthFirstArray.length - 1];
		
		// counter variables iterating through arrays
		int arrayCounter = 0;
		int minSpanCounter = 0;

		Vertex v = vertexList[0]; // starts with first vertex

		Stack<Vertex> depthStack = new Stack<Vertex>(); // creates a stack

		depthStack.push(v); // pushes on stack
		v.wasVisited = true; // marks as visited

		int currentVertex = 0;

		// loops until stack is empty
		while (!depthStack.isEmpty()) {
			int adjacentVertex = getAdjacentUnvisitedVertex(currentVertex);
			
			
			
			
			if (adjacentVertex >= 0) {
				// string to insert in min span tree array
				String s = "";
				s = s + vertexList[currentVertex].label;
				s = s + vertexList[adjacentVertex].label;
				
				// adds min span to the array 
				minSpanList[minSpanCounter] = s;
				//System.out.println(minSpanList[minSpanCounter]);
				minSpanCounter++;
		
				currentVertex = adjacentVertex; // sets current to adjacent

				v = vertexList[currentVertex]; // current vertex

				depthStack.push(v); // pushes on stack
				// System.out.println("Push: " + v.label);

				v.wasVisited = true; // marks as visited

			} else {
				// stores in array for printing
				depthFirstArray[arrayCounter] = depthStack.peek().label;
				arrayCounter++;

				depthStack.pop(); // pops the vertex

				if (depthStack.isEmpty()) {
					break;
				}

				v = depthStack.peek();

				// finds the index for the current vertex
				for (int j = 0; j < labelLength; j++) {
					if (vertexList[j].label == v.label) {
						currentVertex = j;
					}
				}

			}

		}

		// prints out depth first search (reverses order of array)
		for (int i = 0; i < labelLength; i++) {
			System.out.print(depthFirstArray[labelLength - (i + 1)]);
		}

		// sets vertexes to not visited before next search
		for (int i = 0; i < labelLength; i++) {
			vertexList[i].wasVisited = false;
		}
		
	}

	// implements the breadth first search algorithm calling
	// getAdjacentUnvisitedVertex() and displayVertex() as needed
	public void breadthFirstSearch() {

		// array and counter for storing and printing
		char[] breadthFirstArray = new char[labelLength];
		int arrayCounter = 0;

		Vertex v = vertexList[0]; // starts with first vertex

		Queue<Vertex> breadthQueue = new LinkedList<Vertex>(); // creates a
																// queue
		breadthQueue.add(v); // pushes on queue
		v.wasVisited = true; // marks as visited

		int currentVertex = 0;

		while (!breadthQueue.isEmpty()) {

			// get an adjacent vertex
			int adjacentVertex = getAdjacentUnvisitedVertex(currentVertex);

			// checks if any adjacent was found
			if (adjacentVertex >= 0) {
				v = vertexList[adjacentVertex]; // set v to adjacent vertex
				v.wasVisited = true; // mark as visited
				breadthQueue.add(v); // insert in queue

			} else {
				// stores in array for printing
				breadthFirstArray[arrayCounter] = breadthQueue.peek().label;
				arrayCounter++;

				if (breadthQueue.isEmpty()) {
					break;
				}

				v = breadthQueue.remove(); // set v equal to removed vertex

				// finds the index for the current vertex
				for (int j = 0; j < labelLength; j++) {
					if (vertexList[j].label == v.label) {
						currentVertex = j;

					}
				}

			}

		}

		// prints out depth first search (reverses order of array)
		for (int i = 0; i < labelLength; i++) {
			System.out.print(breadthFirstArray[i]);
		}
		
		// sets vertexes to not visited before next search
		for (int i = 0; i < labelLength; i++) {
			vertexList[i].wasVisited = false;
		}

	}

	// implements the minimum spanning tree algorithm calling
	// getAdjacentUnvisitedVertex() and displayVertex() as needed
	public void minimum_spanning_tree() {
		
		// prints out the min span tree
		for (int i = 0; i < minSpanList.length; i++) {
			System.out.print(minSpanList[i] + ' ');
		}
	}

	// returns an unvisited vertex adjacent to the vertex indicated by
	// vertexIndex using the vertexList and adjacenyMatrix fields
	public int getAdjacentUnvisitedVertex(int vertexIndex) {

		// gets an unvisited adjacent vertex that hasn't been visit
		for (int i = 0; i < labelLength; i++) {
			if (adjacencyMatrix[vertexIndex][i] == 1 && vertexList[i].wasVisited == false) {
				return i;
			}
		}

		// returns -1 if there are no unvisited adjacent vertexes
		return -1;

	}
}
