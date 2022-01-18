/**
 * represents a vertex in a graph.
 */
class Vertex(content : Any = "default content")
{
	/**
	 * content of this vertex, with type currently set to Any.
	 * may be updated later with generic.
	 */
	var content : Any
	
	init
	{
		this.content = content
	}
	
	/**
	 * degree, number of times this vertex is used as an endpoint.
	 * uses private setter to avoid external abuse. should only be used in func $newNeighbour(Vertex)
	 * which should only be called within class Edge. Still needs update on this. TODO
	 */
	var degree : Int = 0
		private set
	
	/**
	 * the set of neighbours (i.e. adjacent vertices) for this vertex.
	 * uses private setter to avoid external abuse. should only be used in func $newNeighbour(Vertex)
	 * which should only be called within class Edge. Still needs update on this. TODO
	 */
	var neighbours : MutableSet<Vertex> = mutableSetOf()
		private set
	
	/**
	 * ONLY to be called by Edge.linkOf(Vertex , Vertex) when linking 2 vertices.
	 * TODO: should only be used within this class and in class Edge
	 */
	fun newNeighbour(other : Vertex)
	{
		this.neighbours += other
		this.degree++
	}
	
	/**
	 * states if this vertex is isolated.
	 */
	val isIsolated : Boolean
		get()
		{
			return this.degree == 0
		}
	
	/**
	 * determines if this vertex is adjacent to another vertex.
	 * if involved in self loop, itself is also a neighbour.
	 * A special case is a loop that connects a vertex to itself
	 * if such an edge exists, the vertex belongs to its own neighbourhood.
	 * ref: https://en.wikipedia.org/wiki/Neighbourhood_(graph_theory)
	 */
	fun isAdjacentTo(other : Vertex) : Boolean
	{
		return neighbours.contains(other)
	}
	
	/**
	 * compares contents of two vertices.
	 */
	private fun contentEquals(other : Vertex) : Boolean
	{
		return this.content == other.content
	}
	
	/**
	 * currently equals() compares if they are the same vertex by comparing memory addrs
	 * may be subject to change
	 */
	override fun equals(other : Any?) : Boolean
	{
		return this === other
	}
	
	override fun hashCode() : Int
	{
		return content.hashCode()
	}
	
	/**
	 * toString: returns content as string
	 * may be changed later
	 */
	override fun toString() : String
	{
		return this.content.toString()
	}
}