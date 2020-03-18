package io.vavr.jackson.issues;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.vavr.control.Option;
import io.vavr.jackson.datatype.BaseTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Option serialization not respecting {@code @JsonUnwrapped}
 * https://github.com/vavr-io/vavr-jackson/issues/149
 */
class Issue149Test extends BaseTest {

    static class Foo {
        @JsonUnwrapped
        Option<Bar> getBar() {
            return Option.of(new Bar());
        }
    }

    static class Bar {
        @JsonProperty("bar")
        String getValue() {
            return "value";
        }
    }

    @Test
    void testJsonUnwrapped() throws Exception {
        String json = mapper().writeValueAsString(new Foo());
        assertEquals("{\"bar\":{\"bar\":\"value\"}}", json);
    }
}
