package com.github.fadegor05.cli;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class InstanceDirectoryCli {

    public static String getInstanceDirectoryCli() {
        while (true){
            System.out.println("Введите путь до вашей директории со сборкой (Например: C:/MCData/Instances/CubeShield/.minecraft)");
            Scanner scanner = new Scanner(System.in);
            Path path = Path.of(scanner.nextLine()).toAbsolutePath();
            if (Files.isDirectory(path)) {
                System.out.println("Путь до вашей директории со сборкой был успешно сохранен!");
                return path.toString();
            }
            System.out.println("Данного пути не существует, попробуйте еще раз...");
        }

    }

}
