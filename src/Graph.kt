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
	
	/**
	 * determines if a graph is a cycle graph:
	 * a graph with n vertices and n edges
	 */
	val isCycleGraph : Boolean
		get()
		{
			return this.edges.size == this.vertices.size
		}
}