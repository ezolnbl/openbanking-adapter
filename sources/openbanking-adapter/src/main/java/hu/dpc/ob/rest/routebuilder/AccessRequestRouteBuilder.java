/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 *  https://mozilla.org/MPL/2.0/.
 */
package hu.dpc.ob.rest.routebuilder;

import hu.dpc.ob.cache.TransactionContextHolder;
import hu.dpc.ob.config.AccessSettings;
import hu.dpc.ob.config.AdapterSettings;
import hu.dpc.ob.rest.internal.ApiSchema;
import org.apache.camel.CamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Configuration
public class AccessRequestRouteBuilder extends OpenbankingRouteBuilder {

    private static Logger log = LoggerFactory.getLogger(AccessRequestRouteBuilder.class);

    public static final String ID_SOURCE_ACCESS = "access";

    private AccessSettings accessSettings;

    @Autowired
    public AccessRequestRouteBuilder(CamelContext camelContext, AccessSettings accessSettings) {
        super(camelContext);
        this.accessSettings = accessSettings;
    }

    @Override
    @NotNull
    protected String getSource() {
        return ID_SOURCE_ACCESS;
    }

    @Override
    public void configure() throws Exception {
        getContext().getShutdownStrategy().setTimeout(1);
        AdapterSettings adapterSettings = accessSettings.getAdapterSettings();

        adapterSettings.getSchemas().forEach(schema -> {
            buildDirectRoutes(schema);
            adapterSettings.getTenants().forEach(tenant -> {
                buildConsumerRoutes(schema, tenant);
            });
        });
    }

    private void buildConsumerRoutes(ApiSchema schema, String tenant) {
        buildBindingRoutes(schema, tenant, accessSettings);
    }

    private void buildDirectRoutes(ApiSchema schema) {
        buildDirectRoutes(schema, accessSettings.getBindings(schema));
    }
}