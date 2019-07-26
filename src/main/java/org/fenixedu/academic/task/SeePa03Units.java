package org.fenixedu.academic.task;

import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.scheduler.custom.CustomTask;

public class SeePa03Units extends CustomTask {

    @Override
    public void runTask() throws Exception {
        Bennu.getInstance().getDegreeDesignationsSet().stream().filter(degreeDesignation -> degreeDesignation.getCode().equals("PA03"))
                .flatMap(d -> d.getInstitutionUnitSet().stream()).forEach(u -> taskLog(u.getCode() + " " + u.getName()));
    }
}
