package org.fenixedu.academic.task;

import org.fenixedu.academic.domain.organizationalStructure.AcademicalInstitutionUnit;
import org.fenixedu.academic.domain.organizationalStructure.Unit;
import org.fenixedu.academic.domain.organizationalStructure.UniversityUnit;
import org.fenixedu.bennu.scheduler.custom.CustomTask;

import java.util.Comparator;

public class CheckUnitHasUnitName extends CustomTask {

    @Override
    public void runTask() throws Exception {
        Unit.readAllUnits().stream().filter(u -> u.getCode() != null && !u.getCode().isEmpty() && u.getUnitName() != null && !(u instanceof AcademicalInstitutionUnit))
                .sorted(Comparator.comparing(Unit::getCode))
                .forEach(u -> taskLog(u.getCode() + " " + u.getName()));
    }

}
