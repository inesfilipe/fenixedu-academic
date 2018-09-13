package org.fenixedu.academic.ui.spring.controller.scientificCouncil;

import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.ui.spring.service.DegreeCurricularPlansCyclesAffinityService;
import org.fenixedu.bennu.spring.portal.SpringApplication;
import org.fenixedu.bennu.spring.portal.SpringFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String listAllFirstCycle(Model model, @ModelAttribute DegreeCurricularPlan firstCycleDegree) {
        model.addAttribute("firstCycleDegree", firstCycleDegree);
        model.addAttribute("degreesFirstCycle", degreeCurricularPlansAffinityCyclesService.getAllFirstCycleDegrees());
        model.addAttribute("afffinities", degreeCurricularPlansAffinityCyclesService.getSecondCycleDegreesWithAffinity(firstCycleDegree));
        return view("show");
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String addSecondCycleAffinity(Model model) {
        return redirectHome();
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteSecondCycleAffinity(Model model) {
        return redirectHome();
    }
}