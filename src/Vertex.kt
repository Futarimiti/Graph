/**
 * represents a vertex in a graph.
 * TODO: generify
 */
class Vertex(var content : Any)
{
	/**
	 * degree, number of times this vertex is used as an endpoint.
	 * uses private setter to avoid external abuse. should only be used in func $newNeighbour(Vertex)
	 * which should only be called within class Edge.
	 */
	var degree : Int = 0
		private set
	
	/**
	 * the set of neighbours (i.e. adjacent vertices) for this vertex.
	 * uses private setter to avoid external abuse. should only be used in func $newNeighbour(Vertex)
	 * which should only be called within class Edge.
	 */
	var neighbours : MutableSet<Vertex> = mutableSetOf()
		private set
	
	/**
	 * ONLY to be called by Edge.linkOf(Vertex , Vertex) when linking 2 vertices
	 */
	private fun newNeighbour(other : Vertex)
	{
		this.neighbours += other
		this.degree++
	}
	
	/**
	 * represents a non-directional edge incidenting on two vertices in a graph.
	 * self-loop and parallel edges are allowed.
	 * nested to guarantee the ONLY access to func $newNeighbour.
	 */
	class Edge
	/** instantiate with two end points of this edge. */
	private constructor(val endPoint1 : Vertex , val endPoint2 : Vertex)
	{
		companion object
		{
			/**
			 * use this func to instantiate Edge.
			 * constructors should not be accessible.
			 */
			@JvmStatic
			fun linkOf(endPoint1 : Vertex , endPoint2 : Vertex) : Edge
			{
				endPoint1.newNeighbour(endPoint2)
				endPoint2.newNeighbour(endPoint1)
				
				return Edge(endPoint1 , endPoint2)
			}
		}
		
		/**
		 * determines if this edge is a self loop on a vertex.
		 */
		val isSelfLoop : Boolean
			get()
			{
				return endPoint1 == endPoint2
			}
		
		override fun equals(other : Any?) : Boolean
		{
			if (this === other) return true
			if (javaClass != other!!.javaClass) return false
			
			return this.equals0(other as Edge)
		}
		
		override fun hashCode() : Int
		{
			var result = endPoint1.hashCode()
			result = 31 * result + endPoint2.hashCode()
			return result
		}
		
		/**
		 * determines if this edge is parallel to another edge
		 * (having two identical endpoints)
		 */
		infix fun isParallelTo(other : Edge) : Boolean
		{
			if (this === other) return false // not the same edge
			return this.equals0(other)
		}
		
		/**
		 * compares self with an Edge obj
		 */
		private fun equals0(other : Edge) : Boolean
		{        // compare addr as vertex content may be repeating
			if (this.endPoint1 === other.endPoint1) return this.endPoint2 === other.endPoint2
			else if (this.endPoint1 === other.endPoint2) return this.endPoint2 === other.endPoint1
			
			return false
		}
		
		override fun toString() : String
		{
			return "Edge($endPoint1 - $endPoint2)"
		}
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
	infix fun isAdjacentTo(other : Vertex) : Boolean
	{
		return neighbours.contains(other)
	}
	
	/**
	 * compares contents of two vertices.
	 */
	infix fun contentEquals(other : Vertex) : Boolean
	{
		return this.content == other.content
	}
	
	/**
	 * currently equals() compares if they are the same vertex by comparing memory addrs
	 * may be subject to change
	 */
	override fun equals(other : Any?) : Boolean
	{
		return this === other // null safe
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