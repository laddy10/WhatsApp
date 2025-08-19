package hooks;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import utils.EstadoPrueba;
import utils.WordAppium;

import java.util.ArrayList;
import java.util.List;

public class ReportHooks {

    private static List<String> pasosEjecutados = new ArrayList<>();
    private static String lineaUsada = "Sin datos";

    public static void registrarPaso(String paso) {
        pasosEjecutados.add(paso);
        ultimoPaso = paso;
    }

    public static void setLinea(String linea) {
        lineaUsada = linea;
    }

 /*   @AfterStep
    public void afterEachStep(Scenario scenario) {
        if (scenario.isFailed()) {
            EstadoPrueba.fallo = true;
            if (!pasosEjecutados.isEmpty()) {
                EstadoPrueba.pasoFallido = pasosEjecutados.get(pasosEjecutados.size() - 1);
            } else {
                EstadoPrueba.pasoFallido = "No identificado";
            }
        }
    } */


    @After
    public void generarReporte(Scenario scenario) {
        EstadoPrueba.fin = System.currentTimeMillis();
        long duracionTotalSegundos = (EstadoPrueba.fin - EstadoPrueba.inicio) / 1000;
        long minutos = duracionTotalSegundos / 60;
        long segundos = duracionTotalSegundos % 60;
        String duracionFormato = minutos + " min " + segundos + " seg";


        String estadoFinal = EstadoPrueba.fallo ? "FAILED" : "PASSED";
        String pasoFallido = EstadoPrueba.fallo ? EstadoPrueba.pasoFallido : null;

        WordAppium.generarReporte(
                scenario.getName(),
                pasosEjecutados.toArray(new String[0]),
                lineaUsada,
                duracionFormato,
                pasoFallido,
                estadoFinal
        );


        pasosEjecutados.clear();
        EstadoPrueba.fallo = false;
        EstadoPrueba.pasoFallido = "";
    }


    @Before
    public void beforeEachScenario() {
        EstadoPrueba.inicio = System.currentTimeMillis(); // Marca el inicio de la ejecución real
    }



    private static String ultimoPaso = "";



}
