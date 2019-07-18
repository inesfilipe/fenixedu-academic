package org.fenixedu.academic.task;

import org.fenixedu.academic.domain.organizationalStructure.Unit;
import org.fenixedu.academic.domain.raides.DegreeClassification;
import org.fenixedu.academic.domain.raides.DegreeDesignation;
import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.scheduler.custom.CustomTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class AddNewDegreeDesignations extends CustomTask {

    @Override
    public void runTask() throws Exception {
        List<String> unitsCodes = getUnitsCodes();
        List<String> degreeDesignationsCodes = getDegreeDesignationsCodes();
        List<String> tecnicoCodes = Arrays.asList("0807", "0808", "1518", "1519");

        URL csvURL = new URL("https://gist.githubusercontent.com/inesfilipe/d873eb941c6cbaf61f18212a6e819f2f/raw/59c1d2af2e5d71e7aef97b85c3b9afe3603b948a/tbl_Grau_Estabelecimento_Curso.csv");
        BufferedReader br = getReaderFromURL(csvURL);

        String line = br.readLine(); // excluding header from analysis
        taskLog(line); //for my own reference - will probably delete later
        taskLog();

        Set<List<String>> newSet = parseFile(br);

        //FIXME: degree can belong to more than one unit - code is not "unique"
        taskLog("Degrees not in the system:");
        newSet.stream().filter(l -> unitsCodes.contains(l.get(1)) && !tecnicoCodes.contains(l.get(1)) && !degreeDesignationsCodes.contains(l.get(3))).forEach(this::createDegreeNotInSystem);
        taskLog();
    }

    private void createDegreeNotInSystem(List<String> degree) {
        if(getDegreeClassificationFromName(degree.get(0)) == null) {
            taskLog("ERROR - cannot add: " + degree.get(0) + " — " + degree.get(1) + " : " + degree.get(2) + " — " + degree.get(3) + " : " + degree.get(4));
            return;
        }

        taskLog(degree.get(0) + " — " + degree.get(1) + " : " + degree.get(2) + " — " + degree.get(3) + " : " + degree.get(4));
        Unit.readAllUnits().stream().filter(u -> u.getCode() != null && u.getCode().equals(degree.get(1))).findFirst()
                .ifPresent(u -> u.addDegreeDesignation(new DegreeDesignation(degree.get(3), degree.get(4), getDegreeClassificationFromName(degree.get(0)))));
    }

    //FIXME: DegreeClassification table has to be updated
    private DegreeClassification getDegreeClassificationFromName(String name) {
        switch(name) {
            case "Licenciatura bietápica":
                return DegreeClassification.readByCode("LB");
            case "Licenciatura 1.º ciclo":
                return DegreeClassification.readByCode("L1");
            case "Mestrado 2.º ciclo":
                return DegreeClassification.readByCode("M2");
            case "Licenciatura":
                return DegreeClassification.readByCode("L");
            case "Doutoramento 3.º ciclo":
                return DegreeClassification.readByCode("D3");
            case "Especialização pós-licenciatura":
                return DegreeClassification.readByCode("E");
            case "Bacharelato":
                return DegreeClassification.readByCode("B");
            case "Diploma de estudos superiores especializados":
                return DegreeClassification.readByCode("DE");
            case "Mestrado":
                return DegreeClassification.readByCode("M");
            case "Complemento de formação":
                return DegreeClassification.readByCode("CF");
            case "Bacharelato/Licenciatura":
                return DegreeClassification.readByCode("BL");
            case "Curso técnico superior profissional":
                return null; //doesn't exist
            case "Licenciatura terminal":
                return DegreeClassification.readByCode("LT");
            case "Curso de especialização tecnológica":
                return null; //file doesn't specify which code ("duplicated" name)
            case "Doutoramento":
                return DegreeClassification.readByCode("D");
            case "Licenciatura de mestrado integrado":
                return DegreeClassification.readByCode("LI");
            case "Qualificação para o exercício de outras funções educativas":
                return DegreeClassification.readByCode("QE");
            case "Especialização pós-bacharelato":
                return DegreeClassification.readByCode("GB");
            case "Mestrado integrado":
                return DegreeClassification.readByCode("MI");
            case "Bacharelato em ensino + licenciatura em ensino":
                return DegreeClassification.readByCode("PB");
            case "Outros cursos de compl de form para prof do Ens Básico e Sec":
                return DegreeClassification.readByCode("OC");
            case "Preparatórios de mestrado integrado":
                return DegreeClassification.readByCode("PM");
            case "Preparatórios de licenciatura":
                return DegreeClassification.readByCode("P");
            case "Mestrado integrado terminal":
                return DegreeClassification.readByCode("MT");
            case "Aguarda reconhecimento como licenciatura":
                return DegreeClassification.readByCode("X");
            case "Curso superior não conferente de grau":
                return DegreeClassification.readByCode("A");
            case "Preparatórios de licenciatura 1.º ciclo":
                return DegreeClassification.readByCode("PL");
            default:
                return null;
        }
    }

    //make sure that there are no duplicates (for the purpose of this script, there are currently no duplicates that will affect it)
    private List<String> getUnitsCodes() {
        return Unit.readAllUnits().stream().filter(u -> u.getCode() != null && !u.getCode().isEmpty()).map(Unit::getCode).collect(
                Collectors.toList());
    }

    private List<String> getDegreeDesignationsCodes() {
        return Bennu.getInstance().getDegreeDesignationsSet().stream()
                .filter(d -> d.getCode() != null && !d.getCode().isEmpty()).map(DegreeDesignation::getCode).collect(Collectors.toList());
    }

    private BufferedReader getReaderFromURL(URL url) throws IOException {
        URLConnection urlConn = url.openConnection();
        InputStreamReader input = new InputStreamReader(urlConn.getInputStream());
        return new BufferedReader(input);
    }

    private Set<List<String>> parseFile(BufferedReader br) throws IOException {
        String line;
        Set<List<String>> set = new HashSet<>();

        while((line = br.readLine()) != null) {
            final List<String> data = parseLine(line);
            set.add(data);
        }

        return set;
    }

    //assumes separator is a comma and that there are no quotes inside a column
    private List<String> parseLine(String line) {
        List<String> result = new ArrayList<>();

        //if empty, return!
        if (line == null || line.isEmpty()) {
            return result;
        }

        StringBuilder curVal = new StringBuilder();
        boolean inQuotes = false;

        char[] chars = line.toCharArray();

        for (char ch : chars) {
            if (inQuotes) {
                if (ch == '\"') {
                    inQuotes = false;
                } else {
                    curVal.append(ch);
                }
            } else {
                if (ch == '\"') {
                    inQuotes = true;
                } else if (ch == ',') {
                    result.add(curVal.toString());

                    curVal = new StringBuilder();
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else if (ch != '\r') {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }
}
