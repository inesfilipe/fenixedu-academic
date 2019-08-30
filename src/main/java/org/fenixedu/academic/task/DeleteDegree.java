package org.fenixedu.academic.task;

import org.fenixedu.academic.domain.raides.DegreeDesignation;
import org.fenixedu.bennu.scheduler.custom.CustomTask;
import pt.ist.fenixframework.FenixFramework;

public class DeleteDegree extends CustomTask {

    @Override
    public void runTask() throws Exception {
        DegreeDesignation degreeDesignation = FenixFramework.getDomainObject("850798661599258");
        degreeDesignation.getInstitutionUnitSet().forEach(u -> taskLog(u.getCode() + " - " + u.getName()));
    }

}