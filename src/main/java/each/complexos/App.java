package each.complexos;

import each.complexos.network.Graph;
import each.complexos.network.NearestNeighborNetwork;

public class App 
{
    public static void main( String[] args )
    {
        int D = 2;
        while (D<129){ 
            Graph network = new NearestNeighborNetwork(10000, D);
            network.run(0.05, 2.2, 1.0, 1000);
            network.run(0.6, 2.6, 1.0, 1000);
            network.run(0.95, 4.8, 1.0, 1000);
            D= D*4;
        }
    }
}
