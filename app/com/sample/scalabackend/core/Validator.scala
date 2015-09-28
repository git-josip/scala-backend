package com.sample.scalabackend.core

import com.sample.scalabackend.core.messages.Messages
import com.sample.scalabackend.core.response.ResponseTools
import play.api.libs.json.Writes

trait Validator[T]
{
  def validate(item:T) : ValidationResult[T]
}

case class ValidationResult[T: Writes]
(
  validatedItem : T,
  messages      : Messages
)
{
  Asserts.argumentIsNotNull(validatedItem)
  Asserts.argumentIsNotNull(messages)

  def isValid :Boolean =
  {
    !messages.hasErrors
  }

  def errorsRestResponse =
  {
    Asserts.argumentIsTrue(!this.isValid)

    ResponseTools.of(
      data      = Some(this.validatedItem),
      messages  = Some(messages)
    )
  }
}
