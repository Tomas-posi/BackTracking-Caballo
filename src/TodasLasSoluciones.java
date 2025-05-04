import java.util.*;

public class TodasLasSoluciones{

    static final int N = 6; // Tamaño del tablero (6x6 para pruebas)
    static int[][] tablero = new int[N][N];
    
    // Movimientos posibles del caballo
    static int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
    static int[] dy = {1, 2, 2, 1, -1, -2, -2, -1};
    
    // Lista para guardar las soluciones encontradas
    static ArrayList<int[][]> soluciones = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        // Inicializar tablero a 0
        for (int i = 0; i < N; i++)
            Arrays.fill(tablero[i], 0);

        // Posición inicial aleatoria
        Random rand = new Random();
        int x = rand.nextInt(N);
        int y = rand.nextInt(N);
        tablero[x][y] = 1; // Primer paso

        // Llamar a la función de backtracking para encontrar todas las soluciones
        backtrack(x, y, 2);
        
        // Mostrar todas las soluciones encontradas
        System.out.println("Número de soluciones encontradas: " + soluciones.size());
        for (int[][] solucion : soluciones) {
            imprimirTablero(solucion);
            System.out.println("======================");
        }
    }

    // Función de backtracking para encontrar todas las soluciones
    static void backtrack(int x, int y, int paso) {

        
        // Si hemos recorrido todas las casillas, es una solución válida
        if (paso == N * N + 1) {
            soluciones.add(clonarTablero()); // Guardar la solución
            System.out.println("Solución encontrada:");
            imprimirTablero(tablero); // Mostrar la solución encontrada
            return;
        }

        // Lista para almacenar los movimientos posibles con sus grados de Warnsdorff
        ArrayList<int[]> posiblesMovimientos = new ArrayList<>();
        
        // Intentar todos los movimientos posibles y calcular su "grado de Warnsdorff"
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (esValido(nx, ny)) {
                int grado = contarMovimientosDisponibles(nx, ny);
                posiblesMovimientos.add(new int[]{nx, ny, grado});
            }
        }



        // Ordenar los movimientos por grado de Warnsdorff (de menor a mayor)
        posiblesMovimientos.sort((a, b) -> Integer.compare(a[2], b[2]));

        // Intentar los movimientos en el orden de menor a mayor grado
        for (int[] movimiento : posiblesMovimientos) {
            int nx = movimiento[0];
            int ny = movimiento[1];
            tablero[nx][ny] = paso; // Marcar la casilla como visitada

            // Llamada recursiva para el siguiente paso
            backtrack(nx, ny, paso + 1);
            
            // Retroceder: desmarcar la casilla y continuar
            tablero[nx][ny] = 0; 
        }
    }

    // Cuenta movimientos válidos desde una casilla dada
    static int contarMovimientosDisponibles(int x, int y) {
        int count = 0;
        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];
            if (esValido(nx, ny)) count++;
        }
        return count;
    }

    // Verifica si la casilla está dentro del tablero y no ha sido visitada
    static boolean esValido(int x, int y) {
        return x >= 0 && y >= 0 && x < N && y < N && tablero[x][y] == 0;
    }

    // Clona el tablero actual para guardar una solución
    static int[][] clonarTablero() {
        int[][] copia = new int[N][N];
        for (int i = 0; i < N; i++) {
            copia[i] = Arrays.copyOf(tablero[i], N);
        }
        return copia;
    }

    // Imprime el tablero en consola
    static void imprimirTablero(int[][] tablero) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tablero[i][j] == 0) {
                    System.out.print(" . "); // Casilla no visitada
                } else {
                    System.out.printf("%2d ", tablero[i][j]); // Paso ya hecho
                }
            }
            System.out.println();
        }
    }
}
