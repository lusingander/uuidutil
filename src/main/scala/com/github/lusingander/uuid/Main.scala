package com.github.lusingander.uuid

import scopt.OptionParser

case class Config(
    number: Int = 1
)

object Main extends App {
  val parser = new OptionParser[Config]("uuidutil") {
    opt[Int]('n', "number")
      .optional()
      .action((x, c) => c.copy(number = x))
      .text("number to generate")

    help('h', "help")
      .text("print help")
  }

  parser.parse(args, Config()) match {
    case Some(config) =>
      Generator().generate(config.number).foreach(println)
    case _ =>
      println("Failed to parse options")
  }
}
