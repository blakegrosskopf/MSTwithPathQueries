# MSTwithPathQueries
Optimizing an Energy Network with MST and Dynamic Pipe Queries

This Java program, developed as part of a college algorithms class, tackles the challenge of designing a cost-effective energy network for the fictional region of Timbuk 4. The goal is to connect all cities with pipes while minimizing maintenance costs—and then adapt the network efficiently when new pipes become available.

# What It Does
Builds the Cheapest Network: Constructs an optimal energy network using a Minimum Spanning Tree (MST) to ensure all cities are connected at the lowest possible cost.
Evaluates New Pipes: Answers queries about whether adding a new pipe can reduce costs by replacing a more expensive one, keeping the network fully connected.

# Key Features
Kruskal’s Algorithm: Computes the MST by sorting pipes by cost and connecting cities without forming cycles, using a Union-Find data structure for efficiency.
Binary Lifting for Path Queries: Quickly identifies the most expensive pipe on the path between two cities in the MST, enabling fast decisions about new pipe additions.
Scalable Design: Efficiently handles large networks with a time complexity of O(E log E + N log N + M log N), where E is the number of pipes, N is the number of cities, and M is the number of queries.

# How It Works
Initial Setup: Takes a list of possible pipes (edges) with their maintenance costs and builds the MST to connect all cities.
Dynamic Updates: For each new pipe query, it checks if the new pipe’s cost is lower than the most expensive pipe on the current MST path between the two cities it connects. If so, it suggests an update to reduce the total cost.
This project demonstrates practical applications of graph theory and dynamic query handling, blending classic algorithms with modern optimization techniques. Perfect for anyone interested in network design, algorithmic efficiency, or real-world problem-solving!
