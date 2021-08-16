package it.vincendep.popcorn.infrastructure;

import com.zaxxer.hikari.HikariDataSource;
import it.vincendep.popcorn.PopcornApplication;
import lombok.extern.slf4j.Slf4j;
import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
@Profile(PopcornApplication.DEV)
public class DatabaseManager {

    @Autowired
    public void runDbManager(DataSource ds){
        if (ds instanceof HikariDataSource) {
            System.setProperty("java.awt.headless", "false");
            var datasource = (HikariDataSource) ds;
            DatabaseManagerSwing.main(new String[] {
                    "--url", datasource.getJdbcUrl(),
                    "--user", datasource.getUsername(),
                    "--password", datasource.getPassword(),
                    "--noexit",
            });
        } else {
            log.warn("Unable to lunch DatabaseManager for Datasource={}", ds);
        }
    }
}
