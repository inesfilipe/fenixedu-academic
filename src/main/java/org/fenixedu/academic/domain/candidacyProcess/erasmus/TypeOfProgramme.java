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
package org.fenixedu.academic.domain.candidacyProcess.erasmus;

import org.fenixedu.academic.util.Bundle;
import org.fenixedu.bennu.core.i18n.BundleUtil;

public enum TypeOfProgramme {
    PROJECT, THESIS, LAB, SEMINAR, COURSES;

    public String getName() {
        return name();
    }

    public String getQualifiedName() {
        return TypeOfProgramme.class.getSimpleName() + "." + name();
    }

    public String getFullyQualifiedName() {
        return TypeOfProgramme.class.getName() + "." + name();
    }

    public String getDescription() {
        return BundleUtil.getString(Bundle.ENUMERATION, getFullyQualifiedName());
    }
}
