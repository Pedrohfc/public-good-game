install.packages("quantmod")
install.packages("tseries")
library(quantmod)
library(tseries)

distances = c(2, 8, 32, 128)
colors = c("black", "blue", "green", "red")

plotResults <- function(w, r) {
  inputDirectory = "/home/pedro/workspace/public-good-game/charts"
  outputFile = sprintf("%s/chart-v-w%.2f-r%.1f.png", inputDirectory, w, r)
  png(outputFile, width = 1280, height = 720)
  
  
  inputFile = sprintf("%s/simu-w%.2f-r%.1f-d2.csv", inputDirectory, w, r, 2)
  title = sprintf("Velocidade média com w = %.2f e r = %.2f", w, r)
  data = read.csv(inputFile, header = TRUE, sep = ";")
  
  plot(data$velocity_mean, type="l", xlab="Tempo", ylab="Velocidade média", ylim=c(-0.5,0.5), main=title)
  
  for (i in 2:length(distances)) {
    inputFile = sprintf("%s/simu-w%.2f-r%.1f-d%d.csv", inputDirectory, w, r, distances[i])
    simulationData = read.csv(inputFile, header = TRUE, sep = ";")
    
    lines(simulationData$velocity_mean, lwd=0.5, col=colors[i])
    
    legend("topright", legend = distances, col=colors,lty=1, title="Raio de vizinhança")
  }
  
  dev.off()
}

plotResults(0.05, 2.2)
plotResults(0.95, 4.8)
plotResults(0.6, 2.6)
