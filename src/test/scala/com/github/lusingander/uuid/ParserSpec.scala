package com.github.lusingander.uuid

import java.util.UUID

import org.scalatest.FunSuite
import org.scalatest.OptionValues._

class ParserSpec extends FunSuite {

  val expected = Some(UUID.fromString("1274ec6f-4590-4507-b4ec-6f459015078c"))

  test("lower with dash") {
    val actual = Parser.parse("1274ec6f-4590-4507-b4ec-6f459015078c")
    assert(actual == expected)
  }

  test("upper with dash") {
    val actual = Parser.parse("1274EC6F-4590-4507-B4EC-6F459015078C")
    assert(actual == expected)
  }

  test("lower without dash") {
    val actual = Parser.parse("1274ec6f45904507b4ec6f459015078c")
    assert(actual == expected)
  }

  test("upper without dash") {
    val actual = Parser.parse("1274EC6F45904507B4EC6F459015078C")
    assert(actual == expected)
  }

  test("mixed with dash") {
    val actual = Parser.parse("1274eC6f-4590-4507-B4ec-6F459015078c")
    assert(actual == expected)
  }

  test("only some dashes exist") {
    val actual = Parser.parse("1274ec6f-45904507b4ec-6f459015078c")
    assert(actual == expected)
  }

  test("empty") {
    val actual = Parser.parse("")
    assert(actual == None)
  }

  test("contains non-hex digit") {
    val actual = Parser.parse("1274ec6f-4590-4507-b4ec-6f459015078z")
    assert(actual == None)
  }

  test("too long") {
    val actual = Parser.parse("1274ec6f-4590-4507-b4ec-6f459015078c000")
    assert(actual == None)
  }

  test("too short") {
    val actual = Parser.parse("1274ec6f-4590-4507-b4ec-6f459015078")
    assert(actual == None)
  }

  test("dashes are replaced by hex digit") {
    val actual = Parser.parse("1274ec6f-459004507fb4ec-6f459015078c")
    assert(actual == None)
  }
}
