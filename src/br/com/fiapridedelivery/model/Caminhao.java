package br.com.fiapridedelivery.model;

// Caminhão de entrega, usado para cargas maiores
public class Caminhao extends Veiculo {

    private static final int MINIMO_EIXOS = 2;

    private final int quantidadeEixos;

    // Cria o caminhão validando placa, capacidade (kg) e eixos (mínimo 2)
    public Caminhao(String placa, double capacidadeCargaKg, int quantidadeEixos) {
        super(placa, capacidadeCargaKg);
        this.quantidadeEixos = validarEixos(quantidadeEixos);
    }

    @Override
    public String getTipo() {
        return "Caminhão";
    }

    // Quantidade de eixos do caminhão
    public int getQuantidadeEixos() {
        return quantidadeEixos;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + quantidadeEixos + " eixos";
    }

    private static int validarEixos(int quantidadeEixos) {
        if (quantidadeEixos < MINIMO_EIXOS) {
            throw new IllegalArgumentException("Um caminhão deve ter no mínimo " + MINIMO_EIXOS + " eixos.");
        }
        return quantidadeEixos;
    }
}