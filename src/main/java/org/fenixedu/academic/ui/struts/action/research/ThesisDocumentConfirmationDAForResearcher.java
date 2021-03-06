/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Academic.
 *
 * FenixEdu Academic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Academic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.academic.ui.struts.action.research;

import org.fenixedu.academic.ui.struts.action.research.ResearcherApplication.ResearcherFinalWorkApp;
import org.fenixedu.academic.ui.struts.action.teacher.ThesisDocumentConfirmationDA;
import org.fenixedu.bennu.struts.annotations.Forward;
import org.fenixedu.bennu.struts.annotations.Forwards;
import org.fenixedu.bennu.struts.annotations.Mapping;
import org.fenixedu.bennu.struts.portal.StrutsFunctionality;

@StrutsFunctionality(app = ResearcherFinalWorkApp.class, path = "document-confirmation",
        titleKey = "link.manage.thesis.document.confirmation")
@Mapping(module = "researcher", path = "/thesisDocumentConfirmation")
@Forwards({ @Forward(name = "viewThesis", path = "/teacher/viewThesis.jsp"),
        @Forward(name = "showThesisList", path = "/teacher/showThesisList.jsp") })
public class ThesisDocumentConfirmationDAForResearcher extends ThesisDocumentConfirmationDA {
}