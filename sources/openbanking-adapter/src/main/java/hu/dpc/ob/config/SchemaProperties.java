/*
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at
 *
 * https://mozilla.org/MPL/2.0/.
 */
package hu.dpc.ob.config;

import hu.dpc.ob.rest.internal.ApiSchema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuppressWarnings("unused")
public class SchemaProperties<_H extends Header, _O extends Operation, _B extends Binding> extends UriSettings<_H, _O, _B> implements DefaultProperties {

    @NotNull
    private String name;

    protected SchemaProperties(@NotNull String name) {
        this.name = name;
    }

    protected SchemaProperties(@NotNull ApiSchema schema) {
        this(schema.getConfigName());
    }

    @Override
    public boolean isDefault() {
        return getName().equals(getDefaultName());
    }

    protected String getDefaultName() {
        return SCHEMA_DEFAULT_SETTINGS;
    }

    public ApiSchema getSchema() {
        return ApiSchema.forConfigName(name);
    }
}