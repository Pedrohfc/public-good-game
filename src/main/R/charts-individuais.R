distances = c(2, 8, 32, 128)

plotResults <- function(w, r, d) {
  inputDirectory = "/home/pedro/workspace/public-good-game/resources"
  inputFile = sprintf("%s/simu-w%.2f-r%.1f-d%d.csv", inputDirectory, w, r, d)
  simulationData = read.csv(inputFile, header = TRUE, sep = ";")
  
  outputFile = sprintf("%s/chart-g-w%.2f-r%.1f-d%d.png", inputDirectory, w, r, d)
  title = sprintf("Nível de coperação com d=%d, r=%.1f e w=%.2f", d, r, w)
  
  png(outputFile, width = 1280, height = 720)
  plotGraphics(simulationData[, c("iteration", "g_mean")], title, "Nível de coperação", c(0, 1))
  dev.off()
  
  outputFile = sprintf("%s/chart-v-w%.2f-r%.1f-d%d.png", inputDirectory, w, r, d)
  title = sprintf("Velocidade média com d=%d, r=%.1f e w=%.2f", d, r, w)
  
  png(outputFile, width = 1280, height = 720)
  plotGraphics(simulationData[, c("iteration", "velocity_mean")], title, "Velocidade média", c(-0.5, 0.5))
  dev.off()
  
  return(simulationData)
}

plotGraphics <- function(data, title, yName, yRange) {
  plot(data, type="l", xlab="Tempo", ylab=yName, ylim=yRange, main=title)
}

for (d in distances) {
  plotResults(0.05, 2.2, d)
  plotResults(0.60, 2.6, d)
  plotResults(0.95, 4.8, d)
}

