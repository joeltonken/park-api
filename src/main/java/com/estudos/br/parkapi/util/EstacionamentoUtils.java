package com.estudos.br.parkapi.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EstacionamentoUtils {

    // 2024-05-17T23:13:40.616463500
    // 20240517-231340
    public static String gerarRecibo() {
        LocalDateTime date = LocalDateTime.now();
        String recibo = date.toString().substring(0,19);
        return recibo
                .replace("-", "")
                .replace(":", "")
                .replace("T", "-");
    }

}
