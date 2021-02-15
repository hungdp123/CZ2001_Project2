package Project2Alternative;

public class Node {
    // Individual nodeID
    private int nodeID;
    // Checking if node has been visited
    private boolean visited;
    // Checking if node is a hospital
    private boolean hospital;
    // Checking how many times node has been visited
    private int noOfVisit;
    public Node(int nodeID) {
        this.nodeID = nodeID;
        this.visited = false;
        this.hospital = false;
        this.noOfVisit =0;
    }

    void visit() {
        visited = true;
    }

    void unVisit() {
        visited = false;
    }

    public int getNodeID() {
        return nodeID;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isHospital() {
        return hospital;
    }

    public void setHospital(boolean hospital) {
        this.hospital = hospital;
    }

    public int getNoOfVisit() {
        return noOfVisit;
    }
    public void IncreaseVisit() {
        this.noOfVisit +=1;
    }
}

