package com.laplacian.scalabackendsampling.module.organizationstructure.dao.sql.mapper

import com.laplacian.scalabackendsampling.module.organizationstructure.domain.{OrganizationStructureDetailsEntity, OrganizationStructureType}
import scala.slick.driver.PostgresDriver
import scala.slick.driver.PostgresDriver.simple._

object OrganizationStructureMapper
{
  private final val TABLE_NAME = "organization_structure"

  private final val ID_COLUMN                 = "id"
  private final val NAME_COLUMN               = "name"
  private final val PARENT_ID_COLUMN          = "parent_id"
  private final val TREE_PATH_COLUMN          = "tree_path"
  private final val ENTITY_TYPE_ID_COLUMN     = "entity_type_id"
  private final val DESCRIPTION_COLUMN        = "description"
  private final val SHORT_DESCRIPTION_COLUMN  = "short_description"

  implicit final val organizationStructureType: ColumnType[OrganizationStructureType] = {
    PostgresDriver.MappedColumnType.base[OrganizationStructureType, Long](
      { ost => ost.id                               },
      { dv  => OrganizationStructureType.getById(dv)}
    )
  }

  class OrganizationStructureDetailsTableDescriptor(tag: Tag) extends Table[OrganizationStructureDetailsEntity](tag, TABLE_NAME)
  {
    def id                = column[Long]                      (ID_COLUMN,                 O.PrimaryKey, O.AutoInc )
    def name              = column[String]                    (NAME_COLUMN,               O.NotNull               )
    def entityType        = column[OrganizationStructureType] (ENTITY_TYPE_ID_COLUMN,     O.NotNull               )
    def parentId          = column[Option[Long]]              (PARENT_ID_COLUMN,          O.Nullable              )
    def treePath          = column[String]                    (TREE_PATH_COLUMN,          O.Nullable              )
    def description       = column[String]                    (DESCRIPTION_COLUMN,        O.NotNull               )
    def shortDescription  = column[String]                    (SHORT_DESCRIPTION_COLUMN,  O.NotNull               )

    def * = (id, name, entityType, parentId, treePath, description, shortDescription)  <>
      ((OrganizationStructureDetailsEntity.apply _).tupled, OrganizationStructureDetailsEntity.unapply)
  }
  object OrganizationStructureDetailsTableDescriptor
  {
    def query = TableQuery[OrganizationStructureDetailsTableDescriptor]
  }
}
