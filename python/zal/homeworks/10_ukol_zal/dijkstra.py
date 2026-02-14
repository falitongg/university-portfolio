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
        self.edges = []
    def add_edge(self, edge):
        self.edges.append(edge)
class PriorityQueue:
    def __init__(self):
        self.elements = []
    def push(self, priority, item):
        self.elements.append((priority, item))
    def pop(self):
        self.elements.sort(key=lambda x:x[0])
        return self.elements.pop(0)
    def is_empty(self):
        return len(self.elements) == 0

class Dijkstra:
    def __init__(self):
        self.vertexes = []
    def createGraph(self, vertexes, edgesToVertexes):
        self.vertexes = vertexes
        self.edgesToVertexes = edgesToVertexes
        for edge in edgesToVertexes:
            source_vertex = next(v for v in vertexes if v.id == edge.source)
            source_vertex.add_edge(edge)
    def getVertexes(self):
        return self.vertexes 
    def computePath(self, sourceId):
        source = next(v for v in self.vertexes if v.id == sourceId)
        source.minDistance = 0
        for vertex in self.vertexes:
            if sourceId != vertex.id:
                vertex.minDistance = float('inf')
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
    def getShortestPathTo(self, targetId):
        self.targetId = targetId
        path = []
        target = next(v for v in self.vertexes if v.id == targetId)
        while target is not None:
            path.insert(0, target)
            target = target.previousVertex
        return path
    def resetDijkstra(self):
        for vertex in self.vertexes:
            vertex.minDistance = float('inf')
            vertex.previousVertex = None
a = Dijkstra()
print(a)