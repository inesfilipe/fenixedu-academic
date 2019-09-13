package org.fenixedu.academic.task;

import org.fenixedu.academic.domain.raides.DegreeClassification;
import org.fenixedu.bennu.scheduler.custom.CustomTask;

public class AddNewDegreeClassification extends CustomTask {

    @Override
    public void runTask() {
        new DegreeClassification("T", "Curso técnico superior profissional", "Curso técnico superior profissional", "CTeSP");
    }

}
