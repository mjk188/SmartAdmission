package smartAdmiss;

import java.io.InputStream;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.InfModel;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.FileManager;

public class inference {
	static String defaultNameSpace1 = "https://www.smartadmission.com/Student#";
	static String defaultNameSpace2 = "https://www.smartadmission.com/University#";
	Model studentOnt = null;
	Model universityOnt = null;
	//InfModel inferredFriends = null;
	
	/////////////////////////////////////////////// Enter here
/////////////////////////////////////////////// Enter here
/////////////////////////////////////////////// Enter here
/////////////////////////////////////////////// Enter here
/////////////////////////////////////////////// Enter here
/////////////////////////////////////////////// Enter here
	String name="gNeekhara";   //////////////////////-----------------------    enter the name here for person
	String applied="MTECH";    ////// --------    applied     
	RDFNode graduationMarks, gateMarks,XMarks,XIIMarks, appliedFor;
	RDFNode uName,departmentName,HOD,criteria1,criteria2;

	public static void main(String[] args) {
		// get Student Qualifications
		inference in=new inference();
		
		System.out.println("Load my student Ontology");
		in.populateStudent();
		System.out.println("\nget student qualifications");
		in.getStudentQualifications(in.studentOnt);  
		
		
		// get University criteria
		System.out.println("Load my University Ontology");
		in.populateUniversity();
		System.out.println("get University criteria");
		in.getUniversityCriteria(in.universityOnt);
	}
	private void getUniversityCriteria(Model universityOnt) {
		// TODO Auto-generated method stub
		
		// Establish Prefixes
		//Set default Name space first
		StringBuffer queryStr1 = new StringBuffer();
		String queryStr2,queryStr3;
		queryStr1.append("PREFIX ut" + ": <" + defaultNameSpace2 + "> ");
		queryStr1.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr1.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr1.append("PREFIX owl" + ": <" + "http://www.w3.org/2002/07/owl#" + "> ");
		queryStr1.append("PREFIX xsd" + ": <" + "http://www.w3.org/2001/XMLSchema#" + "> ");
		String queryRequest="SELECT ?univ 	WHERE { ?univ ut:hasUName ?o . }";
		//Now add query
		queryStr2=queryStr1.toString();
		queryStr1.append(queryRequest);
		Query query = QueryFactory.create(queryStr1.toString());
		
		QueryExecution qexec = QueryExecutionFactory.create(query, universityOnt);
		try {
		ResultSet response = qexec.execSelect();
		
		while( response.hasNext()){
			queryStr3=queryStr2;
			
			QuerySolution soln = response.nextSolution();
						 uName = soln.get("?univ");
						 //System.out.println(uName.toString());
						 String x="<"+uName.toString().trim()+">";
						 
						 queryStr3+="SELECT ?UName ?departmentName ?HOD	?criteria1 ?criteria2 WHERE { "+ x +" ut:hasDepartment ?department . ?department ut:hasCourse ?c . ?c ut:hasCName ?kk. filter(str(?kk)="+"'"+applied+"'"+"). "+x+" ut:hasUName ?UName.?department ut:hasHOD ?HOD.?department ut:hasDName ?departmentName.?c ut:hasCriteria1 ?criteria1.?c ut:hasCriteria2 ?criteria2}";
						 //System.out.println(queryStr3);
						 Query query1=QueryFactory.create(queryStr3);
						 QueryExecution qexec1=QueryExecutionFactory.create(query1, universityOnt);
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
						  					if(studentGateMarks>=universityGateMarks)
						  			System.out.println(uName.toString() +" "+departmentName.toString()+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString());
										else
											System.out.println(uName.toString() +" "+departmentName.toString()+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString() +"ur gate marks less try next year for this college");
						
						  				}

						  }
						  else{
						  	int universityXMarks=Integer.parseInt(criteria1.toString());
						  	int universityXIIMarks=Integer.parseInt(criteria2.toString());
						  	int studentXMarks=Integer.parseInt(XMarks.toString());
					  		int studentXIIMarks=Integer.parseInt(XIIMarks.toString());	
					  		if(studentXMarks >= universityXMarks && studentXIIMarks>=universityXIIMarks)
					  		System.out.println(uName.toString() +" "+departmentName.toString()+" "+HOD.toString()+" "+criteria1.toString()+" "+criteria2.toString());	

						  }
						 
						 
			}
		} finally { qexec.close();}	


	}


	private void runQueryStudent(String queryRequest, Model model){
		
		StringBuffer queryStr = new StringBuffer();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX st" + ": <" + defaultNameSpace1 + "> ");
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
			 appliedFor=soln.get("?appliedFor");
			
			System.out.println(appliedFor.toString()+" "+XMarks.toString()+ " " + XIIMarks.toString()+" " + graduationMarks.toString() +" " + gateMarks.toString());
						
			}
		} finally { qexec.close();}				
		}
	private void getStudentQualifications(Model studentOnt) {
		
		runQueryStudent(" SELECT ?appliedFor ?XMarks ?XIIMarks ?graduationMarks ?gateMarks WHERE { ?dh st:hasName ?kk. filter(str(?kk)="+"'"+name+"'"+"). ?dh st:hasQualification ?quali .?dh st:appliedFor ?appliedFor. ?quali st:consistOfGateMarks ?gateMarks . ?quali st:consistOfGraduationMarks ?graduationMarks .?quali st:consistOfXMarks ?XMarks .?quali st:consistOfXIIMarks ?XIIMarks   }", studentOnt);
	
	}
	private void populateStudent(){
		studentOnt = ModelFactory.createOntologyModel();
		InputStream inFoafInstance = FileManager.get().open("C://Users//shub//workspace//smartAdmiss//Ontologies//Student_Instance.owl");
		studentOnt.read(inFoafInstance,defaultNameSpace1);
		//inFoafInstance.close();

	}
	private void populateUniversity() {
		// TODO Auto-generated method stub
		universityOnt = ModelFactory.createOntologyModel();
		InputStream inFoafInstance = FileManager.get().open("C://Users//shub//workspace//smartAdmiss//Ontologies//University_Instance.owl");
		universityOnt.read(inFoafInstance,defaultNameSpace2);
		//inFoafInstance.close();
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
	
	
}
