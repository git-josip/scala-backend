package com.sample.scalabackend.core.messages

import com.sample.scalabackend.core.Asserts
import Asserts._
import play.api.libs.json.Json

case class MessageKey(value: String) {
  argumentIsNotNullNorEmpty(value)
}

object MessageKey {
  implicit val jsonWrites = Json.writes[MessageKey]
}
