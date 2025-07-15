package emc.rna

import org.scalatest.funspec.*
import org.scalatest.matchers.should.Matchers

class RibosomeSpecification extends AnyFunSpec with Matchers:

  import Nucleotide.*
  import AminoAcid.*

  val fixture: RNA =
    rna"CCCCC" ++             // semi-random prefix
    Codon.Start.asRNA ++      // [A,U,G]
    rna"AUGCCC" ++            // semi-random post-start junk rna
    Codon.Stop.Amber.asRNA ++ // known amber codon, also a stop codon
    rna"CCC" ++               // semi-random junk rna
    Codon.Stop.Opal.asRNA ++  // known opal codon, also a stop codon
    Codon.Start.asRNA ++      // start again,...
    rna"CAUG"                 // but include [A,U,G], after just one nucleotide

  // expected results at given reference frame
  val expected =
    Map(
      0 -> Seq(Seq(Methionine)),
      1 -> Seq(),
      2 -> Seq(Seq(Methionine, Methionine, Proline), Seq(Proline), Seq(Methionine, Histidine))
    )

  describe("A Ribosome"):

    it("decodes into a codon sequence relative to given reference frame"):
      (0 to 2).foreach(i => Ribosome.decode(fixture, i) should be(expected(i)))
