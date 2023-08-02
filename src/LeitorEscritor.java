import java.util.concurrent.Semaphore;

public class LeitorEscritor {
    private int leitoresAtivos = 0;
    private int escritoresEsperando = 0;
    private Semaphore mutexLeitores = new Semaphore(1);
    private Semaphore mutexEscritor = new Semaphore(1);
    private Semaphore db = new Semaphore(1);

    public void leitor() throws InterruptedException {
        mutexLeitores.acquire();
        leitoresAtivos++;
        if (leitoresAtivos == 1) {
            mutexEscritor.acquire(); // Bloqueia escritores quando o primeiro leitor chega
        }
        mutexLeitores.release();

        // Leitura do recurso compartilhado
        lerRecurso();

        mutexLeitores.acquire();
        leitoresAtivos--;
        if (leitoresAtivos == 0) {
            mutexEscritor.release(); // Libera escritores quando não há mais leitores
        }
        mutexLeitores.release();
    }

    public void escritor() throws InterruptedException {
        mutexEscritor.acquire(); // Espera sua vez para escrever
        escritoresEsperando++;
        if (escritoresEsperando == 1) {
            mutexLeitores.acquire(); // Bloqueia novos leitores quando há um escritor esperando
        }
        mutexEscritor.release();

        db.acquire(); // Bloqueia todos os outros leitores e escritores

        // Escrita no recurso compartilhado
        escreverRecurso();

        db.release(); // Libera o recurso compartilhado para outros leitores e escritores

        mutexEscritor.acquire();
        escritoresEsperando--;
        if (escritoresEsperando == 0) {
            mutexLeitores.release(); // Libera leitores quando não há mais escritores esperando
        }
        mutexEscritor.release();
    }

    private void lerRecurso() throws InterruptedException {
        // Lógica de leitura do recurso compartilhado
        System.out.println("Leitor está lendo o recurso.");
        Thread.sleep(1000);
    }

    private void escreverRecurso() throws InterruptedException {
        // Lógica de escrita no recurso compartilhado
        System.out.println("Escritor está escrevendo no recurso.");
        Thread.sleep(1000);
    }
}
