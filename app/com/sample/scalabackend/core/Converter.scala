package com.sample.scalabackend.core

trait Converter[TIn, TOut]
{
  def convert(in :TIn): TOut
}
