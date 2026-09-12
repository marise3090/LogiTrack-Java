package com.mycompany.logitrack.persistencia;

import com.mycompany.logitrack.modelo.CentroDistribucion;
import com.mycompany.logitrack.modelo.ColaDinamica;
import com.mycompany.logitrack.modelo.Entrega;
import com.mycompany.logitrack.modelo.ListaSimpleCentros;
import com.mycompany.logitrack.modelo.Paquete;
import com.mycompany.logitrack.modelo.PilaDinamica;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersistenciaJSON {

    private static final Path ARCHIVO = Path.of("datos_logitrack.json");

    public static void guardar(ListaSimpleCentros listaCentros,
            ColaDinamica colaDespacho,
            PilaDinamica historialEntregas) throws IOException {

        StringBuilder json = new StringBuilder();
        json.append("{\n");
        guardarCentros(json, listaCentros);
        json.append(",\n");
        guardarCola(json, colaDespacho);
        json.append(",\n");
        guardarHistorial(json, historialEntregas);
        json.append("\n}\n");

        Files.writeString(ARCHIVO, json.toString(), StandardCharsets.UTF_8);
    }

    private static void guardarCentros(StringBuilder json, ListaSimpleCentros listaCentros) {
        json.append("  \"centros\": [\n");
        List<CentroDistribucion> centros = listaCentros.obtenerCentros();

        for (int i = 0; i < centros.size(); i++) {
            CentroDistribucion centro = centros.get(i);
            json.append("    {\n");
            json.append("      \"id\": ").append(centro.getId()).append(",\n");
            json.append("      \"nombre\": \"").append(escapar(centro.getNombre())).append("\",\n");
            json.append("      \"ubicacion\": \"").append(escapar(centro.getUbicacion())).append("\",\n");
            json.append("      \"paquetes\": [\n");

            List<Paquete> paquetes = centro.getListaPaquetes().obtenerPaquetes();
            for (int j = 0; j < paquetes.size(); j++) {
                agregarPaqueteJSON(json, paquetes.get(j), "        ");
                if (j < paquetes.size() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }

            json.append("      ]\n");
            json.append("    }");
            if (i < centros.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("  ]");
    }

    private static void guardarCola(StringBuilder json, ColaDinamica colaDespacho) {
        json.append("  \"colaDespacho\": [\n");
        List<Paquete> paquetes = colaDespacho.obtenerPaquetes();
        for (int i = 0; i < paquetes.size(); i++) {
            agregarPaqueteJSON(json, paquetes.get(i), "    ");
            if (i < paquetes.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("  ]");
    }

    private static void guardarHistorial(StringBuilder json, PilaDinamica historialEntregas) {
        json.append("  \"historialEntregas\": [\n");
        List<Entrega> entregas = historialEntregas.obtenerEntregas();
        for (int i = 0; i < entregas.size(); i++) {
            Entrega entrega = entregas.get(i);
            json.append("    {\n");
            json.append("      \"idPaquete\": ").append(entrega.getIdPaquete()).append(",\n");
            json.append("      \"transportista\": \"").append(escapar(entrega.getTransportista())).append("\",\n");
            json.append("      \"fecha\": \"").append(escapar(entrega.getFecha())).append("\",\n");
            json.append("      \"resultado\": \"").append(escapar(entrega.getResultado())).append("\"\n");
            json.append("    }");
            if (i < entregas.size() - 1) {
                json.append(",");
            }
            json.append("\n");
        }
        json.append("  ]");
    }

    private static void agregarPaqueteJSON(StringBuilder json, Paquete paquete, String sangria) {
        json.append(sangria).append("{\n");
        json.append(sangria).append("  \"id\": ").append(paquete.getId()).append(",\n");
        json.append(sangria).append("  \"descripcion\": \"").append(escapar(paquete.getDescripcion())).append("\",\n");
        json.append(sangria).append("  \"destino\": \"").append(escapar(paquete.getDestino())).append("\",\n");
        json.append(sangria).append("  \"estado\": \"").append(escapar(paquete.getEstado())).append("\"\n");
        json.append(sangria).append("}");
    }

    public static void cargar(ListaSimpleCentros listaCentros,
            ColaDinamica colaDespacho,
            PilaDinamica historialEntregas) throws IOException {

        if (!Files.exists(ARCHIVO)) {
            return;
        }

        String json = Files.readString(ARCHIVO, StandardCharsets.UTF_8);
        listaCentros.limpiar();
        colaDespacho.limpiar();
        historialEntregas.limpiar();

        cargarCentros(json, listaCentros);
        cargarCola(json, colaDespacho);
        cargarHistorial(json, historialEntregas);
    }

    private static void cargarCentros(String json, ListaSimpleCentros listaCentros) {
        String contenidoCentros = extraerArreglo(json, "centros");

        for (String objetoCentro : separarObjetos(contenidoCentros)) {
            CentroDistribucion centro = new CentroDistribucion(
                    extraerEntero(objetoCentro, "id"),
                    extraerTexto(objetoCentro, "nombre"),
                    extraerTexto(objetoCentro, "ubicacion")
            );

            String contenidoPaquetes = extraerArreglo(objetoCentro, "paquetes");
            for (String objetoPaquete : separarObjetos(contenidoPaquetes)) {
                centro.getListaPaquetes().agregar(crearPaquete(objetoPaquete));
            }
            centro.getListaPaquetes().irAlInicio();
            listaCentros.agregar(centro);
        }
    }

    private static void cargarCola(String json, ColaDinamica colaDespacho) {
        String contenidoCola = extraerArreglo(json, "colaDespacho");
        for (String objetoPaquete : separarObjetos(contenidoCola)) {
            colaDespacho.encolar(crearPaquete(objetoPaquete));
        }
    }

    private static void cargarHistorial(String json, PilaDinamica historialEntregas) {
        String contenidoHistorial = extraerArreglo(json, "historialEntregas");
        List<String> objetos = separarObjetos(contenidoHistorial);

        // El archivo guarda primero la cima. Se recorre al revés para reconstruir el mismo LIFO.
        for (int i = objetos.size() - 1; i >= 0; i--) {
            String objetoEntrega = objetos.get(i);
            historialEntregas.apilar(new Entrega(
                    extraerEntero(objetoEntrega, "idPaquete"),
                    extraerTexto(objetoEntrega, "transportista"),
                    extraerTexto(objetoEntrega, "fecha"),
                    extraerTexto(objetoEntrega, "resultado")
            ));
        }
    }

    private static Paquete crearPaquete(String objetoPaquete) {
        return new Paquete(
                extraerEntero(objetoPaquete, "id"),
                extraerTexto(objetoPaquete, "descripcion"),
                extraerTexto(objetoPaquete, "destino"),
                extraerTexto(objetoPaquete, "estado")
        );
    }

    public static boolean existeArchivo() {
        return Files.exists(ARCHIVO);
    }

    public static String obtenerRutaArchivo() {
        return ARCHIVO.toAbsolutePath().toString();
    }

    private static String escapar(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    private static String desescapar(String texto) {
        StringBuilder resultado = new StringBuilder();
        boolean escape = false;
        for (int i = 0; i < texto.length(); i++) {
            char caracter = texto.charAt(i);
            if (escape) {
                switch (caracter) {
                    case 'n': resultado.append('\n'); break;
                    case 'r': resultado.append('\r'); break;
                    case 't': resultado.append('\t'); break;
                    case '"': resultado.append('"'); break;
                    case '\\': resultado.append('\\'); break;
                    default: resultado.append(caracter);
                }
                escape = false;
            } else if (caracter == '\\') {
                escape = true;
            } else {
                resultado.append(caracter);
            }
        }
        if (escape) {
            resultado.append('\\');
        }
        return resultado.toString();
    }

    private static int extraerEntero(String objeto, String propiedad) {
        Pattern patron = Pattern.compile("\"" + Pattern.quote(propiedad) + "\"\\s*:\\s*(-?\\d+)");
        Matcher matcher = patron.matcher(objeto);
        if (!matcher.find()) {
            throw new IllegalArgumentException("No se encontró la propiedad numérica: " + propiedad);
        }
        return Integer.parseInt(matcher.group(1));
    }

    private static String extraerTexto(String objeto, String propiedad) {
        Pattern patron = Pattern.compile("\"" + Pattern.quote(propiedad)
                + "\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\"");
        Matcher matcher = patron.matcher(objeto);
        if (!matcher.find()) {
            throw new IllegalArgumentException("No se encontró la propiedad: " + propiedad);
        }
        return desescapar(matcher.group(1));
    }

    private static String extraerArreglo(String texto, String propiedad) {
        String buscado = "\"" + propiedad + "\"";
        int posicionPropiedad = texto.indexOf(buscado);
        if (posicionPropiedad == -1) {
            return "";
        }
        int inicio = texto.indexOf('[', posicionPropiedad);
        if (inicio == -1) {
            return "";
        }

        int profundidad = 0;
        boolean dentroTexto = false;
        boolean escape = false;
        for (int i = inicio; i < texto.length(); i++) {
            char caracter = texto.charAt(i);
            if (dentroTexto) {
                if (escape) {
                    escape = false;
                } else if (caracter == '\\') {
                    escape = true;
                } else if (caracter == '"') {
                    dentroTexto = false;
                }
                continue;
            }
            if (caracter == '"') {
                dentroTexto = true;
            } else if (caracter == '[') {
                profundidad++;
            } else if (caracter == ']') {
                profundidad--;
                if (profundidad == 0) {
                    return texto.substring(inicio + 1, i);
                }
            }
        }
        return "";
    }

    private static List<String> separarObjetos(String contenido) {
        List<String> objetos = new ArrayList<>();
        int profundidad = 0;
        int inicioObjeto = -1;
        boolean dentroTexto = false;
        boolean escape = false;

        for (int i = 0; i < contenido.length(); i++) {
            char caracter = contenido.charAt(i);
            if (dentroTexto) {
                if (escape) {
                    escape = false;
                } else if (caracter == '\\') {
                    escape = true;
                } else if (caracter == '"') {
                    dentroTexto = false;
                }
                continue;
            }
            if (caracter == '"') {
                dentroTexto = true;
            } else if (caracter == '{') {
                if (profundidad == 0) {
                    inicioObjeto = i;
                }
                profundidad++;
            } else if (caracter == '}') {
                profundidad--;
                if (profundidad == 0 && inicioObjeto != -1) {
                    objetos.add(contenido.substring(inicioObjeto, i + 1));
                    inicioObjeto = -1;
                }
            }
        }
        return objetos;
    }
}
