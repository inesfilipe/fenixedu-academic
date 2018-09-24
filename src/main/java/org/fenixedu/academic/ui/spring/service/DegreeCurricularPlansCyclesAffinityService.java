package org.fenixedu.academic.ui.spring.service;

import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.degree.DegreeType;
import org.fenixedu.academic.domain.degree.degreeCurricularPlan.DegreeCurricularPlanState;
import org.fenixedu.academic.domain.degreeStructure.CycleCourseGroup;
import org.fenixedu.academic.ui.spring.controller.scientificCouncil.DegreeCurricularPlansCycleBean;
import org.springframework.stereotype.Service;
import pt.ist.fenixframework.Atomic;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DegreeCurricularPlansCyclesAffinityService {

    public List<DegreeCurricularPlan> getAllFirstCycleDegrees() {
        return DegreeCurricularPlan.readByDegreeTypesAndState(
                DegreeType.oneOf(DegreeType::isBolonhaDegree, DegreeType::isIntegratedMasterDegree),
                DegreeCurricularPlanState.ACTIVE).stream().sorted(DegreeCurricularPlan.COMPARATOR_BY_PRESENTATION_NAME).collect(Collectors.toList());
    }

    public List<CycleCourseGroup> getSecondCycleDegreesWithAffinity(final DegreeCurricularPlansCycleBean firstCycleDegree) {
        return firstCycleDegree.getDegree().getFirstCycleCourseGroup().getDestinationAffinitiesSet().stream().
                sorted(CycleCourseGroup.COMPARATOR_BY_PARENT_DEGREE_PRESENTATION_NAME).collect(Collectors.toList());
    }

    public List<CycleCourseGroup> getSecondCycleDegreesWithoutAffinity(final DegreeCurricularPlansCycleBean firstCycleDegree) {
        List<CycleCourseGroup> affinities = getSecondCycleDegreesWithAffinity(firstCycleDegree);

        return firstCycleDegree.getDegree().getFirstCycleCourseGroup().getAllPossibleAffinities().stream().
                filter(c -> !affinities.contains(c)).sorted(CycleCourseGroup.COMPARATOR_BY_PARENT_DEGREE_PRESENTATION_NAME).
                collect(Collectors.toList());
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
