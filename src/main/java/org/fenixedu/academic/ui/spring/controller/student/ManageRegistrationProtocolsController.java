package org.fenixedu.academic.ui.spring.controller.student;

import org.fenixedu.bennu.spring.portal.SpringApplication;
import org.fenixedu.bennu.spring.portal.SpringFunctionality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
