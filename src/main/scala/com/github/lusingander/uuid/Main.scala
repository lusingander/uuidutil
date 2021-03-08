package com.github.lusingander.uuid

import scopt.OParser
import scopt.Read

sealed trait Mode
case object Default extends Mode
case object GenerateMode extends Mode

case class Config(
    mode: Mode = Default,
    number: Int = 1,
    timeBased: Boolean = false,
    randomBased: Boolean = true,
    withDash: Boolean = false,
    withUpper: Boolean = false
)

object Main extends App {

  val builder = OParser.builder[Config]
  import builder._

  val uuidBaseParser = {
    OParser.sequence(
      opt[Unit]("dash")
        .action((_, c) => c.copy(withDash = true))
        .text("display UUID with dash"),
      opt[Unit]("upper")
        .action((_, c) => c.copy(withUpper = true))
        .text("display UUID with upper case")
    )
  }
  val uuidGenerateParser = {
    OParser.sequence(
      opt[Int]('n', "number")
        .optional()
        .action((x, c) => c.copy(number = x))
        .validateWith(_ >= 1, "number must not be less than 1")
        .text("number to generate"),
      opt[Unit]('t', "time")
        .action((_, c) => c.copy(timeBased = true))
        .text("generate time based UUID"),
      opt[Unit]('r', "random")
        .action((_, c) => c.copy(randomBased = true))
        .text("generate random based UUID (default)")
    )
  }

  val parser = {
    OParser.sequence(
      programName("uuidutil"),
      help('h', "help")
        .text("print help"),
      cmd("generate")
        .action((_, c) => c.copy(mode = GenerateMode))
        .children(uuidGenerateParser, uuidBaseParser)
    )
  }

  OParser.parse(parser, args, Config()) match {
    case Some(config) =>
      config.mode match {
        case Default =>
          println(OParser.usage(parser))
        case GenerateMode =>
          Generator(config.timeBased)
            .generate(config.number, config.withDash, config.withUpper)
            .foreach(println)
      }
    case _ =>
      println("Failed to parse options")
  }

  implicit class RichOParser[A: Read, C](val self: OParser[A, C]) {
    def validateWith(pred: A => Boolean, error: String): OParser[A, C] =
      self.validate(validator(pred, error))
  }

  private def validator[T](
      pred: T => Boolean,
      error: String
  ): T => Either[String, Unit] =
    v => if (pred(v)) Right(()) else Left(error)
}
