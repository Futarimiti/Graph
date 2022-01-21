import Vertex.Edge

/**
 * represents a graph as an integration of edges and vertices.
 */
class Graph(var edges : Array<Edge> = arrayOf() , var vertices : Array<Vertex> = arrayOf())
{
	val isNullGraph : Boolean
		get()
		{
			return this.edges.isEmpty()
		}
}