package com.navint.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import com.navint.jooq.model.pms.tables.records.RoleRecord;

//import com.navint.jooq.model.library.tables.records.AuthorRecord;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.navint.jooq.model.pms.Tables.*;

/**
 * 
 * Test code run query from jooq
 * 
 * @author Rajeev Krishna Singh
 *
 */
public class JOOQApplication {
    public static void main(String[] args) throws Exception {
        String user = System.getProperty("jdbc.user");
        String password = System.getProperty("jdbc.password");
        String url = System.getProperty("jdbc.url");
        String driver = System.getProperty("jdbc.driver");

        Class.forName(driver).newInstance();
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            DSLContext dslContext = DSL.using(connection, SQLDialect.MYSQL);
            //Result<Record> result = dslContext.select().from(AUTHOR).fetch();
            Result<RoleRecord> result = dslContext.selectFrom(ROLE).fetch();
           // dslContext.selectFrom(AUTHOR).fetch(AuthorRecord.class);

            //result.
            for (Record r : result) {
                Long id = r.getValue(ROLE.ID);
                String roleName = r.getValue(ROLE.NAME);

                System.out.println("ID: " + id + " role name: " + roleName);
            }
        }
        
    }
}
