package com.sample.scalabackend.core

case class GeneratedId(id: Long)
{
  Asserts.argumentIsTrue(id > 0)
}
