import java.util.*;
import java.io.*;
/*Citations:  - https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-greedy-algo-7/ 
 - https://www.programiz.com/dsa/dijkstra-algorithm
 - Class slides and lab
 - https://www.w3schools.com/java/java_files.asp
 - https://www.baeldung.com/java-graphs
 - https://www.programiz.com/java-programming/enhanced-for-loop
 - https://www.geeksforgeeks.org/priority-queue-class-in-java-2/
 - https://www.geeksforgeeks.org/printing-paths-dijkstras-shortest-path-algorithm/
 - https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/
 - https://stackoverflow.com/questions/50396835/priority-queue-implementation-with-updatepriority-olog-n
 - https://www.geeksforgeeks.org/graph-and-its-representations/
*/
//Edge and Vertex act as constructors while Comparison is used for the priority queue 

// class that stores destination vertex and their edge weight
class Edge{

    int destination, weight;
    Edge(int destination, int weight){
        this.destination = destination;
        this.weight = weight;
    }

}
 
 //Constructor for vertices 
 class Vertex{

    int V_S, weight;
    Vertex(int V_S, int weight){
        this.V_S = V_S;
        this.weight = weight;
    }

}
 
 // comparator that sorts the data in priority queue using weight in Vertex
 class Comparison implements Comparator<Vertex>{

    public int compare(Vertex vertex1, Vertex vertex2){
        return vertex1.weight - vertex2.weight;
    }  

}

public class DijkstraSpanningTree {

     //Method to implement Dijkstra’s algorithm for finding the shortest distance between Vertexs
     public static void Dijkstra(int V_S, ArrayList<ArrayList<Edge>> edgeGraph) throws Exception{

        //Priority queue which uses Comparison to order the queue
        PriorityQueue<Vertex> priority_q = new PriorityQueue<Vertex>(new Comparison());

        //Get the size of the edgeGraph 
        int vertices = edgeGraph.size();

        //Stores parent vertex
        int [] vertex_p = new int[vertices];

        //Stores path weight
        int [] path_w = new int[vertices];

        //T/F checking if each node was visited
        boolean [] nodesVisited = new boolean[vertices];
        
        //Initalize all distances as infinite and nodesVisited to false
        for(int n = 0; n < vertices; n++){
            nodesVisited[n] = false;
            path_w[n] = Integer.MAX_VALUE;
        }
         
        //Distance from source vertex from itself is 0
        path_w[V_S] = 0;
        
        //fills priority_q with the vertices needed
        for(int i = 0; i < vertices; i++){
            priority_q.add(new Vertex(i, path_w[i]));
        }
          
        //After all the previous setup we start with Dijkstras algorithim
        for(int j = 0; j < vertices; j++){
            //Take the head of the priority queue and put it in vertex to be put through Dijkstras
            Vertex Vertex = priority_q.poll();

            //Mark the node as visited
            nodesVisited[Vertex.V_S] = true;

            //Create new List<Edge> V_S_List to start mapping 
            List<Edge> V_S_List = edgeGraph.get(Vertex.V_S);

            //enhanced for loop for iterating through the entirety of the data structure just created in the line before
            for(Edge edge : V_S_List){
                //Statement that checks the weights
                if(!nodesVisited[edge.destination] && path_w[edge.destination] > path_w[Vertex.V_S] + edge.weight){
                    //Sets path_w with new weight
                    path_w[edge.destination] = path_w[Vertex.V_S] + edge.weight;

                    //Sets new vertex
                    vertex_p[edge.destination] = Vertex.V_S+1;

                    //puts in new path_w into the priortiy queue
                    priority_q.add(new Vertex(edge.destination, path_w[edge.destination]));
                }
                  
            }
           
        }
        //Setting source vertext values for path_w and vertex_p to -1 after doing Dijkstras algorithim
        path_w[V_S] = -1;
        vertex_p[V_S] = -1;
      
        // write data into output.txt file
        fileWrite(path_w, vertex_p);
    }  
  
    //method that writes result data into cop3503-asn2-output-Rizk-Alex.txt
    public static void fileWrite(int path_w[], int vertex_p[]) throws Exception{
        //Names the new file
        FileWriter writer = new FileWriter("cop3503-asn2-output-Rizk-Alex.txt");

        //Length of the new file
        int len = path_w.length;

        //Formats the new txt output in the desired layout
        writer.write(len + "\n");
        for(int i = 0; i < len ; i++){
            writer.write((i + 1) + " " + path_w[i] + " " + vertex_p[i] + "\n");
        }
        writer.close();
    }

    public static void main(String args[]){
        try{
            //Takes in the txt file to be processed
            Scanner data = new Scanner(new File("cop3503-asn2-input.txt"));

            //Nummber of verticies
            int vertices = Integer.parseInt(data.nextLine());

            //source of vertex
            int V_S = Integer.parseInt(data.nextLine());

            //Number of edges 
            int edges = Integer.parseInt(data.nextLine());

            //Creation of edge graph
            ArrayList<ArrayList<Edge>> edgeGraph = new ArrayList<ArrayList<Edge>>();
           
            //Creates space for the number of verticies
            for(int i = 0; i < vertices; i++){
                edgeGraph.add(new ArrayList<Edge>());
            }
            
            //Places the V_S, destinationination, and weight into edgeGraph
            int s,d,w;
            for(int i = 0; i < edges; i++){
                //Processes the rows of integers
                s = data.nextInt();
                d = data.nextInt();
                w = data.nextInt();

                //edgeGraph being populated
                edgeGraph.get(s-1).add(new Edge(d-1, w));
                edgeGraph.get(d-1).add(new Edge(s-1, w));
            }
           
            //call Dijkstra and pass in the edgeGraph and the V_S vertex
            Dijkstra(V_S - 1, edgeGraph);
        }
        catch(Exception ex){
            System.out.println("There is an error");
        }
    }

}