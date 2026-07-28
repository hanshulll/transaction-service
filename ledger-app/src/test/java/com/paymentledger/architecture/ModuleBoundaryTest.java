package com.paymentledger.architecture;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

/**
 * Enforces the module boundary rule from ARCHITECTURE.md: a module's application layer
 * may only reach another module through its ports, never through that module's
 * infrastructure package directly.
 */
class ModuleBoundaryTest {

	private static JavaClasses importedClasses;

	@BeforeAll
	static void importClasses() {
		importedClasses = new ClassFileImporter()
				.withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
				.importPackages("com.paymentledger");
	}

	@Test
	void transactionApplicationShouldNotDependOnAccountInfrastructure() {
		ArchRule rule = noClasses()
				.that().resideInAPackage("com.paymentledger.transaction.application..")
				.should().dependOnClassesThat().resideInAPackage("com.paymentledger.account.infrastructure..");

		rule.check(importedClasses);
	}

	@Test
	void accountApplicationShouldNotDependOnTransactionInfrastructure() {
		ArchRule rule = noClasses()
				.that().resideInAPackage("com.paymentledger.account.application..")
				.should().dependOnClassesThat().resideInAPackage("com.paymentledger.transaction.infrastructure..");

		rule.check(importedClasses);
	}
}
