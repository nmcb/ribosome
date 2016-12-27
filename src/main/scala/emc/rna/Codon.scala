package emc.rna

case class Codon(_1: Nucleotide, _2: Nucleotide, _3: Nucleotide) {
  val rna:RNA                        = RNA(_1, _2, _3)
  val length: Int                    = Codon.GROUP_SIZE
  val iterator: Iterator[Nucleotide] = rna.iterator

  lazy val aminoAcid = AminoAcid.fromCodon(this).get

  def isStart = Codon.Start.equals(this)
  def isStop = Codon.StopCodons.contains(this)
  def encodesAminoAcid = !isStop
}

object Codon {

  /**
   * The encoding group size of a codon in nucleotides.
   */
  val GROUP_SIZE = 3

  /**
   * Defines the start codon.
   */
  val Start = Codon(A, U, G)

  /**
   * Defines a stop codon extractor i.o.t. pattern match Amber, Occur and Opal, i.e. UAG, UGA and UAA.
   */
  object Stop {
    val Amber = Codon(U, A, G)
    val Occur = Codon(U, G, A)
    val Opal  = Codon(U, A, A)

    def apply(n1: Nucleotide, n2: Nucleotide, n3: Nucleotide): Codon = Codon(n1, n2, n3)

    def unapply(codon: Codon): Option[(Nucleotide, Nucleotide, Nucleotide)] = {
      if (codon.isStop) Some((codon._1, codon._2, codon._3)) else None
    }
  }

  import Stop._

  /**
   * Convenience definition for the set of stop codons.
   */
  val StopCodons: Set[Codon] = Set(Amber, Occur, Opal)

  /**
   * Convenience definition of the set of stop codon names.
   */
  val StopCodonNames: Codon => String = Map(Amber -> "Amber", Occur -> "Occur", Opal -> "Opal")

  /**
   * Constructs a codon from the first three nucleotides of given sequence.
   * @param rna The RNA sequence to construct a codon from.
   * @return A codon representation of the first three nucleotides of given sequence.
   * @throws IndexOutOfBoundsException If given sequence does not contain enough nucleotides to construct a codon.
   */
  def fromSeq(rna: Seq[Nucleotide]) = Codon(rna(0), rna(1), rna(2))
}










