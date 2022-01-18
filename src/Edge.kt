/**
 * represents a non-directional edge incidenting on two vertices in a graph.
 * self-loop and parallel edges are allowed.
 */
class Edge
private constructor(endPoint1 : Vertex , endPoint2 : Vertex)
{
	/**
	 * two end points of this edge.
	 */
	var endPoint1 : Vertex
	var endPoint2 : Vertex
	
	companion object
	{
		fun linkOf(endPoint1 : Vertex , endPoint2 : Vertex) : Edge
		{
			return Edge(endPoint1 , endPoint2)
		}
	}
	
	init
	{
		endPoint1.newNeighbour(endPoint2)
		endPoint2.newNeighbour(endPoint1)
		
		this.endPoint1 = endPoint1
		this.endPoint2 = endPoint2
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
	fun isParallelTo(other : Edge) : Boolean
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