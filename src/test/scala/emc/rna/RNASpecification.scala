package emc.rna

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class RNASpecification extends FunSpec {

  val fixture = RNA(C, C, C, C, C) ++ // semi-random prefix
  Codon.Start.rna ++                  // [A,U,G]
  RNA(A, U, G, C, C, C) ++            // semi-random post-start junk rna
  Codon.Stop.Amber.rna ++             // known amber codon, also a stop codon
  RNA(C, C, C) ++                     // semi-random junk rna
  Codon.Stop.Opal.rna ++              // known opal codon, also a stop codon
  Codon.Start.rna ++                  // start again,...
  RNA(C, A, U, G)                     // but include [A,U,G], after just one nucleotide

  // expected results at given reference frame
  val expected = Map(
    0 -> Seq(Seq(Methionine)),
    1 -> Seq(),
    2 -> Seq(Seq(Methionine, Methionine, Proline), Seq(Proline), Seq(Methionine, Histidine))
  )

  describe("An RNA sequence") {

    it("concatenates, i.e. (++)") {
      fixture should be(
        RNA(C, C, C, C, C, A, U, G, A, U, G, C, C, C, U, A, G, C, C, C, U, A, A, A, U, G, C, A, U, G)
      )
    }

    it("know its codons relative to given reference frame") {
      fixture.codons(0).toSeq should be(Seq(
        Codon(C, C, C),
        Codon(C, C, A),
        Codon(U, G, A),
        Codon(U, G, C),
        Codon(C, C, U),
        Codon(A, G, C),
        Codon(C, C, U),
        Codon(A, A, A),
        Codon(U, G, C),
        Codon(A, U, G))
      )
      fixture.codons(1).toSeq should be(Seq(
        Codon(C, C, C),
        Codon(C, A, U),
        Codon(G, A, U),
        Codon(G, C, C),
        Codon(C, U, A),
        Codon(G, C, C),
        Codon(C, U, A),
        Codon(A, A, U),
        Codon(G, C, A))
      )
      fixture.codons(2).toSeq should be(Seq(
        Codon(C, C, C),
        Codon(A, U, G),
        Codon(A, U, G),
        Codon(C, C, C),
        Codon(U, A, G),
        Codon(C, C, C),
        Codon(U, A, A),
        Codon(A, U, G),
        Codon(C, A, U))
      )
    }
  }
}
