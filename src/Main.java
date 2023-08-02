public class Main {
    public static void main(String[] args) {
        LeitorEscritor leitorEscritor = new LeitorEscritor();

        // Criando threads para os leitores
        for (int i = 0; i < 5; i++) {
            Thread leitorThread = new Thread(() -> {
                try {
                    leitorEscritor.leitor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            leitorThread.start();
        }

        // Criando threads para os escritores
        for (int i = 0; i < 3; i++) {
            Thread escritorThread = new Thread(() -> {
                try {
                    leitorEscritor.escritor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            escritorThread.start();
        }
    }
}
