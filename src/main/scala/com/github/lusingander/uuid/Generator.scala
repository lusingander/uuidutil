package com.github.lusingander.uuid

import java.util.UUID
import com.fasterxml.uuid.Generators
import com.fasterxml.uuid.NoArgGenerator

class Generator(private val generator: NoArgGenerator) {
  def generate(number: Int): Seq[UUID] =
    (1 to number).map(_ => generator.generate())
}

object Generator {
  def apply() = new Generator(Generators.timeBasedGenerator())
}
