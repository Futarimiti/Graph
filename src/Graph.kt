/**
 * represents a graph as an integration of edges and vertices.
 */
class Graph(edges : Array<Edge> = arrayOf() , vertices : Array<Vertex> = arrayOf())
{
	var edges : Array<Edge>
	var vertices : Array<Vertex>
	
	init
	{
		this.edges = edges
		this.vertices = vertices
	}
	
	val isNullGraph : Boolean
		get()
		{
			return this.edges.isEmpty()
		}
}