package br.com.onealura.TabelaFipe.service;

import com.fasterxml.jackson.core.type.TypeReference;

public interface IApiDataMapper {
    <T> T convertFromJson(String json, Class<T> tClass);

    <T> T convertFromJson(String json, TypeReference<T> typeReference);
}
