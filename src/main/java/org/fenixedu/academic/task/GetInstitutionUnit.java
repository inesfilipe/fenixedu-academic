package org.fenixedu.academic.task;

import org.fenixedu.academic.domain.organizationalStructure.Unit;
import org.fenixedu.bennu.core.domain.Bennu;
import org.fenixedu.bennu.scheduler.custom.CustomTask;

public class GetInstitutionUnit extends CustomTask {

    @Override
    public void runTask() throws Exception {
        Unit unit = Bennu.getInstance().getInstitutionUnit();
        taskLog(unit.getOid() + " " + unit.getCode() + " " + unit.getName());
    }
}
