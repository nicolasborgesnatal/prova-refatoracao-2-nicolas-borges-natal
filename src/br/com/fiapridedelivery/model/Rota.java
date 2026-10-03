package br.com.fiapridedelivery.model;

import java.util.Objects;

// Rota de entrega: associa um pacote a qualquer Veiculo (caminhão, moto ou futuros tipos)
public class Rota {

    private final Pacote pacote;
    private final Veiculo veiculo;

    // Cria a rota garantindo que o veículo comporta o pacote
    public Rota(Pacote pacote, Veiculo veiculo) {
        this.pacote = Objects.requireNonNull(pacote, "O pacote é obrigatório.");
        this.veiculo = Objects.requireNonNull(veiculo, "O veículo é obrigatório.");
        if (!veiculo.suportaPeso(pacote.getPesoKg())) {
            throw new IllegalArgumentException("O pacote " + pacote.getCodigo() + " (" + pacote.getPesoKg()
                    + " kg) excede a capacidade do veículo " + veiculo.getPlaca() + ".");
        }
    }

    // Inicia a entrega: pacote PENDENTE passa para EM_TRANSITO
    public void iniciarEntrega() {
        if (pacote.getStatus() != StatusPacote.PENDENTE) {
            throw new IllegalStateException("Só é possível iniciar a entrega de um pacote pendente.");
        }
        pacote.atualizarStatus(StatusPacote.EM_TRANSITO);
        System.out.println("Levando pacote " + pacote.getCodigo() + " no veículo " + veiculo.getTipo()
                + " " + veiculo.getPlaca());
    }

    // Conclui a entrega: pacote EM_TRANSITO passa para ENTREGUE
    public void concluirEntrega() {
        if (pacote.getStatus() != StatusPacote.EM_TRANSITO) {
            throw new IllegalStateException("Só é possível concluir a entrega de um pacote em trânsito.");
        }
        pacote.atualizarStatus(StatusPacote.ENTREGUE);
        System.out.println("Pacote " + pacote.getCodigo() + " entregue pelo veículo " + veiculo.getPlaca());
    }

    // Pacote transportado nesta rota
    public Pacote getPacote() {
        return pacote;
    }

    // Veículo responsável pela rota
    public Veiculo getVeiculo() {
        return veiculo;
    }
}