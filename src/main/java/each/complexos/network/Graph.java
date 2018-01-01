package each.complexos.network;

import java.util.ArrayList;
import java.util.List;

public abstract class Graph {
	protected List<Node> adjList;
	
	public Graph(int size, int d)
	{
		adjList = new ArrayList<>();
		initGraph(size, d);
	}
	
	public abstract void initGraph(int size, int d);
	
	public void run(double w, double r, double piMax, int iterations)
	{
		while (iterations-- > 0)
		{
			for (Node node : adjList)
			{
				node.payoff = 0.0;
			}
			
			for (Node node : adjList)
			{
				node.calcPayoff(r, piMax);
			}
			
			for (Node node : adjList)
			{
				node.calcVelocity(w);
			}
			
			for (Node node : adjList)
			{
				node.update(piMax);
			}
			
			System.out.println(mean());
		}
	}
	
	public double mean()
	{
		double mean = 0.0;
		for (Node node : adjList)
		{
			mean += node.g;
		}
		
		return mean / adjList.size();
	}
}
