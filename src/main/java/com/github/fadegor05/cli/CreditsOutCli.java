package com.github.fadegor05.cli;

import com.github.lalyos.jfiglet.FigletFont;
import com.github.fadegor05.Main;
import java.io.IOException;

import static com.github.fadegor05.Main.CUBESTART_VERSION;

public class CreditsOutCli {
    public static void CreditsOutCli() throws IOException {
        String asciiArt1 = FigletFont.convertOneLine("CubeStart");
        System.out.println(asciiArt1);
        String text = String.format("v%s / CubeStudio / by fadegor05 (Lyroq1s)", CUBESTART_VERSION);
        System.out.println(text);
    }
}
