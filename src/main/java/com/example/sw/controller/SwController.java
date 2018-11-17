package com.example.sw.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import java.io.ByteArrayOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
@CrossOrigin(maxAge = 3600)
@RestController
public class SwController {
	static String serviceEndPoint = "http://localhost:3030/dataset1/query";
	JSONObject json = new JSONObject();
	@RequestMapping(method = RequestMethod.GET, value = "getSW")
	public @ResponseBody Map<String, Object> getDisplayData(HttpServletRequest request){
		StringBuffer queryStr = buildQueryString();
		json = loadClasses(serviceEndPoint, queryStr.toString());
		System.out.println(json);
		return json.toMap();
	}
	
	private static StringBuffer buildQueryString() {
		// TODO Auto-generated method stub
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("PREFIX rdfs" + ": <" +  
                "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX ev: <http://www.w3.org/2001/xml-events/> ");
		queryStr.append("PREFIX owl: <http://www.w3.org/2002/07/owl#> ");
		queryStr.append("prefix event:<http://www.semanticweb.org/project/team10/event#> ");
		queryStr.append("prefix event2:<http://www.semanticweb.org/project/team10/cameoEvents#> ");
		queryStr.append("SELECT ?desc (COUNT(?code) AS ?code_count) WHERE { ?code event2:hasCameoCode ?desc.} GROUP BY ?desc ORDER BY DESC(?code_count) LIMIT 5");
//		System.out.println(queryStr);

		return queryStr;
	}
	
	private JSONObject loadClasses(String uri, String query) {
		QueryExecution q = QueryExecutionFactory.sparqlService(uri, query);
		ResultSet res = q.execSelect();
//		System.out.println(res);
	
//		ResultSetFormatter.out(System.out, res);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ResultSetFormatter.outputAsJSON(outputStream, res);
//		System.out.println(outputStream);
//		String text = ResultSetFormatter.asText(res);
//		System.out.println(text);
//		// and turn that into a String
//		String json = new String(outputStream.toByteArray());
		JSONObject testV=new JSONObject(new String(outputStream.toByteArray()));

//		System.out.println(testV);

//		System.out.println(json);
		return testV;
	}
}
