package it.vincendep.popcorn.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.json.JsonObjectReader;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;

public class JacksonJsonObjectReader<T> implements JsonObjectReader<T> {

    private final Class<? extends T> itemType;
    private JsonParser jsonParser;
    private ObjectMapper mapper;
    private InputStream inputStream;

    public JacksonJsonObjectReader(Class<? extends T> itemType) {
        this(new ObjectMapper(), itemType);
    }

    public JacksonJsonObjectReader(ObjectMapper mapper, Class<? extends T> itemType) {
        this.mapper = mapper;
        this.itemType = itemType;
    }

    public void setMapper(ObjectMapper mapper) {
        Assert.notNull(mapper, "The mapper must not be null");
        this.mapper = mapper;
    }

    @Override
    public void open(@NonNull Resource resource) throws Exception {
        Assert.notNull(resource, "The resource must not be null");
        this.inputStream = resource.getInputStream();
        this.jsonParser = this.mapper.getFactory().createParser(this.inputStream);
        Assert.state(this.jsonParser.nextToken() == JsonToken.START_OBJECT,
                "The Json input stream must start with a Json object");
    }

    @Nullable
    @Override
    public T read() throws Exception {
        try {
            if (this.jsonParser.currentToken() == JsonToken.START_OBJECT ||
                    this.jsonParser.nextToken() == JsonToken.START_OBJECT) {
                return this.mapper.readValue(this.jsonParser, this.itemType);
            }
        } catch (IOException e) {
            throw new ParseException("Unable to read next JSON object", e);
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        this.inputStream.close();
        this.jsonParser.close();
    }
}
