package com.laplacian.scalabackendsampling.module.organizationstructure.domain

import com.laplacian.scalabackendsampling.core.Asserts
import com.laplacian.scalabackendsampling.core.json.customfomatters.CustomFormatter.Enum
import play.api.libs.json.Json

case class OrganizationStructureLookupEntity
(
  id          : Long,
  name        : String,
  entityType  : OrganizationStructureType
)
{
  Asserts.argumentIsNotNull(id)
  Asserts.argumentIsNotNull(name)
  Asserts.argumentIsNotNull(entityType)
}

object OrganizationStructureLookupEntity
{
  implicit val organizationStructureTypeWrites= Enum.enumWritesByName[OrganizationStructureType]
  implicit val organizationStructureTypeReads = Enum.enumReadsByName[OrganizationStructureType]

  implicit val jsonFormat = Json.format[OrganizationStructureLookupEntity]
}
