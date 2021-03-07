package com.github.lusingander.uuid

import java.util.UUID
import com.fasterxml.uuid.Generators
import com.fasterxml.uuid.NoArgGenerator

trait Generator {
  def generator: NoArgGenerator
  def generate(number: Int): Seq[UUID] =
    (1 to number).map(_ => generator.generate())
}

class TimeBasedGenerator() extends Generator {
  override def generator: NoArgGenerator = Generators.timeBasedGenerator()
}

class RandomBasedGenerator() extends Generator {
  override def generator: NoArgGenerator = Generators.randomBasedGenerator()
}

object Generator {
  def apply(timeBased: Boolean) =
    if (timeBased) new TimeBasedGenerator else new RandomBasedGenerator
}
