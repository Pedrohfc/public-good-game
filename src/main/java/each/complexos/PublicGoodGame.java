package each.complexos;

import java.util.Random;

public class PublicGoodGame
{
    int populationSize;
    double piMax;
    int d;
    double r ,w;
    int iterations;
    double[] g;
    double[] gMax;
    double[] velocity;
    double[] payoff;
    double[] payoffMax;
    
    public PublicGoodGame()
    {
        populationSize = 10000;
        piMax = 1.0;
        r = 2.6;
        w = 0.6;
        d = 1000;
        iterations = 800;
        g = new double[populationSize];
        gMax = new double[populationSize];
        payoff = new double[populationSize];
        payoffMax = new double[populationSize];
        velocity = new double[populationSize];
        
        Random random = new Random();
        for (int i = 0; i < populationSize; i++)
        {
            g[i] = random.nextDouble();
            gMax[i] = g[i];
        }
    }
    
    private int getIndex(int range)
    {
        if (range < 0)
        {
            range += populationSize;
        }
        return range % populationSize;
    }
    
    public void calcPayoff()
    {
        int range = (2 * d + 1);
        double[] payoffAux = new double[populationSize];
        for (int i = 0; i < populationSize; i++)
        {
            double sumRange = 0;
            for (int neighbor = i-d; neighbor <= i+d; neighbor++)
            {
                sumRange += r * g[getIndex(neighbor)];
            }
            payoffAux[i] = sumRange/range;
        }
        
        for (int i = 0; i < populationSize; i++)
        {
            double sumRange = 0;
            for (int neighbor = i-d; neighbor <= i+d; neighbor++)
            {
                sumRange += (piMax - g[i]) + payoffAux[getIndex(neighbor)];
            }
            
            if (sumRange > payoffMax[i])
            {
                payoffMax[i] = sumRange;
                gMax[i] = g[i];
            }
            payoff[i] = sumRange;
        }
    }
    
    public void calcVelocity()
    {
        double mean = 0.0;
        for (int i = 0; i < populationSize; i++)
        {
            velocity[i] = velocity[i] + w * (gMax[i] - g[i]) + (1-w) * (maxNeighbor(i)-g[i]);
            mean += velocity[i];
        }
        //System.out.println(mean/populationSize);
    }
    
    public double maxNeighbor(int index)
    {
        int indexMax = index;
        for (int i = index-d; i <= index+d; i++)
        {
            int k = getIndex(i);
            if (payoff[k] > payoff[indexMax])
            {
                indexMax = k;
            }
        }
        return g[indexMax];
    }
    
    public void update()
    {
        calcPayoff();
        calcVelocity();
        for (int i = 0; i < populationSize; i++)
        {
            g[i] += velocity[i];
            if (g[i] > piMax)
            {
                g[i] = piMax;
            }
            else if (g[i] < 0)
            {
                g[i] = 0;
            }
        }
        System.out.println(getGMean());
    }
    
    public double getGMean()
    {
        double sum = 0.0;
        for (double x : g)
        {
            sum += x;
        }
        
        return sum / populationSize;
    }
    
    public void run()
    {
        for (int i = 0; i < iterations; i++)
        {
            update();
        }
        System.out.println("nivel de coperacao: "+getGMean());
    }
}
