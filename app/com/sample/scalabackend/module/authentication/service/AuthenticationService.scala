package com.sample.scalabackend.module.authentication.service

import com.sample.scalabackend.core.jwt.ResponseToken
import com.sample.scalabackend.module.user.domain.UserDetailsEntity

trait AuthenticationService
{
  def authenticate(username: String, password: String): Option[ResponseToken]
  def refreshToken(token : String) : Option[ResponseToken]
  def validateToken(token: String): Boolean
  def getUserFromToken(token : String) : UserDetailsEntity
}
