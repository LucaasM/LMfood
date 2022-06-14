package io.github.lucasmarts.lmfood.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrors {

   private Integer status;
   private String type;
   private String title;
   private String detail;
}
