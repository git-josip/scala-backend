package com.laplacian.scalabackendsampling.core

case class GeneratedId(id: Long)
{
  Asserts.argumentIsTrue(id > 0)
}
