package org.springframework.samples.petclinic.chat;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;
import org.springframework.samples.petclinic.vet.Vets;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class VetTools {

	private final VetRepository vetRepository;

	public VetTools(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	@Tool(value = { "return list of Vets, include their specialist" })
	public Collection<Vet> getVetList() {
		return vetRepository.findAll();
	}

}
