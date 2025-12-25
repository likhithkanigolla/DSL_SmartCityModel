/*
 * BACnet HVAC DSL Code Generator Launcher
 * 
 * Usage: java SmartCityModelGeneratorLauncher <input.smartcitymodel> <outputDir>
 * Example: java SmartCityModelGeneratorLauncher example.smartcitymodel src-gen
 */
package iiit.ac.in.smartcitymodel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtext.generator.GeneratorContext;
import org.eclipse.xtext.generator.GeneratorDelegate;
import org.eclipse.xtext.generator.JavaIoFileSystemAccess;
import org.eclipse.xtext.util.CancelIndicator;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;

public class SmartCityModelGeneratorLauncher {

	@Inject
	private Provider<ResourceSet> resourceSetProvider;

	@Inject
	private GeneratorDelegate generator;

	@Inject
	private JavaIoFileSystemAccess fileAccess;

	public static void main(String[] args) {
		if (args.length != 2) {
			System.err.println("Usage: SmartCityModelGeneratorLauncher <input.smartcitymodel> <outputDir>");
			System.err.println("Example: SmartCityModelGeneratorLauncher example.smartcitymodel src-gen");
			System.exit(1);
		}

		// Initialize Xtext standalone setup
		Injector injector = new SmartCityModelStandaloneSetup().createInjectorAndDoEMFRegistration();
		SmartCityModelGeneratorLauncher launcher = injector.getInstance(SmartCityModelGeneratorLauncher.class);

		// Run the generator
		launcher.runGenerator(args[0], args[1]);
	}

	protected void runGenerator(String inputFile, String outputPath) {
		// Load the resource
		ResourceSet set = resourceSetProvider.get();
		
		File inputFileObj = new File(inputFile);
		if (!inputFileObj.exists()) {
			System.err.println("Error: Input file does not exist: " + inputFile);
			System.exit(1);
		}
		
		URI uri = URI.createFileURI(inputFileObj.getAbsolutePath());
		Resource resource = set.getResource(uri, true);

		// Configure output
		fileAccess.setOutputPath(outputPath);
		
		// Create output directory if it doesn't exist
		File outputDir = new File(outputPath);
		if (!outputDir.exists()) {
			outputDir.mkdirs();
			System.out.println("Created output directory: " + outputPath);
		}

		// Generate code
		System.out.println("Generating code from: " + inputFile);
		System.out.println("Output directory: " + outputPath);
		
		GeneratorContext context = new GeneratorContext();
		context.setCancelIndicator(CancelIndicator.NullImpl);
		
		generator.generate(resource, fileAccess, context);
		
		System.out.println("Code generation completed successfully!");
		System.out.println("\nGenerated files:");
		printGeneratedFiles(outputDir, "");
	}
	
	private void printGeneratedFiles(File dir, String indent) {
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		
		for (File file : files) {
			if (file.isDirectory()) {
				System.out.println(indent + file.getName() + "/");
				printGeneratedFiles(file, indent + "  ");
			} else {
				System.out.println(indent + file.getName());
			}
		}
	}
}
