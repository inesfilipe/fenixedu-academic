/*
 * Created on 23/Ago/2003
 *
 */
package ServidorAplicacao.Servico.teacher;

import java.util.Iterator;
import java.util.List;

import Dominio.DisciplinaExecucao;
import Dominio.IDisciplinaExecucao;
import Dominio.IFrequenta;
import Dominio.IStudent;
import Dominio.IStudentGroup;
import Dominio.IStudentGroupAttend;
import Dominio.StudentGroup;
import ServidorAplicacao.IServico;
import ServidorAplicacao.Servico.exceptions.FenixServiceException;
import ServidorAplicacao.Servico.exceptions.NonExistingServiceException;
import ServidorPersistente.ExcepcaoPersistencia;
import ServidorPersistente.IDisciplinaExecucaoPersistente;
import ServidorPersistente.IFrequentaPersistente;
import ServidorPersistente.IPersistentStudent;
import ServidorPersistente.IPersistentStudentGroup;
import ServidorPersistente.IPersistentStudentGroupAttend;
import ServidorPersistente.ISuportePersistente;
import ServidorPersistente.OJB.SuportePersistenteOJB;

/**
 * @author asnr and scpo
 *
 */

public class DeleteStudentGroupMembers implements IServico {

	
	private static DeleteStudentGroupMembers service = new DeleteStudentGroupMembers();

	/**
		* The singleton access method of this class.
		*/
	public static DeleteStudentGroupMembers getService() {
		return service;
	}
	/**
	 * The constructor of this class.
	 */
	private DeleteStudentGroupMembers() {}
	/**
	 * The name of the service
	 */
	public final String getNome() {
		return "DeleteStudentGroupMembers";
	}


	/**
	 * Executes the service.
	 */

	public void run(Integer executionCourseCode, Integer studentGroupCode,List studentUsernames) throws FenixServiceException {
		
		
		IDisciplinaExecucaoPersistente persistentExecutionCourse = null;
		IPersistentStudentGroup persistentStudentGroup = null;
		IFrequentaPersistente persistentAttend = null;
		IPersistentStudent persistentStudent = null;
		IPersistentStudentGroupAttend persistentStudentGroupAttend = null;
		
		
		try {
			
			ISuportePersistente persistentSupport = SuportePersistenteOJB.getInstance();
			
			persistentExecutionCourse = persistentSupport.getIDisciplinaExecucaoPersistente();
			persistentAttend = persistentSupport.getIFrequentaPersistente();
			persistentStudent = persistentSupport.getIPersistentStudent();
			persistentStudentGroup = persistentSupport.getIPersistentStudentGroup();
			persistentStudentGroupAttend = persistentSupport.getIPersistentStudentGroupAttend();
		
			IDisciplinaExecucao executionCourse = (IDisciplinaExecucao) persistentExecutionCourse.readByOId(new DisciplinaExecucao(executionCourseCode),false);
			IStudentGroup studentGroup =(IStudentGroup) persistentStudentGroup.readByOId(new StudentGroup(studentGroupCode), false);
			
			if(studentGroup==null)
				throw new NonExistingServiceException();
			
			Iterator iterator = studentUsernames.iterator();
			
			while (iterator.hasNext()) {
				IStudent student =  persistentStudent.readByUsername(iterator.next().toString());
				
				IFrequenta attend = persistentAttend.readByAlunoAndDisciplinaExecucao(student, executionCourse);
				
				IStudentGroupAttend oldStudentGroupAttend = persistentStudentGroupAttend.readBy(studentGroup,attend); 
				if(oldStudentGroupAttend!=null)
				{
				
				persistentStudentGroupAttend.delete(oldStudentGroupAttend);
				}
			}

		}
		catch (ExcepcaoPersistencia excepcaoPersistencia) {
			throw new FenixServiceException(excepcaoPersistencia.getMessage());
		}

	}
}
