package emc.rna

import org.scalatest.funspec.*
import org.scalatest.matchers.should.*

class InterpolationSpecification extends AnyFunSpec with Matchers:

  import Nucleotide.*

  describe("String interpolation"):

    it("interpolates valid nucleotide strings"):
      rna"AUGC" should be( RNA(A, U, G, C))
