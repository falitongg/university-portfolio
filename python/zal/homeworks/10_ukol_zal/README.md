# Task 10 - Dijkstra

Your tenth task is to implement Dijkstra's algorithm and use it to find the shortest paths in a graph (Shortest Path Problem). The graph will be given to you using structures (vertices and edges), where the edges are positively weighted and oriented. The graph may contain cycles. 

A class representing an edge in a graph. This class has the following attributes:

    source - numerical identifier of the vertex where the edge begins (integer type)
    target - numerical identifier of the vertex where the edge ends (integer type)
    weight - weight of the edge (integer type)

The class must have a method __init__(self, source, target, weight) which is called when the class is created and accepts the ID of the source vertex (source), the ID of the target vertex (target), and the weight of the edge (weight). Setting other parameters (if you need them) is up to you. You can add their settings to the __init__() method, or you can use the createGraph() method to initialize the graph.


Vertex

A class representing a vertex in a graph. This class has the following attributes:

id - numerical identifier of the vertex (integer type)
name - name of the vertex (character string)
    edges - edges that start at this vertex (list of type Edge)
    minDistance - minimum distance from which this vertex can be reached from the vertex over which the computePath() function was performed (integer type)
    previousVertex - previous vertex - the vertex through which the path to this vertex leads for the minimum path (Vertex type)

The class must have a method __init__(self, id, name), i.e., a constructor that sets the vertex's id and name. Setting other parameters (if you need them) is up to you.

Dijkstra

A class representing Dijkstra's algorithm. This class has only one variable

    vertexes - a list of graph vertices on which we want to perform Dijkstra's algorithm

The class has the following methods:

    createGraph(self, vertexes, edgesToVertexes) - creates a graph from the specified vertices. Vertexes is a list of objects of type Vertex and edgesToVertexes is a list of objects of type Edge.
    getVertexes(self) - the method returns the vertices on which Dijkstra's algorithm can be performed.
    computePath(self, sourceId) - the method finds the shortest paths from the vertex with sourceId to all vertices in the graph. The method does not return anything, but after the operation is complete, all vertices should have the minDistance variable filled in, which represents the minimum distance to the given vertex.
    getShortestPathTo(self, targetId) - this method returns a list of vertices through which the path from the vertex sourceId - over which the computePath() operation was run - leads to the vertex specified in targetId.
    resetDijkstra(self) - this method resets the current results after passing through Dijkstra's algorithm. The method does not disconnect or discard the graph, it only returns it to the state it was in before the computePath() operation.

The sourceId and targetId variables are numeric. It is therefore necessary to save the specified graph and then find the vertices based on their ID.

Dijkstra's algorithm assumes that the distance of vertices it has not processed is infinite. In Python, please represent it as follows: float('inf').
