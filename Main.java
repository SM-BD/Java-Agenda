import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Main {

  public static void main(String[] args) {
    BD();
  }

  //PRIMERAMENTE, LA VIDA DEL PROGRAMA

  public static void BD() {
    String escritorio = System.getProperty("user.home") + "/Desktop/";
    File archivo = new File(escritorio + "J-E-N-O-V-A.txt");
    if (archivo.exists()) {
      try {
        Scanner lectorArchivo = new Scanner(archivo);
        String nombreUsuario = lectorArchivo.nextLine().split(": ")[1];
        System.out.println("Bienvenido de nuevo, " + nombreUsuario + ".");
        lectorArchivo.close();
        menu(nombreUsuario);
      } catch (IOException e) {
        System.out.println("Ha ocurrido un error al leer el archivo.");
        e.printStackTrace();
      }
    } else {
      primerUso();
    }
  }

  //SI NO TIENES LA BASE DE DATOS

  public static void primerUso() {
    Scanner escaner = new Scanner(System.in);
    System.out.println(
      "Parece que es tu primera vez usando este programa, empezemos por lo primero, ¿Cuál es tu nombre?"
    );
    String nombreUsuario = escaner.nextLine();
    System.out.println("Mucho gusto, " + nombreUsuario + ".");
    espera(700);
    System.out.println("Empezemos agregando unos 3 contactos a tu agenda");
    espera(700);

    try {
      FileWriter escritor = new FileWriter(
        System.getProperty("user.home") + "/Desktop/" + "J-E-N-O-V-A.txt",
        true
      );
      escritor.write("Nombre de usuario: " + nombreUsuario + "\n");

      for (int i = 0; i < 3; i++) {
        System.out.println("Ingrese el nombre del contacto " + (i + 1) + ":");
        String nombre = escaner.nextLine();
        System.out.println("Ingrese la dirección del contacto:");
        String direccion = escaner.nextLine();
        System.out.println("Ingrese el número de celular del contacto:");
        String celular = escaner.nextLine();
        while (!celular.matches("[0-9]+")) {
          System.out.println(
            "El número de celular solo puede contener números. Inténtalo de nuevo:"
          );
          celular = escaner.nextLine();
        }
        System.out.println(
          "Ingrese una descripción para el contacto (opcional):"
        );
        String descripcion = escaner.nextLine();

        escritor.write("Contacto " + (i + 1) + ":\n");
        escritor.write("Nombre: " + nombre + "\n");
        escritor.write("Dirección: " + direccion + "\n");
        escritor.write("Celular: " + celular + "\n");
        if (i == 2) {
          escritor.write("Descripción: " + descripcion + "\n");
        } else {
          escritor.write("Descripción: " + descripcion + "\n\n");
        }
      }

      System.out.println("Gracias, ahora ya puedes usar el programa.");
      espera(700);
      escritor.close();
      menu(nombreUsuario);
    } catch (IOException e) {
      System.out.println("Ha ocurrido un error al escribir en el archivo.");
      e.printStackTrace();
    }
  }

  ///MENU EN SI
  public static void menu(String nombreUsuario) {
    int opcion;
    String[] aleatorio = {
      "Sayonara",
      "Ja ne",
      "Mata ne",
      "Mata ashita",
      "Gokigenyo",
      "Oyasumi",
    };
    Random rand = new Random();
    do {
      System.out.println("\nMenú:");
      System.out.println("1. Ver contactos");
      System.out.println("2. Agregar contactos");
      System.out.println("3. Music?");
      System.out.println("4. Salir");
      System.out.print("Elige una opción, " + nombreUsuario + ": ");
      Scanner escaner = new Scanner(System.in);
      opcion = escaner.nextInt();

      switch (opcion) {
        case 1:
          verContactos();
          break;
        case 2:
          agregarContacto();
          break;
        case 3:
          playMusic();
          break;
        case 4:
          System.out.println(
            "Saliendo del programa... " +
            aleatorio[rand.nextInt(aleatorio.length)]
          );
          break;
        default:
          System.out.println(
            "\nOpción no válida. Por favor, elige una opción del menú."
          );
          espera(700);
      }
    } while (opcion != 4);
  }

  //Ver y modificar contactos
  public static void verContactos() {
    try {
      File archivo = new File(
        System.getProperty("user.home") + "/Desktop/" + "J-E-N-O-V-A.txt"
      );
      Scanner lectorArchivo = new Scanner(archivo);
      lectorArchivo.nextLine(); // Saltar la línea del nombre de usuario

      System.out.println("\n¿Deseas filtrar los contactos? (s/n)");
      Scanner escaner = new Scanner(System.in);
      String respuesta = escaner.nextLine();
      if (respuesta.equalsIgnoreCase("s")) {
        filtrarContactos();
      } else {
        while (lectorArchivo.hasNextLine()) {
          System.out.println("\n" + lectorArchivo.nextLine());
          System.out.println(lectorArchivo.nextLine());
          System.out.println(lectorArchivo.nextLine());
          System.out.println(lectorArchivo.nextLine());
          System.out.println(lectorArchivo.nextLine());
          if (lectorArchivo.hasNextLine()) { // Saltar la línea vacía
            lectorArchivo.nextLine();
          }
        }
      }
      lectorArchivo.close();

      System.out.println("\n¿Deseas modificar algún contacto? (s/n)");
      respuesta = escaner.nextLine();
      if (respuesta.equalsIgnoreCase("s")) {
        System.out.println(
          "Ingresa el número del contacto que deseas modificar:"
        );
        int numeroContacto = escaner.nextInt();
        modificarContacto(numeroContacto);
      }
    } catch (IOException e) {
      System.out.println("Ha ocurrido un error al leer el archivo.");
      e.printStackTrace();
    }
  }

  public static void modificarContacto(int numeroContacto) {
    try {
      File archivo = new File(
        System.getProperty("user.home") + "/Desktop/" + "J-E-N-O-V-A.txt"
      );
      Scanner lectorArchivo = new Scanner(archivo);
      List<String> lineas = new ArrayList<>();
      while (lectorArchivo.hasNextLine()) {
        lineas.add(lectorArchivo.nextLine());
      }
      lectorArchivo.close();

      int indiceInicio = (numeroContacto - 1) * 6 + 1;
      if (indiceInicio < lineas.size()) {
        Scanner escaner = new Scanner(System.in);
        System.out.println("Ingresa el nuevo nombre del contacto:");
        lineas.set(indiceInicio + 1, "Nombre: " + escaner.nextLine());
        System.out.println("Ingresa la nueva dirección del contacto:");
        lineas.set(indiceInicio + 2, "Dirección: " + escaner.nextLine());
        System.out.println("Ingresa el nuevo número de celular del contacto:");
        lineas.set(indiceInicio + 3, "Celular: " + escaner.nextLine());
        System.out.println("Ingresa la nueva descripción del contacto:");
        lineas.set(indiceInicio + 4, "Descripción: " + escaner.nextLine());

        FileWriter escritorArchivo = new FileWriter(archivo);
        for (String linea : lineas) {
          escritorArchivo.write(linea + "\n");
        }
        escritorArchivo.close();
        espera(700);
      } else {
        System.out.println("El número de contacto ingresado no es válido.");
      }
    } catch (IOException e) {
      System.out.println("Ha ocurrido un error al leer o escribir el archivo.");
      e.printStackTrace();
    }
  }

  //Agregar contactos
  public static void agregarContacto() {
    try {
      File archivo = new File(
        System.getProperty("user.home") + "/Desktop/" + "J-E-N-O-V-A.txt"
      );
      Scanner lectorArchivo = new Scanner(archivo);
      int numeroContacto = 0;
      while (lectorArchivo.hasNextLine()) {
        lectorArchivo.nextLine();
        numeroContacto++;
      }
      lectorArchivo.close();
      numeroContacto = numeroContacto / 6 + 1;

      FileWriter escritorArchivo = new FileWriter(archivo, true);

      Scanner escaner = new Scanner(System.in);
      escritorArchivo.write("\nContacto " + numeroContacto + ":\n");
      System.out.println("Ingresa el nombre del nuevo contacto:");
      escritorArchivo.write("Nombre: " + escaner.nextLine() + "\n");
      System.out.println("Ingresa la dirección del nuevo contacto:");
      escritorArchivo.write("Dirección: " + escaner.nextLine() + "\n");
      String celular;
      System.out.println("Ingresa el número de celular del nuevo contacto:");
      celular = escaner.nextLine();
      while (!celular.matches("[0-9]+")) {
        System.out.println(
          "El número de celular solo puede contener números. Inténtalo de nuevo:"
        );
        celular = escaner.nextLine();
      }
      escritorArchivo.write("Celular: " + celular + "\n");
      System.out.println("Ingresa la descripción del nuevo contacto:");
      escritorArchivo.write("Descripción: " + escaner.nextLine() + "\n");

      escritorArchivo.close();
    } catch (IOException e) {
      System.out.println("Ha ocurrido un error al leer o escribir el archivo.");
      e.printStackTrace();
    }
  }

  //Metodo para filtrar los contactos
  public static void filtrarContactos() {
    try {
      File archivo = new File(
        System.getProperty("user.home") + "/Desktop/" + "J-E-N-O-V-A.txt"
      );
      Scanner lectorArchivo = new Scanner(archivo);
      lectorArchivo.nextLine(); // Saltar la línea del nombre de usuario

      System.out.println(
        "¿Qué tipo de filtro deseas aplicar? (nombre/direccion/celular)"
      );
      Scanner escaner = new Scanner(System.in);
      String tipoFiltro = escaner.nextLine();

      System.out.println("Ingresa el filtro:");
      String filtro = escaner.nextLine();

      while (lectorArchivo.hasNextLine()) {
        String numeroContacto = lectorArchivo.nextLine();
        String nombre = lectorArchivo.nextLine().split(": ")[1];
        String direccion = lectorArchivo.nextLine().split(": ")[1];
        String celular = lectorArchivo.nextLine().split(": ")[1];
        String descripcion = lectorArchivo.nextLine().split(": ")[1];

        switch (tipoFiltro.toLowerCase()) {
          case "nombre":
            if (nombre.startsWith(filtro)) {
              imprimirContacto(
                numeroContacto,
                nombre,
                direccion,
                celular,
                descripcion
              );
            }
            break;
          case "direccion":
            if (direccion.startsWith(filtro)) {
              imprimirContacto(
                numeroContacto,
                nombre,
                direccion,
                celular,
                descripcion
              );
            }
            break;
          case "celular":
            if (celular.startsWith(filtro)) {
              imprimirContacto(
                numeroContacto,
                nombre,
                direccion,
                celular,
                descripcion
              );
            }
            break;
          default:
            System.out.println("Tipo de filtro no reconocido.");
            return;
        }

        if (lectorArchivo.hasNextLine()) { // Saltar la línea vacía
          lectorArchivo.nextLine();
        }
      }
      lectorArchivo.close();
    } catch (IOException e) {
      System.out.println("Ha ocurrido un error al leer el archivo.");
      e.printStackTrace();
    }
  }

  public static void imprimirContacto(
    String numeroContacto,
    String nombre,
    String direccion,
    String celular,
    String descripcion
  ) {
    System.out.println("\n" + numeroContacto);
    System.out.println("Nombre: " + nombre);
    System.out.println("Dirección: " + direccion);
    System.out.println("Celular: " + celular);
    System.out.println("Descripción: " + descripcion);
  }

  //Esperar, hace que el programa quede mas elegante UwU
  public static void espera(int tiempo) {
    try {
      Thread.sleep(tiempo);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  //Musica pq no
  public static void playMusic() {
    try {
      String musicFolder = System.getProperty("user.dir") + "/Music/";
      Random rand = new Random();
      int musicFileNumber = rand.nextInt(3) + 1;
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(
        new File(musicFolder + musicFileNumber + ".wav").getAbsoluteFile()
      );
      Clip clip = AudioSystem.getClip();
      clip.open(audioInputStream);
      clip.start();
    } catch (Exception ex) {
      System.out.println("Error with playing sound.");
      ex.printStackTrace();
    }
  }
}
