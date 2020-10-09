package radio;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

import org.junit.jupiter.api.Test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;

public class TestArchitecture {

 private static final String PKG_UI = "radio.ui";
 private static final String PKG_MODEL_API = "radio.model.api";
 private static final String PKG_MODEL_IMPL = "radio.model";
 private static final String PKG_PERSISTENCE_API = "radio.persistence.api";
 private static final String PKG_PERSISTENCE_IMPL = "radio.persistence";
 private static final String ROOT_PACKAGE = "radio";

 @Test
 public void layersAreValid() {
  layeredArchitecture()
    .layer("UI").definedBy(PKG_UI)
    .layer("BusinessLogicAPI").definedBy(PKG_MODEL_API)
    .layer("BusinessLogicImpl").definedBy(PKG_MODEL_IMPL)
    .layer("PersistenceAPI").definedBy(PKG_PERSISTENCE_API)
    .layer("PersistenceImpl").definedBy(PKG_PERSISTENCE_IMPL)
    .whereLayer("UI").mayNotBeAccessedByAnyLayer()
    .whereLayer("BusinessLogicImpl").mayNotBeAccessedByAnyLayer()
    .whereLayer("BusinessLogicAPI").mayOnlyBeAccessedByLayers("UI", "BusinessLogicImpl")
    .whereLayer("PersistenceAPI").mayOnlyBeAccessedByLayers("BusinessLogicImpl", "PersistenceImpl")
    .whereLayer("PersistenceImpl").mayNotBeAccessedByAnyLayer()
    .check(new ClassFileImporter().importPackages(ROOT_PACKAGE));
 }

 @Test
 public void uiShouldOnlyDependOnModelApiOrJdk() {
  JavaClasses jc = new ClassFileImporter().importPackages(ROOT_PACKAGE);

  ArchRule r1 = classes().that().resideInAPackage(PKG_UI).should()
    .onlyDependOnClassesThat()
    .resideInAnyPackage(PKG_UI, PKG_MODEL_API, "java..", "javax..");
  r1.check(jc);
 }

 @Test
 public void modelAPIShouldNotDependOnOtherPackages() {
  JavaClasses jc = new ClassFileImporter().importPackages(ROOT_PACKAGE);

  ArchRule r1 = classes().that().resideInAPackage(PKG_MODEL_API)
    .should().onlyDependOnClassesThat()
    .resideInAnyPackage(PKG_MODEL_API, "java..");
  r1.check(jc);
 }

 @Test
 public void modelShouldOnlyDependOnModelOrJdk() {
  JavaClasses jc = new ClassFileImporter().importPackages(ROOT_PACKAGE);

  ArchRule r1 = classes().that().resideInAPackage(PKG_MODEL_IMPL).should()
    .onlyDependOnClassesThat().resideInAnyPackage(PKG_MODEL_IMPL,
      PKG_MODEL_API, PKG_PERSISTENCE_API, "java..");
  r1.check(jc);
 }

 @Test
 public void persistenceAPIShouldNotDependOnOtherPackages() {
  JavaClasses jc = new ClassFileImporter().importPackages(ROOT_PACKAGE);

  ArchRule r1 = classes().that().resideInAPackage(PKG_PERSISTENCE_API)
    .should().onlyDependOnClassesThat()
    .resideInAnyPackage(PKG_PERSISTENCE_API, "java..");
  r1.check(jc);
 }

 
 @Test
 public void persistenceShouldOnlyDependOnPersistenceOrJdk() {
  JavaClasses jc = new ClassFileImporter().importPackages(ROOT_PACKAGE);

  ArchRule r1 = classes().that().resideInAPackage(PKG_PERSISTENCE_IMPL)
    .should().onlyDependOnClassesThat()
    .resideInAnyPackage(PKG_PERSISTENCE_IMPL, PKG_PERSISTENCE_API, "java..");
  r1.check(jc);
 } 
}
