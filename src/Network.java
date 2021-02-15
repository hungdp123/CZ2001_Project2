package Project2Alternative;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Network {
    // Each node contains a list of all the neighbours
    private HashMap<Node, LinkedList<Node>> map;
    // Store nodesID in a string array (From nodeID, To nodeID)
    private String[] input;
    // HashMap to indicate which node to refer to from a string
    private Map<String, Node> object;
    // HashMap to store nodes linking to all hospitals they are connected to
    private HashMap<Node, LinkedList<Node>> distance;
    // HashMap to store actual shortest distance
    private HashMap<Node, LinkedList<Integer>> dis;
    // Boolean expression to see if graph is directed or undirected
    boolean directed;
    // Linked List for hospitals
    LinkedList<Node> hospital = new LinkedList<>();
    // Directory Name
    String directory;

    public Network(String directory, boolean directed) {
        // Check if graph is directed or not
        this.directed = directed;
        this.directory = directory;
        object = new HashMap<>();
        map = new HashMap<>();
        distance = new HashMap<>();
        dis = new HashMap<>();
        insertEdge(directory);
    }


    // Function to read text file and input edges into graph network
    private void insertEdge(String directory) {
        try (Scanner scanner = new Scanner(new File(directory))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.startsWith("#")) {
                    input = line.split("\\s+");
                    if (!object.containsKey(input[0])) {
                        object.put(input[0], new Node(Integer.parseInt(input[0])));
                        distance.put(object.get(input[0]), new LinkedList<>());
                        dis.put(object.get(input[0]), new LinkedList<>());

                    }
                    if (!object.containsKey(input[1])) {
                        object.put(input[1], new Node(Integer.parseInt(input[1])));
                        distance.put(object.get(input[1]), new LinkedList<>());
                        dis.put(object.get(input[1]), new LinkedList<>());

                    }
                    addEdge(object.get(input[0]), object.get(input[1]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Function to add edge from one node to another in hashmap
    private void addEdge(Node source, Node destination) {
        // Add Node inside linked list if it is not inside yet

        if (!map.containsKey(source)) {
            map.put(source, null);

        }

        if (!map.containsKey(destination)) {
            map.put(destination, null);
        }

        addEdgeFunction(source, destination);

        // If graph is undirected, we have to add edge from destination to source too
        if (!directed) {
            addEdgeFunction(destination, source);
        }
    }

    // Help to see if both nodes is already in map or not, adds them if they are not
    private void addEdgeFunction(Node source, Node destination) {
        // Check if we are adding a duplicated edge by removing destination node first, then adding it
        LinkedList<Node> temp = map.get(source);

        if (temp != null) {
            temp.remove(destination);
        } else {
            temp = new LinkedList<>();
        }
        temp.add(destination);
        map.put(source, temp);
    }

    // Get a random number of hospitals, make random nodes hospitals, and write into a text file containing the number of hospitals and all hospital nodeIDs
    public void insertHospitals(String directory) {
        Random random = new Random();
        int noOfHospitals = random.nextInt((object.size() / 100 + 1) * 5) + 1;
        while (noOfHospitals > 0) {
            int hospitalID = random.nextInt(object.size());
            try {
                object.get(String.valueOf(hospitalID)).setHospital(true);
            } catch (NullPointerException e) {
                continue;
            }
            if (!hospital.contains(object.get(String.valueOf(hospitalID)))) {
                hospital.add(object.get(String.valueOf(hospitalID)));
                noOfHospitals--;
            }
        }
        writeIntoHospitalFile(directory);

    }

    // Write into a file containing the number of hospitals and all hospital nodeIDs
    private void writeIntoHospitalFile(String directory) {
        try {
            FileWriter writer = new FileWriter(directory);

            //// print numHosp at top of txt file
            writer.write("#" + hospital.size() + "\n");
            for (Node node : hospital
            ) {
                writer.write(String.valueOf(node.getNodeID()));
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add all hospital nodes into a linked list containing all hospitals
    private void hospitals() {
        for (Node node: object.values()
        ) {
            if (node.isHospital()) {
                hospital.add(node);
            }
        }
    }

    // returns a linked list containing hospitals
    public LinkedList<Node> getHospital() {
        return hospital;
    }

    // Does BFS and get the path from node to nearest hospital
    public void BFS(LinkedList<Node> hospital) // parameters is the list of hospitals (whichever data structure we use)
    {

        LinkedList<Node> queue = new LinkedList<>();  // Create a queue for BFS

        // visits each node, adds node into queue and add itself into the shortest path linked list for all hospitals
        for (Node node: hospital
        ) {
            node.visit(); // mark the node as visited
            queue.add(node);
            distance.get(node).add(node);
        }

        // BFS algorithm
        while (queue.size() != 0) { // when the queue is not empty
            Node s = queue.poll(); // Pop the first item in the queue
            if (map.get(s) != null) { // if there is a neighbouring node
                for (Node n : map.get(s)) { // check each neighbouring node
                    if (!n.isVisited()) { // if the node has not been visited before
                        n.visit(); // mark the node as visited
                        distance.get(n).add(n); // add the path from the node to the hospital
                        for (Node v : distance.get(s)
                        ) {
                            distance.get(n).add(v);
                        }
                        queue.add(n); // Add the node to the queue
                    }
                }
            }
        }
    }

    public void BFS(LinkedList<Node> hospital, int k) // parameters is the list of hospitals (whichever data structure we use)
    {
        LinkedList<Node> queue = new LinkedList<>();  // Create a queue for BFS

        // Instead of visiting now, we increase the number of visits
        for (Node node: hospital // Initialize nodes that are hospitals
        ) {
            node.IncreaseVisit();
            queue.add(node);
            distance.get(node).add(node);
            dis.get(node).add(0);
        }

        // BFS algorithm
        while (queue.size() != 0) {
            Node s = queue.poll(); // Pop the first node in the queue
            if (map.get(s) != null) { // If the item has neighbouring node
                for (Node n : map.get(s)) { // For each neighbouring node
                    if (n.getNoOfVisit() < k) { // If the neighbouring node has not been visited by k hospitals yet
                        int h = distance.get(s).size();
                        for (int i=0; i<h; i++) { // Check each item in the node
                            Node b = distance.get(s).get(i);
                            int y = dis.get(s).get(i);
                            if (!distance.get(n).contains(b)) { // if the neighbouring node does not contain any element in the main node
                                distance.get(n).add(b); // add that item to neighbouring node
                                dis.get(n).add(y+1); // add the distance to neighbouring node
                                n.IncreaseVisit(); // Increase noOfVisit of node
                                if (!queue.contains(n)) queue.add(n); // if the neighbouring node is not in the queue yet, add the node to queue
                                if (n.getNoOfVisit() ==k) break;
                            }
                        }
                    }

                }
            }
        }
    }

    // Printing path and shortest distance for part a and b
    public void printDistance() {
        for (Node node: distance.keySet()
        ) {
            System.out.println("For Node " + node.getNodeID() + ":");
            for (Node b: distance.get(node)
            ) {
                if (!b.isHospital()) {
                    System.out.print(b.getNodeID() + " -> ");
                } else {
                    System.out.println(b.getNodeID());
                    System.out.print("Node " + b.getNodeID());
                }
            }
            System.out.println(" is the nearest hospital");
            if (distance.get(node).isEmpty()) {
                System.out.println("The distance is " + (distance.get(node).size()));
            } else {
                System.out.println("The distance is " + (distance.get(node).size() - 1));
            }
            System.out.println();
        }
    }

    // printing shortest distance for part c  and d
    public void printDistance1(int k) {
        System.out.println("Result (according to node): ");
        System.out.println();

        for (Node node : map.keySet())
        {
            System.out.println("For node " + node.getNodeID()+":");
            for (Node b: distance.get(node))
            {
                System.out.print(b.getNodeID() + " , ");
            }
            if (distance.get(node).size() < k) {
                System.out.println("There is only " + distance.get(node).size() + " hospital connected to Node " + node.getNodeID());
            } else {
                System.out.println("This is the top " + k + " nearest hospital");
            }
            if (dis.get(node).size() > 0) {
                System.out.print("Respective distance to the hospitals are ");
                for (int b: dis.get(node))
                {
                    System.out.print(b + " , ");
                }
            }
            System.out.println();
            System.out.println();
        }
    }

    // Output results into a file for part a and b
    public void outputFile1(String directory){
        try {
            FileWriter writer = new FileWriter(directory);
            writer.write("Shortest distance for each node to hospital\n\n");
            for (Node node: distance.keySet()
            ) {
                writer.write("For Node " + node.getNodeID() + ":\n");
                    for (Node b: distance.get(node)
                    ) {
                        if (!b.isHospital()) {
                            writer.write(b.getNodeID() + " -> ");
                        }
                        else {
                            writer.write(b.getNodeID() + "\n");
                            writer.write("Node " + b.getNodeID());
                        }
                    }
                writer.write(" is the nearest hospital\n");
                if (distance.get(node).size() == 0) {
                    writer.write("The distance is " + (distance.get(node).size()) + "\n\n");
                } else {
                    writer.write("The distance is " + (distance.get(node).size() - 1) + "\n\n");
                }
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // output shortest distance/path to a file for part c and d

    public void outputFile2(String directory, int k){
        try {
            FileWriter writer = new FileWriter(directory);
            writer.write("Result (according to node): \n\n");
            for (Node node: map.keySet()
            ) {
                writer.write("For node "+node.getNodeID()+":\n");
                for (Node b : distance.get(node)
                ) {
                    writer.write(b.getNodeID()+", ");
                }
                if (distance.get(node).size() < k) {
                    writer.write("There is only " + distance.get(node).size() + " hospital connected to Node " + node.getNodeID() + "\n");
                } else {
                    writer.write("This is the top "+k+ " nearest hospital\n");
                }
                if (dis.get(node).size() > 0) {
                    writer.write("Respective distance to the hospitals are ");
                    for (int b : dis.get(node)
                    ) {
                        writer.write(b + " , ");
                    }
                }
                writer.write("\n\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // -------------------------------------------------Checking/Debugging--------------------------------------------------------------------
    // Print edges of node in graph
    public void printEdges(Node a) {
        for (Node node : map.get(a)
        ) {
            System.out.println("Node " + a.getNodeID() + " has an edge towards: " + node.getNodeID());

        }
    }

    // get all nodeIDs of hospitals
    public void printHospitals() {
        for (Node node : hospital
        ) {
            if (node.isHospital()) {
                System.out.println(node.getNodeID());
            }
        }
    }
    // return the linked list of nodes where there is an edge between source and the returned nodes
    private LinkedList<Node> edges(Node source) {
        return map.get(source);
    }
    // return a boolean expression to see if these 2 nodes have an edge
    public boolean hasEdge(Node source, Node destination) {
        return (map.containsKey(source) && map.get(source).contains(destination));
    }
}
