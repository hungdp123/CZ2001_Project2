package Project2Alternative;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        int k;
        String directory;
        String fileName, hospitalName;
        String outputFileName;
        System.out.println("Enter your directory where all your files are contained:");
        // For example : C:\Users\Eugene\Downloads\ ** make sure to add the last \ at the end
        directory = scanner.next();
        do {
            System.out.println("Select which type of graph you want:");
            System.out.println("1: Random Graph");
            System.out.println("2: Random Graph with nearest k hospitals");
            System.out.println("3: Real World Road Graph");
            System.out.println("4: Real World Road Graph with nearest k hospitals");
            System.out.println("5: Exit");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter file name you want to save to:");
                    fileName = scanner.next();
                    RandomGraph randomGraph = new RandomGraph(directory + fileName);
                    Network random = new Network(directory + fileName, false);
                    System.out.println("Enter hospital file name you want to save to:");
                    hospitalName = scanner.next();
                    random.insertHospitals(directory + hospitalName);
                    random.BFS(random.hospital);
//                    random.printDistance();
                    System.out.println("Enter output file name you want to save the shortest distance to: ");
                    outputFileName = scanner.next();
                    random.outputFile1(directory + outputFileName);
                    break;
                case 2:
                    System.out.println("Enter file name you want to save to:");
                    fileName = scanner.next();
                    RandomGraph randomGraph1 = new RandomGraph(directory + fileName);
                    Network random1 = new Network(directory + fileName, false);
                    System.out.println("Enter hospital file name you want to save to:");
                    hospitalName = scanner.next();
                    random1.insertHospitals(directory + hospitalName);
                    System.out.println("Enter k value");
                    k = scanner.nextInt();
                    random1.BFS(random1.getHospital(), k);
//                    random1.printDistance1(k);
                    System.out.println("Enter name of output file you want to save to:");
                    outputFileName = scanner.next();
                    random1.outputFile2(directory + outputFileName, k);
                    break;
                case 3:
                    System.out.println("Enter your graph file name:");
                    fileName = scanner.next();
                    Network realWorld = new Network(directory + fileName,false);
                    System.out.println("Enter hospital file name you want to save to:");
                    hospitalName = scanner.next();
                    realWorld.insertHospitals(directory + hospitalName);
                    realWorld.BFS(realWorld.getHospital());
//                    realWorld.printDistance1(1);
//                    realWorld.printHospitals();
//                    realWorld.BFS_PartB(realWorld.hospital);
//                    realWorld.printDistance_PartB();
//                    realWorld.printDistance();
                    System.out.println("Enter output file name you want to save the shortest distance to: ");
                    outputFileName = scanner.next();
                    realWorld.outputFile1(directory + outputFileName);
                    break;
                case 4:
                    System.out.println("Enter your graph file name:");
                    fileName = scanner.next();
                    Network realWorld2 = new Network(directory + fileName,false);
                    System.out.println("Enter hospital file name you want to save to:");
                    hospitalName = scanner.next();
                    realWorld2.insertHospitals(directory + hospitalName);
                    System.out.println("Enter k value: ");
                    k = scanner.nextInt();
                    realWorld2.BFS(realWorld2.getHospital(), k);
//                    realWorld2.printDistance1(k);
                    System.out.println("Enter output file name you want to save the shortest distance to: ");
                    outputFileName = scanner.next();
                    realWorld2.outputFile2(directory + outputFileName, k);
                    break;
                default:
                    break;
            }
        } while (choice < 5);
    }
}
