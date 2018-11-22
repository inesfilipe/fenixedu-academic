package org.fenixedu.academic.ui.spring.controller.student;

import org.fenixedu.commons.i18n.LocalizedString;

public class RegistrationProtocolBean {

    private String code;
    private LocalizedString description;
    private Boolean enrolmentByStudentAllowed;
    private Boolean payGratuity;
    private Boolean allowsIDCard;
    private Boolean onlyAllowedDegreeEnrolment;
    private Boolean isAlien;
    private Boolean exempted;
    private Boolean mobility;
    private Boolean military;
    private Boolean allowDissertationCandidacyWithoutChecks;
    private Boolean forOfficialMobilityReporting;
    private Boolean attemptAlmaMatterFromPrecedent;

    public String getCode() {
        return code;
    }

    public LocalizedString getDescription() {
        return description;
    }

    public Boolean getEnrolmentByStudentAllowed() {
        return enrolmentByStudentAllowed;
    }

    public Boolean getPayGratuity() {
        return payGratuity;
    }

    public Boolean getAllowsIDCard() {
        return allowsIDCard;
    }

    public Boolean getOnlyAllowedDegreeEnrolment() {
        return onlyAllowedDegreeEnrolment;
    }

    public Boolean getAlien() {
        return isAlien;
    }

    public Boolean getExempted() {
        return exempted;
    }

    public Boolean getMobility() {
        return mobility;
    }

    public Boolean getMilitary() {
        return military;
    }

    public Boolean getAllowDissertationCandidacyWithoutChecks() {
        return allowDissertationCandidacyWithoutChecks;
    }

    public Boolean getForOfficialMobilityReporting() {
        return forOfficialMobilityReporting;
    }

    public Boolean getAttemptAlmaMatterFromPrecedent() {
        return attemptAlmaMatterFromPrecedent;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDescription(LocalizedString description) {
        this.description = description;
    }

    public void setEnrolmentByStudentAllowed(Boolean enrolmentByStudentAllowed) {
        this.enrolmentByStudentAllowed = enrolmentByStudentAllowed;
    }

    public void setPayGratuity(Boolean payGratuity) {
        this.payGratuity = payGratuity;
    }

    public void setAllowsIDCard(Boolean allowsIDCard) {
        this.allowsIDCard = allowsIDCard;
    }

    public void setOnlyAllowedDegreeEnrolment(Boolean onlyAllowedDegreeEnrolment) {
        this.onlyAllowedDegreeEnrolment = onlyAllowedDegreeEnrolment;
    }

    public void setAlien(Boolean alien) {
        isAlien = alien;
    }

    public void setExempted(Boolean exempted) {
        this.exempted = exempted;
    }

    public void setMobility(Boolean mobility) {
        this.mobility = mobility;
    }

    public void setMilitary(Boolean military) {
        this.military = military;
    }

    public void setAllowDissertationCandidacyWithoutChecks(Boolean allowDissertationCandidacyWithoutChecks) {
        this.allowDissertationCandidacyWithoutChecks = allowDissertationCandidacyWithoutChecks;
    }

    public void setForOfficialMobilityReporting(Boolean forOfficialMobilityReporting) {
        this.forOfficialMobilityReporting = forOfficialMobilityReporting;
    }

    public void setAttemptAlmaMatterFromPrecedent(Boolean attemptAlmaMatterFromPrecedent) {
        this.attemptAlmaMatterFromPrecedent = attemptAlmaMatterFromPrecedent;
    }
}
