package radio;

import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import org.junit.Test;

import com.tngtech.archunit.core.importer.ClassFileImporter;

public class TestLayers {

	@Test
	public void testLayers() {
		layeredArchitecture()
			.layer("UI").definedBy("..ui..")
			.layer("BusinessLogic").definedBy("..model..")
			.layer("Persistence").definedBy("..persistence..")
			.whereLayer("UI").mayNotBeAccessedByAnyLayer()
 			.whereLayer("BusinessLogic").mayOnlyBeAccessedByLayers("UI")
			.whereLayer("Persistence").mayOnlyBeAccessedByLayers("BusinessLogic")
			.check(new ClassFileImporter()
	    .importPackages("radio"));
	}
}
