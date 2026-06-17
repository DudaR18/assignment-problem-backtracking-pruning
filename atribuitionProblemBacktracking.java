// Backtracking
// Complexidade - pior caso: O(n!)  e melhor caso O(n)
public class atribuitionProblemBacktracking {

    static int melhorCusto = Integer.MAX_VALUE;

    static int[] melhorSolucao;

    public static void backtracking(
            int[][] custos,
            int pessoa,
            boolean[] tarefaUsada,
            int[] solucaoAtual,
            int custoAtual) {

        int n = custos.length;

        // PODA
        if (custoAtual >= melhorCusto) {
            return;
        }

        // SOLUÇÃO COMPLETA
        if (pessoa == n) {

            melhorCusto = custoAtual;

            melhorSolucao = solucaoAtual.clone();

            return;
        }

        // TESTA TODAS AS TAREFAS
        for (int tarefa = 0; tarefa < n; tarefa++) {

            if (!tarefaUsada[tarefa]) {

                tarefaUsada[tarefa] = true;

                solucaoAtual[pessoa] = tarefa;

                backtracking(
                        custos,
                        pessoa + 1,
                        tarefaUsada,
                        solucaoAtual,
                        custoAtual + custos[pessoa][tarefa]
                );

                tarefaUsada[tarefa] = false;
            }
        }
    }

    public static void main(String[] args) {

        int[][] custos = {
                {9, 2, 7, 8},
                {6, 4, 3, 7},
                {5, 8, 1, 8},
                {7, 6, 9, 4}
        };

        int n = custos.length;

        melhorSolucao = new int[n];

        backtracking(
                custos,
                0,
                new boolean[n],
                new int[n],
                0
        );

        System.out.println("Melhor custo: "
                + melhorCusto);

        System.out.println();

        for (int pessoa = 0;
             pessoa < n;
             pessoa++) {

            System.out.println(
                    "Pessoa "
                            + (char) ('A' + pessoa)
                            + " -> Tarefa "
                            + (melhorSolucao[pessoa] + 1)
            );
        }
    }
}