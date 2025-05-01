
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TodasLasSoluciones {

    static final int N = 6;
    static int[][] tablero = new int[N][N];
    static int totalSoluciones = 0;

    // Movimientos posibles del caballo
    static final int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
    static final int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};

    public static void main(String[] args) {
        // Inicializar tablero
        for (int i = 0; i < N; i++)
            Arrays.fill(tablero[i], 0);

        // Posición inicial aleatoria
        Random rand = new Random();
        int startX = rand.nextInt(N);
        int startY = rand.nextInt(N);

        System.out.println("Posición inicial: (" + startX + ", " + startY + ")");

        // Marcar la primera casilla
        tablero[startX][startY] = 1;

        long inicio = System.currentTimeMillis();

        // Comenzar backtracking
        resolver(startX, startY, 2);

        long fin = System.currentTimeMillis();

        System.out.println("Total de soluciones: " + totalSoluciones);
        System.out.println("Tiempo de ejecución: " + (fin - inicio) + " ms");
    }

    static void resolver(int x, int y, int paso) {
        if (paso > N * N) {
            totalSoluciones++;
            return;
        }

        List<int[]> movimientosOrdenados = generarMovimientosOrdenados(x, y);

        for (int[] move : movimientosOrdenados) {
            int nx = move[0];
            int ny = move[1];
            tablero[nx][ny] = paso;
            resolver(nx, ny, paso + 1);
            tablero[nx][ny] = 0; // backtrack
        }
    }

    // Genera los movimientos válidos desde (x, y) ordenados por menor grado (estilo Warnsdorff)
    static List<int[]> generarMovimientosOrdenados(int x, int y) {
        List<int[]> moves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (esValido(nx, ny)) {
                int grado = contarGrado(nx, ny);
                moves.add(new int[]{nx, ny, grado});
            }
        }

        // Ordenar por el grado (número de movimientos futuros posibles)
        moves.sort(Comparator.comparingInt(a -> a[2]));

        // Retornar solo coordenadas (sin el grado)
        List<int[]> ordenados = new ArrayList<>();
        for (int[] move : moves) {
            ordenados.add(new int[]{move[0], move[1]});
        }

        return ordenados;
    }

    // Cuenta cuántos movimientos válidos hay desde (x, y)
    static int contarGrado(int x, int y) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (esValido(nx, ny)) count++;
        }
        return count;
    }

    // Verifica que esté dentro del tablero y no visitado
    static boolean esValido(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N && tablero[x][y] == 0;
    }
}


