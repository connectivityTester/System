<?php

class ClassMerger extends Merger
{
	public function merge( $firstEntity, $secondEntity, $iMergeParameter )
	{
		if($iMergeParameter == ScriptParameters::MERGETOONE)
		{
			return self::mergeAsOneEntity( $firstEntity, $secondEntity );
		}
		else
		{	
			return self::mergeAsSingleEntities( $firstEntity, $secondEntity );
		}
	}

	private function mergeAsSingleEntities( $firstEntity, $secondEntity )
	{
		return array($firstEntity, $secondEntity);
	}

	private function mergeAsOneEntity($firstEntity, $secondEntity)
	{
		$entityName = array_keys($firstEntity->GetEntities())[0];
		$iType = $secondEntity->GetEntities()[$entityName]->getType();
		$iNamespace = new NamespaceM( array_merge( $firstEntity->GetEntities()[$entityName]->getNamespace()->asArray(), $secondEntity->GetEntities()[$entityName]->getNamespace()->asArray() ) );
		$iName = $firstEntity->GetEntities()[$entityName]->getName() . $secondEntity->GetEntities()[$entityName]->getName();
		$iVisibility = $secondEntity->GetEntities()[$entityName]->getVisibility();
		$iIsAbstract = $secondEntity->GetEntities()[$entityName]->getIsAbstract();
		$iParents = $firstEntity->GetEntities()[$entityName]->getParents();
		$tempParentLogic = $iParents['Logic'];
		foreach ($secondEntity->GetEntities()[$entityName]->getParents()['Logic'] as $key => $parent) 
		{
			array_push($tempParentLogic, $parent);
		}
		$tempParentInterfaces = $iParents['Interfaces'];
		foreach ($secondEntity->GetEntities()[$entityName]->getParents()['Interfaces'] as $key => $parent) 
		{
			array_push($tempParentInterfaces, $parent);
		}
		$iParents['Logic'] = $tempParentLogic;
		$iParents['Interfaces'] = $tempParentInterfaces;
		$iVariables = array_merge( $firstEntity->GetEntities()[$entityName]->getVariables(), $secondEntity->GetEntities()[$entityName]->getVariables() );
		$iMethods = array_merge( $firstEntity->GetEntities()[$entityName]->getMethods(), $secondEntity->GetEntities()[$entityName]->getMethods() );
		$iConstant = $secondEntity->GetEntities()[$entityName]->getIsConstant();
		$iTypeParametrs = $secondEntity->GetEntities()[$entityName]->getTypeParametrs();
		$newEntity = new EntityM( $iType, $iNamespace, $iName, $iVisibility, $iIsAbstract, $iParents, $iVariables, $iMethods, $iConstant, $iTypeParametrs );

		return array(new ClassesConfiguration (array($entityName => $newEntity), false));
	}
}