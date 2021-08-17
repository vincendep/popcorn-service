package it.vincendep.popcorn.infrastructure;

import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Slf4j
@Component
@ConditionalOnProperty("popcorn.database-manager.enabled")
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
            log.warn("Unable to lunch DatabaseManager for the configured datasource");
        }
    }
}
