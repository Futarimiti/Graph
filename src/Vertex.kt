@file:Suppress("unused")

/**
 * represents a vertex in a graph.
 */
class Vertex<VertexType>(var content : VertexType)
{
	/**
	 * degree, number of times this vertex is used as an endpoint.
	 * uses private setter to avoid external abuse. should only be used in func $friends(Vertex)
	 * which should only be called within class Edge.
	 */
	var degree : Int = 0
		private set
	
	/**
	 * the set of neighbours (i.e. adjacent vertices) for this vertex.
	 * uses private setter to avoid external abuse. should only be used in func $friends(Vertex)
	 * which should only be called within class Edge.
	 */
	val neighbours : MutableSet<Vertex<VertexType>> = mutableSetOf()
	
	/**
	 * ONLY to be called by Edge.linkOf(Vertex , Vertex) when linking 2 vertices
	 */
	private infix fun friends(other : Vertex<VertexType>)
	{
		this.neighbours += other
		other.neighbours += this
		this.degree++
		other.degree++
	}
	
	/**
	 * ONLY to be called by Graph.unlink(Vertex , Vertex) when linking 2 vertices
	 * when used two vertices are guaranteed to be linked previously
	 */
	private infix fun unfriends(other : Vertex<VertexType>)
	{
		this.neighbours -= other
		other.neighbours -= this
		this.degree--
		other.degree--
	}
	
	/**
	 * represents a non-directional edge incidenting on two vertices in a graph.
	 * self-loop and parallel edges are allowed.
	 * nested to guarantee the ONLY access to func $friends and $unfriends.
	 */
	@Suppress("RemoveExplicitTypeArguments")
	class Edge<EdgeType>
	/** instantiate with two end points of this edge. */
	private constructor(val endPoint1 : Vertex<EdgeType> , val endPoint2 : Vertex<EdgeType>)
	{
		companion object
		{
			/**
			 * while constructors are privatised, use this func for instantiation.
			 */
			@JvmStatic
			fun <TLinkOf> linkOf(endPoint1 : Vertex<TLinkOf> , endPoint2 : Vertex<TLinkOf>) : Edge<TLinkOf>
			{
				endPoint1 friends endPoint2
				return Edge<TLinkOf>(endPoint1 , endPoint2)
			}
		}
		
		/**
		 * represents a graph as an integration of edges and vertices.
		 */
		class Graph<GraphType>(var edges : MutableList<Edge<GraphType>> = mutableListOf() ,
		                       var vertices : MutableList<Vertex<GraphType>> = mutableListOf())
		{
			val isNullGraph : Boolean
				get()
				{
					return this.edges.isEmpty()
				}
			
			/**
			 * unlinks two vertices in a graph by appointing two vertices.
			 * @return true if successfully unlinked, false if two vertices were not linked or do not exist in this graph
			 * @throws NoSuchElementException when unexpected behaviour occurs.
			 */
			fun unlink(endPoint1 : Vertex<GraphType> , endPoint2 : Vertex<GraphType>) : Boolean
			{
				if (endPoint1 !in this.vertices || endPoint2 !in this.vertices) return false
				if (!(endPoint1 isAdjacentTo endPoint2)) return false
				
				// if passed, ep1 contains ep2 and vice versa
				
				if (endPoint1.degree > 0 && endPoint2.degree > 0)
				{
					endPoint1 unfriends endPoint2
					this.edges -= Edge(endPoint1 , endPoint2)
				} else throw NoSuchElementException("Unexpected: Two vertices are adjacent but one with degree 0")
				
				return true
			}
			
			/**
			 * unlinks an edge in a graph by appointing an edge.
			 * @return true if the edge is successfully unlinked, false if this edge is not found
			 */
			fun unlink(e : Edge<GraphType>) : Boolean
			{
				if (e !in this.edges) return false
				
				this.edges -= e
				return true
			}
			
			override fun toString() : String
			{
				return "Graph(edges=$edges, vertices=$vertices)"
			}
		}
		
		/**
		 * determines if this edge is a self loop on a vertex.
		 */
		val isSelfLoop : Boolean
			get()
			{
				return endPoint1 === endPoint2
			}
		
		/**
		 * if two edges have the same endpoints, they are identical
		 */
		override fun equals(other : Any?) : Boolean
		{
			if (this === other) return true
			if (this.javaClass != other!!.javaClass) return false
			
			return equals0(other as Edge<*>)
		}
		
		override fun hashCode() : Int
		{
			return endPoint1.hashCode() + endPoint2.hashCode()
		}
		
		/**
		 * determines if this edge is parallel to another edge
		 * (having two identical endpoints)
		 */
		infix fun isParallelTo(other : Edge<EdgeType>) : Boolean
		{
			if (this === other) return false // not the same edge
			return this.equals0(other)
		}
		
		/**
		 * compares self with an Edge obj to check if both have the same endpoints
		 * I'd like to force it compares to Edge obj of this generic, but I cannot check generic
		 */
		private fun equals0(other : Edge<*>) : Boolean
		{        // compare addr as vertex content may be repeating
			return (this.endPoint1 === other.endPoint1 && this.endPoint2 === other.endPoint2) || (this.endPoint1 === other.endPoint2 && this.endPoint2 === other.endPoint1)
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
	infix fun isAdjacentTo(other : Vertex<VertexType>) : Boolean
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
	infix fun contentEquals(other : Vertex<VertexType>) : Boolean
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
