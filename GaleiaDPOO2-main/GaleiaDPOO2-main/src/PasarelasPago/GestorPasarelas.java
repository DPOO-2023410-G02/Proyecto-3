package PasarelasPago;


public class GestorPasarelas {
    private static PasarelaPago pasarelaPayPal;
    private static PasarelaPago pasarelaStripe;

    public static void inicializarPasarelas() {
        // Inicializa las pasarelas de pago
        pasarelaPayPal = new PayPal();
        pasarelaStripe = new PayU();
    }

    public static PasarelaPago getPasarelaPayPal() {
        return pasarelaPayPal;
    }

    public static PasarelaPago getPasarelaStripe() {
        return pasarelaStripe;
    }
}
