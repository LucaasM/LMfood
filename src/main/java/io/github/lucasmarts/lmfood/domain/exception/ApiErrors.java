package io.github.lucasmarts.lmfood.domain.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiErrors {

   private Integer status;
   private String type;
   private String title;
   private String detail;
   private List<Field> fields;

   @Getter
   @Builder
   public static class Field{

      private String nome;
      private String userMessage;
   }
}
