package org.fenixedu.academic.ui.spring.controller;

import org.fenixedu.academic.ui.spring.controller.manager.DegreeCurricularPlansAffinityCyclesService;
import org.fenixedu.bennu.spring.portal.SpringFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@SpringFunctionality(app = ManageDegreeCurricularPlansAffinityCyclesController.class, title = "title.manage.degreeCurricularPlans.affinity",
        accessGroup = "academic(MANAGE_DEGREE_CURRICULAR_PLANS)")
@RequestMapping("/degree-affinity-manager")
public class ManageDegreeCurricularPlansAffinityCycles {

    @Autowired
    private DegreeCurricularPlansAffinityCyclesService degreeCurricularPlansAffinityCyclesService;

    @RequestMapping(method = RequestMethod.GET)
    public String listFirstCycles(Model model) {
        model.addAttribute("degreesFirstCycle", degreeCurricularPlansAffinityCyclesService.getAllFirstCycleDegrees());
        return ""
    }
}
