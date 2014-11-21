package com.laplacian.scalabackendsampling.core.response

import com.laplacian.scalabackendsampling.core.Asserts
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
