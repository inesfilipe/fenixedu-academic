package net.sourceforge.fenixedu.domain.phd.individualProcess.activities;

import net.sourceforge.fenixedu.applicationTier.IUserView;
import net.sourceforge.fenixedu.domain.accessControl.PermissionType;
import net.sourceforge.fenixedu.domain.caseHandling.PreConditionNotValidException;
import net.sourceforge.fenixedu.domain.phd.PhdIndividualProgramProcess;

public class RemoveLastStateOnPhdIndividualProgramProcess extends PhdIndividualProgramProcessActivity {

    @Override
    public void activityPreConditions(PhdIndividualProgramProcess process, IUserView userView) {
	if (!PhdIndividualProgramProcess.isMasterDegreeAdministrativeOfficeEmployee(userView)) {
	    throw new PreConditionNotValidException();
	}

	if (!hasPermissionFor(userView, PermissionType.MANAGE_PHD_PROCESS_STATES)) {
	    throw new PreConditionNotValidException();
	}
    }

    @Override
    protected void processPreConditions(final PhdIndividualProgramProcess process, final IUserView userView) {
    }

    @Override
    protected PhdIndividualProgramProcess executeActivity(PhdIndividualProgramProcess process, IUserView userView, Object object) {
	process.removeLastState();
	return process;
    }

}