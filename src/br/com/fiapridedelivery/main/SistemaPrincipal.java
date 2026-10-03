package br.com.fiapridedelivery.main;

import br.com.fiapridedelivery.model.Caminhao;
import br.com.fiapridedelivery.model.Moto;
import br.com.fiapridedelivery.model.Pacote;
import br.com.fiapridedelivery.model.Rota;

// Ponto de entrada: demonstra o fluxo de entrega refatorado
public class SistemaPrincipal {

    // Classe de inicialização, não deve ser instanciada
    private SistemaPrincipal() {
    }

    // Executa a demonstração do sistema
    public static void main(String[] args) {
        Caminhao caminhao = new Caminhao("ABC1234", 5000.0, 3);
        Moto moto = new Moto("XYZ1A23", 30.0, true);

        System.out.println("===== Frota =====");
        System.out.println(caminhao);
        System.out.println(moto);

        System.out.println("\n===== Entrega com caminhão =====");
        Pacote pacoteCaminhao = new Pacote("BR999", 10.5);
        Rota rotaCaminhao = new Rota(pacoteCaminhao, caminhao);
        rotaCaminhao.iniciarEntrega();
        rotaCaminhao.concluirEntrega();
        System.out.println(pacoteCaminhao);

        // Mesma classe Rota, agora com moto
        System.out.println("\n===== Entrega com moto =====");
        Pacote pacoteMoto = new Pacote("BR1000", 2.3);
        Rota rotaMoto = new Rota(pacoteMoto, moto);
        rotaMoto.iniciarEntrega();
        System.out.println(pacoteMoto);

        // Erros que o código legado deixava passar
        System.out.println("\n===== Validações =====");
        try {
            new Caminhao("DEF5678", -500.0, 2);
        } catch (IllegalArgumentException erro) {
            System.out.println("Erro: " + erro.getMessage());
        }

        try {
            new Rota(new Pacote("BR2000", 50.0), moto);
        } catch (IllegalArgumentException erro) {
            System.out.println("Erro: " + erro.getMessage());
        }
    }
}