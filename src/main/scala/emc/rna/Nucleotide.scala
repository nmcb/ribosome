package emc
package rna

enum Nucleotide:
  case G, A, U, C

object Nucleotide:
  
  def fromChar(c: Char): Nucleotide =
    c match
      case 'G' => G
      case 'A' => A
      case 'U' => U
      case 'C' => C
      case _   => sys.error(s"Not a nucleotide char: $c")

  val fromInt: Int => Nucleotide =
    Array(G, A, U, C)

  val toInt: Nucleotide => Int =
    Map(G -> 0, A -> 1, U -> 2, C -> 3)
