package emc.rna

import org.scalatest.funspec.*
import org.scalatest.matchers.should.*

class InterpolationSpecification extends AnyFunSpec with Matchers:

  import Nucleotide.*

  val rna        = RNA(C, C, C)
  val codon      = Codon(C, C, C)
  val nucleotide = C

  describe("String interpolation"):

    it("interpolates valid nucleotide strings"):
      rna"AUGC" should be( RNA(A, U, G, C))

    it("injects valid rna sequences"):
      rna"AUGC$rna" should be(RNA(A, U, G, C, C, C, C))

    it("injects valid codons"):
      rna"AUGC$codon" should be(RNA(A, U, G, C, C, C, C))

    it("injects valid nucleotides"):
      rna"AUGC$nucleotide" should be(RNA(A, U, G, C, C))
