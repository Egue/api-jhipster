package com.comunicamosmas.api;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.comunicamosmas.api");

        noClasses()
            .that()
            .resideInAnyPackage("com.comunicamosmas.api.service..")
            .or()
            .resideInAnyPackage("com.comunicamosmas.api.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.comunicamosmas.api.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
