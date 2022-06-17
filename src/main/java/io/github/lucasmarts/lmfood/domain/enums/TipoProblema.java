package io.github.lucasmarts.lmfood.domain.enums;

import lombok.Getter;

@Getter
public enum TipoProblema {

    MENSAGEM_INCROMPREENSIVEL("/mensagem-incompreensivel", "O corpo da requisição informada é invalido"),
    DADOS_INVALIDOS("/dados-invalidos", "Dado informado é inválido"),
    ARGUMENTO_VAZIO("/argumento-vazio", "Argumento está vazio"),
    ENTIDADE_NAO_ENCONTRADA("/entidade-nao-encontrada", "Entidade não encontrada"),
    PARAMETRO_INVALIDO("/parametro-invalido", "Parametro informada é invalido"),
    ENTIDADE_EM_USO("/entidade-em-uso", "Entidade em uso");

    private String uri;
    private String title;

    TipoProblema(String path, String title) {
        this.uri = "https://localhost:8081" + path;
        this.title = title;
    }
}
