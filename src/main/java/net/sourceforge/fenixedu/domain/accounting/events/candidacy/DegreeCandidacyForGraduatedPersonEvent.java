/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Core.
 *
 * FenixEdu Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Core.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.sourceforge.fenixedu.domain.accounting.events.candidacy;

import net.sourceforge.fenixedu.domain.Degree;
import net.sourceforge.fenixedu.domain.Person;
import net.sourceforge.fenixedu.domain.accounting.EntryType;
import net.sourceforge.fenixedu.domain.accounting.EventType;
import net.sourceforge.fenixedu.domain.administrativeOffice.AdministrativeOffice;
import net.sourceforge.fenixedu.domain.administrativeOffice.AdministrativeOfficeType;
import net.sourceforge.fenixedu.domain.candidacyProcess.graduatedPerson.DegreeCandidacyForGraduatedPerson;

public class DegreeCandidacyForGraduatedPersonEvent extends DegreeCandidacyForGraduatedPersonEvent_Base {

    private DegreeCandidacyForGraduatedPersonEvent() {
        super();
    }

    public DegreeCandidacyForGraduatedPersonEvent(final DegreeCandidacyForGraduatedPerson candidacy, final Person person) {
        this();
        super.init(candidacy, EventType.DEGREE_CANDIDACY_FOR_GRADUATED_PERSON, person);

        attachAvailablePaymentCode(person);
    }

    @Override
    protected AdministrativeOffice readAdministrativeOffice() {
        return AdministrativeOffice.readByAdministrativeOfficeType(AdministrativeOfficeType.DEGREE);
    }

    @Override
    public DegreeCandidacyForGraduatedPerson getIndividualCandidacy() {
        return (DegreeCandidacyForGraduatedPerson) super.getIndividualCandidacy();
    }

    public Degree getCandidacyDegree() {
        return getIndividualCandidacy().getSelectedDegree();
    }

    @Override
    protected EntryType getEntryType() {
        return EntryType.DEGREE_CANDIDACY_FOR_GRADUATED_PERSON_FEE;
    }

}