package ControledeMatricula;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class controleDVMatricula {
    private static URL resorceEntrada;
    private static Path pathEntrada;
    private static URL resorceSaida;
    private static Path pathSaida;
    private static List<String> stringList;

    public static void main(String[] args) {
        try {
            resorceEntrada = getResource("/matriculasSemDV.txt");
            pathEntrada = getPath(resorceEntrada);

            resorceSaida = getResource("/matriculasComDV.txt");
            pathSaida = getPath(resorceSaida);

            stringList = getContetFile(pathEntrada);

            String matriculaComDv = "";

            for (String linha : stringList) {
                String[] digitosMatricula = linha.split("");

                matriculaComDv += getMatriculaComDv(linha, digitosMatricula) + "\n";
            }

            gravarMatricula(pathSaida, matriculaComDv);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }

        try {
            resorceEntrada = getResource("/matriculasParaVerificar.txt");
            pathEntrada = getPath(resorceEntrada);

            resorceSaida = getResource("/matriculasVerificadas.txt");
            pathSaida = getPath(resorceSaida);

            stringList = getContetFile(pathEntrada);

            String matriculaComDv = "";

            for (String linha : stringList) {
                String[] digitosMatricula = linha.split("");

                matriculaComDv += getMatriculaComDv(linha.substring(0, 4), digitosMatricula);

                if (matriculaComDv.equalsIgnoreCase(linha)) {
                    matriculaComDv += " verdadeiro\n";
                } else {
                    matriculaComDv += " falso\n";
                }
            }

            gravarMatricula(pathSaida, matriculaComDv);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static URL getResource(String file) {
        return controleDVMatricula.class.getResource(file);
    }

    private static Path getPath(URL resorce) throws URISyntaxException {
        return Paths.get(resorce.toURI());
    }

    private static List<String> getContetFile(Path pathEntrada) throws IOException {
        return Files.readAllLines(pathEntrada);
    }

    private static String getMatriculaComDv(String linha, String[] digitosMatricula) {
        int somaDigitosMatricula = (Integer.parseInt(digitosMatricula[0]) * 5)
                + (Integer.parseInt(digitosMatricula[1]) * 4)
                + (Integer.parseInt(digitosMatricula[2]) * 3)
                + (Integer.parseInt(digitosMatricula[3]) * 2);

        return linha + "-" + (Integer.toHexString(somaDigitosMatricula % 16)).toUpperCase();
    }

    private static void gravarMatricula(Path pathSaida, String matriculaComDv) throws IOException {
        Files.writeString(pathSaida,
                matriculaComDv + System.lineSeparator(),
                StandardOpenOption.TRUNCATE_EXISTING);
    }
}