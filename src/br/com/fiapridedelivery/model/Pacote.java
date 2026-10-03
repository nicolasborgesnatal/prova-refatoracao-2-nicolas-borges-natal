package br.com.fiapridedelivery.model;

import java.util.Objects;

// Pacote a ser transportado; todo pacote nasce com status PENDENTE
public class Pacote {

    private final String codigo;
    private final double pesoKg;
    private StatusPacote status;

    // Cria o pacote validando código e peso (kg)
    public Pacote(String codigo, double pesoKg) {
        this.codigo = validarCodigo(codigo);
        this.pesoKg = validarPeso(pesoKg);
        this.status = StatusPacote.PENDENTE;
    }

    // Atualiza o status; pacote já entregue não pode mudar
    public void atualizarStatus(StatusPacote novoStatus) {
        Objects.requireNonNull(novoStatus, "O status é obrigatório.");
        if (status == StatusPacote.ENTREGUE) {
            throw new IllegalStateException("O pacote " + codigo + " já foi entregue e não pode mudar de status.");
        }
        this.status = novoStatus;
    }

    // Código de rastreio do pacote
    public String getCodigo() {
        return codigo;
    }

    // Peso do pacote, em kg
    public double getPesoKg() {
        return pesoKg;
    }

    // Status atual do pacote
    public StatusPacote getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Pacote " + codigo + " (" + pesoKg + " kg) - " + status.getDescricao();
    }

    private static String validarCodigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código do pacote é obrigatório.");
        }
        return codigo.trim().toUpperCase();
    }

    private static double validarPeso(double pesoKg) {
        if (pesoKg <= 0) {
            throw new IllegalArgumentException("O peso do pacote deve ser maior que zero.");
        }
        return pesoKg;
    }
}