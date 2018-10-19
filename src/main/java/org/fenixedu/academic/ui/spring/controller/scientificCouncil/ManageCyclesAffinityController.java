package org.fenixedu.academic.ui.spring.controller.scientificCouncil;

import org.fenixedu.academic.domain.degreeStructure.CycleCourseGroup;
import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.ui.spring.service.CyclesAffinityService;
import org.fenixedu.bennu.spring.portal.SpringApplication;
import org.fenixedu.bennu.spring.portal.SpringFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@SpringApplication(path = "degreeAffinityCycles", hint = "Manager", group = "#managers", title = "title.manage.degreeCurricularPlans.affinity")
@SpringFunctionality(app = ManageCyclesAffinityController.class, title = "title.manage.degreeCurricularPlans.affinity")
@RequestMapping("/cycle-affinity-management")
public class ManageCyclesAffinityController {

    @Autowired
    private CyclesAffinityService cyclesAffinityService;

    private String redirectHome() {
        return "redirect:/scientific-council/bolonha-process/cycle-affinity-management";
    }

    private String redirectHome(CycleCourseGroup firstCycle) {
        return redirectHome() + "?firstCycle=" + firstCycle.getExternalId();
    }

    private String view(String string) {
        return "fenixedu-academic/cycle-affinity-management/" + string;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listAllFirstCycle(Model model, @RequestParam(required = false) CycleCourseGroup firstCycle, @RequestParam(required = false) CycleCourseGroup potentialAffinity) {
        List<CycleCourseGroup> firstCycles = cyclesAffinityService.getAllFirstCycles();

        if(firstCycle == null) {
            firstCycle = firstCycles.get(0);
        }

        List<CycleCourseGroup> affinities = cyclesAffinityService.getSecondCycleDegreesWithAffinity(firstCycle);
        List<CycleCourseGroup> potentialAffinities = cyclesAffinityService.getSecondCycleDegreesWithoutAffinity(firstCycle, affinities);

        if(potentialAffinity == null) {
            potentialAffinity = potentialAffinities.get(0);
        }

        model.addAttribute("firstCycles", firstCycles);
        model.addAttribute("firstCycle", firstCycle);
        model.addAttribute("affinities", affinities);
        model.addAttribute("potentialAffinities", potentialAffinities);
        model.addAttribute("potentialAffinity", potentialAffinity);
        return view("show");
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addAffinity(Model model, @RequestParam CycleCourseGroup firstCycle, @RequestParam CycleCourseGroup potentialAffinity) {
        try {
            cyclesAffinityService.addDestinationAffinity(firstCycle, potentialAffinity);
            return listAllFirstCycle(model, firstCycle, null);
        } catch (DomainException de) {
            model.addAttribute("error", de.getLocalizedMessage());
            return listAllFirstCycle(model, firstCycle, potentialAffinity);
        }
    }

    @RequestMapping(value="/deleteAffinity", method = RequestMethod.POST)
    public String deleteSecondCycleAffinity(Model model, @RequestParam CycleCourseGroup firstCycle, @RequestParam CycleCourseGroup affinity) {
        try {
            cyclesAffinityService.deleteDestinationAffinity(firstCycle, affinity);
            return redirectHome(firstCycle);
        } catch (Exception e) {
            return redirectHome(firstCycle);
        }
    }

    @RequestMapping(value="/logs", method = RequestMethod.GET)
    public String listLogs(Model model) {

        //TODO fix bug where exception is thrown if adding or removing affinity after reyturning from log
        model.addAttribute("affinityLogs", cyclesAffinityService.getAffinityLogs());
        return view("logs");
    }
}