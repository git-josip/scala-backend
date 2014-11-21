package com.laplacian.scalabackendsampling.module.organizationstructure.dao

import com.laplacian.scalabackendsampling.core.GeneratedId
import com.laplacian.scalabackendsampling.module.organizationstructure.domain.{OrganizationStructureLookupEntity, OrganizationStructureType, OrganizationStructureDetailsEntity, OrganizationStructureCreateEntity}

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
