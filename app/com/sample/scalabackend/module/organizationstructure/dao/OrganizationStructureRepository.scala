package com.sample.scalabackend.module.organizationstructure.dao

import com.sample.scalabackend.module.organizationstructure.domain.OrganizationStructureCreateEntity
import com.sample.scalabackend.core.GeneratedId
import com.sample.scalabackend.module.organizationstructure.domain.{OrganizationStructureType, OrganizationStructureLookupEntity, OrganizationStructureDetailsEntity, OrganizationStructureCreateEntity}

trait OrganizationStructureRepository
{
  def insert(item: OrganizationStructureCreateEntity): GeneratedId

  def findById(id: Long): Option[OrganizationStructureDetailsEntity]
  def findByName(name: String): Option[OrganizationStructureDetailsEntity]

  def findAll: List[OrganizationStructureDetailsEntity]
  def findAllByType(entityType: OrganizationStructureType): List[OrganizationStructureDetailsEntity]
  def findAllLookupByType(entityType: OrganizationStructureType): List[OrganizationStructureLookupEntity]
  def findAllByParent(parentId: Long): List[OrganizationStructureDetailsEntity]
}
