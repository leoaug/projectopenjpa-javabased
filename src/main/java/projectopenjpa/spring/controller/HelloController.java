package projectopenjpa.spring.controller;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import bossanovadata.constants.Constants;
import bossanovadata.model.persistence.query.BOSSAQuery;
import projectopenjpa.entity.Author;
import projectopenjpa.entity.Music;
import projectopenjpa.entity.Type;
import projectopenjpa.service.AuthorService;

@Controller
public class HelloController {

	@Autowired
	private AuthorService authorService;


	@GetMapping("/")
	public String initialize() {
			
		try {
		
			Author author = new Author();
			author.setName("Tom jobim");
			author.setMusic(new Music());
			author.getMusic().setDescription("Garota de Ipanema");
			author.getMusic().setType(new Type());
			author.getMusic().getType().setDescription("Bossa Nova");
			
			authorService.findByEntityList(author);
		
			
			
			SimpleDateFormat sdfFrom = new SimpleDateFormat("dd/MM/yyyy");   
			Date dateFrom = sdfFrom.parse("01/01/1981"); 
			
			SimpleDateFormat sdfTo = new SimpleDateFormat("dd/MM/yyyy");   
			Date dateTo = sdfTo.parse("01/01/2017"); 
		
			
			BOSSAQuery<Author> query = authorService.initialize();
			
			
			
			query.addQuery(query.groupFilterAND(query.addFilter("dateOfBirth", new Date[]{dateFrom,dateTo}, Constants.OPERATION_BETWEEN),
								 				query.addFilter("music.type.description", "Bossa Nova", Constants.OPERATION_EQUAL)
						  )
			);
			
			authorService.findByFilterList(query);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "welcome"; 
	}
	
	@GetMapping("/hello")
	public String hello(Model model) {

		model.addAttribute("name", "John Doe");

		return "welcome";
	}
}
