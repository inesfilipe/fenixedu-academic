package org.fenixedu.academic.task;

import org.fenixedu.academic.domain.organizationalStructure.Unit;
import org.fenixedu.bennu.scheduler.custom.CustomTask;
import org.fenixedu.commons.StringNormalizer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.stream.Collectors;

public class AddNewUnits extends CustomTask {

    @Override
    public void runTask() throws Exception {
        List<Unit> allUnits = Unit.readAllUnits();
        List<Unit> unitsWithCode = getUnitsWithCode();

        Set<List<String>> unitsNotInSystem = new HashSet<>();

        URL csvURL = new URL("https://gist.githubusercontent.com/inesfilipe/d873eb941c6cbaf61f18212a6e819f2f/raw/59c1d2af2e5d71e7aef97b85c3b9afe3603b948a/tbl_Grau_Estabelecimento_Curso.csv");
        BufferedReader br = getReaderFromURL(csvURL);
        String line = br.readLine(); // excluding header from analysis

        while((line = br.readLine()) != null) {
            final List<String> data = parseLine(line);

            if(unitsWithCode.stream().noneMatch(u -> isUnitWithSameCode(u, data.get(1)))) {
                unitsNotInSystem.add(data.subList(1,3));
            }
        }

        for(List<String> unit : sortByUnitCode(unitsNotInSystem)) {
            taskLog(unit.get(0) + " — " + unit.get(1));

            if(allUnits.stream().anyMatch(u -> isExternalUnitWithSameNameAndNoCode(u, unit.get(1)))) {
                taskLog("equal");
                allUnits.stream()
                        .filter(u -> isExternalUnitWithSameNameAndNoCode(u, unit.get(1)))
                        .findFirst().ifPresent(u -> taskLog(u.getOid().toString() + " "
                        + u.getCode() + " " + u.getName() + " autocomplete: " + u.getUnitName().getName() + " -- externa: " + u.getUnitName().getIsExternalUnit().toString()
                        + " no degree: " + u.getDegreeDesignationSet().size()));
            }
            else if(allUnits.stream().anyMatch(u -> isExternalUnitWithSimilarNameAndNoCode(u, splitAndNormalizeName(unit.get(1))))) { //TODO: autocomplete update
                taskLog("similar");
                allUnits.stream()
                        .filter(u -> isExternalUnitWithSimilarNameAndNoCode(u, splitAndNormalizeName(unit.get(1))) && !StringNormalizer.normalize(u.getName()).contains("tecnica") && !StringNormalizer.normalize(u.getName()).contains("lusiada"))
                        .findFirst().ifPresent(u -> taskLog(u.getOid().toString() + " " + u.getCode() + " "
                        + u.getName() + " autocomplete: " + u.getUnitName().getName() + " -- externa: " + u.getUnitName().getIsExternalUnit().toString()
                        + " no degree: " + u.getDegreeDesignationSet().size()));
            }
            else if (unit.get(0).equals("1519")) { //TODO:autocomplete update
                taskLog("tagus");
                allUnits.stream()
                        .filter(u -> isExternalUnitWithSimilarNameAndNoCode(u, Arrays.asList("instituto", "superior", "tecnico", "taguspark")) && !StringNormalizer.normalize(u.getName()).contains("tecnica") && !StringNormalizer.normalize(u.getName()).contains("lusiada"))
                        .findFirst().ifPresent(u -> taskLog(u.getOid().toString() + " " + u.getCode() + " "
                        + u.getName() + " autocomplete: " + u.getUnitName().getName() + " -- externa: " + u.getUnitName().getIsExternalUnit().toString()
                        + " no degree: " + u.getDegreeDesignationSet().size()));
            }
            else if (unit.get(0).equals("2403")) { //TODO:autocomplete update
                taskLog("lusiada porto");
                allUnits.stream()
                        .filter(u -> isExternalUnitWithSimilarNameAndNoCode(u, Arrays.asList("universidade", "lusiada", "porto")) && !StringNormalizer.normalize(u.getName()).contains("instituto"))
                        .findFirst().ifPresent(u -> taskLog(u.getOid().toString() + " " + u.getCode() + " "
                        + u.getName() + " autocomplete: " + u.getUnitName().getName() + " -- externa: " + u.getUnitName().getIsExternalUnit().toString()
                        + " no degree: " + u.getDegreeDesignationSet().size()));
            }
            else if (unit.get(0).equals("2404")) { //TODO:autocomplete update
                taskLog("lusiada famalicao");
                allUnits.stream()
                        .filter(u -> isExternalUnitWithSimilarNameAndNoCode(u, Arrays.asList("universidade", "lusiada", "vila", "nova", "famalicao")))
                        .findFirst().ifPresent(u -> taskLog(u.getOid().toString() + " " + u.getCode() + " "
                        + u.getName() + " autocomplete: " + u.getUnitName().getName() + " -- externa: " + u.getUnitName().getIsExternalUnit().toString()
                        + " no degree: " + u.getDegreeDesignationSet().size()));
            }
            else {
                taskLog("new");
                taskLog(" " + unit.get(0) + " " + unit.get(1) + " autocomplete: "
                        + StringNormalizer.normalize(unit.get(1)) + " -- externa: " + Boolean.TRUE.toString()
                        + " no degree: 0");
            }
            taskLog();
        }
    }

    private List<String> splitAndNormalizeName(String name) {
        return Arrays.asList(name.split(" ")).stream()
                .map(s -> StringNormalizer.normalize(s).replaceAll("[^a-zA-Z]", ""))
                .filter(s -> s.length() > 3).collect(Collectors.toList());
    }

    private boolean isExternalUnitWithoutCode(Unit unit) { return unit.getCode() == null && unit.getUnitName() != null && unit.getUnitName().getIsExternalUnit(); }

    private boolean isExternalUnitWithSameNameAndNoCode(Unit unit, String name) { return isExternalUnitWithoutCode(unit) && unit.getName().equals(name); }

    private boolean isExternalUnitWithSimilarNameAndNoCode(Unit unit, List<String> words) { return isExternalUnitWithoutCode(unit) && words.stream().allMatch(StringNormalizer.normalize(unit.getName())::contains); }

    private List<Unit> getUnitsWithCode() {
        return Unit.readAllUnits().stream().filter(u -> u.getCode() != null && !u.getCode().isEmpty()).collect(
                Collectors.toList());
    }

    private boolean isUnitWithSameCode(Unit unit, String code) {
        return code.equals(unit.getCode());
    }

    private List<List<String>> sortByUnitCode(Set<List<String>> set) {
        return set.stream().sorted(Comparator.comparing(l -> l.get(0))).collect(Collectors.toList());
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
