package com.laplacian.scalabackendsampling.core

trait Converter[TIn, TOut]
{
  def convert(in :TIn): TOut
}
