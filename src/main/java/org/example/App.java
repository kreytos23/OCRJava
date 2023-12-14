package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class App {
    public static void main( String[] args ){
        String filePath = "src/main/java/org/documents/MAMC0111103C9.pdf";
        try {
            PDDocument document = PDDocument.load(new File(filePath));
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);
            document.close();

            // Regex patterns para los c
            // ampos requeridos
            Pattern nombrePattern = Pattern.compile("Nombre \\(s\\):\\s*(.+)");
            Pattern apellidoPattern = Pattern.compile("Primer Apellido:\\s*([\\p{L} ]+)\\s*Segundo Apellido:\\s*([\\p{L} ]+)");
            Pattern curpPattern = Pattern.compile("CURP:\\s*([A-Z0-9]+)");
            Pattern rfcPattern = Pattern.compile("RFC:\\s*([A-Z0-9]+)");
            Pattern regimenPattern = Pattern.compile("Regímenes:\\s*\\R\\s*(.+?)\\s+\\d{2}/\\d{2}/\\d{4}", Pattern.DOTALL);

            System.out.println(text);

            // Buscar y mostrar los datos
            Matcher matcher = nombrePattern.matcher(text);
            if (matcher.find()) {
                System.out.println("Nombre: " + matcher.group(1));
            }
            matcher = apellidoPattern.matcher(text);
            if (matcher.find()) {
                System.out.println("Apellido: " + matcher.group(1) + " " + matcher.group(2));
            }
            matcher = curpPattern.matcher(text);
            if (matcher.find()) {
                System.out.println("CURP: " + matcher.group(1));
            }
            matcher = rfcPattern.matcher(text);
            if (matcher.find()) {
                System.out.println("RFC: " + matcher.group(1));
            }
            matcher = regimenPattern.matcher(text);
            if (matcher.find()) {
                String[] lines = matcher.group(1).split("\\R"); // "\\R" es una expresión regular que coincide con cualquier salto de línea
                String regimen = lines[lines.length - 1].trim();
                System.out.println("Régimen: " + regimen);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
