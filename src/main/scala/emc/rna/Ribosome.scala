package emc.rna

import collection.mutable.ListBuffer

object Ribosome {

  type PeptideChain = Seq[AminoAcid]

  /**
   * Decodes an RNA sequence into a sequences of peptide bounded amino acid molecules.
   * @param rna The RNA sequence
   * @param rf The reading frame
   * @return A sequence containing the amino acid molecules encoded by the RNA sequence.
   */
  def decode(rna: RNA, rf: Int = 0): Seq[PeptideChain] = {

    var started: Boolean = false
    var sequence: ListBuffer[PeptideChain] = ListBuffer.empty
    var chain: ListBuffer[AminoAcid] = ListBuffer.empty

    for (codon <- rna.codons(rf)) codon match {
      case Codon.Start         => {
        chain += codon.aminoAcid
        started = true
      }
      case Codon.Stop(_, _, _) => {
        if (started) {
          sequence += chain.toSeq
          chain = ListBuffer.empty
        }
      }
      case codon: Codon        => {
        if (started) {
          chain += codon.aminoAcid
        }
      }
    }

    if (!chain.isEmpty) sequence += chain.toSeq
    sequence.toSeq
  }
}




