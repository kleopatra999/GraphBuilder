# GraphBuilder
This program doesn't have any real-life application. Here you can find how math expressions can be parsed and evaluated:

1. I make an expression tokenizable with regex, then split it.
2. Convert it to rpn using Shunting-yard algorithm.
3. Build an expression tree.
4. Convert this function to Function<Double, Double> java object.

To find some application for this - we then plot graph of obtained function and (using derivative) find equation of tangent line (it is plotted too). Don't ask me why Swing is chosen fo UI. 

