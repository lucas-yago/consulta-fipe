package br.com.onealura.TabelaFipe.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ApiDataMapper implements IApiDataMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T convertFromJson(String json, Class<T> tClass) {
        try {
            return mapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> T convertFromJson(String json, TypeReference<T> typeReference) {
        try {
            return mapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public <T> List<T> convertFromJson(String json, Class<T> tClass) {
//        CollectionType list = mapper.getTypeFactory()
//                .constructCollectionType(List.class, tClass);
//        try {
//            return mapper.readValue(json, list);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
