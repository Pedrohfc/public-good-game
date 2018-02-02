if [ ! -d "bin" ]; then
	mkdir bin
fi
echo "Compiling files"
javac -cp src/main/java -d bin/ src/main/java/each/complexos/*.java src/main/java/each/complexos/**/*.java
echo "Compiled"
cd 