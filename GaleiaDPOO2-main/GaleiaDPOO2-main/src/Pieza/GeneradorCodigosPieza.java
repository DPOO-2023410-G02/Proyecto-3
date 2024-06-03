package Pieza;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class GeneradorCodigosPieza {

    private static GeneradorCodigosPieza instance;

    private static final int LONGITUD_CODIGO = 8;
    private static final String DIGITOS = "0123456789";
    private static final Random RANDOM = new Random();

    private Set<String> codigosExistentes;

    private GeneradorCodigosPieza() {
        codigosExistentes = new HashSet<>();
    }

    public static GeneradorCodigosPieza getInstance() {
        if (instance == null) {
            instance = new GeneradorCodigosPieza();
        }
        return instance;
    }

    public String generarCodigo() {
        String codigo;
        do {
            codigo = generarCodigoUnico();
        } while (codigosExistentes.contains(codigo));
        codigosExistentes.add(codigo);
        return codigo;
    }

    private String generarCodigoUnico() {
        StringBuilder sb = new StringBuilder(LONGITUD_CODIGO);
        for (int i = 0; i < LONGITUD_CODIGO; i++) {
            int indice = RANDOM.nextInt(DIGITOS.length());
            sb.append(DIGITOS.charAt(indice));
        }
        return sb.toString();
    }
}
