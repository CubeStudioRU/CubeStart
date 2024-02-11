package com.github.fadegor05.cli;

import java.util.Scanner;

public class ExitWaitCli {
    public static void exitWaitCli() {
        System.out.println("\nНажмите любую клавишу, чтобы завершить программу...");
        Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();
        System.exit(0);
    }
}
