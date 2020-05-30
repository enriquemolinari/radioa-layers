package ar.cpfw.book.radio;

import org.junit.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
//import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import com.tngtech.archunit.library.Architectures;

public class TestLayers {

	@Test
	public void testLayers() {

		JavaClasses importedClasses = new ClassFileImporter()
				.importPackages("ar.cpfw.book.radio");

		Architectures
			.layeredArchitecture()
			.layer("UI").definedBy("..ui..")
			.layer("BusinessLogic").definedBy("..model..")
			.layer("Persistence").definedBy("..persistence..")
			.whereLayer("UI").mayNotBeAccessedByAnyLayer()
 			.whereLayer("BusinessLogic").mayOnlyBeAccessedByLayers("UI")
			.whereLayer("Persistence").mayOnlyBeAccessedByLayers("BusinessLogic")
			.check(importedClasses);
	}
}
