package org.fenixedu.academic.task;

import org.fenixedu.academic.domain.organizationalStructure.Unit;
import org.fenixedu.bennu.scheduler.custom.CustomTask;
import pt.ist.fenixframework.FenixFramework;

public class RemoveDuplicatedCodeFromTecnicoUnit extends CustomTask {

    @Override
    public void runTask() throws Exception {
        Unit unit = FenixFramework.getDomainObject("2160369218947");
        unit.setCode(null);
    }
}
