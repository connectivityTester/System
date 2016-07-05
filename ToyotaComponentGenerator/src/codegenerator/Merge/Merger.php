<?php 

abstract class Merger
{
	protected abstract function merge( $firstEntity, $secondEntity, $iMergeParameter );
}