package com.github.lusingander.uuid

import java.util.UUID

import scala.util.Try
import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.combinator.Parsers

object Parser extends RegexParsers {
  private def hexDigit = "[0-9a-fA-F]".r
  private def repHex(n: Int) = repN(n, hexDigit) ^^ (_.mkString)
  private def withoutDash =
    repHex(8) ~ repHex(4) ~ repHex(4) ~ repHex(4) ~ repHex(12) ^^ {
      case (h1 ~ h2 ~ h3 ~ h4 ~ h5) => joinWithDash(h1, h2, h3, h4, h5)
    }
  private def withDash =
    "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}".r
  private def joinWithDash(args: String*) = args.mkString("-")
  private def uuid = withoutDash | withDash

  def parse(in: String): Option[UUID] = parseAll(uuid, in) match {
    case Success(result, next) => Some(UUID.fromString(result))
    case _                     => None
  }
}
