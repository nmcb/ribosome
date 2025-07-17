package emc
package rna

import org.scalatest.funspec.*
import org.scalatest.matchers.should.*

val fixture: RNA =
  rna"CCCCC" ++             // semi-random prefix
  Codon.Start.asRNA ++      // [A,U,G]
  rna"AUGCCC" ++            // semi-random post-start junk rna
  Codon.Stop.Amber.asRNA ++ // known amber codon, also a stop codon
  rna"CCC" ++               // semi-random junk rna
  Codon.Stop.Opal.asRNA ++  // known opal codon, also a stop codon
  Codon.Start.asRNA ++      // start again,...
  rna"CAUG"                 // but include [A,U,G], after just one nucleotide

class RNASpecification extends AnyFunSpec with Matchers:

  import Nucleotide.*
  import AminoAcid.*

  describe("An RNA sequence"):

    it("know its codons relative to given reference frame"):
      fixture.codons(0).toSeq should be(Seq(
        Codon(C,C,C),
        Codon(C,C,A),
        Codon(U,G,A),
        Codon(U,G,C),
        Codon(C,C,U),
        Codon(A,G,C),
        Codon(C,C,U),
        Codon(A,A,A),
        Codon(U,G,C),
        Codon(A,U,G))
      )
      fixture.codons(1).toSeq should be(Seq(
        Codon(C,C,C),
        Codon(C,A,U),
        Codon(G,A,U),
        Codon(G,C,C),
        Codon(C,U,A),
        Codon(G,C,C),
        Codon(C,U,A),
        Codon(A,A,U),
        Codon(G,C,A))
      )
      fixture.codons(2).toSeq should be(Seq(
        Codon(C,C,C),
        Codon(A,U,G),
        Codon(A,U,G),
        Codon(C,C,C),
        Codon(U,A,G),
        Codon(C,C,C),
        Codon(U,A,A),
        Codon(A,U,G),
        Codon(C,A,U))
      )
