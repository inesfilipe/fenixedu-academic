package org.fenixedu.academic.ui.spring.controller.manager;

import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.degree.DegreeType;
import org.fenixedu.academic.domain.degree.degreeCurricularPlan.DegreeCurricularPlanState;
import org.fenixedu.academic.domain.degreeStructure.CycleCourseGroup;
import org.springframework.stereotype.Service;
import pt.ist.fenixframework.Atomic;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DegreeCurricularPlansAffinityCyclesService {

    public List<DegreeCurricularPlan> getAllFirstCycleDegrees() {
        return DegreeCurricularPlan.readByDegreeTypesAndState(
                DegreeType.oneOf(DegreeType::isBolonhaDegree, DegreeType::isIntegratedMasterDegree),
                DegreeCurricularPlanState.ACTIVE);
    }

    public List<CycleCourseGroup> getSecondCycleDegreesWithAffinity(DegreeCurricularPlan firstCycleDegree) {
        return firstCycleDegree.getFirstCycleCourseGroup().getDestinationAffinitiesSet().stream().collect(Collectors.toList());
    }

    @Atomic
    public void addDestinationAffinities(CycleCourseGroup firstCycleCourseGroup, CycleCourseGroup secondCycleCourseGroup) {
        firstCycleCourseGroup.addDestinationAffinities(secondCycleCourseGroup);
    }

    @Atomic
    public void deleteDestinationAffinities(CycleCourseGroup firstCycleCourseGroup, CycleCourseGroup secondCycleCourseGroup) {
        firstCycleCourseGroup.getDestinationAffinitiesSet().remove(secondCycleCourseGroup);
    }
}
