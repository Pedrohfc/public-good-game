package each.complexos.network;

public class NearestNeighborNetwork extends Graph {
	
	/***
	 * Cria um grafo em formato de anel circular, com cada no
	 * sendo vizinhos dos d nos mais proximos
	 * @param size
	 * @param d
	 */
	public NearestNeighborNetwork(int size, int d)
	{
		super(size, d);
	}
	
	public void initGraph(int size, int d)
	{
		adjList.clear();
		
		// cria os nos
		while (size-- > 0)
		{
			adjList.add(new Node());
		}
		
		// cria os vizinhos
		for (int i = 0; i < adjList.size(); i++)
		{
			Node node = adjList.get(i);
			for (int j = 1; j <= d; j++)
			{
				int left = (i-j);
				if (left < 0) left += adjList.size();
				
				int right = (i+j) % adjList.size();
				
				node.addNeighbor(adjList.get(left));
				node.addNeighbor(adjList.get(right));
			}
		}
	}
}
