package org.fenixedu.academic.ui.spring.service;

import org.fenixedu.academic.domain.AffinityCycleCourseGroupLog;
import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.degree.DegreeType;
import org.fenixedu.academic.domain.degree.degreeCurricularPlan.DegreeCurricularPlanState;
import org.fenixedu.academic.domain.degreeStructure.CycleCourseGroup;
import org.fenixedu.academic.util.Bundle;
import org.fenixedu.bennu.core.domain.Bennu;
import org.springframework.stereotype.Service;
import pt.ist.fenixframework.Atomic;

import java.util.*;
import java.util.stream.Collectors;

import static org.fenixedu.academic.domain.AffinityCycleCourseGroupLog.createLog;

@Service
public class CyclesAffinityService {

    public List<CycleCourseGroup> getAllFirstCycles() {
        List<CycleCourseGroup> firstCycles = new ArrayList<CycleCourseGroup>();

        DegreeCurricularPlan.readByDegreeTypesAndState(
                DegreeType.oneOf(DegreeType::isBolonhaDegree, DegreeType::isIntegratedMasterDegree),
                DegreeCurricularPlanState.ACTIVE).stream().sorted(DegreeCurricularPlan.COMPARATOR_BY_PRESENTATION_NAME).forEachOrdered(d -> firstCycles.add(d.getFirstCycleCourseGroup()));

        return firstCycles;
    }

    public List<CycleCourseGroup> getSecondCycleDegreesWithAffinity(final CycleCourseGroup firstCycle) {
        return firstCycle.getDestinationAffinitiesSet().stream().
                sorted(CycleCourseGroup.COMPARATOR_BY_PARENT_DEGREE_PRESENTATION_NAME).collect(Collectors.toList());
    }

    public List<CycleCourseGroup> getSecondCycleDegreesWithoutAffinity(final CycleCourseGroup firstCycle, final List<CycleCourseGroup> affinities) {

        return firstCycle.getAllPossibleAffinities().stream().
                filter(c -> !affinities.contains(c)).sorted(CycleCourseGroup.COMPARATOR_BY_PARENT_DEGREE_PRESENTATION_NAME).
                collect(Collectors.toList());
    }

    public List<CycleCourseGroup> getSecondCycleDegreesWithoutAffinity(final CycleCourseGroup firstCycle) {
        return getSecondCycleDegreesWithoutAffinity(firstCycle, getSecondCycleDegreesWithAffinity(firstCycle));
    }


    @Atomic
    public void addDestinationAffinity(CycleCourseGroup firstCycle, CycleCourseGroup secondCycle) {
        firstCycle.addDestinationAffinities(secondCycle);
        createLog(Bundle.MESSAGING, "log.affinity.added", firstCycle.getDegree().getPresentationName(), secondCycle.getDegree().getPresentationName());
    }

    @Atomic(mode = Atomic.TxMode.WRITE)
    public void deleteDestinationAffinity(CycleCourseGroup firstCycle, CycleCourseGroup secondCycle) {
        firstCycle.removeDestinationAffinities(secondCycle);
        createLog(Bundle.MESSAGING, "log.affinity.removed", firstCycle.getDegree().getPresentationName(), secondCycle.getDegree().getPresentationName());
    }

    public List<AffinityCycleCourseGroupLog> getAffinityLogs() {
        return Bennu.getInstance().getAffinityCycleCourseGroupLogSet().stream().sorted(AffinityCycleCourseGroupLog.COMPARATOR_BY_WHEN_DATETIME).
                collect(Collectors.toList());
    }

}
