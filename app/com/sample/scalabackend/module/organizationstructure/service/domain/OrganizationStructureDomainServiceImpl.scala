package com.sample.scalabackend.module.organizationstructure.service.domain

import com.sample.scalabackend.core.{GeneratedId, Asserts}
import com.sample.scalabackend.module.organizationstructure.dao.OrganizationStructureRepository
import com.sample.scalabackend.module.organizationstructure.domain.{OrganizationStructureLookupEntity, OrganizationStructureType, OrganizationStructureDetailsEntity, OrganizationStructureCreateEntity}
import com.sample.scalabackend.core.Asserts
import com.sample.scalabackend.module.organizationstructure.dao.OrganizationStructureRepository
import com.sample.scalabackend.module.organizationstructure.domain.{OrganizationStructureType, OrganizationStructureDetailsEntity, OrganizationStructureCreateEntity}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrganizationStructureDomainServiceImpl @Autowired
(
  private final val entityRepository: OrganizationStructureRepository
) extends OrganizationStructureDomainService
{
  Asserts.argumentIsNotNull(entityRepository)

  override def create(item: OrganizationStructureCreateEntity): GeneratedId =
  {
    Asserts.argumentIsNotNull(item)

    this.entityRepository.insert(item)
  }

  override def tryGetById(id: Long): Option[OrganizationStructureDetailsEntity] =
  {
    Asserts.argumentIsTrue(id > 0)

    this.entityRepository.findById(id)
  }

  override def tryGetByName(name: String): Option[OrganizationStructureDetailsEntity] =
  {
    Asserts.argumentIsNotNull(name)

    this.entityRepository.findByName(name)
  }

  override def getAll: List[OrganizationStructureDetailsEntity] =
  {
    this.entityRepository.findAll
  }

  override def getAllByType(entityType: OrganizationStructureType): List[OrganizationStructureDetailsEntity] =
  {
    Asserts.argumentIsNotNull(entityType)

    this.entityRepository.findAllByType(entityType)
  }

  override def getAllLookupByType(entityType: OrganizationStructureType): List[OrganizationStructureLookupEntity] =
  {
    Asserts.argumentIsNotNull(entityType)

    this.entityRepository.findAllLookupByType(entityType)
  }

  override def getAllByParent(parentId: Long): List[OrganizationStructureDetailsEntity] =
  {
    this.entityRepository.findAllByParent(parentId)
  }
}
