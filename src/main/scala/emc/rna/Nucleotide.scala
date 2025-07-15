package emc
package rna

enum Nucleotide(val char: Char):
  case G extends Nucleotide('G')
  case A extends Nucleotide('A')
  case U extends Nucleotide('U')
  case C extends Nucleotide('C')

  override def toString: String =
    s"$char"

object Nucleotide:
  
  def fromChar(c: Char): Nucleotide =
    values.find(_.char == c).getOrElse(sys.error(s"Not a nucleotide char: $c"))

  val fromInt: Int => Nucleotide =
    Array(G, A, U, C)

  val toInt: Nucleotide => Int =
    Map(G -> 0, A -> 1, U -> 2, C -> 3)
