package com.github.lusingander.uuid

import scopt.OptionParser

case class Config(
    number: Int = 1,
    timeBased: Boolean = false,
    randomBased: Boolean = true
)

object Main extends App {
  val parser = new OptionParser[Config]("uuidutil") {
    opt[Int]('n', "number")
      .optional()
      .action((x, c) => c.copy(number = x))
      .text("number to generate")
    opt[Unit]('t', "time")
      .action((x, c) => c.copy(timeBased = true))
      .text("generate time based UUID")
    opt[Unit]('r', "random")
      .action((x, c) => c.copy(randomBased = true))
      .text("generate random based UUID (default)")
    help('h', "help")
      .text("print help")
  }

  parser.parse(args, Config()) match {
    case Some(config) =>
      Generator(config.timeBased).generate(config.number).foreach(println)
    case _ =>
      println("Failed to parse options")
  }
}
