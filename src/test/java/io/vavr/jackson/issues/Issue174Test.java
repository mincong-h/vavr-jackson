package io.vavr.jackson.issues;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.vavr.control.Either;
import io.vavr.jackson.datatype.BaseTest;
import io.vavr.jackson.datatype.VavrModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Issue174Test extends BaseTest {
    @Test
    void name() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new VavrModule());

        Cat cat = new Cat();
        cat.name = "Bianca";
        Either<Cat, Dog> either = Either.left(cat);

        assertEquals("[\"left\",{\"name\":\"Bianca\"}]", objectMapper.writeValueAsString(either));
    }

    class Cat {
        @JsonProperty("name")
        String name;
    }

    class Dog {
        String name;
    }
}
