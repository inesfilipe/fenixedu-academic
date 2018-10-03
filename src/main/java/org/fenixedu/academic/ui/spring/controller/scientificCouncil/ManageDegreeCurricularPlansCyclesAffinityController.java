package org.fenixedu.academic.ui.spring.controller.scientificCouncil;

import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.degreeStructure.CycleCourseGroup;
import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.ui.spring.service.DegreeCurricularPlansCyclesAffinityService;
import org.fenixedu.academic.ui.struts.action.exceptions.FenixActionException;
import org.fenixedu.bennu.spring.portal.SpringApplication;
import org.fenixedu.bennu.spring.portal.SpringFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@SpringApplication(path = "degreeAffinityCycles", hint = "Manager", group = "#managers", title = "title.manage.degreeCurricularPlans.affinity")
@SpringFunctionality(app = ManageDegreeCurricularPlansCyclesAffinityController.class, title = "title.manage.degreeCurricularPlans.affinity")
@RequestMapping("/cycle-affinity-management")
public class ManageDegreeCurricularPlansCyclesAffinityController {

    @Autowired
    private DegreeCurricularPlansCyclesAffinityService degreeCurricularPlansAffinityCyclesService;

    private String redirectHome() {
        return "redirect:/fenixedu-academic/cycle-affinity-management";
    }

    private String view(String string) {
        return "fenixedu-academic/cycle-affinity-management/" + string;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String listAllFirstCycle(Model model, @ModelAttribute DegreeCurricularPlansCycleBean firstCycleDegree, @ModelAttribute CycleCourseGroupAffinityBean newAffinity) {
        List<DegreeCurricularPlan> degreesFirstCycle = degreeCurricularPlansAffinityCyclesService.getAllFirstCycleDegrees();

        if(firstCycleDegree.getDegree() == null) {
            firstCycleDegree.setDegree(degreesFirstCycle.get(0));
        }

        List<CycleCourseGroup> potentialAffinities = degreeCurricularPlansAffinityCyclesService.getSecondCycleDegreesWithoutAffinity(firstCycleDegree);

        if(newAffinity.getSecondCycleCourseGroup() == null) {
            newAffinity.setSecondCycleCourseGroup(potentialAffinities.get(0));
        }

        model.addAttribute("degreesFirstCycle", degreesFirstCycle);
        model.addAttribute("firstCycleDegree", firstCycleDegree);
        model.addAttribute("affinities", degreeCurricularPlansAffinityCyclesService.getSecondCycleDegreesWithAffinity(firstCycleDegree));
        model.addAttribute("potentialAffinities", potentialAffinities);
        model.addAttribute("newAffinity", newAffinity);
        return view("show");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String addAffinity(Model model, @ModelAttribute DegreeCurricularPlansCycleBean firstCycleDegree, @ModelAttribute CycleCourseGroupAffinityBean newAffinity) {
        try {
            degreeCurricularPlansAffinityCyclesService.addDestinationAffinity(firstCycleDegree, newAffinity);
            return redirectHome();
        } catch (DomainException de) {
            model.addAttribute("error", de.getLocalizedMessage());
            return listAllFirstCycle(model, firstCycleDegree, newAffinity);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteSecondCycleAffinity(Model model) {
        return redirectHome();
    }
}