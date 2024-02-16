package com.github.fadegor05.cli;

import com.github.lalyos.jfiglet.FigletFont;
import java.io.IOException;

import static com.github.fadegor05.Main.CUBESTART_VERSION;

public class CreditsOutCli {
    public static void CreditsOutCli() {
        String asciiArt1;
        try {
            asciiArt1 = FigletFont.convertOneLine("CubeStart");
            System.out.println(asciiArt1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String text = String.format("v%s / CubeStudio / by fadegor05 (Lyroq1s)%n", CUBESTART_VERSION);
        System.out.println(text);
    }
}
