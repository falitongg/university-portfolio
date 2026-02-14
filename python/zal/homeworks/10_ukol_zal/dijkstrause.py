from dijkstra import *

#Test graph
vertexes = [
  Vertex(0, 'Redville'),
  Vertex(1, 'Blueville'),
  Vertex(2, 'Greenville'),
  Vertex(3, 'Orangeville'),
  Vertex(4, 'Purpleville')
]
edges = [
  Edge(0, 1, 5),
  Edge(0, 2, 10),
  Edge(0, 3, 8),
  Edge(1, 0, 5),
  Edge(1, 2, 3),
  Edge(1, 4, 7),
  Edge(2, 0, 10),
  Edge(2, 1, 3),
  Edge(3, 0, 8),
  Edge(3, 4, 2),
  Edge(4, 1, 7),
  Edge(4, 3, 2)
]
#New Dijkstra created
dijkstra = Dijkstra()
#Graph created
dijkstra.createGraph(vertexes,edges)
#Getting all vertexes
dijkstraVertexes = dijkstra.getVertexes()
# Computing min distance for each vertex in graph
for vertexToCompute in dijkstraVertexes:
    dijkstra.computePath(vertexToCompute.id)
    print('Printing min distance from vertex:'+str(vertexToCompute.name))
    #Print minDitance from current vertex to each other
    for vertex in dijkstraVertexes:
        print('Min distance to:'+str(vertex.name)+' is: '+str(vertex.minDistance))
    #Reset Dijkstra between counting
    # dijkstra.resetDijkstra()
# Distance with path
for vertexToCompute in dijkstraVertexes:
    dijkstra.computePath(vertexToCompute.id)
    print('Printing min distance from vertex:'+str(vertexToCompute.name))
    #Print minDitance and path from current vertex to each other
    for vertex in dijkstraVertexes:
        print('Min distance to:'+str(vertex.name)+' is: '+str(vertex.minDistance))
        print('Path is:',end=" ")
        #Get shortest path to target vertex
        path = dijkstra.getShortestPathTo(vertex.id)
        for vertexInPath in path:
            print(str(vertexInPath.name),end=" ")
        print()
    #Reset Dijkstra between counting
    dijkstra.resetDijkstra()
print(dijkstraVertexes)