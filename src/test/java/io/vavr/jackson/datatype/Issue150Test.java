package io.vavr.jackson.datatype;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import io.vavr.collection.List;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Issue150Test extends BaseTest {

    @Test
    void itShouldDeserializeListList() throws IOException {
        String json = mapper().writeValueAsString(List.of(List.of(1)));
        assertEquals("[[1]]", json);

        // FIXME
//        List<List<Integer>> list = mapper().readValue(json, List.class);
//        assertEquals(List.of(1), list.get(0));

        List<List<Integer>> listFromRef = mapper().readValue(json, new TypeReference<List<List<Integer>>>() {
        });
        assertEquals(List.of(1), listFromRef.get(0));
    }

    @Test
    void itShouldDeserializeListListFromClass() throws Exception {
        MyClass obj = new MyClass(List.of(List.of(1)));
        String json = mapper().writeValueAsString(obj);
        assertEquals("{\"t\":[[1]]}", json);

        MyClass deserialized = mapper().readValue(json, MyClass.class);
        assertEquals(List.of(List.of(1)), deserialized.table);
    }

    static class MyClass {
        @JsonProperty("t")
        List<List<Integer>> table;

        @JsonCreator
        MyClass(@JsonProperty("t") List<List<Integer>> table) {
            this.table = table;
        }
    }

}
