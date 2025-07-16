package emc
package rna

object Ribosome:

  type PeptideChain = Seq[AminoAcid]

  /**
   * Decodes an RNA sequence into a sequences of peptide bounded amino acid molecules.
   * @param rna The RNA sequence
   * @param rf The reference frame
   * @return A sequence containing the amino acid molecules encoded by the RNA sequence.
   */
  def decode(rna: RNA, rf: Int = 0): Seq[PeptideChain] =

    import collection.mutable.ListBuffer

    // input parameter rna is an indexed seq, i.e. immutable
    var started: Boolean = false
    val sequence: ListBuffer[PeptideChain] = ListBuffer.empty
    var chain: ListBuffer[AminoAcid] = ListBuffer.empty

    for
      codon <- rna.codons(rf)
    do
      codon match
        case Codon.Start =>
          chain += codon.aminoAcid
          started = true
        case Codon.Stop(_, _, _) =>
          if started then
            sequence += chain.toSeq
            chain = ListBuffer.empty
        case codon: Codon =>
          if started then
            chain += codon.aminoAcid

    if chain.nonEmpty then sequence += chain.toSeq
    sequence.toSeq
