package com.github.lusingander.uuid

import java.util.UUID

import scala.util.Try
import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.combinator.Parsers

object Parser extends RegexParsers {
  private def hexDigit = "[0-9a-fA-F]".r

  private def repHex(n: Int) = repN(n, hexDigit) ^^ (_.mkString)

  private def hex8 = repHex(8)
  private def hex4 = repHex(4)
  private def hex12 = repHex(12)

  private def withoutDash =
    hex8 ~ hex4 ~ hex4 ~ hex4 ~ hex12 ^^ { case (h1 ~ h2 ~ h3 ~ h4 ~ h5) =>
      joinWithDash(h1, h2, h3, h4, h5)
    }

  private def dash = "-"
  private def withDash =
    hex8 ~ dash ~ hex4 ~ dash ~ hex4 ~ dash ~ hex4 ~ dash ~ hex12 ^^ {
      case ((h1 ~ _ ~ h2 ~ _ ~ h3 ~ _ ~ h4 ~ _ ~ h5)) =>
        joinWithDash(h1, h2, h3, h4, h5)
    }

  private def joinWithDash(args: String*) = args.mkString("-")

  private def uuid = withoutDash | withDash

  def parse(in: String): Option[UUID] = parseAll(uuid, in) match {
    case Success(result, next) => Some(UUID.fromString(result))
    case _                     => None
  }
}
