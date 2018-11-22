package org.fenixedu.academic.ui.spring.controller.student;

import org.fenixedu.academic.domain.student.RegistrationProtocol;
import org.fenixedu.bennu.core.domain.Bennu;
import org.springframework.stereotype.Service;
import pt.ist.fenixframework.Atomic;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationProtocolsService {

    public List<RegistrationProtocol> getAllInterestRates() {
        return Bennu.getInstance().getRegistrationProtocolsSet().stream().sorted(RegistrationProtocol.AGREEMENT_COMPARATOR).
                collect(Collectors.toList());
    }

    //TODO: add registration protocol log

    @Atomic
    public void createRegistrationProtocol(RegistrationProtocolBean bean) {
        RegistrationProtocol.create(bean.getCode(), bean.getDescription(), bean.getEnrolmentByStudentAllowed(),
                bean.getPayGratuity(), bean.getAllowsIDCard(), bean.getOnlyAllowedDegreeEnrolment(), bean.getAlien(),
                bean.getExempted(), bean.getMobility(), bean.getMilitary(), bean.getAllowDissertationCandidacyWithoutChecks(),
                bean.getForOfficialMobilityReporting(), bean.getAttemptAlmaMatterFromPrecedent());
    }

    public void editRegistrationProtocol(RegistrationProtocolBean bean) {}

}
