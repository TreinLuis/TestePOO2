package app;

import dados.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ACMEMidia {
    private Scanner in = new Scanner(System.in);

    private Midiateca midiateca;

    public ACMEMidia() {
        midiateca = new Midiateca();
    }

    public void executa() {
        cadastraVideo();//Done
        cadastraMusica();//Done
        exibeDadosMidia();//Done
        //exibeDadosCategoria();//Done
        //exibeDadosQualidade();//Done
        //exibeDadosDuracao();//Done
        //removeMidia();//Done
        somatorioLocacoes();//To_Do
        exibeDadosMusicaLocacaoMedia();//To_Do
        exibeDadosMidiaNova();//To_Do
    }
    public void cadastraVideo() {
        var continuar = 0;
        while (continuar != -1) {
            System.out.println("Digite o código do vídeo: ");
            int codigo = in.nextInt();
            in.nextLine();

            System.out.println("Digite o título: ");
            String titulo = in.nextLine();

            System.out.println("Digite a categoria do vídeo: ");
            String categoriaStr = in.nextLine();

            Categoria categoria;
            try {
                categoria = Categoria.fromString(categoriaStr);
                System.out.println(categoria);
            } catch (IllegalArgumentException e) {
                System.out.println("Categoria inválida!");
                return;
            }

            System.out.println("Digite o ano de lançamento do vídeo: ");
            int ano = in.nextInt();
            in.nextLine();

            System.out.println("Digite a qualidade da imagem do vídeo: ");
            int qualidade = in.nextInt();

            Video v = new Video(codigo, titulo, ano, categoria, qualidade);
            if (!midiateca.cadastraMidia(v)) {
                System.out.println(" 1:Erro-video com codigo repetido: " + codigo);
            } else {
                System.out.println("Codigo: " + codigo + " | Título: " + titulo + " | Ano: " +
                        ano + " | Categoria: " + categoria.getNome() + " | Qualidade: " + qualidade + "p.");
            }
            System.out.println("Caso não queira mais adicionar musíca digite -1! ");
            continuar = in.nextInt();
            in.nextLine();
        }

    }

    public void cadastraMusica() {
        var continuar = 0;

        while (continuar != -1) {
            System.out.println("Digite o código da musíca: ");
            int codigo = in.nextInt();
            in.nextLine();
            System.out.println("Digite o título: ");
            String titulo = in.nextLine();

            Categoria categoria = categoria();

            System.out.println("Digite o ano de lançamento da musíca: ");
            int ano = in.nextInt();
            in.nextLine();

            System.out.println("Digite a duração da musíca: ");
            double duracao = in.nextDouble();
            in.nextLine();

            Musica m = new Musica(codigo, titulo, ano, categoria, duracao);
            if (!midiateca.cadastraMidia(m)) {
                System.out.println(" 1:Erro-musica com codigo repetido: " + codigo);
            } else {
                System.out.println("Codigo: " + codigo + " | Título: " + titulo + " | Ano: " +
                        ano + " | Categoria: " + categoria.getNome() + " | Duração: " + duracao + " min.");
            }
            System.out.println("Caso não queira mais adicionar musíca digite -1! ");
            continuar = in.nextInt();
            in.nextLine();
        }
    }

    public void exibeDadosMidia() {
        Midia m;
        System.out.println("Digite o código da mídia:");
        int codigo = in.nextInt();
        m = midiateca.consultaCodigo(codigo);
        if (m != null) {
            System.out.println(m.toString());
        } else {
            System.out.println("3:codigo inexistente.");
        }

    }

    public void exibeDadosCategoria() {
        List<Midia> midiasCategoria;

        System.out.println("Digite a categoria da mídia par aobter os dados: ");
        Categoria categoria = categoria();
        midiasCategoria = midiateca.consultaPorCategoria(categoria);
        if (midiasCategoria.isEmpty()) {
            System.out.println("4:Nenhuma midia encontrada.");
        } else {
            for (Midia m : midiasCategoria) {
                if (m instanceof Video) {
                    Video video = (Video) m;
                    System.out.println(video.toString());
                } else if (m instanceof Musica) {
                    Musica musica = (Musica) m;
                    System.out.println(musica.toString());
                }
            }
        }
    }

    public void exibeDadosQualidade() {
        List<Midia> midiasQualidade;

        System.out.println("Digite a qualidade do vídeo par aobter os dados:");
        int qualidade = in.nextInt();
        in.nextLine();
        midiasQualidade = midiateca.consultaGeneralizada(Midia.class, qualidade);
        if (midiasQualidade.isEmpty()) {
            System.out.println("5:Qualidade inexistente.");
        } else {
            for (Midia m : midiasQualidade) {
                System.out.println(m.toString());
            }
        }
    }

    public void exibeDadosDuracao() {
        List<Midia> midiasDuracao;

        System.out.println("Digite a duração da musíca para aobter os dados:");
        double duracao = in.nextDouble();
        in.nextLine();
        midiasDuracao = midiateca.consultaGeneralizada(Midia.class, duracao);
        if (midiasDuracao.isEmpty()) {
            System.out.println(" 4:Nenhuma midia encontrada.");
        } else {
            for (Midia m : midiasDuracao) {
                System.out.println(m.toString());
            }
        }
    }

    public void removeMidia() {
        System.out.println("Digite o código da mídia");
        int codigo = in.nextInt();
        in.nextLine();
        Midia m = midiateca.consultaCodigo(codigo);

        if(m!=null){
            System.out.println("7: " + m.toString());
            midiateca.removeMidia(codigo);
        } else {
            System.out.println("7:codigo inexistente.");

        }
    }

    public void somatorioLocacoes() {
        double somatorio = midiateca.somatorioLocacoes();
        if(somatorio == 0.0){
            System.out.println("8:Nenhuma mídia encontrada.");
        } else{
            System.out.println("8: "+ somatorio);
        }
//		calcula o somatório do
//			valor de locação de todas as mídias do sistema. Se não existir mídia cadastrada
//		no sistema, mostra a mensagem de erro: 8:Nenhuma mídia encontrada.
//				Se existir, mostra a mensagem no formato: 8:valor do somatório
    }

    public void exibeDadosMusicaLocacaoMedia() {
//		calcula a média dos valores de locações das músicas
//		cadastradas e localiza a música com valor de locação mais próximo da média
//		calculada. Se não existir nenhuma música cadastrada, mostra a mensagem de erro:
//		9:Nenhuma musica encontrada
    }

    public void exibeDadosMidiaNova() {
//		mostra os dados da mídia mais nova. Se não
//		existir nenhuma mídia cadastrada, mostra a mensagem de erro: 10:Nenhuma midia
//		encontrada.
//				Se existir, mostra os dados da mídia no formato: 10:codigo,titulo,ano

    }

    public Categoria categoria() {
        System.out.println("Digite a categoria: ");
        String categoriaStr = in.nextLine();

        Categoria categoria;
        try {
            categoria = Categoria.fromString(categoriaStr);
            System.out.println(categoria);
        } catch (IllegalArgumentException e) {
            System.out.println("Categoria inválida!");
            return null;
        }
        return categoria;
    }

    public Number defineAtributo(String entrada) {
        Number atributo;

        try {
            atributo = Integer.parseInt(entrada);
        } catch (NumberFormatException e1) {
            try {
                atributo = Double.parseDouble(entrada);
            } catch (NumberFormatException e2) {
                System.out.println("Entrada inválida. Por favor, digite um número válido.");
                atributo = null;
            }
        }
        return atributo;
    }
}