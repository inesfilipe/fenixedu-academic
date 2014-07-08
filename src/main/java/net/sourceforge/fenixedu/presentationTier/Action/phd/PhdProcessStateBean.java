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
package net.sourceforge.fenixedu.presentationTier.Action.phd;

import java.io.Serializable;

import net.sourceforge.fenixedu.domain.phd.PhdProcessState;
import net.sourceforge.fenixedu.domain.phd.PhdProgramProcess;

import org.joda.time.DateTime;

public class PhdProcessStateBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private DateTime stateDate;
    private PhdProcessState state;

    public PhdProcessStateBean(final PhdProcessState state) {
        this.state = state;
        this.stateDate = state.getStateDate();
    }

    public DateTime getStateDate() {
        return stateDate;
    }

    public void setStateDate(DateTime stateDate) {
        this.stateDate = stateDate;
    }

    public PhdProcessState getState() {
        return state;
    }

    public PhdProgramProcess getPhdProcess() {
        return state.getProcess();
    }
}