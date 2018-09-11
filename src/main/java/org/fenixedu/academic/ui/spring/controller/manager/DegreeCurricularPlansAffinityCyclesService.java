package org.fenixedu.academic.ui.spring.controller.manager;

import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.degree.DegreeType;
import org.fenixedu.academic.domain.degree.degreeCurricularPlan.DegreeCurricularPlanState;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DegreeCurricularPlansAffinityCyclesService {

    public List<DegreeCurricularPlan> getAllFirstCycleDegrees() {
        return DegreeCurricularPlan.readByDegreeTypesAndState(
                DegreeType.oneOf(DegreeType::isBolonhaDegree, DegreeType::isIntegratedMasterDegree),
                DegreeCurricularPlanState.ACTIVE);
    }
}
