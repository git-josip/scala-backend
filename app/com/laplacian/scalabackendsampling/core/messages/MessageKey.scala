package com.laplacian.scalabackendsampling.core.messages

import com.laplacian.scalabackendsampling.core.Asserts._
import play.api.libs.json.Json

case class MessageKey(value: String)
{
  argumentIsNotNullNorEmpty(value)
}

object MessageKey
{
  implicit val jsonWrites = Json.writes[MessageKey]
}
