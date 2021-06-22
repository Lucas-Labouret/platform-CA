package compiler


import dataStruc.{Dag, DagNode}
import org.scalatest.{BeforeAndAfter, FunSuite}
import prog.{CycleLayer2}
class  DagTest  extends FunSuite  with BeforeAndAfter{
  
 /** A node of a DAG for testing cycles implemented as a Bag of neighbors
 *  @constructor create a node with neighbors and a name
 *  @param neighbors  passed by name for delaying evaluation
 *  @param name for printing purpose
 */
   class Node(  neighbors : => List[Node],val name:String) extends DagNode[Node]    {
     def inputNeighbors: List[Node] = neighbors

   override def toString: String = name

   def this(name: String) = this(List.empty, name)

   def this(name: String, e: => Node) = this(List(e), name)
 }

  val n1 = new Node("n1");
  val n2 = new Node("n2");
  val n3 = new Node(List(n1, n2), "n3")
  test("GetGreater") {
    val (_, s) = DagNode.getGreater(List(n3)); assert(s === Set(n1, n2, n3))
  }
  val n4 = new Node("n4", n5) //list n5 sera évalué plus tard
  val n4bis: Node = new Node("n4bis", n4);
  lazy val n5 = new Node(List(n1, n4bis), "n5")
  val n6 = new Node("n6", n4bis)
  test("GetcycleDagSimple") {
    assert(Dag.getCycle2(n4) match { case Some(c) => println("there is a cycle: " + c); true; case None => false })
  }

  val testCycle: Circuit[V, SI] = new Circuit[V, SI]() {
    val chip = new CycleLayer2(3);

    def computeRoot: ASTLt[V, SI] = chip.next
  }

  test("GetcycleDagAst") {
    try {
      testCycle.compile2(Circuit.hexagon)
      assert(false)
    } //if no exeption is raised, test fails.
    catch {
      case e@(_: RuntimeException | _: java.io.IOException) => println(e)
    }
  }

}

