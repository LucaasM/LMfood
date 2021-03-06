package io.github.lucasmarts.lmfood.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import io.github.lucasmarts.lmfood.domain.enums.TipoProblema;
import io.github.lucasmarts.lmfood.domain.exception.ApiErrors;
import io.github.lucasmarts.lmfood.domain.exception.CozinhaNaoEncontradoException;
import io.github.lucasmarts.lmfood.domain.exception.EntidadeEmUsoException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;


@RestControllerAdvice
public class ApplicationControllerAdvice extends ResponseEntityExceptionHandler {


    private final MessageSource messageSource;

    public ApplicationControllerAdvice (MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @ExceptionHandler(CozinhaNaoEncontradoException.class)
    public ResponseEntity<?> handleValidationErrors(CozinhaNaoEncontradoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_NAO_ENCONTRADA;
        String detail = ex.getMessage();

        ApiErrors apiErrors = getApiErrorsBuilders(status, tipoProblema, detail).build();

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleValidationErrors(EntidadeEmUsoException ex, WebRequest request) {

        HttpStatus status = HttpStatus.CONFLICT;
        TipoProblema tipoProblema = TipoProblema.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        ApiErrors apiErrors = getApiErrorsBuilders(status, tipoProblema, detail).build();

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCROMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s', que ?? do tipo inv??lido. " +
                "Corriga e informe um valor compat??vel com o tipo '%s'", path, ex.getValue(),
                ex.getTargetType().getSimpleName());

        ApiErrors apiErrors = getApiErrorsBuilders(status, tipoProblema, detail).build();

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCROMPREENSIVEL;
        String detail = String.format("A propriedade '%s' informada n??o ?? suportada na requisi????o. Por favor remove-la", path);

        ApiErrors apiErrors = getApiErrorsBuilders(status, tipoProblema, detail).build();

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }

    @ExceptionHandler
    private ResponseEntity<Object> handleNumberFormatException(NumberFormatException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        TipoProblema tipoProblema = TipoProblema.PARAMETRO_INVALIDO;
        String detail = "O par??metro informado na URL ?? inv??lido. Corrija e envie um valor v??lido";

        ApiErrors apiErrors = getApiErrorsBuilders(status, tipoProblema, detail).build();

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if(rootCause instanceof NumberFormatException){
            return handleNumberFormatException((NumberFormatException) rootCause, headers, status, request);
        }

        TipoProblema tipoProblema = TipoProblema.PARAMETRO_INVALIDO;
        String detail = "O valor informado na URL ?? inv??lido.";

        ApiErrors apiErrors = getApiErrorsBuilders(status, tipoProblema, detail).build();

        return this.handleExceptionInternal(ex, apiErrors, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if(rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }

        if(rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }

        TipoProblema tipoProblema = TipoProblema.MENSAGEM_INCROMPREENSIVEL;
        String detail = "O corpo da requisi????o est?? inv??lido. Verifique os dados passados";

        ApiErrors apiErrors = getApiErrorsBuilders(status, tipoProblema, detail).build();

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request){

        BindingResult bindingResult = ex.getBindingResult();
        List<ApiErrors.Field> fieldList = bindingResult.getFieldErrors().stream().map(
                fieldError ->{
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());

                    String name = null;

                    if(fieldError instanceof FieldError) {
                        name = ((FieldError) fieldError).getField();
                    }

                    return ApiErrors.Field.builder()
                            .nome(name)
                            .userMessage(message)
                            .build();
                }).collect(Collectors.toList());


        TipoProblema tipoProblema = TipoProblema.DADOS_INVALIDOS;
        String detail = "Um ou mais dados informados s??o inv??lidos. Corrija e informe dados v??lidos.";

        ApiErrors apiErrors = getApiErrorsBuilders(status, tipoProblema, detail)
                .fields(fieldList)
                .build();

        return handleExceptionInternal(ex, apiErrors, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if(body == null) {
            body = ApiErrors.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .build();
        } else if(body instanceof String) {
            body = ApiErrors.builder()
                    .title((String) body)
                    .status(status.value())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    // M??todo para criar um builder de API erros
    private ApiErrors.ApiErrorsBuilder getApiErrorsBuilders(HttpStatus status, TipoProblema tipoProblema, String detail) {
        return ApiErrors.builder()
                .status(status.value())
                .type(tipoProblema.getUri())
                .title(tipoProblema.getTitle())
                .detail(detail);
    }

}
