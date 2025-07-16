package emc.rna

import org.scalatest.funspec.*
import org.scalatest.matchers.should.Matchers

class RibosomeSpecification extends AnyFunSpec with Matchers:

  import Nucleotide.*
  import AminoAcid.*

  // expected results at given reference frame
  val expected =
    Map(
      0 -> Seq(Seq(Methionine)),
      1 -> Seq(),
      2 -> Seq(Seq(Methionine, Methionine, Proline), Seq(Proline), Seq(Methionine, Histidine))
    )

  describe("A Ribosome"):

    it("decodes into a codon sequence relative to given reference frame"):
      (0 to 2).foreach: i =>
        Ribosome.decode(rna = fixture, rf = i) should be(expected(i))
