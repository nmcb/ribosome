package emc
package rna

import org.scalatest.funspec._
import org.scalatest.matchers.should._

class AminoAcidSpecification extends AnyFunSpec with Matchers:

  val fixture = RNA(C, A, A, G, G, G, C, U, U, U, C, C, C)
  val xs      = Seq(C, A, A, G, G, G, C, U, U, U, C, C, C)

  describe("An amino acid") {

    it("requires to be decoded from known codon sequences, including rna sequences") {
      AminoAcid.fromCodon(Codon.fromSeq(xs))      should be(Some(Glutamine))
      AminoAcid.fromCodon(Codon.fromSeq(fixture)) should be(Some(Glutamine))
    }

    it("requires to decoded from codon corner cases") {
      AminoAcid.fromCodon(Codon.Start)      should be (Some(Methionine))
      AminoAcid.fromCodon(Codon.Stop.Amber) should be(None)
      AminoAcid.fromCodon(Codon.Stop.Occur) should be(None)
      AminoAcid.fromCodon(Codon.Stop.Opal)  should be(None)
    }
  }
