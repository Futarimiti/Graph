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
	private infix fun friends(other : Vertex)
	{
		this.neighbours += other
		other.neighbours += this
		this.degree++
		other.degree++
	}
	
	/**
	 * ONLY to be called by Edge.linkOf(Vertex , Vertex) when linking 2 vertices
	 * when used two vertices are guaranteed to be linked previously
	 */
	private infix fun unfriends(other : Vertex)
	{
		this.neighbours -= other
		other.neighbours -= this
		this.degree--
		other.degree--
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
			 * while constructors are privatised, use this func for instantiation.
			 */
			@JvmStatic
			fun linkOf(endPoint1 : Vertex , endPoint2 : Vertex) : Edge
			{
				endPoint1 friends endPoint2
				return Edge(endPoint1 , endPoint2)
			}
			
			/**
			 * unlinks two vertices.
			 * @return true if two vertices are unlinked, false if two vertices were not linked
			 * @throws NoSuchElementException when two vertices are adjacent but one with degree 0 (unexpected)
			 */
			fun unlink(endPoint1 : Vertex , endPoint2 : Vertex) : Boolean
			{
				if (endPoint1 isAdjacentTo endPoint2)
				{    // if passed, ep1 contains ep2 and vice versa
					
					if (endPoint1.degree > 0 && endPoint2.degree > 0) endPoint1 unfriends endPoint2
					else throw NoSuchElementException("Unexpected: Two vertices are adjacent but one with degree 0")
					
					return true
				}
				return false
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
	 * determines if this vertex is adjacent to another vertex by checking their $neighbours.
	 */
	infix fun isAdjacentTo(other : Vertex) : Boolean
	{
		if (other in this.neighbours)
		{
			assert(this in other.neighbours) {"FATAL: A neighbours B but B does not neighbour A"}
			return true
		} else if (this in other.neighbours) throw AssertionError("FATAL: A neighbours B but B does not neighbour A")
		
		return false
	}
	
	/**
	 * compares contents of two vertices.
	 */
	infix fun contentEquals(other : Vertex) : Boolean
	{
		return this.content == other.content
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