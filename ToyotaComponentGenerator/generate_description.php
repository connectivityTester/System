<?php

include "/src/DescriptionGenerator/ScriptParameters.php";
include "/src/DescriptionGenerator/Parsers/HBSIParser.php";
include "/src/DescriptionGenerator/Parsers/LOGICHBSIParser.php";
include "/src/DescriptionGenerator/Parsers/IMPLHBSIParser.php";
include "/src/DescriptionGenerator/Parsers/CommandEnumParser.php";
include "/src/DescriptionGenerator/Parsers/CommandSpecificHBSIParser.php";
include "/src/DescriptionGenerator/Descriptions/IMPLDescription.php";
include "/src/DescriptionGenerator/Descriptions/LOGICDescription.php";
include "/src/DescriptionGenerator/Descriptions/ComponentDescription.php";
include "/src/DescriptionGenerator/Descriptions/OnOffDescription.php";
include "/src/DescriptionGenerator/Descriptions/ClientDescription.php";
include "/src/DescriptionGenerator/Descriptions/CommandEnumDescription.php";
include "/src/DescriptionGenerator/Descriptions/CommandSpecificDescription.php";
include "/src/DescriptionGenerator/MappingGenerator.php";


$scriptParams = ScriptParameters::fromArgv( __DIR__ . "\\");

if(!file_exists($scriptParams->getOutConfigFolder() . "/configurations"))
{
	if (!mkdir($scriptParams->getOutConfigFolder() . "/configurations", 0777, true)) 
	{
	    throw new \Exception("Can not create directory ". $scriptParams->getOutConfigFolder() . "/configurations" );
	}
}
if(!file_exists($scriptParams->getOutConfigFolder() . "/mappings"))
{
	if (!mkdir($scriptParams->getOutConfigFolder() . "/mappings", 0777, true)) 
	{
	    throw new \Exception("Can not create directory ". $scriptParams->getOutConfigFolder() . "/mappings" );
	}
}

$hbsiFileContent = file_get_contents($scriptParams->getHBSIFilePath());
$mappingGenerator = new MappingGenerator($scriptParams);

if(!empty($scriptParams->getEntities()['templ_class']))
{
	$commandSpecificParser = new CommandSpecificHBSIParser($hbsiFileContent, $scriptParams);
	$commandSpecificDescription =CommandSpecificDescription::getDescription($scriptParams, $commandSpecificParser);
	$commandSpecificMappings = $mappingGenerator->getIMPLMappings($scriptParams->getEntities()['command_specific_logic']['file_name'], "\private\Commands", "\private\Commands", true, ScriptParameters::COMMAND_SPECIFIC_LOGIC);
	
	file_put_contents($scriptParams->getOutConfigFolder() . "/configurations/" . $scriptParams->getEntities()['command_specific_logic']['file_name'] . ".yml", descriptionToOutputString($commandSpecificDescription));
	file_put_contents($scriptParams->getOutConfigFolder() . "/mappings/" . $scriptParams->getEntities()['command_specific_logic']['file_name'] . ".yml", $commandSpecificMappings);
}


if(!empty($scriptParams->getEntities()['commands']))
{
	$commandEnumParser = new CommandEnumParser($hbsiFileContent, $scriptParams);
	$commandEnumDescription = CommandEnumDescrtiption::getDescription($scriptParams, $commandEnumParser);
	$commandEnumMappings = $mappingGenerator->getIMPLMappings($scriptParams->getEntities()['commands']['file_name'], "\private", "\private", false, ScriptParameters::COMMANDS);
	
	file_put_contents($scriptParams->getOutConfigFolder() . "/configurations/" . $scriptParams->getEntities()['commands']['file_name'] . ".yml", descriptionToOutputString($commandEnumDescription));
	file_put_contents($scriptParams->getOutConfigFolder() . "/mappings/" . $scriptParams->getEntities()['commands']['file_name'] . ".yml", $commandEnumMappings);
}

if(!empty($scriptParams->getEntities()['client']))
{
	$clientDescription =  ClientDescription::getDescription($scriptParams);
	$clientMappings = $mappingGenerator->getIMPLMappings($scriptParams->getEntities()['client'], "\private", "\private", true, ScriptParameters::CLIENT);

	file_put_contents($scriptParams->getOutConfigFolder() . "/configurations/" . $scriptParams->getEntities()["client"] . ".yml", descriptionToOutputString($clientDescription));
	file_put_contents($scriptParams->getOutConfigFolder() . "/mappings/" . $scriptParams->getEntities()["client"] . ".yml", $clientMappings);
}


if(!empty($scriptParams->getEntities()['onoff']))
{
	$onoffDescription =  OnOffDescription::getDescription($scriptParams);
	$onoffMappings = $mappingGenerator->getIMPLMappings($scriptParams->getEntities()['onoff'], "\private", "\private", true, ScriptParameters::ONOFF);

	file_put_contents($scriptParams->getOutConfigFolder() . "/configurations/" . $scriptParams->getEntities()["onoff"] . ".yml", descriptionToOutputString($onoffDescription));
	file_put_contents($scriptParams->getOutConfigFolder() . "/mappings/" . $scriptParams->getEntities()["onoff"] . ".yml", $onoffMappings);
}

if(!empty($scriptParams->getEntities()['component']))
{
	$componentDescription =  ComponentDescription::getDescription($scriptParams);
	$componentMappings = $mappingGenerator->getIMPLMappings($scriptParams->getEntities()['component'], "", "\private", true, ScriptParameters::COMPONENT);

	file_put_contents($scriptParams->getOutConfigFolder() . "/configurations/" . $scriptParams->getEntities()["component"] . ".yml", descriptionToOutputString($componentDescription));
	file_put_contents($scriptParams->getOutConfigFolder() . "/mappings/" . $scriptParams->getEntities()["component"] . ".yml", $componentMappings);
}

if(!empty($scriptParams->getEntities()['logic']))
{
	$logicHbsiParser = new LOGICHBSIParser($hbsiFileContent, $scriptParams);
	$logicDescriptions =  LOGICDescription::getDescription($logicHbsiParser, $scriptParams);
	$implMappings = $mappingGenerator->getIMPLMappings($scriptParams->getEntities()['logic'], "\private", "\private", true, ScriptParameters::LOGIC);

	file_put_contents($scriptParams->getOutConfigFolder() . "/configurations/" . $scriptParams->getEntities()["logic"] . ".yml", descriptionToOutputString($logicDescriptions));
	file_put_contents($scriptParams->getOutConfigFolder() . "/mappings/" . $scriptParams->getEntities()["logic"] . ".yml", $implMappings);
}

if(!empty($scriptParams->getEntities()['impl']))
{
	$implHbsiParser = new IMPLHBSIParser($hbsiFileContent, $scriptParams);
	$implDescriptions =  IMPLDescription::getDescription($implHbsiParser, $scriptParams);
	$implMappings = $mappingGenerator->getIMPLMappings($scriptParams->getEntities()['impl'], "\private", "\private", true, ScriptParameters::IMPL);

	file_put_contents($scriptParams->getOutConfigFolder() . "/configurations/" . $scriptParams->getEntities()["impl"] . ".yml", descriptionToOutputString($implDescriptions));
	file_put_contents($scriptParams->getOutConfigFolder() . "/mappings/" . $scriptParams->getEntities()["impl"] . ".yml", $implMappings);
}

$files = scandir($scriptParams->getOutConfigFolder() . "/configurations");

foreach ($files as $key => $file) 
{
	$path_parts = pathinfo($file);
     if ( strtolower($path_parts['extension']) == "yml")
     {
     	exec ("php generate_code.php " .
     		" --config==" . $scriptParams->getOutConfigFolder() . "/configurations/" . $file . 
     		" --output==output/component --templates=templates/cpp ".
     		" --mapping==" . $scriptParams->getOutConfigFolder() . "/mappings/" . $file . 
     		" --merge==as_single_entities",
     		$logs);
     	echo implode("\n", $logs);
     }
}






function descriptionToOutputString($description)
{
	$description = yaml_emit($description);

	$description = str_replace(HBSIParser::STR_TO_REPLACE, "", $description);
	$description = str_replace("'\"", "\"", $description);
	$description = str_replace("\"'", "\"", $description);
	$description = substr($description, 4, strlen($description) - 10);

	return $description;
}
