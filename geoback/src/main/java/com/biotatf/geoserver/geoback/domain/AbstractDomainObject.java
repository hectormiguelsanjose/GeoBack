package com.biotatf.geoserver.geoback.domain;

import org.springframework.data.annotation.Id;

public abstract class AbstractDomainObject implements DomainObject{

    @Id
    private String id;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
