package smartAdmiss;
//Iterator<OntClass> it=model.listClasses();
//
//while(it.hasNext()){
//	OntClass ontcls=it.next();
//	System.out.println(ontcls.getURI());
//	
//	//ontcls.get
//	
//}


import java.util.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.ModelFactory;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;

public class AddIndividual  {
	 
	static OntModel model,modelUniversity;
//	public static void main(String ar[]) throws Exception{
//		 Scanner s=new Scanner(System.in);
//		 System.out.println("hgkfk");
////		String name = s.nextLine();
////		String dob =s.nextLine();
////		String sex = s.nextLine();
////		String contact = s.nextLine();
////		String appliedfor =s.nextLine();
////		String bemarks = s.nextLine();
////		String gatescore = s.nextLine();
////		String xmarks =s.nextLine();
////		String xiimarks =s.nextLine();
//		
//		String uname = s.nextLine();
//		String HOD =s.nextLine();
//		String DName = s.nextLine();
//		String CName1 = s.nextLine();
//		String CName1Criteria1 = s.nextLine();
//		String CName1Criteria2 =s.nextLine();
//		String CName2 = s.nextLine();
//		String CName2Criteria1 = s.nextLine();
//		String CName2Criteria2 =s.nextLine();
//		
//				
//		//System.out.println(name + "  "+ dob + "  "+  sex+" "+ contact +" "+ appliedfor +" "+ bemarks+" "+gatescore+" "+ xmarks+" "+ xiimarks);
//		System.out.println(uname + "  "+ HOD + "  "+  DName+" "+ CName1+" "+CName1Criteria1+" "+CName1Criteria2+" "+CName2+" "+CName2Criteria1+" "+CName2Criteria2);
//		
//		//addStudent(name,dob,sex,contact,appliedfor,bemarks,gatescore,xmarks,xiimarks);
//		addUniversity(uname,HOD,DName,CName1,CName1Criteria1,CName1Criteria2,CName2,CName2Criteria1,CName2Criteria2);
//	}
//	
	
	private static void addUniversity(String uname, String hOD, String dName, String cName1, String cName1Criteria1, String cName1Criteria2, String cName2, String cName2Criteria1, String cName2Criteria2) throws FileNotFoundException{
			
			
		String NS = "https://www.smartadmission.com/University#";
		
		InputStream in=new FileInputStream("C://Users//shub//workspace//smartAdmiss//Ontologies//UniversityOntology.owl");
		 modelUniversity= ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		 modelUniversity.read(in,null);
		 
		 OntClass university=modelUniversity.getOntClass(NS + "University");
		 OntClass universityDepartment=modelUniversity.getOntClass(NS + "Department");
		 OntClass universityCourse=modelUniversity.getOntClass(NS + "Course");

		 Individual university1 = modelUniversity.createIndividual(NS + "university1", university);
		 Individual department1 = modelUniversity.createIndividual(NS + "university1department1", universityDepartment);
		 Individual course1 = modelUniversity.createIndividual(NS + "university1department1course1", universityCourse);
		 Individual course2 = modelUniversity.createIndividual(NS + "university1department1course2", universityCourse);

			OntProperty hasCName =  modelUniversity.getOntProperty(NS+"hasCName");
		    OntProperty hasCriteria1 =  modelUniversity.getOntProperty(NS+"hasCriteria1");
		    OntProperty hasCriteria2 =  modelUniversity.getOntProperty(NS+"hasCriteria2");
		    OntProperty hasDName =  modelUniversity.getOntProperty(NS+"hasDName");
		    OntProperty hasHOD =  modelUniversity.getOntProperty(NS+"hasHOD");
		    OntProperty hasUName =  modelUniversity.getOntProperty(NS+"hasUName");
		    OntProperty hasCourse =  modelUniversity.getOntProperty(NS+"hasCourse");
		    OntProperty hasDepartment =  modelUniversity.getOntProperty(NS+"hasDepartment");
		    
		    modelUniversity.add(university1, hasUName, uname);
		    modelUniversity.add(department1, hasDName, dName);
		    modelUniversity.add(department1, hasHOD, hOD);

		    modelUniversity.add(course1, hasCName, cName1);
		    modelUniversity.add(course1, hasCriteria1, cName1Criteria1);
		    modelUniversity.add(course1, hasCriteria2, cName1Criteria2);
		    modelUniversity.add(course2, hasCName, cName2);
		    modelUniversity.add(course2, hasCriteria1,cName2Criteria1);
		    modelUniversity.add(course2, hasCriteria2, cName2Criteria2);

		    modelUniversity.add(university1, hasDepartment, department1);
		    modelUniversity.add(department1, hasCourse, course1);
		    modelUniversity.add(department1, hasCourse, course2);

		    modelUniversity.write(System.out);
		    PrintStream p= new PrintStream("C://Users//shub//workspace//smartAdmiss//Ontologies//University_Instance.owl");
		    modelUniversity.write(p);
		    p.close();


	}


	public static void addStudent(String name, String dob, String sex, String contact, String appliedfor, String bemarks, String gatescore, String xmarks, String xiimarks)throws Exception
	  {
		
		String NS = "https://www.smartadmission.com/Student#";
		  
		  InputStream in=new FileInputStream("C://Users//shub//workspace//smartAdmiss//Ontologies//Student_Instance.owl");
		 model= ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
		 
		  model.read(in, null);
		    int n=inference.getIndiviual();
	        model.write(System.out);
		    OntClass student = model.getOntClass(NS + "Student");
		    OntClass studentQualification=model.getOntClass(NS + "Qualification");
		   // Individual[] array=new Individual[n];
		    Individual x=model.getIndividual(NS+"student2");
		    Individual student1 = model.createIndividual(NS + "student"+(n+1), student);
		    Individual student1qualification=model.createIndividual(NS + "student"+(n+1)+"qualification", studentQualification);
		    
		    OntProperty hasName =  model.getOntProperty(NS+"hasName");
		    OntProperty hasDOB =  model.getOntProperty(NS+"hasDOB");
		    OntProperty hasSex =  model.getOntProperty(NS+"hasSex");
		    OntProperty hasContact =  model.getOntProperty(NS+"hasContact");
		    OntProperty appliedFor =  model.getOntProperty(NS+"appliedFor");
		    OntProperty consistOfXIIMarks =  model.getOntProperty(NS+"consistOfXIIMarks");
		    OntProperty consistOfXMarks =  model.getOntProperty(NS+"consistOfXMarks");
		    OntProperty consistOfGraduationMarks =  model.getOntProperty(NS+"consistOfGraduationMarks");
		    OntProperty consistOfGateMarks =  model.getOntProperty(NS+"consistOfGateMarks");
		    OntProperty hasQualification =  model.getOntProperty(NS+"hasQualification");
		    
		    model.add(student1, hasName, name);
		    model.add(student1, hasDOB, dob);
		    model.add(student1, hasSex, sex);
		    model.add(student1, hasContact, contact);
		    model.add(student1, appliedFor, appliedfor);
		    model.add(student1qualification, consistOfXIIMarks, xiimarks);
		    model.add(student1qualification, consistOfXMarks, xmarks);
		    model.add(student1qualification, consistOfGraduationMarks, bemarks);
		    model.add(student1qualification, consistOfGateMarks, gatescore);
		    model.add(student1,hasQualification,student1qualification);
		    
		    model.write(System.out);
		    PrintStream p= new PrintStream("C://Users//shub//workspace//smartAdmiss//Ontologies//Student_Instance.owl");
		    model.write(p);
		    p.close();
		    
		    
		    
		    
	  }
	 
}



