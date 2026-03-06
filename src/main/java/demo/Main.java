package demo;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        // ВАЖНО: package-имя, а не путь
        FlatLaf.registerCustomDefaultsSource("demo.themes");

        FlatLightLaf.setup();

        SwingUtilities.invokeLater(() -> ComponentGallery.main(args));
    }
}
