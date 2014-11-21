package com.laplacian.scalabackendsampling.module.log.action.domain;

public enum ActionType
{
    CREATED("CREATED");

    private ActionType(String displayName)
    {
        this.displayName = displayName;
    }
    private final String displayName;

    public String displayName()
    {
        return this.displayName;
    }
}
