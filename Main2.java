public class Main2 {
    int tamanioPar = 130;
    int tamanioProceso = 30;

    public void verificarEspacio() {
        System.out.println("Particion: " + tamanioPar);
        System.out.println("Tamanio Proceso: " + tamanioProceso);

        if (tamanioPar > tamanioProceso) {
            System.out.println("Hay espacio");
        }
    }

    public static void main(String[] args) {
        Main2 main2 = new Main2();
        main2.verificarEspacio();
    }
}
