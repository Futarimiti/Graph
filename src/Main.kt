fun main()
{
	val node1 : Vertex = Vertex(1)
	val node2 : Vertex = Vertex(2)
	
	println(node1)
	node1.content = 1
	println(node2)
	
	val edge12 : Edge = Edge.linkOf(node1 , node2)
	val edge21 : Edge = Edge.linkOf(node2 , node1)
	println(edge12)
	println(edge21)
	println(edge21 == edge12)
	println(edge21 === edge12)
	
	println("degree of node1: ${node1.degree}")
	println("degree of node2: ${node2.degree}")
	
	println("${node1.neighbours}")
}