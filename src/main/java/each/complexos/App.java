package each.complexos;

import each.complexos.network.Graph;
import each.complexos.network.NearestNeighborNetwork;

public class App 
{
    public static void main( String[] args )
    {
        Graph network = new NearestNeighborNetwork(10000, 2);
        network.run(0.6, 2.6, 1.0, 800);
    }
}
