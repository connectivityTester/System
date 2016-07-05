<?php

include "/src/CodeGenerator/Utils/Logger.php";
include "/src/CodeGenerator/Utils/ScriptParameters.php";
include "/src/CodeGenerator/Loader.php";
include "/src/CodeGenerator/Twig/TwigLoader.php";
include "/src/CodeGenerator/Utils/Formatters/CPPFormatedOutputsProcessor.php";
include "/src/CodeGenerator/Merge/MergeManager.php";
include "/src/CodeGenerator/Merge/ClassMerger.php";

Logger::log("Main", "=== Generator started ===");

$ScriptParameters = ScriptParameters::fromArgv( __DIR__ );

// Getting mapping configuration array from configuration file
$mappingsConfiguration = Loader::LoadData($ScriptParameters->getMappingFilename(), Loader::TYPE_MAPPING);

// Getting configurations array from configuration files
$classesConfigurationArray = array();
foreach ($ScriptParameters->getConfigurationFilenames() as $key => $filePath) 
{
	Logger::log("Main","Reading configuration #" . $key . " \"" . $filePath . "\"");
	$classesConfigurationArray[$key] = Loader::LoadData( $filePath, Loader::TYPE_CLASSES );
}

Logger::log("Main","Merging started");

$mergeManager = new MergeManager(new ClassMerger());
$mergedClassConfiguration = $mergeManager->mergeClasses($classesConfigurationArray, $ScriptParameters->getMergeParameter());

Logger::log("Main","Merging finished");

// Preparing templating engine
$Twig = TwigLoader::Load( $ScriptParameters->getTemplatesDirectories());

// Preparing generator processor
$OProcessor = new CPPFormatedOutputsProcessor( $Twig, $ScriptParameters->getOutputDirectory(), $mappingsConfiguration->GetGenericMappings() );

// Generating outputs
foreach ($mergedClassConfiguration as $key => $classConfiguration) 
{
	$OProcessor->GenerateOutputs( $mappingsConfiguration->GetOutputs(), $classConfiguration->GetEntities() );
}

Logger::log("Main","=== Generator finished ===");
