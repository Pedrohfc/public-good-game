package each.complexos.network;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public abstract class Graph {
	protected List<Node> adjList;
	private PrintWriter writerCSV;
	private String outputDir;
	private int d;
	
	public Graph(int size, int d)
	{
		adjList = new ArrayList<>();
		initGraph(size, d);
		outputDir = "charts";
		this.d = d;
	}
	
	public abstract void initGraph(int size, int d);
	
	public void run(double w, double r, double piMax, int iterations)
	{
		createSaveFile(w, r);
		
		double velocity, mean;
		double[][] arrayStatus = new double[10][iterations];
		double[][] arrayVelocity = new double[10][iterations];
		
		for (int m = 0; m <10; m++)
		{
			for (Node node : adjList)
			{
				node.setInitParams();
			}
			
			for (int i = 0; i < iterations; i++)
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
				
				//System.out.println(mean());
				velocity = 0.0;
				mean = 0.0;
				
				for (Node node : adjList)
				{
					mean += node.g;
					velocity += node.velocity;
				}
				
				arrayStatus[m][i] = mean / adjList.size();
				arrayVelocity[m][i] = velocity / adjList.size();
			}
		}
		
		for (int i = 0; i < iterations; i++)
		{
			velocity = 0.0;
			mean = 0.0;

			for (int m = 0; m <10; m++)
			{
				mean += arrayStatus[m][i];
				velocity += arrayVelocity[m][i];
			}
			
			String status = i+";"+mean/10+";"+velocity/10;
			writerCSV.println(status);
		}
		writerCSV.close();
	}
	
/*	public double mean()
	{
		double mean = 0.0;
		for (Node node : adjList)
		{
			mean += node.g;
		}
		
		return mean / adjList.size();
	}
*/	
	private void createSaveFile(double w, double r)
	{
		String fileName = outputDir+"/simu-w"+w+"-r"+r+"-d"+d;
		File file = new File(fileName+".csv");
		
		int copia = 0;
		
		while (file.exists())
		{
			file = new File(fileName+"-("+copia+").csv");
			copia++;
		}
		
		try
		{
			writerCSV = new PrintWriter(file);
			writerCSV.println("iteration;g_mean;velocity_mean");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
/*	private void saveIteration(int i)
	{
		for (int m = 0; m <10; m++)
		{
			velocity += node.velocity;
		}
		String status = i+";"+mean()+";";
		double velocity = 0.0;
		
		velocity /= adjList.size();
		status = status + velocity;
		writerCSV.println(status);
	}
*/
}
