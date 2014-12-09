package org.jpwh.shared;

import org.hibernate.dialect.MySQL5InnoDBDialect;

import java.sql.Types;

/**
 * @author Christian Bauer
 */
public class ImprovedMySQL56Dialect extends MySQL5InnoDBDialect {

    public ImprovedMySQL56Dialect() {
        super();
        // TODO https://hibernate.atlassian.net/browse/HHH-8401
        // http://dev.mysql.com/doc/refman/5.6/en/fractional-seconds.html
        registerColumnType( Types.TIMESTAMP, "datetime(6)" );
    }
}
