#!/bin/bash

# Compile and run Main.java
echo "Compiling Main.java..."
javac -cp lib/jeromq-0.6.0.jar src/Main.java
echo "Running Main..."
java -cp lib/jeromq-0.6.0.jar src/Main.java

# Compile and run Process1.java
echo "Compiling Process1.java..."
javac -cp lib/jeromq-0.6.0.jar src/Process1.java
echo "Running Process1..."
java -cp lib/jeromq-0.6.0.jar src.Process1.java

# Compile and run Process2.java
echo "Compiling Process2.java..."
javac -cp lib/jeromq-0.6.0.jar src/Process2.java
echo "Running Process2..."
java -cp lib/jeromq-0.6.0.jar src.Process2.java

# Compile and run Process3.java
echo "Compiling Process3.java..."
javac -cp lib/jeromq-0.6.0.jar src/Process3.java
echo "Running Process3..."
java -cp lib/jeromq-0.6.0.jar src.Process3.java

# Compile and run Process4.java
echo "Compiling Process4.java..."
javac -cp lib/jeromq-0.6.0.jar src/Process4.java
echo "Running Process4..."
java -cp lib/jeromq-0.6.0.jar src.Process4.java

# Compile and run Process5.java
echo "Compiling Process5.java..."
javac -cp lib/jeromq-0.6.0.jar src/Process5.java
echo "Running Process5..."
java -cp lib/jeromq-0.6.0.jar src.Process5.java
