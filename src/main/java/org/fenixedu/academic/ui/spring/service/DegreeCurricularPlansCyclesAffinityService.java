package org.fenixedu.academic.ui.spring.service;

import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.degree.DegreeType;
import org.fenixedu.academic.domain.degree.degreeCurricularPlan.DegreeCurricularPlanState;
import org.fenixedu.academic.domain.degreeStructure.CycleCourseGroup;
import org.springframework.stereotype.Service;
import pt.ist.fenixframework.Atomic;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DegreeCurricularPlansCyclesAffinityService {

    public List<DegreeCurricularPlan> getAllFirstCycleDegrees() {
        return DegreeCurricularPlan.readByDegreeTypesAndState(
                DegreeType.oneOf(DegreeType::isBolonhaDegree, DegreeType::isIntegratedMasterDegree),
                DegreeCurricularPlanState.ACTIVE);
    }

    public List<CycleCourseGroup> getSecondCycleDegreesWithAffinity(DegreeCurricularPlan firstCycleDegree) {
        return firstCycleDegree.getFirstCycleCourseGroup().getDestinationAffinitiesSet().stream().collect(Collectors.toList());
    }

    @Atomic
    public void addDestinationAffinity(CycleCourseGroup firstCycleCourseGroup, CycleCourseGroup secondCycleCourseGroup) {
        firstCycleCourseGroup.addDestinationAffinities(secondCycleCourseGroup);
    }

    @Atomic
    public void deleteDestinationAffinity(CycleCourseGroup firstCycleCourseGroup, CycleCourseGroup secondCycleCourseGroup) {
        firstCycleCourseGroup.getDestinationAffinitiesSet().remove(secondCycleCourseGroup);
    }
}
