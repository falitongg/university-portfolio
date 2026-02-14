class Edge:
    def __init__(self, source, target, weight):
        self.source = source
        self.target = target
        self.weight = weight

class Vertex:
    def __init__(self, id, name):
        self.id = id
        self.name = name
        self.minDistance = float('inf')
        self.previousVertex = None

class PriorityQueue:
    def __init__(self):
        self.elements = []

    def push(self, priority, item):
        self.elements.append((priority, item))

    def pop(self):
        self.elements.sort(key=lambda x: x[0])
        return self.elements.pop(0)

    def is_empty(self):
        return len(self.elements) == 0

class Dijkstra:
    def __init__(self):
        self.vertexes = []
        self.edgesToVertexes = []

    def createGraph(self, vertexes, edgesToVertexes):
        self.vertexes = vertexes
        self.edgesToVertexes = edgesToVertexes

    def getVertexes(self):
        return self.vertexes

    def computePath(self, sourceId):
        source = next(v for v in self.vertexes if v.id == sourceId)
        source.minDistance = 0

        queue = PriorityQueue()
        queue.push(0, source)

        while not queue.is_empty():
            current_vertex = queue.pop()[1]

            for edge in self.edgesToVertexes:
                if edge.source == current_vertex.id:
                    neighbor = next(v for v in self.vertexes if v.id == edge.target)
                    new_distance = current_vertex.minDistance + edge.weight
                    if new_distance < neighbor.minDistance:
                        neighbor.minDistance = new_distance
                        neighbor.previousVertex = current_vertex
                        queue.push(new_distance, neighbor)

    def resetDijkstra(self):
        for vertex in self.vertexes:
            vertex.minDistance = float('inf')
            vertex.previousVertex = None

    def getShortestPathTo(self, targetId):
        path = []
        target = next(v for v in self.vertexes if v.id == targetId)
        while target is not None:
            path.insert(0, target)
            target = target.previousVertex
        return path

# Test graph
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
    Edge(1, 2, 3),
    Edge(1, 4, 7),
    Edge(3, 4, 2)
]

# New Dijkstra created
dijkstra = Dijkstra()
# Graph created
dijkstra.createGraph(vertexes, edges)

# Computing distances
for vertexToCompute in vertexes:
    dijkstra.computePath(vertexToCompute.id)
    print(f"Distances from {vertexToCompute.name}:")
    for vertex in vertexes:
        print(f"  To {vertex.name}: {vertex.minDistance}")
    dijkstra.resetDijkstra()

# Paths
for vertexToCompute in vertexes:
    dijkstra.computePath(vertexToCompute.id)
    print(f"Paths from {vertexToCompute.name}:")
    for vertex in vertexes:
        print(f"  To {vertex.name}: {[v.name for v in dijkstra.getShortestPathTo(vertex.id)]}")
    dijkstra.resetDijkstra()
