package org.jpwh.shared;

import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/**
 * TODO: https://hibernate.onjira.com/browse/HHH-6670
 */
public class ImprovedH2Dialect extends H2Dialect {


    public ImprovedH2Dialect() {
        super();
        registerFunction( "lpad", new StandardSQLFunction( "lpad", StandardBasicTypes.STRING ) );
    }

    @Override
    public String getDropSequenceString(String sequenceName) {
        // Just adding the "if exists" clause to avoid warnings
        return "drop sequence if exists " + sequenceName;
    }

    @Override
    public boolean dropConstraints() {
        // We don't need to drop constraints before dropping tables, that just leads to error
        // messages about missing tables when we don't have a schema in the database
        return false;
    }

}
