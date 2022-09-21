
// importação da classe Random para poder ter acesso a comandos de geração de numeros aleatorios.
import java.util.Random;

// importação da classe Scanner para ter acesso a comandos de entrada de dados.
import java.util.Scanner;

public class GameOfLife {

    // Declaração de variaveis globais
    public static  int colunasMatriz = 0;
    public static  int linhasMatriz = 0;

    public static void main(String[] args) throws InterruptedException {


            // instanciando um objeto da classe Scanner
            Scanner teclado = new Scanner(System.in);

            System.out.println("Qual o numero de linhas da matriz?");
            linhasMatriz = teclado.nextInt();
            System.out.println("Qual o numero de colunas da matriz?");
            colunasMatriz = teclado.nextInt();
            System.out.printf("Matriz %d x %d" , linhasMatriz, colunasMatriz);

            // Declaração da matriz
            int [][] matriz = new int[linhasMatriz][colunasMatriz];

            Play(matriz);

    }

    // método para gerar uma matriz com valores aleatorios entre 0 e 1
    public static void gerarMatrizAleatoria(int[][] mtz) {
        Random random = new Random();

        for (int linha=0;linha<linhasMatriz;linha++) {
            for (int coluna = 0; coluna < colunasMatriz; coluna++) {
                mtz[linha][coluna] = random.nextInt(2);
            }
            System.out.printf("%n");
        }
    }

    // método para imprimir no console a matriz
    public static void imprimirMatriz(int[][] mtz) {
        for (int linha=0;linha<linhasMatriz;linha++) {
            for (int coluna = 0; coluna < colunasMatriz; coluna++) {
                System.out.printf("%d | ", mtz[linha][coluna]);
            }
            System.out.printf("%n");
        }
    }

    // método para contagem de vizinhos vivos de cada célula + tratamento das bordas
    public static int contadorDeVizinhos(int[][] mtz, int x , int y) {
        int vizinhosVivos = 0;
        for (int indiceLinha =-1;indiceLinha<2;indiceLinha++) {
            for (int indiceColuna =-1; indiceColuna<2; indiceColuna++) {
                int linha = (x + indiceLinha + linhasMatriz) % linhasMatriz;
                int coluna = (y + indiceColuna + colunasMatriz) % colunasMatriz;
                vizinhosVivos += mtz[linha][coluna];
            }
        }
        vizinhosVivos -= mtz[x][y];

        return vizinhosVivos;
    }

    // método só para verificar se não há mais vidas na matriz e encerrar o loop
    public static int contadorDeVidas(int[][] mtz) {
        int vidas = 0;

        for (int linha=0;linha<linhasMatriz;linha++) {
            for (int coluna = 0; coluna < colunasMatriz; coluna++) {
                if (mtz[linha][coluna] == 1) {
                    vidas += 1;
                }
            }
        }


        return vidas;

    }

    // método para iniciar o desafio
    public static void Play(int[][] matriz) throws InterruptedException {

        gerarMatrizAleatoria(matriz);
        imprimirMatriz(matriz);
        // clear();

        int[][] proximaGeracao = new int[linhasMatriz][colunasMatriz];

        do {
            for (int linha=0;linha<linhasMatriz;linha++) {
                for (int coluna = 0; coluna < colunasMatriz; coluna++) {
                    int celula = matriz[linha][coluna];
                    int vizinhos = contadorDeVizinhos(matriz, linha, coluna);



                    if (celula == 0 && vizinhos == 3) {
                        proximaGeracao[linha][coluna] = 1;
                    } else if (celula == 1 && (vizinhos < 2 || vizinhos > 3)) {
                        proximaGeracao[linha][coluna] = 0;
                    } else {
                        proximaGeracao[linha][coluna] = celula;
                    }
                }
            }
            matriz = proximaGeracao;
            clear();
            imprimirMatriz(matriz);
            sleep();



        }while(contadorDeVidas(matriz) != 0 );

    }

    // método para dar um tempo entre cada impressão, para melhorar a visualização
    private static void sleep() throws InterruptedException {
        Thread.sleep(500);
    }

    // método para limpar o console
    private static void clear() {
        System.out.print("\033\143");

    }


}

// Esquema para varrer todos os vizinhos da celula

// matriz[l-1][c-1]; --> Superior Esquerdo (Diagonal)
// matriz[l-1][c];   --> Esquerda
// matriz[l-1][c+1]; --> Inferior Esquerdo (Diagonal)
// matriz[l][c+1];   --> Baixo
// matriz[l+1][c+1]; --> Inferior Direito (Diagonal)
// matriz[l+1][c];   --> Direita
// matriz[l+1][c-1]; --> Superior Direito (Diagonal)
// matriz[l][c-1];   --> Cima
