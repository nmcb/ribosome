package emc.rna

import org.scalatest.FunSpec
import org.scalatest.Matchers._

class RibosomeSpecification extends FunSpec {

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

  describe("A Ribosome") {

    it("decodes into a codon sequence relative to given reference frame") {
      (0 to 2).foreach({ i =>
        Ribosome.decode(fixture, i) should be(expected(i))
      })
    }
  }
}
