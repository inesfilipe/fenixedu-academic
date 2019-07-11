package org.fenixedu.academic.task;

import org.fenixedu.academic.domain.organizationalStructure.Unit;
import org.fenixedu.academic.domain.organizationalStructure.UnitName_Base;
import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.scheduler.custom.CustomTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CheckMissingUnitsAndDegrees extends CustomTask {
    @Override
    public void runTask() throws Exception {
        Set<Unit> units = Bennu.getInstance().getUnitNameSet().stream().map(UnitName_Base::getUnit).filter(u -> u != null && u.getCode() != null).collect(Collectors.toSet());

        Set<List<String>> unitsThatdontExist = new HashSet<>();
        Set<List<String>> unitsWhoseNamesDontMatch = new HashSet<>();
        Set<List<String>> degreesThatDontExist = new HashSet<>();
        Set<List<String>> degressWhoseNamesDontMatch = new HashSet<>();

        URL csvURL = new URL("https://gist.githubusercontent.com/inesfilipe/d873eb941c6cbaf61f18212a6e819f2f/raw/59c1d2af2e5d71e7aef97b85c3b9afe3603b948a/tbl_Grau_Estabelecimento_Curso.csv");
        BufferedReader br = getReaderFromURL(csvURL);
        String line = br.readLine(); // excluding header from analysis
        taskLog(line); //for my own reference - will probably delete later

        while((line = br.readLine()) != null) {
            final List<String> data = parseLine(line);

            if(units.stream().noneMatch(u -> u.getCode().equals(data.get(1)))) {
                unitsThatdontExist.add(data.subList(1,3));
            }
            else {
                List<Unit> unitsNameNotMatch = units.stream().filter(u -> u.getCode().equals(data.get(1)) && !u.getUnitName().getName().equals(data.get(2))).collect(Collectors.toList());
            }
        }

        printUnitsThatDontExist(unitsThatdontExist);
    }

    private void printUnitsThatDontExist(Set<List<String>> units) {
        taskLog("Units that don't exist:\n");

        for(List<String> l : units) {
            taskLog(l.get(0) + " — " + l.get(1));
        }
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

        StringBuffer curVal = new StringBuffer();
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

                    curVal = new StringBuffer();
                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }

        }

        result.add(curVal.toString());

        return result;
    }
}