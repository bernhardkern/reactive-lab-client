package de.virtual7.reactivelabclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;

import java.util.Collections;
import java.util.List;

/**
 * Created by mihai.dobrescu
 */
@Configuration
public class CassandraConfiguration extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {
        return "reactive_lab";
    }

    @Override
    protected String getContactPoints() {

        //specify nodes here
        return super.getContactPoints();
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification =
                CreateKeyspaceSpecification.createKeyspace("reactive_lab")
                        .ifNotExists()
                        .withSimpleReplication()
                        .with(KeyspaceOption.DURABLE_WRITES);

        return Collections.singletonList(specification);
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[]{"de.virtual7.reactivelabclient.events"};
    }
}
