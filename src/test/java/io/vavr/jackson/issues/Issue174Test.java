package io.vavr.jackson.issues;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Either;
import io.vavr.jackson.datatype.VavrModule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Issue174Test {
    @Test
    void eitherLeft() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new VavrModule());
        Cat cat = new Cat();
        cat.name = "Bianca";
        Either<Cat, Dog> either = Either.left(cat);

        String json = objectMapper.writeValueAsString(either);
        assertEquals("[\"left\",{\"name\":\"Bianca\"}]", json);

        Either<Cat, Dog> restored = objectMapper.readValue(json, new TypeReference<Either<Cat, Dog>>() {});
        assertEquals("Bianca", restored.getLeft().name);
    }

    @Test
    void eitherRight() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new VavrModule());

        Dog dog = new Dog();
        dog.name = "Labrador";
        Either<Cat, Dog> either = Either.right(dog);

        String json = objectMapper.writeValueAsString(either);
        assertEquals("[\"right\",{\"name\":\"Labrador\"}]", json);

        Either<Cat, Dog> restored = objectMapper.readValue(json, new TypeReference<Either<Cat, Dog>>() {});
        assertEquals("Labrador", restored.get().name);
    }

    static class Cat {
        @JsonProperty("name")
        String name;
    }

    static class Dog {
        @JsonProperty("name")
        String name;
    }
}
