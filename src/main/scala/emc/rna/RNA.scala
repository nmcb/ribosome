package emc
package rna

import collection._

/**
 * A ribonucleic acid sequence, e.g. an RNA sequence of nucleotide molecules.
 */
final class RNA private(val slots: Array[Int], val length: Int)
  extends immutable.IndexedSeq[Nucleotide]
  with    immutable.IndexedSeqOps[Nucleotide, immutable.IndexedSeq, RNA]
  with    immutable.StrictOptimizedSeqOps[Nucleotide, immutable.IndexedSeq, RNA]:
    rna =>

    import RNA._

    /** Returns the nucleotide at given index */
    def apply(index: Int): Nucleotide =
      if index < 0 || length <= index then sys.error("illegal state")
      Nucleotide.fromInt(slots(index / N) >> (index % N * S) & M)

    /** Returns an RNA sequence from given nucleotides. */
    override protected def fromSpecific(nucleotides: IterableOnce[Nucleotide]): RNA =
      RNA.fromSpecific(nucleotides)

    /** Returns an RNA sequence builder from given specific nucleotides. */
    override protected def newSpecificBuilder: mutable.Builder[Nucleotide, RNA] =
      RNA.newBuilder

    /** Returns an empty RNA sequence. */
    override def empty: RNA =
      RNA.empty

    // Overloads returning RNA

    @inline
    final def concat(nucleotides: IterableOnce[Nucleotide]): RNA =
      strictOptimizedConcat(nucleotides, newSpecificBuilder)

    @inline
    final def ++(nucleotides: IterableOnce[Nucleotide]): RNA =
      concat(nucleotides)

    @inline
    final def appended(nucleotide: Nucleotide): RNA =
      (newSpecificBuilder ++= this += nucleotide).result()

    @inline
    final def appendedAll(nucleotides: Iterable[Nucleotide]): RNA =
      strictOptimizedConcat(nucleotides, newSpecificBuilder)

    @inline
    final def prepended(nucleotide: Nucleotide): RNA =
      (newSpecificBuilder += nucleotide ++= this).result()

    @inline
    final def prependedAll(nucleotides: Iterable[Nucleotide]): RNA =
      (newSpecificBuilder ++= nucleotides ++= this).result()

    @inline
    final def map(f: Nucleotide => Nucleotide): RNA =
      strictOptimizedMap(newSpecificBuilder, f)

    @inline
    final def flatMap(f: Nucleotide => IterableOnce[Nucleotide]): RNA =
      strictOptimizedFlatMap(newSpecificBuilder, f)

    /**
     * Reimplementation of iterator, making it more efficient in space.
     * We can mitigate against indirection  (necessary for iteration, i.e. in the
     * default implementation)  by utilizing the indexing property of an emc.rna.RNA
     * sequence since we know the length of the sequence and the group size N in
     * terms of emc.rna.Nucleotide symbols.
     */
    @inline
    override def iterator: Iterator[Nucleotide] =
      new AbstractIterator[Nucleotide]:
        var i = 0
        var b = 0

        def hasNext: Boolean =
          i < rna.length

        def next(): Nucleotide =
          b = if i % N == 0 then slots(i / N) else b >>> S
          i += 1
          Nucleotide.fromInt(b & M)


    /** Returns a codon iterator for this sequence starting at given reading frame,
     * which will drop `rf` nucleotides first, RNA library writers may as conjugated
     * with RNA application writers may prefer to read `codons` as `skip`.
     *
     * @return A codon iterator for this RNA sequence.
     */
    @inline
    def codons(rf: Int): Iterator[Codon] =
      for
        group <- drop(rf).grouped(Codon.GroupSize)
        if group.size == Codon.GroupSize
      yield
        Codon.fromSeq(group)

    @inline
    def skip(rf: Int): Iterator[Codon] =
      codons(rf)


object RNA extends SpecificIterableFactory[Nucleotide, RNA]:

  /** Defines the number of bits in a nucleotide slot, i.e the number of bit's needed to encode one nucleotide. */
  private val S = 2                // Note : Nucleotides Specific - your mileage may vary ;)

  /** Defines the bitmask to isolate the least significant slot, i.e. cuts of a nucleotide from an RNA sequence. */
  private val M = (1 << S) - 1     // Note : Nucleotides and JVM Specific - your mileage may vary ;)

  /** Defines the number of slots in an Int, i.e. the number of nucleotides that fit in an integer of 32 bits. */
  private val N = 32 / S           // Note : Nucleotides and JVM Specific - your mileage may vary ;)

  /** Creates an RNA sequence from given scala collection sequence of nucleotides.
    * @param nucleotides The sequence of nucleotides.
    * @return The RNA sequence from given sequence.
    */
  def fromSeq(nucleotides: Seq[Nucleotide]): RNA =
    val slots = new Array[Int]((nucleotides.length + N - 1) / N)
    for i <- nucleotides.indices do slots(i / N) |= Nucleotide.toInt(nucleotides(i)) << (i % N * S)
    new RNA(slots, nucleotides.length)

  /** Creates an empty RNA sequence.
    * @return The empty RNA sequence.
    */
  val empty: RNA =
    fromSeq(Seq.empty)

  /** Creates a mutable RNA sequence builder.
   * @return The mutable RNA sequence builder.
   */
  def newBuilder: mutable.Builder[Nucleotide, RNA] =
    mutable.ArrayBuffer.newBuilder[Nucleotide].mapResult(fromSeq)

    /** Creates an RNA sequence from given iterable nucleotides.
      * @param nucleotides The iterable nucleotides.
      * @return The RNA sequence from given nucleotides.
      */
  def fromSpecific(nucleotides: IterableOnce[Nucleotide]): RNA =
    nucleotides match
      case sequence: Seq[Nucleotide] => fromSeq(sequence)
      case _                         => fromSeq(mutable.ArrayBuffer.from(nucleotides))
