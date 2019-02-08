package org.fenixedu.academic.ui.spring.controller.student;

import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.domain.student.RegistrationProtocol;
import org.fenixedu.bennu.spring.portal.SpringApplication;
import org.fenixedu.bennu.spring.portal.SpringFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@SpringApplication(path = "registrationProtocols", hint = "Manager", group = "#managers", title = "title.manage.registrationProtocols")
@SpringFunctionality(app = ManageRegistrationProtocolsController.class, title = "title.manage.registrationProtocols")
@RequestMapping("/registrationProtocols")
public class ManageRegistrationProtocolsController {

    @Autowired
    RegistrationProtocolsService registrationProtocolsService;

    private String redirectHome() {
        return "redirect:/registrationProtocols";
    }

    private String view(String string) {
        return "fenixedu-academic/registrationProtocol/" + string;
    }


    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("registrationProtocols", registrationProtocolsService.getAllRegistrationProtocols());
        return view("show");
    }

    @RequestMapping(method = RequestMethod.GET, value = "create")
    public String create(Model model) {
        return view("edit");
    }

    @RequestMapping(method = RequestMethod.POST, value = "create")
    public String create(Model model, @ModelAttribute RegistrationProtocolBean bean) {
        try {
            registrationProtocolsService.createRegistrationProtocol(bean);
            return redirectHome();
        } catch (DomainException de) {
            model.addAttribute("error", de.getLocalizedMessage());
            return redirectHome(); //TODO: replace
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{registrationProtocol}")
    public String edit(Model model, @PathVariable RegistrationProtocol registrationProtocol) {
        try {
            model.addAttribute("registrationProtocol", registrationProtocol);
            return view("edit");
        } catch (DomainException de) {
            model.addAttribute("error", de.getLocalizedMessage());
            return redirectHome();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "{registrationProtocol}")
    public String edit(Model model, @PathVariable RegistrationProtocol registrationProtocol, @ModelAttribute RegistrationProtocolBean bean) {
        try {
            registrationProtocolsService.editRegistrationProtocol(registrationProtocol, bean);
            return redirectHome();
        } catch (DomainException de) {
            model.addAttribute("error", de.getLocalizedMessage());
            return redirectHome(); //TODO: replace
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "logs")
    public String listLogs(Model model) {
        model.addAttribute("registrationProtocolsLogs", registrationProtocolsService.getAllRegistrationProtocolLogs());
        return view("showLogs");
    }
}
