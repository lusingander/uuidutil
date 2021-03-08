package com.github.lusingander.uuid

import scopt.OptionParser
import scopt.OptionDef
import scopt.Read

case class Config(
    number: Int = 1,
    timeBased: Boolean = false,
    randomBased: Boolean = true,
    withDash: Boolean = false,
    withUpper: Boolean = false
)

object Main extends App {
  val parser = new OptionParser[Config]("uuidutil") {
    opt[Int]('n', "number")
      .optional()
      .action((x, c) => c.copy(number = x))
      .validateWith(_ >= 1, "number must not be less than 1")
      .text("number to generate")
    opt[Unit]('t', "time")
      .action((x, c) => c.copy(timeBased = true))
      .text("generate time based UUID")
    opt[Unit]('r', "random")
      .action((x, c) => c.copy(randomBased = true))
      .text("generate random based UUID (default)")
    opt[Unit]("dash")
      .action((x, c) => c.copy(withDash = true))
      .text("display UUID with dash")
    opt[Unit]("upper")
      .action((x, c) => c.copy(withUpper = true))
      .text("display UUID with upper case")
    help('h', "help")
      .text("print help")
  }

  parser.parse(args, Config()) match {
    case Some(config) =>
      Generator(config.timeBased)
        .generate(config.number, config.withDash, config.withUpper)
        .foreach(println)
    case _ =>
      println("Failed to parse options")
  }

  implicit class RichOptionDef[A: Read, C](val self: OptionDef[A, C]) {
    def validateWith(pred: A => Boolean, error: String): OptionDef[A, C] =
      self.validate(validator(pred, error))
  }

  private def validator[T](
      pred: T => Boolean,
      error: String
  ): T => Either[String, Unit] =
    v => if (pred(v)) Right(()) else Left(error)
}
