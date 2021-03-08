package com.github.lusingander.uuid

import java.util.UUID

object Formatter {
  def format(uuid: UUID, dash: Boolean, upper: Boolean): String =
    uuid.toString().formatWithDash(dash).formatWithUpper(upper)

  implicit class RichUUIDString(val self: String) {
    def formatWithDash(dash: Boolean): String =
      if (dash) self else self.replace("-", "")
    def formatWithUpper(upper: Boolean): String =
      if (upper) self.toUpperCase else self
  }
}
