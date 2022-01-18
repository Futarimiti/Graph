/**
 * represents a vertex in a graph.
 */
class Vertex(content : Any = "default content")
{
	/**
	 * content of this vertex, with type currently set to Any.
	 * may be changed later with generic.
	 */
	var content : Any
	
	init
	{
		this.content = content
	}
	
	/**
	 * degree: the number of times this vertex is used as an endpoint.
	 * degree should only be changed as
	 *
	 * set to val as should be immutable outside the
	 */
	val degree : Int
		get()
		{
			return degree0
		}
	
	/**
	 * mutable degree, to be used within func $newNeighbour(Vertex)
	 */
	private var degree0 : Int = 0
	
	/**
	 * to be called by Edge init when linking 2 vertices.
	 * previously moved to class Edge as private extension func to not be used elsewhere
	 * now moved back as problem solved
	 *
	 * TODO: should only be used within this class and in class Edge
	 */
	fun newNeighbour(other : Vertex)
	{
		this.neighbours0.add(other)
		this.degree0++
	}
	
	/**
	 * a set of adjacent vertices of this vertex. will include itself if involved in self loop
	 * ref: https://en.wikipedia.org/wiki/Neighbourhood_(graph_theory)
	 * read-only, returns $neighbours0 when accessed.
	 * the values should only be changed via $neighbours0 via func $newNeighbour(Vertex), as represented in get()
	 * designed so on purpose to avoid abuse.
	 */
	val neighbours : Set<Vertex>
		get()
		{
			return this.neighbours0
		}
	
	/**
	 * the set of neighbours for this vertex.
	 * different from $neighbours, this set is mutable as designed to be updated
	 * everytime new edges has been constructed.
	 * set to private to avoid external abuse. should only be used in func $newNeighbour(Vertex)
	 * which should only be used within class Edge.
	 */
	private var neighbours0 : MutableSet<Vertex> = mutableSetOf()
	
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
		return neighbours0.contains(other)
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
