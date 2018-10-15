package org.fenixedu.academic.ui.spring.service;

import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.degree.DegreeType;
import org.fenixedu.academic.domain.degree.degreeCurricularPlan.DegreeCurricularPlanState;
import org.fenixedu.academic.domain.degreeStructure.CycleCourseGroup;
import org.fenixedu.academic.ui.spring.controller.scientificCouncil.CycleCourseGroupAffinityBean;
import org.fenixedu.academic.ui.spring.controller.scientificCouncil.DegreeCurricularPlansCycleBean;
import org.springframework.stereotype.Service;
import pt.ist.fenixframework.Atomic;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DegreeCurricularPlansCyclesAffinityService {

    public List<DegreeCurricularPlan> getAllFirstCycleDegrees() {
        return DegreeCurricularPlan.readByDegreeTypesAndState(
                DegreeType.oneOf(DegreeType::isBolonhaDegree, DegreeType::isIntegratedMasterDegree),
                DegreeCurricularPlanState.ACTIVE).stream().sorted(DegreeCurricularPlan.COMPARATOR_BY_PRESENTATION_NAME).collect(Collectors.toList());
    }

    public CycleCourseGroup getCycleCourseGroupFromBean(final DegreeCurricularPlansCycleBean degree) {
        return degree.getDegree().getFirstCycleCourseGroup();
    }

    public List<CycleCourseGroup> getSecondCycleDegreesWithAffinity(final DegreeCurricularPlansCycleBean firstCycleDegree) {
        return getCycleCourseGroupFromBean(firstCycleDegree).getDestinationAffinitiesSet().stream().
                sorted(CycleCourseGroup.COMPARATOR_BY_PARENT_DEGREE_PRESENTATION_NAME).collect(Collectors.toList());
    }

    public List<CycleCourseGroup> getSecondCycleDegreesWithoutAffinity(final DegreeCurricularPlansCycleBean firstCycleDegree) {
        List<CycleCourseGroup> affinities = getSecondCycleDegreesWithAffinity(firstCycleDegree);

        return getCycleCourseGroupFromBean(firstCycleDegree).getAllPossibleAffinities().stream().
                filter(c -> !affinities.contains(c)).sorted(CycleCourseGroup.COMPARATOR_BY_PARENT_DEGREE_PRESENTATION_NAME).
                collect(Collectors.toList());
    }

    @Atomic
    public void addDestinationAffinity(DegreeCurricularPlansCycleBean firstCycleDegree, CycleCourseGroupAffinityBean newAffinity) {
        getCycleCourseGroupFromBean(firstCycleDegree).addDestinationAffinities(newAffinity.getSecondCycleCourseGroup());
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public void deleteDestinationAffinity(DegreeCurricularPlan firstCycleDegree, CycleCourseGroup affinity) {
        firstCycleDegree.getFirstCycleCourseGroup().removeDestinationAffinities(affinity);
    }
}
