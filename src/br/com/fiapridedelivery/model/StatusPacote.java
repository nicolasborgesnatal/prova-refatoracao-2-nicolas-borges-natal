package br.com.fiapridedelivery.model;

// Etapas do pacote no fluxo de entrega
public enum StatusPacote {

    // Aguardando saída para entrega
    PENDENTE("Pendente"),
    // Saiu para entrega
    EM_TRANSITO("Em trânsito"),
    // Entregue ao destinatário
    ENTREGUE("Entregue");

    private final String descricao;

    StatusPacote(String descricao) {
        this.descricao = descricao;
    }

    // Descrição amigável do status
    public String getDescricao() {
        return descricao;
    }
}