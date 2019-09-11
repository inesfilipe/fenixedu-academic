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

public class SeeUnitsWithSimilarNamesRAIDES extends CustomTask {

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

        for(List<String> l : sortByUnitCode(unitsNotInSystem)) {
            taskLog(l.get(0) + " — " + l.get(1));
            if(allUnits.stream().anyMatch(u -> u.getCode() == null && u.getName().equals(l.get(1)) && u.getUnitName() != null)) {
                allUnits.stream().filter(u -> u.getCode() == null && u.getName().equals(l.get(1)) && u.getUnitName() != null)
                        .forEach(u -> taskLog(u.getOid().toString() + " " + u.getCode() + " " + u.getName() + " -- externa: "
                                + u.getUnitName().getIsExternalUnit().toString() + " no degree: " + u.getDegreeDesignationSet().size()));
            }
            else {
                String name = l.get(1);
                List<String> splitted = Arrays.asList(name.split(" ")).stream().map(s -> StringNormalizer.normalizeAndRemoveAccents(s).replaceAll("[^a-zA-Z]", "").toLowerCase()).filter(s -> s.length() > 3).collect(Collectors.toList());

                allUnits.stream().filter(u -> u.getCode() == null && splitted.stream().allMatch(StringNormalizer.normalizeAndRemoveAccents(u.getName()).toLowerCase()::contains) && u.getUnitName() != null)
                        .forEach(u -> taskLog(u.getOid().toString() + " " + u.getCode() + " " + u.getName() + " -- externa: "
                                + u.getUnitName().getIsExternalUnit().toString() + " no degree: " + u.getDegreeDesignationSet().size()));
            }
            taskLog();
        }

    }

    private List<Unit> getUnitsWithCode() {
        return Unit.readAllUnits().stream().filter(u -> u.getCode() != null && !u.getCode().isEmpty()).collect(
                Collectors.toList());
    }

    private boolean isUnitWithSameCode(Unit unit, String code) {
        return code.equals(unit.getCode());
    }

    private boolean isUnitWithSameName(Unit unit, String name) {
        return name.equals(unit.getName());
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
