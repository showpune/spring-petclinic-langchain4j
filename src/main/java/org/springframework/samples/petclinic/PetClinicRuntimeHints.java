package org.springframework.samples.petclinic;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.samples.petclinic.model.BaseEntity;
import org.springframework.samples.petclinic.model.Person;
import org.springframework.samples.petclinic.vet.Vet;

/*
 * This class is to provide hints to the runtime environment about specific resources and classes, particularly for
 * environments where reflection, resource access, and serialization are restricted, such as in AOT compilation scenarios.
 */
public class PetClinicRuntimeHints implements RuntimeHintsRegistrar {

	@Override
	public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
		// https://github.com/spring-projects/spring-boot/issues/32654
		hints.resources().registerPattern("db/*");
		hints.resources().registerPattern("messages/*");
		hints.resources().registerPattern("META-INF/resources/webjars/*");
		hints.resources().registerPattern("mysql-default-conf");
		hints.serialization().registerType(BaseEntity.class);
		hints.serialization().registerType(Person.class);
		hints.serialization().registerType(Vet.class);
	}

}
