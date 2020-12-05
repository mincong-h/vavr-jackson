package io.vavr.jackson.issues;

import com.fasterxml.jackson.databind.ObjectMapper;
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

        // yields com.fasterxml.jackson.databind.JsonMappingException: No serializer found for class io.vavr.jackson.issues.Issue174Test$Cat and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) )
        // but not java.lang.IncompatibleClassChangeError: Found class io.vavr.control.Either, but interface was expected
        assertEquals("[\"left\",{\"name\":\"Bianca\"}]", objectMapper.writeValueAsString(either));
    }

    class Cat {
        String name;
    }

    class Dog {
        String name;
    }
}
