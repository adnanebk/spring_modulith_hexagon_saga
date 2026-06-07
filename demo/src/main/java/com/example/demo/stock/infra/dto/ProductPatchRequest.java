package com.example.demo.stock.infra.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;

public record ProductPatchRequest(JsonNullable<String> name, JsonNullable<BigDecimal> price, JsonNullable<String> description, JsonNullable<Integer> amountInStock) {


}
