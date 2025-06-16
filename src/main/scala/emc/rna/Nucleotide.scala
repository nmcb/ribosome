package emc
package rna

enum Nucleotide:
  case G, A, U, C

object Nucleotide:
  
  import Nucleotide.*

  val fromInt: Int => Nucleotide =
    Array(G, A, U, C)

  val toInt: Nucleotide => Int =
    Map(G -> 0, A -> 1, U -> 2, C -> 3)
