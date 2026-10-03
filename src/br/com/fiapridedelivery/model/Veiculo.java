package br.com.fiapridedelivery.model;

// Veículo da frota: centraliza atributos e validações comuns a todos os tipos
public abstract class Veiculo {

    // Padrão antigo (ABC1234) ou Mercosul (ABC1D23)
    private static final String FORMATO_PLACA = "[A-Z]{3}[0-9][A-Z0-9][0-9]{2}";

    private final String placa;
    private final double capacidadeCargaKg;

    // Cria o veículo validando placa e capacidade de carga (kg)
    protected Veiculo(String placa, double capacidadeCargaKg) {
        this.placa = validarPlaca(placa);
        this.capacidadeCargaKg = validarCapacidade(capacidadeCargaKg);
    }

    // Tipo do veículo, definido por cada subclasse
    public abstract String getTipo();

    // Retorna true se o peso (kg) não ultrapassa a capacidade
    public boolean suportaPeso(double pesoKg) {
        return pesoKg <= capacidadeCargaKg;
    }

    // Placa padronizada (maiúscula, sem hífen)
    public String getPlaca() {
        return placa;
    }

    // Capacidade máxima de carga, em kg
    public double getCapacidadeCargaKg() {
        return capacidadeCargaKg;
    }

    @Override
    public String toString() {
        return getTipo() + " " + placa + " (capacidade: " + capacidadeCargaKg + " kg)";
    }

    private static String validarPlaca(String placa) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("A placa é obrigatória.");
        }
        // Aceita minúsculas e hífen, mas armazena padronizado
        String placaNormalizada = placa.trim().toUpperCase().replace("-", "");
        if (!placaNormalizada.matches(FORMATO_PLACA)) {
            throw new IllegalArgumentException("Placa inválida: " + placa);
        }
        return placaNormalizada;
    }

    private static double validarCapacidade(double capacidadeCargaKg) {
        if (capacidadeCargaKg <= 0) {
            throw new IllegalArgumentException("A capacidade de carga deve ser maior que zero.");
        }
        return capacidadeCargaKg;
    }
}