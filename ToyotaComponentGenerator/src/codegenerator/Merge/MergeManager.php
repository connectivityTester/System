<?php

include "Merger.php";

class MergeManager
{
	private $mClassMerger;

	public  function __construct( Merger $iClassMerger )
	{
		$this->mClassMerger = $iClassMerger;
	}

	public function mergeClasses( $iEntityArray, $iMergeParameter )
	{
		Logger::log("MergeManager","Merging classes...");
		if(count($iEntityArray) == 1)
		{
			return array($iEntityArray[0]);
		}
		return $this->mClassMerger->merge( $iEntityArray[0], $iEntityArray[1], $iMergeParameter );		 
	}
}