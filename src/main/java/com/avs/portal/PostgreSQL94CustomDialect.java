package com.avs.portal;

import org.hibernate.dialect.PostgreSQL94Dialect;

import com.vladmihalcea.hibernate.type.array.UUIDArrayType;

public class PostgreSQL94CustomDialect extends PostgreSQL94Dialect {

    public PostgreSQL94CustomDialect() {
        super();
        this.registerHibernateType(2003, UUIDArrayType.class.getName());
    }

}
