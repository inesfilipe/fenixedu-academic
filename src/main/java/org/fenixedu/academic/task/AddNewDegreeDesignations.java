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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AddNewDegreeDesignations extends CustomTask {

    @Override
    public void runTask() throws Exception {
        List<DegreeDesignation> degreeDesignations;
        List<String> tecnicoCodes = Arrays.asList("0807", "0808", "1518", "1519");

        URL csvURL = new URL("https://gist.githubusercontent.com/inesfilipe/48e0b4830e86f438a478882c0e49d1a9/raw/08a26ca99a07a6fbdbd8a27ccb0d6f70cfe24c28/pa03.csv");
        BufferedReader br = getReaderFromURL(csvURL);
        String line = br.readLine(); // excluding header from analysis
        taskLog(line); //for my own reference - will probably delete later
        taskLog();

        while((line = br.readLine()) != null) {
            final List<String> data = parseLine(line);

            degreeDesignations = getDegreeDesignationsWithCode();
            if(!tecnicoCodes.contains(data.get(1))) {
                if(degreeDesignations.stream().noneMatch(d -> isDegreeWithSameCodeAndUnit(d, data.get(3), data.get(1)))) {
                    addDegreeToSystem(data);
                }
                else if(degreeDesignations.stream().anyMatch(d -> isDegreeWithSameCodeAndUnit(d, data.get(3), data.get(1)) && !data.get(4).equals(d.getDescription()))) {
                    updateDegreeName(data);
                }
            }
        }
    }

    private void addDegreeToSystem(List<String> degree) {
        if(getDegreeClassificationFromName(degree.get(0)) == null) {
            taskLog("ERROR - cannot add: " + degree.get(0) + " — " + degree.get(1) + " : " + degree.get(2) + " — " + degree.get(3) + " : " + degree.get(4));
            return;
        }

        taskLog("CREATE: " + degree.get(0) + " — " + degree.get(1) + " : " + degree.get(2) + " — " + degree.get(3) + " : " + degree.get(4));
        getUnitsWithCode().stream().filter(u -> isUnitWithSameCode(u, degree.get(1))).findFirst().ifPresent(unit -> {
            DegreeDesignation degreeDesignation = getDegreeDesignationsWithCode().stream()
                    .filter(d -> degree.get(3).equals(d.getCode())).findFirst().orElseGet(() -> new DegreeDesignation(degree.get(3), degree.get(4), getDegreeClassificationFromName(degree.get(0))));
            unit.addDegreeDesignation(degreeDesignation);
        });
    }

    private void updateDegreeName(List<String> degree) {
        taskLog("UPDATE: " + degree.get(0) + " — " + degree.get(1) + " : " + degree.get(2) + " — " + degree.get(3) + " : " + degree.get(4));
        getDegreeDesignationsWithCode().stream().filter(d -> isDegreeWithSameCodeAndUnit(d, degree.get(3), degree.get(1))).findFirst().ifPresent(d -> d.setDescription(degree.get(4)));
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

    private List<Unit> getUnitsWithCode() {
        return Unit.readAllUnits().stream().filter(u -> u.getCode() != null && !u.getCode().isEmpty()).collect(
                Collectors.toList());
    }

    private List<DegreeDesignation> getDegreeDesignationsWithCode() {
        return Bennu.getInstance().getDegreeDesignationsSet().stream()
                .filter(d -> d.getCode() != null && !d.getCode().isEmpty()).collect(Collectors.toList());
    }

    private boolean isUnitWithSameCode(Unit unit, String code) {
        return code.equals(unit.getCode());
    }

    private boolean isDegreeWithSameCodeAndUnit(DegreeDesignation degree, String code, String unitCode) {
        return code.equals(degree.getCode()) && degree.getInstitutionUnitSet().stream()
                .anyMatch(u -> unitCode.equals(u.getCode()));
    }

    private BufferedReader getReaderFromURL(URL url) throws IOException {
        URLConnection urlConn = url.openConnection();
        InputStreamReader input = new InputStreamReader(urlConn.getInputStream());
        return new BufferedReader(input);
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
