package smartAdmiss;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

import org.mindswap.pellet.jena.PelletReasonerFactory;

import com.hp.hpl.jena.ontology.Individual;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntProperty;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.reasoner.ValidityReport;
import com.hp.hpl.jena.reasoner.rulesys.GenericRuleReasoner;
import com.hp.hpl.jena.reasoner.rulesys.Rule;
import com.hp.hpl.jena.util.FileManager;

public class UniversityMatching {
	static String defaultNameSpace0 = "https://www.smartadmission.com/Student#";
	static String defaultNameSpace1 = "https://www.smartadmission.com/University#";
	static String defaultNameSpace2 = "https://www.smartadmission.com/ForeignUniversity1#";
	static OntModel modelUniversity=null,schema=null;
	static Model studentOnt = null;
	static InfModel inferred=null;
	static String name;   //////////////////////-----------------------    enter the name here for person
	static String appliedFor;    ////// --------    applied     
	static RDFNode graduationMarks, gateMarks,XMarks,XIIMarks, applied;
	static RDFNode uName,departmentName , HOD , criteria1,	 criteria2;
	
	public static StringBuilder getUniv(String name1, String appliedFor1) throws IOException {
		// TODO Auto-generated method stub
		 
		name=name1;
		appliedFor=appliedFor1.toUpperCase();
		System.out.println(name+" "+appliedFor);
		Scanner s=new Scanner(System.in);
		 UniversityMatching x=new UniversityMatching();
//		 System.out.println("hgkfk");
//		String uname = s.nextLine();
//		String HOD =s.nextLine();
//		String DName = s.nextLine();
//		String CName1 = s.nextLine();
//		String CName1Criteria1 = s.nextLine();
//		String CName1Criteria2 =s.nextLine();
//		String CName2 = s.nextLine();
//		String CName2Criteria1 = s.nextLine();
//		String CName2Criteria2 =s.nextLine();
//		System.out.println(uname + "  "+ HOD + "  "+  DName+" "+ CName1+" "+CName1Criteria1+" "+CName1Criteria2+" "+CName2+" "+CName2Criteria1+" "+CName2Criteria2);
//		//addUniversity(uname,HOD,DName,CName1,CName1Criteria1,CName1Criteria2,CName2,CName2Criteria1,CName2Criteria2);
		 
		 populateStudent();
		 getStudentQualifications(studentOnt);
		 populateUnivesity();
		 populateNewUniversity();
		 populateBothSchema();
		 addAlignment();
		 bindReasoner();
		 
//		 PrintStream p= new PrintStream("C://Users//shub//workspace//smartAdmiss//Ontologies//inferredmodel.owl");
//		    inferred.write(p);
// p.close();
		 return helloToAllUniversities(inferred);
	}
	private static void getStudentQualifications(Model studentOnt) {
		
		runQueryStudent(" SELECT ?appliedFor ?XMarks ?XIIMarks ?graduationMarks ?gateMarks WHERE { ?dh st:hasName ?kk. filter(str(?kk)="+"'"+name+"'"+"). ?dh st:hasQualification ?quali .?dh st:appliedFor ?appliedFor. ?quali st:consistOfGateMarks ?gateMarks . ?quali st:consistOfGraduationMarks ?graduationMarks .?quali st:consistOfXMarks ?XMarks .?quali st:consistOfXIIMarks ?XIIMarks   }", studentOnt);
		System.out.println("yes sdf");
	}
	public static int getIndiviual()
	  {
		 int count=0;
		  Model model = ModelFactory.createOntologyModel();
			InputStream inFoafInstance = FileManager.get().open("C://Users//shub//workspace//smartAdmiss//Ontologies//Student_Instance.owl");
			model.read(inFoafInstance,defaultNameSpace1);
		  StringBuffer queryStr = new StringBuffer();
			// Establish Prefixes
			//Set default Name space first
		    String queryRequest="SELECT * WHERE {?a st:hasName ?o .}";
			queryStr.append("PREFIX st" + ": <" + defaultNameSpace1 + "> ");
			queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
			queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
			queryStr.append("PREFIX owl" + ": <" + "http://www.w3.org/2002/07/owl#" + "> ");
			queryStr.append("PREFIX xsd" + ": <" + "http://www.w3.org/2001/XMLSchema#" + "> ");
			queryStr.append(queryRequest);
			Query query = QueryFactory.create(queryStr.toString());
			QueryExecution qexec = QueryExecutionFactory.create(query, model);
			try {
				System.out.println("check");
				ResultSet response = qexec.execSelect();
				while(response.hasNext())
					{count++;
					response.next().toString();
					// System.out.println(response.next().toString());
					}
			} finally { qexec.close();}				
		
		  return count;
	  }
	static private void populateStudent(){
		studentOnt = ModelFactory.createOntologyModel();
		InputStream inFoafInstance = FileManager.get().open("C://Users//shub//workspace//smartAdmiss//Ontologies//Student_Instance.owl");
		studentOnt.read(inFoafInstance,defaultNameSpace0);
		System.out.println("yes");
		//inFoafInstance.close();

	}
	private static void populateUnivesity() throws IOException{
		modelUniversity = ModelFactory.createOntologyModel();
		InputStream inFoafInstance = FileManager.get().open("C://Users//shub//workspace//smartAdmiss//Ontologies//University_Instance.owl");
		modelUniversity.read(inFoafInstance,defaultNameSpace2);
		inFoafInstance.close();

	}

	private static void populateNewUniversity() throws IOException {		
		InputStream inFoafInstance = FileManager.get().open("C://Users//shub//workspace//smartAdmiss//Ontologies//ForeignUniversity_Instance1.owl");
		modelUniversity.read(inFoafInstance,defaultNameSpace2);
		inFoafInstance.close();


	} 
	private static void populateBothSchema() throws IOException{
		InputStream inFoaf = FileManager.get().open("C://Users//shub//workspace//smartAdmiss//Ontologies//University_Instance.owl");
		
		schema = ModelFactory.createOntologyModel();
	
		schema.read(inFoaf, defaultNameSpace2);
	
		inFoaf.close();
		System.out.println("here now");
		}
	private static void addAlignment(){
		
		// State that :individual is equivalentClass of foaf:Person
		Resource resource = schema.createResource(defaultNameSpace2 + "College");
		Property prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentClass");
		Resource obj =  schema.createResource(defaultNameSpace1 + "University");
		schema.add(resource,prop,obj);
		
		resource = schema.createResource(defaultNameSpace2 + "Programme");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentClass");
		 obj =  schema.createResource(defaultNameSpace1 + "Course");
		schema.add(resource,prop,obj);
		
		resource = schema.createResource(defaultNameSpace2 + "Depart");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentClass");
		 obj =  schema.createResource(defaultNameSpace1 + "Department");
		schema.add(resource,prop,obj);
		
		
		 //State that :hasName is an equivalentProperty of foaf:name
		 resource = schema.createResource(defaultNameSpace2 + "hasCollegeName");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		 obj = schema.createResource(defaultNameSpace1 + "hasUName");
		 schema.add(resource,prop,obj);
		 
		 resource = schema.createResource(defaultNameSpace2 + "hasDNames");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		 obj = schema.createResource(defaultNameSpace1 + "hasDName");
		 schema.add(resource,prop,obj);
		 
		 resource = schema.createResource(defaultNameSpace2 + "hasDepart");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		 obj = schema.createResource(defaultNameSpace1 + "hasDepartment");
		 schema.add(resource,prop,obj);
		 
		 
		 resource = schema.createResource(defaultNameSpace2 + "hasPName");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		 obj = schema.createResource(defaultNameSpace1 + "hasCName");
		 schema.add(resource,prop,obj);
		 
		 resource = schema.createResource(defaultNameSpace2 + "hasHead");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		 obj = schema.createResource(defaultNameSpace1 + "hasHOD");
		 schema.add(resource,prop,obj);
		 
		 resource = schema.createResource(defaultNameSpace2 + "hasProgramme");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		 obj = schema.createResource(defaultNameSpace1 + "hasCourse");
		 schema.add(resource,prop,obj);
		 
		 resource = schema.createResource(defaultNameSpace2 + "hasC1");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		 obj = schema.createResource(defaultNameSpace1 + "hasCriteria1");
		 schema.add(resource,prop,obj);
		 
		 resource = schema.createResource(defaultNameSpace2 + "hasC2");
		 prop = schema.createProperty("http://www.w3.org/2002/07/owl#equivalentProperty");
		 obj = schema.createResource(defaultNameSpace1 + "hasCriteria2");
		 schema.add(resource,prop,obj);
			
		// //State that sem web is the same person as Semantic Web
		// resource = schema.createResource("http://org.semwebprogramming/chapter2/people#me");
		// prop = schema.createProperty("http://www.w3.org/2002/07/owl#sameAs");
		// obj = schema.createResource("http://org.semwebprogramming/chapter2/people#Individual_5");
		// schema.add(resource,prop,obj);
	}	

	private static void bindReasoner(){
	    Reasoner reasoner =  ReasonerRegistry.getOWLReasoner();
	    reasoner = reasoner.bindSchema(schema);
	    inferred =   ModelFactory.createInfModel(reasoner, modelUniversity);
	    System.out.println("now here");
	}

private static void runQueryStudent(String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX st" + ": <" + defaultNameSpace0 + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX owl" + ": <" + "http://www.w3.org/2002/07/owl#" + "> ");
		queryStr.append("PREFIX xsd" + ": <" + "http://www.w3.org/2001/XMLSchema#" + "> ");
	
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			 graduationMarks = soln.get("?graduationMarks");
			 gateMarks=soln.get("?gateMarks");
			 XMarks=soln.get("?XMarks");
			 XIIMarks=soln.get("?XIIMarks");
			 applied=soln.get("?appliedFor");
			
			System.out.println(appliedFor.toString()+" "+XMarks.toString()+ " " + XIIMarks.toString()+" " + graduationMarks.toString() +" " + gateMarks.toString());
			System.out.println("yes sdf vdd");		
			}
		} finally { qexec.close();}				
		}

//	private static void addUniversity(String uname, String hOD, String dName, String cName1, String cName1Criteria1,
//		String cName1Criteria2, String cName2, String cName2Criteria1, String cName2Criteria2) throws FileNotFoundException {
//		// TODO Auto-generated method stub
//		String NS = "https://www.smartadmission.com/ForeignUniversity1#";
//		InputStream in=new FileInputStream("C://Users//shub//workspace//smartAdmiss//Ontologies//ForeignUniversityOntology1.owl");
//		 modelUniversity= ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);
//		 modelUniversity.read(in,null);
//		 OntClass university=modelUniversity.getOntClass(NS + "College");
//		 OntClass universityDepartment=modelUniversity.getOntClass(NS + "Department");
//		 OntClass universityCourse=modelUniversity.getOntClass(NS + "Programme");
//
//		 Individual university1 = modelUniversity.createIndividual(NS + "college1", university);
//		 Individual department1 = modelUniversity.createIndividual(NS + "college1department1", universityDepartment);
//		 Individual course1 = modelUniversity.createIndividual(NS + "college1department1programme1", universityCourse);
//		 Individual course2 = modelUniversity.createIndividual(NS + "college1department1programme2", universityCourse);
//
//			OntProperty hasCName =  modelUniversity.getOntProperty(NS+"hasPName");
//		    OntProperty hasCriteria1 =  modelUniversity.getOntProperty(NS+"hasCriteria1");
//		    OntProperty hasCriteria2 =  modelUniversity.getOntProperty(NS+"hasCriteria2");
//		    OntProperty hasDName =  modelUniversity.getOntProperty(NS+"hasDName");
//		    OntProperty hasHOD =  modelUniversity.getOntProperty(NS+"hasHead");
//		    OntProperty hasUName =  modelUniversity.getOntProperty(NS+"hasCollegeName");
//		    OntProperty hasCourse =  modelUniversity.getOntProperty(NS+"hasProgramme");
//		    OntProperty hasDepartment =  modelUniversity.getOntProperty(NS+"hasDepartment");
//		    
//		    modelUniversity.add(university1, hasUName, uname);
//		    modelUniversity.add(department1, hasDName, dName);
//		    modelUniversity.add(department1, hasHOD, hOD);
//
//		    modelUniversity.add(course1, hasCName, cName1);
//		    modelUniversity.add(course1, hasCriteria1, cName1Criteria1);
//		    modelUniversity.add(course1, hasCriteria2, cName1Criteria2);
//		    modelUniversity.add(course2, hasCName, cName2);
//		    modelUniversity.add(course2, hasCriteria1,cName2Criteria1);
//		    modelUniversity.add(course2, hasCriteria2, cName2Criteria2);
//
//		    modelUniversity.add(university1, hasDepartment, department1);
//		    modelUniversity.add(department1, hasCourse, course1);
//		    modelUniversity.add(department1, hasCourse, course2);
//
//		    modelUniversity.write(System.out);
//		    PrintStream p= new PrintStream("C://Users//shub//workspace//smartAdmiss//Ontologies//ForeignUniversity_Instance1.owl");
//		    modelUniversity.write(p);
//		    p.close();
//	}

//	private static void helloToAllUniversities(Model model){
//		runQueryUniversity("SELECT * where {?x ut:hasUName ?UName .?x ut:hasDepartment ?deartment.?department ut:hasDName ?departmentName} ",model);
//		
//		//runQuery("SELECT * WHERE { ?x ut:hasDepartment ?department . ?department ut:hasCourse ?c . ?c ut:hasCName ?kk. filter(str(?kk)="+"'"+applied+"'"+"). ?x ut:hasUName ?UName.?department ut:hasHOD ?HOD.?department ut:hasDName ?departmentName.?c ut:hasCriteria1 ?criteria1.?c ut:hasCriteria2 ?criteria2}", model);  //add the query string
//	}
	private static void runQueryUniversity(String queryRequest, Model model){
			StringBuffer queryStr = new StringBuffer();
		queryStr.append("PREFIX ut" + ": <" + defaultNameSpace0 + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX owl" + ": <" + "http://www.w3.org/2002/07/owl#" + "> ");
		queryStr.append("PREFIX xsd" + ": <" + "http://www.w3.org/2001/XMLSchema#" + "> ");
	
		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			 uName = soln.get("?UName");
			 departmentName=soln.get("?departmentName");
//			 HOD=soln.get("?HOD");
//			 criteria1=soln.get("?criteria1");
//			 criteria2=soln.get("?criteria2");
		System.out.println(uName.toString());//+" "+departmentName.toString());//+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString());
						
		
			}
		} finally { qexec.close();}				
		}
	private static StringBuilder helloToAllUniversities(Model model) {
		// TODO Auto-generated method stub
		
		// Establish Prefixes
		//Set default Name space first
		StringBuffer queryStr1 = new StringBuffer();
		StringBuilder resultset = new StringBuilder();
		String queryStr2,queryStr3;
		queryStr1.append("PREFIX ut" + ": <" + defaultNameSpace1 + "> ");
		queryStr1.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr1.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr1.append("PREFIX owl" + ": <" + "http://www.w3.org/2002/07/owl#" + "> ");
		queryStr1.append("PREFIX xsd" + ": <" + "http://www.w3.org/2001/XMLSchema#" + "> ");
		String queryRequest="SELECT ?univ 	WHERE { ?univ ut:hasUName ?o . }";
		//Now add query
		queryStr2=queryStr1.toString();
		queryStr1.append(queryRequest);
		Query query = QueryFactory.create(queryStr1.toString());
		
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			queryStr3=queryStr2;
			
			QuerySolution soln = response.nextSolution();
						 uName = soln.get("?univ");
						 //System.out.println(uName.toString());
						 String x="<"+uName.toString().trim()+">";
						 
						 queryStr3+="SELECT ?UName ?departmentName ?HOD	?criteria1 ?criteria2 WHERE { "+ x +" ut:hasDepartment ?department . ?department ut:hasCourse ?c . ?c ut:hasCName ?kk. filter(str(?kk)="+"'"+appliedFor+"'"+"). "+x+" ut:hasUName ?UName.?department ut:hasHOD ?HOD.?department ut:hasDName ?departmentName.?c ut:hasCriteria1 ?criteria1.?c ut:hasCriteria2 ?criteria2}";
						 //System.out.println(queryStr3);
						 Query query1=QueryFactory.create(queryStr3);
						 QueryExecution qexec1=QueryExecutionFactory.create(query1, model);
						 ResultSet response1=qexec1.execSelect();
						 QuerySolution soln1 = response1.nextSolution();
						 uName=soln1.get("?UName"); departmentName=soln1.get("?departmentName"); HOD=soln1.get("?HOD"); criteria1=soln1.get("?criteria1"); criteria2=soln1.get("?criteria2");
						// System.out.println(uName.toString() +" "+departmentName.toString()+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString());
						 //soln1=response1.nextSolution();
						// System.out.println(soln1.get("?department").toString() +" "+soln1.get("?c").toString());
						  if(appliedFor.toString().equalsIgnoreCase("MTECH")){
						  		int universityBtechMarks=Integer.parseInt(criteria1.toString());
						  		int universityGateMarks=Integer.parseInt(criteria2.toString());
						  		int studentBtechMarks = Integer.parseInt(graduationMarks.toString());
				  				int studentGateMarks= Integer.parseInt(gateMarks.toString());
						  				if(studentBtechMarks >= universityBtechMarks ){
						  					if(studentGateMarks>=universityGateMarks){
						  			System.out.println(uName.toString() +" "+departmentName.toString()+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString());
						  				resultset.append(uName.toString() +" "+departmentName.toString()+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString()+" ***");
						  					}
						  					else{
											System.out.println(uName.toString() +" "+departmentName.toString()+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString() +"ur gate marks less try next year for this college");
						  					resultset.append(uName.toString() +" "+departmentName.toString()+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString() +"ur gate marks less try next year for this college"+" ***");
						  					}}
						  				System.out.println(resultset);

						  }
						  else{
						  	int universityXMarks=Integer.parseInt(criteria1.toString());
						  	int universityXIIMarks=Integer.parseInt(criteria2.toString());
						  	int studentXMarks=Integer.parseInt(XMarks.toString());
					  		int studentXIIMarks=Integer.parseInt(XIIMarks.toString());	
					  		if(studentXMarks >= universityXMarks && studentXIIMarks>=universityXIIMarks){
					  		System.out.println(uName.toString() +" "+departmentName.toString()+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString());	
					  		resultset.append(uName.toString() +" "+departmentName.toString()+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString()+" ***");
					  		}
					  		}
						 
						 
			}
		} finally { qexec.close();}	
return resultset;

	}

}
