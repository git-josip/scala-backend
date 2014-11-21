package com.laplacian.scalabackendsampling.module.user.service.domain

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.beans.factory.annotation.Autowired
import com.laplacian.scalabackendsampling.module.user.dao.UserRepository
import com.laplacian.scalabackendsampling.core.{GeneratedId, Asserts}
import com.laplacian.scalabackendsampling.module.user.domain.{UserDetailsEntity, UserCreateEntity}

@Service
class UserDomainServiceImpl @Autowired
(
  private val entityRepository: UserRepository
) extends UserDomainService
{
  Asserts.argumentIsNotNull(entityRepository)

  override def create(item: UserCreateEntity): GeneratedId =
  {
    Asserts.argumentIsNotNull(item)

    this.entityRepository.insert(item)
  }

  override def tryGetById(id: Long): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsTrue(id > 0)

    this.entityRepository.findById(id)
  }

  override def tryGetByUsername(username: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNull(username)

    this.entityRepository.findByUsername(username)
  }

  override def tryGetByEmail(email: String): Option[UserDetailsEntity] =
  {
    Asserts.argumentIsNotNull(email)

    this.entityRepository.findByEmail(email)
  }

  override def getAll: List[UserDetailsEntity] =
  {
    this.entityRepository.findAll
  }
}
