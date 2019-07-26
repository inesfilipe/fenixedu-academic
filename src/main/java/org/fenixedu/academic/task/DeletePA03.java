package org.fenixedu.academic.task;

import org.fenixedu.academic.domain.raides.DegreeDesignation;
import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.scheduler.custom.CustomTask;

public class DeletePA03 extends CustomTask {

    @Override
    public void runTask() throws Exception {
        Bennu.getInstance().getDegreeDesignationsSet().stream().filter(degreeDesignation -> degreeDesignation.getCode().equals("PA03")).forEach(
                DegreeDesignation::delete);
    }
}
