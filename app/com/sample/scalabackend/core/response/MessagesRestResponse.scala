package com.sample.scalabackend.core.response

import com.sample.scalabackend.core.Asserts
import play.api.libs.json.Json

case class MessagesRestResponse
(
  global    : Option[GlobalMessagesRestResponse]  = None,
  local     : List[LocalMessagesRestResponse]     = List.empty
)
{
  Asserts.argumentIsNotNull(global)
  Asserts.argumentIsNotNull(local)
}

object MessagesRestResponse
{
  implicit val jsonFormat = Json.format[MessagesRestResponse]
}
