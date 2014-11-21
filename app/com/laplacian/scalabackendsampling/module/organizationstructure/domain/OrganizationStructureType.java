package com.laplacian.scalabackendsampling.module.organizationstructure.domain;

import com.laplacian.scalabackendsampling.core.Asserts;
import scala.Option;

public enum OrganizationStructureType
{
    COUNTRY (1L,    Option.<OrganizationStructureType>apply(null)),
    REGION  (2L,    Option.apply(OrganizationStructureType.COUNTRY)),
    RIVIERA (3L,    Option.apply(OrganizationStructureType.REGION)),
    CITY    (4L,    Option.apply(OrganizationStructureType.RIVIERA));

    private OrganizationStructureType(Long id, Option<OrganizationStructureType> parentType)
    {
        this.id = id;
        this.parentType = parentType;
    }
    private final Long id;
    private final Option<OrganizationStructureType> parentType;

    public Long id()
    {
        return this.id;
    }

    public Option<OrganizationStructureType> parentType()
    {
        return this.parentType;
    }

    public boolean isCountry()
    {
        return this == OrganizationStructureType.COUNTRY;
    }

    public boolean isRegion()
    {
        return this == OrganizationStructureType.REGION;
    }

    public boolean isRiviera()
    {
        return this == OrganizationStructureType.RIVIERA;
    }

    public boolean isCity()
    {
        return this == OrganizationStructureType.CITY;
    }

    public static OrganizationStructureType getById(Long id)
    {
        Asserts.argumentIsNotNull(id);
        Asserts.argumentIsTrue(id > 0L);

        for(OrganizationStructureType item: OrganizationStructureType.values())
        {
            if(item.id().equals(id)) { return item; }
        }

        throw new RuntimeException("OrganizationStructureType with id: " + id + " doesn't exists.");
    }
}
