package Project2Alternative;

import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomGenerator;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.file.FileSinkDOT;

import java.io.IOException;

public class RandomGraph {
    Graph graph;
    Generator generator;

    public RandomGraph(String directory) {
        // Generating the random graph with nodes and edges
        System.setProperty("org.graphstream.ui", "swing");
        graph = new SingleGraph("Random");
        generator = new RandomGenerator(2);
        generator.addSink(graph);
        generator.begin();
        for (int i = 0; i < 10; i++)
            generator.nextEvents();
        generator.end();
        //graph.setStrict(false);

//        graph.display();

        // Write into a text file of edges
        try {
            writeIntoFile(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeIntoFile(String directory) throws IOException {
        FileSinkDOT fs = new FileSinkDOT();
        fs.writeAll(graph, directory);
        ReplaceFormat replaceFormat = new ReplaceFormat();
        replaceFormat.fileMain(directory);
    }
}