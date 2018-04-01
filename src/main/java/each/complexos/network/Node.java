package each.complexos.network;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private List<Node> neighborhood;
	double g, gMax, payoff, payoffMax, velocity, r;
	
	public Node()
	{
		neighborhood = new ArrayList<>();
		setInitParams();
	}
	
	public void addNeighbor(Node neighbor)
	{
		neighborhood.add(neighbor);
	}
	
	public void setInitParams()
	{
		g = Math.random(); // Aposta inicial aleatoria
		gMax = g;
		payoffMax = -1.0 * Double.MAX_VALUE;
		velocity = 0;
		payoff = 0;
	}
	
	/***
	 * Modifica a estrategia, de acordo com as estrategias dos vizinhos
	 * @param val - somatorio dos vizinhos
	 * @param piMax
	 */
	public void addPayoff(double val, double piMax)
	{
		payoff += piMax - g + val;
	}
	
	public void calcPayoff(double r, double piMax)
	{
		// Somatorio das estrategias do vizinhos
		double payoffAux = r * g;
		
		for (Node neighbor : neighborhood)
		{
			payoffAux += r * neighbor.g;
		}
		
		payoffAux /= (neighborhood.size() + 1);
		
		// Jogo centralizado no proprio no - Adiciona payoff
		addPayoff(payoffAux, piMax);
		
		// Adiciona payoff para os vizinhos que participaram desse jogo
		for (Node neighbor : neighborhood)
		{
			neighbor.addPayoff(payoffAux, piMax);
		}
	}
	
	public void calcVelocity(double w)
	{
		// Guarda o maior payoff do proprio no
		if (payoff > payoffMax)
		{
			payoffMax = payoff;
			gMax = g;
		}
		
		// Procura o maior payoff entre os vizinhos nessa rodada
		double maxNeighborhoodPayoff = payoff;
		double maxNeighborhoodG = g;
		
		for (Node neighbor : neighborhood)
		{
			if (neighbor.payoff > maxNeighborhoodPayoff)
			{
				maxNeighborhoodPayoff = neighbor.payoff;
				maxNeighborhoodG = neighbor.g;
			}
		}
		
		// Calcula a velocidade de mudanca de estrategia
		velocity += w * (gMax - g) + (1.0 - w) * (maxNeighborhoodG - g);
	}
	
	/***
	 * Atualiza o valor de aposta do no
	 * @param piMax
	 */
	public void update(double piMax)
	{
		g += velocity;
		
		// Tratamento para valores fora dos limites
		if (g > piMax)
		{
			g = piMax;
		}
		else if (g < 0)
		{
			g = 0;
		}
	}
}
