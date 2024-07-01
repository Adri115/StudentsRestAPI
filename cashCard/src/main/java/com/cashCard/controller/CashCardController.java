package com.cashCard.controller;

import com.cashCard.CashCard;
import com.cashCard.repository.CashCardRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.Optional;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cashcards")
public class CashCardController {

	 private final CashCardRepository cashCardRepository;

	   private CashCardController(CashCardRepository cashCardRepository) {
	      this.cashCardRepository = cashCardRepository;
	   }
	   
	@GetMapping("/{requestedId}")
	private ResponseEntity<CashCard> findById(@PathVariable Long requestedId){
		Optional<CashCard> cashCardOptional = cashCardRepository.findById(requestedId);
	    if (cashCardOptional.isPresent()) {
	        return ResponseEntity.ok(cashCardOptional.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@PostMapping
	private ResponseEntity<CashCard> createCashCard(@RequestBody CashCard cashCard, UriComponentsBuilder ucb){
		CashCard createdCard = cashCardRepository.save(cashCard);
		URI locationOfNewCard = ucb.path("cashcards/{id}").buildAndExpand(createdCard.id()).toUri();
		return ResponseEntity.created(locationOfNewCard).build();
	}
}
