package com.laplacian.scalabackendsampling.module.user.service.domain

import com.laplacian.scalabackendsampling.module.user.domain.{UserDetailsEntity, UserCreateEntity}
import com.laplacian.scalabackendsampling.core.{Asserts, GeneratedId}

trait UserDomainService
{
  def create(item: UserCreateEntity): GeneratedId

  def tryGetById(id: Long): Option[UserDetailsEntity]
  def tryGetByUsername(username: String): Option[UserDetailsEntity]
  def tryGetByEmail(email: String): Option[UserDetailsEntity]
  def getAll: List[UserDetailsEntity]

  def getById(id: Long): UserDetailsEntity =
  {
    Asserts.argumentIsNotNull(id)

    this.tryGetById(id).getOrElse(throw new RuntimeException("user with this id does not exist"))
  }
  def getByUsername(username: String): UserDetailsEntity =
  {
    Asserts.argumentIsNotNull(username)

    this.tryGetByUsername(username).getOrElse(throw new RuntimeException("user with this username does not exist"))
  }

  def doesExistByUsername(userName: String): Boolean =
  {
    Asserts.argumentIsNotNull(userName)

    val itemCandidate = this.tryGetByUsername(userName)
    itemCandidate.isDefined
  }

  def doesExistByByEmail(email: String): Boolean =
  {
    Asserts.argumentIsNotNull(email)

    val itemCandidate = this.tryGetByEmail(email)
    itemCandidate.isDefined
  }
}
