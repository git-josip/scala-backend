package com.laplacian.scalabackendsampling.module.authentication.service

import com.laplacian.scalabackendsampling.core.jwt.ResponseToken
import com.laplacian.scalabackendsampling.module.user.domain.UserDetailsEntity

trait AuthenticationService
{
  def authenticate(username: String, password: String): Option[ResponseToken]
  def refreshToken(token : String) : Option[ResponseToken]
  def validateToken(token: String): Boolean
  def getUserFromToken(token : String) : UserDetailsEntity
}
