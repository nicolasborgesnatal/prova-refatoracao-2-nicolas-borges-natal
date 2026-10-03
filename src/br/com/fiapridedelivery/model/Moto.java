package br.com.fiapridedelivery.model;

// Moto de entrega, usada para pacotes leves e entregas rápidas
public class Moto extends Veiculo {

    private boolean possuiBau;

    // Cria a moto validando placa e capacidade (kg)
    public Moto(String placa, double capacidadeCargaKg, boolean possuiBau) {
        super(placa, capacidadeCargaKg);
        this.possuiBau = possuiBau;
    }

    @Override
    public String getTipo() {
        return "Moto";
    }

    // Retorna true se a moto tem baú instalado
    public boolean possuiBau() {
        return possuiBau;
    }

    // Registra a instalação ou remoção do baú
    public void setPossuiBau(boolean possuiBau) {
        this.possuiBau = possuiBau;
    }

    @Override
    public String toString() {
        return super.toString() + (possuiBau ? " - com baú" : " - sem baú");
    }
}