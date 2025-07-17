package emc
package rna

import org.scalatest._
import funspec.*
import matchers.should.*

class SequenceConversionSpecification extends AnyFunSpec with Matchers:

  import Nucleotide.*

  val base: RNA =
    RNA(G,U,A,C)

  val expected: RNA =
    RNA(G,U,A,C,G,U,A,C)

  import scala.collection._

  describe("An RNA collection"):

    // immutable

    it("is automatically converted from an immutable.List"):
      base ++ immutable.List(G,U,A,C) should be(expected)

    it("is automatically converted from an immutable.Vector"):
      base ++ immutable.Vector(G,U,A,C) should be(expected)

    it("is automatically converted from an immutable.Seq"):
      base ++ immutable.Seq(G,U,A,C) should be(expected)

    it("is automatically converted from an immutable.ArraySeq"):
      base ++ immutable.ArraySeq(G,U,A,C) should be(expected)

    it("is automatically converted from an immutable.IndexedSeq"):
      base ++ immutable.IndexedSeq(G,U,A,C) should be(expected)

    it("is automatically converted from an immutable.Queue"):
      base ++ immutable.Queue(G,U,A,C) should be(expected)


    // mutable

    it("is automatically converted from a mutable.Buffer"):
      base ++ mutable.Buffer(G,U,A,C) should be(expected)

    it("is automatically converted from a mutable.ListBuffer"):
      base ++ mutable.ListBuffer(G,U,A,C) should be(expected)

    it("is automatically converted from a mutable.Stack"):
      base ++ mutable.Stack(G,U,A,C) should be(expected)

    it("is automatically converted from a mutable.IndexedBuffer"):
      base ++ mutable.IndexedBuffer(G,U,A,C) should be(expected)

    it("is automatically converted from a mutable.ArrayDeque"):
      base ++ mutable.ArrayDeque(G,U,A,C) should be(expected)

    it("is automatically converted from a mutable.Queue"):
      base ++ mutable.Queue(G,U,A,C) should be(expected)
