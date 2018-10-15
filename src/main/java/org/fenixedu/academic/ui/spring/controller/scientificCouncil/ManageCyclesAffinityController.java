package org.fenixedu.academic.ui.spring.controller.scientificCouncil;

import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.degreeStructure.CycleCourseGroup;
import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.ui.spring.service.CyclesAffinityService;
import org.fenixedu.bennu.spring.portal.SpringApplication;
import org.fenixedu.bennu.spring.portal.SpringFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return "redirect:/cycle-affinity-management";
    }

    private String redirectHome(DegreeCurricularPlansCycleBean bean) {
        return redirectHome() + "?degree=" + bean.getDegree().getExternalId();
    }

    private String view(String string) {
        return "fenixedu-academic/cycle-affinity-management/" + string;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listAllFirstCycle(Model model, @ModelAttribute DegreeCurricularPlansCycleBean firstCycleDegree, @ModelAttribute CycleCourseGroupAffinityBean newAffinity) {
        List<DegreeCurricularPlan> degreesFirstCycle = cyclesAffinityService.getAllFirstCycleDegrees();

        if(firstCycleDegree.getDegree() == null) {
            firstCycleDegree.setDegree(degreesFirstCycle.get(0));
        }

        List<CycleCourseGroup> potentialAffinities = cyclesAffinityService.getSecondCycleDegreesWithoutAffinity(firstCycleDegree);

        if(newAffinity.getSecondCycleCourseGroup() == null) {
            newAffinity.setSecondCycleCourseGroup(potentialAffinities.get(0));
        }

        model.addAttribute("degreesFirstCycle", degreesFirstCycle);
        model.addAttribute("firstCycleDegree", firstCycleDegree);
        model.addAttribute("affinities", cyclesAffinityService.getSecondCycleDegreesWithAffinity(firstCycleDegree));
        model.addAttribute("potentialAffinities", potentialAffinities);
        model.addAttribute("newAffinity", newAffinity);
        return view("show");
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addAffinity(Model model, @ModelAttribute DegreeCurricularPlansCycleBean firstCycleDegree, @ModelAttribute CycleCourseGroupAffinityBean newAffinity) {
        try {
            cyclesAffinityService.addDestinationAffinity(firstCycleDegree, newAffinity);
            return listAllFirstCycle(model, firstCycleDegree, newAffinity);
        } catch (DomainException de) {
            model.addAttribute("error", de.getLocalizedMessage());
            return listAllFirstCycle(model, firstCycleDegree, newAffinity);
        }
    }

    @RequestMapping(value="/deleteAffinity", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> deleteSecondCycleAffinity(Model model, @RequestParam  DegreeCurricularPlan degree, @RequestParam CycleCourseGroup affinity) {
        try {
            cyclesAffinityService.deleteDestinationAffinity(degree, affinity);
            return new ResponseEntity<String>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getLocalizedMessage(), HttpStatus.PRECONDITION_FAILED);
        }
    }
}