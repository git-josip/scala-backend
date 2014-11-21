package com.laplacian.scalabackendsampling.module.user.domain

import com.laplacian.scalabackendsampling.core.Asserts
import com.laplacian.scalabackendsampling.core.messages.MessageKey
import play.api.libs.json.Json

case class UserCreateEntity
(
  id        : Option[Long],
  firstName : String,
  lastName  : String,
  username  : String,
  email     : String,
  password  : String
)
{
  Asserts.argumentIsNotNull(id)
  Asserts.argumentIsTrue(id.isEmpty, "create entity must have empty id")
  Asserts.argumentIsNotNull(firstName)
  Asserts.argumentIsNotNull(lastName)
  Asserts.argumentIsNotNull(username)
  Asserts.argumentIsNotNull(email)
  Asserts.argumentIsNotNull(password)
}

object UserCreateEntity
{
  implicit val jsonFormat = Json.format[UserCreateEntity]

  val FIRST_NAME_FORM_ID  = MessageKey("firstName")
  val LAST_NAME_FORM_ID   = MessageKey("lastName")
  val EMAIL_FORM_ID       = MessageKey("email")
  val USERNAME_FORM_ID    = MessageKey("username")
  val PASSWORD_FORM_ID    = MessageKey("password")
}
