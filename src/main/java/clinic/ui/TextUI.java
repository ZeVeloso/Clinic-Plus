package clinic.ui;

import clinic.business.ClinicFacade;
import clinic.business.Clinica;
import clinic.business.Consulta;
import clinic.business.Utente;
import clinic.data.QueryDAO;

import java.util.Scanner;

public class TextUI {
    // O model tem a 'lógica de negócio'.
    private final ClinicFacade model;

    // Menus da aplicação
    private final Menu menu;

    // Scanner para leitura
    private final Scanner scin;

    /**
     * Construtor.
     *
     * Cria os menus e a camada de negócio.
     */
    public TextUI() {
        // Criar o menu
        String[] opcoes = {
                "Adicionar Utente",
                "Listar utentes",
                "Editar Utente",
                "Delete Utente",
                "Listar consulta",
                "Adicionar Consulta",
                "Editar Consulta",
                "Delete Consulta",
                "Adicionar Clinica",
                "Listar clinicas",
                "getUtentes"
                };
        this.menu = new Menu(opcoes);
        this.model = new ClinicFacade();
        scin = new Scanner(System.in);
    }

    /**
     * Executa o menu principal e invoca o método correspondente à opção seleccionada.
     */
    public void run() {
        do {
            menu.executa();
            switch (menu.getOpcao()) {
                case 1:
                    trataAdicionarUtente();
                    break;
                case 2:
                    trataListarUtentes();
                    break;
                case 3:
                    trataEditarUtentes();
                    break;
                case 4:
                    trataDeleteUtente();
                    break;
                case 5:
                    trataListarConsultasUtente();
                    break;
                case 6:
                    trataAdicionarConsulta();
                    break;
                case 7:
                    trataEditarConsulta();
                    break;
                case 8:
                    trataDeleteConsulta();
                    break;
                case 9:
                    trataAdicionarClinica();
                    break;
                case 10:
                    trataGetClinicas();
                    break;
                case 11:
                    trataGetUtentesClinicas();
                    break;
            }
        } while (menu.getOpcao()!=0); // A opção 0 é usada para sair do menu.
        System.out.println("Até breve!...");
    }

    private void trataGetUtentesClinicas() {
        try {
            System.out.println("id da clinica: ");
            String idade = scin.nextLine();
            int idadeI = Integer.parseInt(idade);
            System.out.println(this.model.getUtentesClinica(idadeI).toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void trataAdicionarClinica() {
        try {
            System.out.println("Nome da novo clinica: ");
            String nome = scin.nextLine();
            this.model.adicionaClinica(new Clinica(null,nome));
            System.out.println("Clinica adicionado");
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void trataGetClinicas() {
        try {
            System.out.println(this.model.getClinicas().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }


    private void trataEditarUtentes() {
        try {
            System.out.println("id da utente: ");
            String id = scin.nextLine();
            int idI = Integer.parseInt(id);
            System.out.println("nome da utente: ");
            String nome = scin.nextLine();
            System.out.println("data de nascimento da utente: ");
            String nascimento = scin.nextLine();
            System.out.println("telemovel da utente: ");
            String telemovel = scin.nextLine();
            int telemovelInt = Integer.parseInt(id);
            System.out.println("idade da utente: ");
            String idade = scin.nextLine();
            int idadeI = Integer.parseInt(idade);
            System.out.println("Profissao da utente: ");
            String profissao = scin.nextLine();
            System.out.println("histPess da utente: ");
            String histPess = scin.nextLine();

            System.out.println("histFam da utente: ");
            String histFam = scin.nextLine();
            System.out.println("atvFis da utente: ");
            String atvFis = scin.nextLine();

            this.model.editarUtente(new Utente(idI,nascimento,telemovelInt,idadeI,profissao,histFam, histPess, nome, atvFis, "",1));

            System.out.println("Utente editado");
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void trataDeleteUtente() {
        try {
            System.out.println("id da utente: ");
            String idade = scin.nextLine();
            int idadeI = Integer.parseInt(idade);
            this.model.removeUtente(idadeI);
            System.out.println("Utente removido");
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    // Métodos auxiliares
    private void trataAdicionarUtente() {
        try {
            System.out.println("Nome da novo utente: ");
            String nome = scin.nextLine();
            System.out.println("Idade da novo utente: ");
            String idade = scin.nextLine();
            int idadeI = Integer.parseInt(idade);
            this.model.adicionaUtente(new Utente(null,nome, idadeI,1));
            System.out.println("Aluno adicionado");
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void trataListarUtentes(){
        try {
            System.out.println(this.model.getUtentes().toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void trataAdicionarConsulta() {

        try {
            System.out.println("id do utente ");
            String id = scin.nextLine();
            int idI = Integer.parseInt(id);
            System.out.println("data da consulta ");
            String data = scin.nextLine();
            this.model.adicionaConsulta(new Consulta(idI,data));
            System.out.println("Consulta adicionado ao utente" + idI + " - " + this.model.getUtente(idI).getNome());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

    }

    private void trataListarConsultasUtente(){
        QueryDAO t = new QueryDAO();
        try {
            System.out.println(t.getConsulta(1).toString());
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    private void trataEditarConsulta() {
        try {
            System.out.println("id da consulta: ");
            String id = scin.nextLine();
            int idI = Integer.parseInt(id);
            System.out.println("data da utente: ");
            String data = scin.nextLine();
            System.out.println("custo da consulta: ");
            String custo = scin.nextLine();
            float custoFloat = Float.parseFloat(custo);
            System.out.println("id do utente da consulta: ");
            String utente = scin.nextLine();
            int utenteInt = Integer.parseInt(utente);

            this.model.editarConsulta(new Consulta(idI,data, custoFloat, null, "Agendado","",utenteInt));

            System.out.println("Utente editado");
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void trataDeleteConsulta() {
        try {
            System.out.println("id da consulta: ");
            String id = scin.nextLine();
            int idI = Integer.parseInt(id);
            this.model.removeConsulta(idI);
            System.out.println("Consulta removido");
        }
        catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

}
