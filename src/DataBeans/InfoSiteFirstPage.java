/*
 * Created on 5/Mai/2003
 *
 * 
 */
package DataBeans;

import java.util.List;

/**
 * @author Jo�o Mota
 *
 * 
 */
public class InfoSiteFirstPage implements ISiteComponent {
	
	
	private String alternativeSite;
	private String initialStatement;
	private String introduction;	
	private InfoAnnouncement lastAnnouncement;
	private List responsibleTeachers;
	private List lecturingTeachers;
	
	

	/**
	 * @return
	 */
	public String getAlternativeSite() {
		return alternativeSite;
	}

	/**
	 * @return
	 */
	public String getInitialStatement() {
		return initialStatement;
	}

	/**
	 * @return
	 */
	public String getIntroduction() {
		return introduction;
	}

	/**
	 * @return
	 */
	public InfoAnnouncement getLastAnnouncement() {
		return lastAnnouncement;
	}

	/**
	 * @return
	 */
	public List getLecturingTeachers() {
		return lecturingTeachers;
	}

	/**
	 * @return
	 */
	public List getResponsibleTeachers() {
		return responsibleTeachers;
	}

	/**
	 * @param string
	 */
	public void setAlternativeSite(String string) {
		alternativeSite = string;
	}

	/**
	 * @param string
	 */
	public void setInitialStatement(String string) {
		initialStatement = string;
	}

	/**
	 * @param string
	 */
	public void setIntroduction(String string) {
		introduction = string;
	}

	/**
	 * @param announcement
	 */
	public void setLastAnnouncement(InfoAnnouncement announcement) {
		lastAnnouncement = announcement;
	}

	/**
	 * @param list
	 */
	public void setLecturingTeachers(List list) {
		lecturingTeachers = list;
	}

	/**
	 * @param list
	 */
	public void setResponsibleTeachers(List list) {
		responsibleTeachers = list;
	}

}
